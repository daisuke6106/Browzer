package jp.co.dk.browzer.scenario;

import java.util.List;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.exception.MoveActionException;
import jp.co.dk.browzer.exception.MoveActionFatalException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.MovableElement;
import jp.co.dk.browzer.scenario.action.MoveAction;
import jp.co.dk.document.exception.DocumentException;

public abstract class MoveScenario {
	
	/** 親シナリオ */
	protected MoveScenario parentScenario;
	
	/** 子シナリオ */
	protected MoveScenario childScenario;
	
	/** 移動時に実行するアクション */
	protected List<MoveAction> moveActionList;
	
	public MoveScenario(List<MoveAction> moveActionList) {
		this.moveActionList = moveActionList;
	}
	
	public boolean hasChildScenario() {
		return childScenario != null;
	}
	
	public MoveScenario getChildScenario() {
		return this.childScenario;
	}
	
	public void setChildScenario(MoveScenario scenario) {
		this.childScenario = scenario;
	}
	
	public boolean hasParentScenario() {
		return parentScenario != null;
	}
	
	public MoveScenario getParentScenario() {
		return this.parentScenario;
	}
	
	public void setParentScenario(MoveScenario scenario) {
		this.parentScenario = scenario;
	}
	
	public MoveScenario getTopScenario() {
		MoveScenario topScenario = this;
		while (topScenario.hasParentScenario()) topScenario = topScenario.getParentScenario();
		return topScenario;
	}
	
	/**
	 * ページ移動前に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動前のブラウザオブジェクト
	 * @throws MoveActionException 再起可能例外が発生した場合
	 * @throws MoveActionFatalException 致命的例外が発生した場合
	 */
	public MoveControl beforeAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {
		for(MoveAction moveAction : moveActionList) moveAction.beforeAction(movable, browzer);
		return MoveControl.Continuation;
	}
	
	/**
	 * ページ移動後に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動後のブラウザオブジェクト
	 * @throws MoveActionException 再起可能例外が発生した場合
	 * @throws MoveActionFatalException 致命的例外が発生した場合
	 */
	public void afterAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {
		for(MoveAction moveAction : moveActionList) moveAction.afterAction(movable, browzer);
	}
	
	/**
	 * ページ移動時にエラーが発生した場合に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動後のブラウザオブジェクト
	 * @throws MoveActionException 再起可能例外が発生した場合
	 * @throws MoveActionFatalException 致命的例外が発生した場合
	 */
	public void errorAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {
		for(MoveAction moveAction : moveActionList) moveAction.errorAction(movable, browzer);
	}
	
	public abstract List<A> getMoveAnchor(Browzer browzer) throws PageAccessException, DocumentException;
	
	@Override
	public abstract String toString();
}
