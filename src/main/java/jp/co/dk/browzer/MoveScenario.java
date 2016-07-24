package jp.co.dk.browzer;

import java.util.List;

import jp.co.dk.browzer.html.element.A;

public abstract class MoveScenario {
	
	/** 移動時に実行するアクション */
	protected MoveAction moveAction;
	
	public MoveScenario(MoveAction moveAction) {
		this.moveAction = moveAction;
	}
	
	MoveAction action() {
		return this.moveAction;
	}
	
	abstract List<A> getMoveAnchor(Browzer browzer);
}
