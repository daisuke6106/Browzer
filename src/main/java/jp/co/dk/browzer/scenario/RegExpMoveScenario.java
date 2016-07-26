package jp.co.dk.browzer.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.exception.MoveActionFatalException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.scenario.action.MoveAction;
import jp.co.dk.document.exception.DocumentException;

public class RegExpMoveScenario extends MoveScenario {
	
	protected Pattern urlPattern;
	
	public RegExpMoveScenario(Pattern urlPattern, List<MoveAction> moveActionList) {
		super(moveActionList);
		this.urlPattern = urlPattern;
	}
	
	public List<A> getMoveAnchor(Browzer browzer) throws PageAccessException, DocumentException {
		return browzer.getAnchor(this.urlPattern);
	}

	private static Pattern commandPattern = Pattern.compile("^(.+)\\{(.+)\\}$");
	
	public static RegExpMoveScenario create(String command) {
		String[] commandList = command.split("->");
		RegExpMoveScenario beforeScenario = null;
		for (int i=commandList.length-1; i!=0; i--) {
			RegExpMoveScenario scenario = createScenario(commandList[i]);
			if (beforeScenario != null) scenario.setChildScenario(beforeScenario);
			beforeScenario = scenario;
		}
		return beforeScenario;
	}

	protected static RegExpMoveScenario createScenario(String command) throws MoveActionFatalException {
		Matcher matcher = commandPattern.matcher(command);
		if (matcher.find()) {
			String urlPatternStr = matcher.group(1);
			if (urlPatternStr == null || urlPatternStr.equals("")) throw new MoveActionFatalException(null);
			String actionStr     = matcher.group(2);
			if (actionStr     == null || actionStr.equals("")) throw new MoveActionFatalException(null);
			Pattern urlPattern;
			try {
				urlPattern = Pattern.compile(urlPatternStr);
			} catch (PatternSyntaxException e) {
				throw new MoveActionFatalException(null);
			}
			List<MoveAction> moveActionList = new ArrayList<>();
			String[] actionClassList = actionStr.split(";");
			for (String actionClass : actionClassList) {
				moveActionList.add(createMoveActionByClassName(actionClass));
			}
			
			return new RegExpMoveScenario(urlPattern, moveActionList);
		} else {
			throw new MoveActionFatalException(null);
		}
	}
	
	protected static MoveAction createMoveActionByClassName(String className) {
		MoveAction moveAction;
		try {
			Class<MoveAction> actionClass = (Class<MoveAction>) Class.forName(className);
			moveAction = actionClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new MoveActionFatalException(null);
		}
		return moveAction;
	}
}
