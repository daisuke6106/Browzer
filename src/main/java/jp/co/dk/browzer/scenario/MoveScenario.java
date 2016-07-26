package jp.co.dk.browzer.scenario;

import java.util.List;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.scenario.action.MoveAction;
import jp.co.dk.document.exception.DocumentException;

public abstract class MoveScenario {
	
	/** シナリオ */
	protected MoveScenario childScenario;
	
	/** 移動時に実行するアクション */
	protected List<MoveAction> moveActionList;
	
	public MoveScenario(List<MoveAction> moveActionList) {
		this.moveActionList = moveActionList;
	}
	
	public List<MoveAction> getActionList() {
		return this.moveActionList;
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
	
	public abstract List<A> getMoveAnchor(Browzer browzer) throws PageAccessException, DocumentException;
}
