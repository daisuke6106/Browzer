package jp.co.dk.browzer;

import jp.co.dk.browzer.html.element.MovableElement;

/**
 * MoveActionは、ページ移動時に処理を行う際に実装するインターフェースです。
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
	 */
	public default void before(MovableElement movable, Browzer browzer){}
	
	/**
	 * ページ移動後に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動後のブラウザオブジェクト
	 */
	public default void after(MovableElement movable, Browzer browzer){}
	
	/**
	 * ページ移動時にエラーが発生した場合に行う処理
	 * 
	 * @param movable 移動先が記載された要素
	 * @param browzer 移動後のブラウザオブジェクト
	 */
	public default void error(MovableElement movable, Browzer browzer){}
}
