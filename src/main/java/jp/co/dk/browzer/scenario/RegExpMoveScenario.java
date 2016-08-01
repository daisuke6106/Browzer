package jp.co.dk.browzer.scenario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.exception.MoveActionException;
import jp.co.dk.browzer.exception.MoveActionFatalException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.MovableElement;
import jp.co.dk.browzer.scenario.action.MoveAction;
import jp.co.dk.document.exception.DocumentException;

public class RegExpMoveScenario extends MoveScenario {
	
	protected String urlPatternStr;
	
	protected Pattern urlPattern;
	
	protected Set<String> visitedUrl = new HashSet<>();
	
	public RegExpMoveScenario(String urlPatternStr, Pattern urlPattern, List<MoveAction> moveActionList) {
		super(moveActionList);
		this.urlPatternStr = urlPatternStr;
		this.urlPattern    = urlPattern;
	}
	
	@Override
	public List<A> getMoveAnchor(Browzer browzer) throws PageAccessException, DocumentException {
		return browzer.getAnchor(this.urlPattern);
	}
	
	public String getUrlPattern() {
		return this.urlPatternStr;
	}
	
	@Override
	public MoveControl beforeAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {
		super.beforeAction(movable, browzer);
		if (this.visitedUrl.contains(movable.getUrl())) return MoveControl.Interruption;
		return MoveControl.Continuation;
	}
	
	@Override
	public void afterAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {
		this.visitedUrl.add(movable.getUrl());
		super.afterAction(movable, browzer);
	}
	
	@Override
	public void errorAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {
		this.visitedUrl.add(movable.getUrl());
		super.errorAction(movable, browzer);
	}
	
	@Override
	public String toString() {
		return this.urlPatternStr;
	}
}
