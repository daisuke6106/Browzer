package jp.co.dk.browzer;

import java.util.List;

import jp.co.dk.browzer.html.element.A;

public abstract class MoveScenario {
	
	/** シナリオ */
	protected MoveScenario childScenario;
	
	/** 移動時に実行するアクション */
	protected MoveAction moveAction;
	
	public MoveScenario(MoveAction moveAction) {
		this.moveAction = moveAction;
	}
	
	public MoveScenario(MoveScenario childScenario, MoveAction moveAction) {
		this.childScenario = childScenario;
		this.moveAction    = moveAction;
	}
	
	MoveAction getAction() {
		return this.moveAction;
	}
	
	boolean hasChildScenario() {
		return childScenario != null;
	}
	
	MoveScenario getChildScenario() {
		return this.childScenario;
	}
	
	abstract List<A> getMoveAnchor(Browzer browzer);
}
