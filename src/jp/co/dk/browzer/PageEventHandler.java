package jp.co.dk.browzer;

import jp.co.dk.document.html.constant.HtmlRequestMethodName;

/**
 * PageEventHandlerは単一のページにおける各イベント発生した際の、アクションを定義するクラスです。<p/>
 * 
 * @version 1.0
 * @author D.Kanno
 */
public interface PageEventHandler {
	
	/**
	 * コネクション生成前に呼び出されるイベント
	 * @param urlObj 接続先URL
	 * @param method 接続時メソッド
	 */
	void beforeOpenConnection(Url urlObj, HtmlRequestMethodName method);
	
	/**
	 * コネクション生成終了後に呼び出されるイベント
	 */
	void afterOpenConnection();
	
	/**
	 * 接続先のデータを取得する際に実行されるイベント
	 */
	void beforeGetData(Page page);
	
	/**
	 * 接続先のデータを取得した後に実行されるイベント
	 */
	void afterGetData(Page page);
	
	/**
	 * 接続先のデータをドキュメントクラスのオブジェクトに変換する際に実行されるイベント
	 */
	void beforeCreateDocument(Page page);
	
	/**
	 * 接続先のデータをドキュメントクラスのオブジェクトに変換した後に実行されるイベント
	 */
	void afterCreateDocument(Page page);
}