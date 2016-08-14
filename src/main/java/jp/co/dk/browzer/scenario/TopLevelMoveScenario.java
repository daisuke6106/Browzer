package jp.co.dk.browzer.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.exception.MoveActionException;
import jp.co.dk.browzer.exception.MoveActionFatalException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageMovableLimitException;
import jp.co.dk.browzer.exception.PageRedirectException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.MovableElement;
import jp.co.dk.browzer.scenario.action.MoveAction;
import jp.co.dk.document.exception.DocumentException;

public abstract class TopLevelMoveScenario extends MoveScenario {
	
	public TopLevelMoveScenario(String scenario) {
		createScenarios(scenario);
	}
	
	public TopLevelMoveScenario(List<MoveAction> moveActionList) {
		super(moveActionList);
	}

	private static Pattern commandPattern = Pattern.compile("^(.+)@(.+)$");
	
	private static Pattern actionPattern  = Pattern.compile("^(.+)\\((.*)\\)$");
	
	public MoveScenario createScenarios(String command) {
		String[] commandList = command.split("->");
		MoveScenario beforeScenario = null;
		for (int i=commandList.length-1; i>=0; i--) {
			MoveScenario scenario = createScenario(commandList[i]);
			if (beforeScenario != null) {
				scenario.setChildScenario(beforeScenario);
			}
			beforeScenario = scenario;
		}
		return beforeScenario;
	}

	protected MoveScenario createScenario(String command) throws MoveActionFatalException {
		Matcher matcher = this.commandPattern.matcher(command);
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
			String[] actionList = actionStr.split(";");
			for (String action : actionList) {
				Matcher actionMatcher = this.actionPattern.matcher(action);
				if (actionMatcher.find()) {
					String   commandStr   = actionMatcher.group(1);
					String   argumentsStr = actionMatcher.group(2);
					String[] argiments    = argumentsStr.split(",");
					if ("none".equals(commandStr)) {
						moveActionList.add(createNoneMoveAction(argiments));
					} else if ("print".equals(commandStr)) {
						moveActionList.add(createPrintMoveAction(argiments));
					} else if ("save".equals(commandStr)) {
						moveActionList.add(createSaveMoveAction(argiments));
					} else {
						moveActionList.add(createMoveActionByClassName(commandStr, argiments));
					}
				} else {
					if ("none".equals(action)) {
						moveActionList.add(createNoneMoveAction(new String[]{}));
					} else if ("print".equals(action)) {
						moveActionList.add(createPrintMoveAction(new String[]{}));
					} else if ("save".equals(action)) {
						moveActionList.add(createSaveMoveAction(new String[]{}));
					} else {
						moveActionList.add(createMoveActionByClassName(action, new String[]{}));
					}
				}
				
			}
			
			return new MoveScenario(urlPatternStr, urlPattern, moveActionList);
		} else {
			throw new MoveActionFatalException(null);
		}
	}
	
	public abstract Browzer createBrowzer(String url) throws PageIllegalArgumentException, PageAccessException;

	@SuppressWarnings("all")
	protected MoveAction createMoveActionByClassName(String className, String[] arguments) {
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
