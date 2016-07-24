package jp.co.dk.browzer.scenario.action;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.exception.MoveActionException;
import jp.co.dk.browzer.exception.MoveActionFatalException;
import jp.co.dk.browzer.html.element.MovableElement;

/**
 * MoveActionは、ページ移動時に追加処理を行う際に実装するインターフェースです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public interface MoveAction {
	
	/**
	 * ページ移動前に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動前のブラウザオブジェクト
	 * @throws MoveActionException 再起可能例外が発生した場合
	 * @throws MoveActionFatalException 致命的例外が発生した場合
	 */
	public default void beforeAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {}
	
	/**
	 * ページ移動後に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動後のブラウザオブジェクト
	 * @throws MoveActionException 再起可能例外が発生した場合
	 * @throws MoveActionFatalException 致命的例外が発生した場合
	 */
	public default void afterAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {}
	
	/**
	 * ページ移動時にエラーが発生した場合に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動後のブラウザオブジェクト
	 * @throws MoveActionException 再起可能例外が発生した場合
	 * @throws MoveActionFatalException 致命的例外が発生した場合
	 */
	public default void errorAction(MovableElement movable, Browzer browzer) throws MoveActionException, MoveActionFatalException {}
}
