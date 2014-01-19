package jp.co.dk.browzer;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.http.header.ResponseHeader;
import jp.co.dk.document.html.constant.HtmlRequestMethodName;

/**
 * PageEventHandlerは単一のページにおける各イベント発生した際の、アクションを定義するクラスです。<p/>
 * 
 * @version 1.0
 * @author D.Kanno
 */
public interface PageEventHandler {
	// ==================================================[PageRedirectHandlerクラス関連イベント]==================================================
	/**
	 * リダイレクトハンドラが実施される前に実行されるイベント
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 */
	void beforeRedirect(ResponseHeader header, Page page);
	
	/**
	 * リダイレクトハンドラが実施された後に実行されるイベント
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 */
	void afterRedirect();
	
	// ==================================================[PageManagerクラス関連イベント]==================================================
	/**
	 * ページ遷移前に呼び出されるイベント
	 * @param pageManager 遷移元ページマネージャ
	 * @param url 遷移先URL 
	 */
	void beforeMove(PageManager pageManager, String url);
	
	/**
	 * ページ遷移後に呼び出されるイベント
	 */
	void afterMove();
	
	// ==================================================[Pageクラス関連イベント]==================================================
	/**
	 * コネクション生成前に呼び出されるイベント
	 * @param urlObj 接続先URL
	 * @param method 接続時メソッド
	 */
	void beforeOpenConnection(Url urlObj, HtmlRequestMethodName method);
	
	/**
	 * コネクション生成中にエラーが発生した場合、呼び出されるイベント
	 * @param browzingException 生成された例外オブジェクト
	 */
	void errorOpenConnection(BrowzingException browzingException);
	
	/**
	 * コネクション生成終了後に呼び出されるイベント
	 */
	void afterOpenConnection();
	
	/**
	 * 接続先のデータを取得する際に実行されるイベント
	 * @param page ページオブジェクト
	 */
	void beforeGetData(Page page);
	
	/**
	 * 接続先のデータを取得する際にエラーが発生した場合、呼び出されるイベント
	 * @param browzingException 生成された例外オブジェクト
	 */
	void errorGetData(BrowzingException browzingException);
	
	/**
	 * 接続先のデータを取得した後に実行されるイベント
	 * @param page ページオブジェクト
	 */
	void afterGetData(Page page);
	
	/**
	 * 接続先のデータをドキュメントクラスのオブジェクトに変換する際に実行されるイベント
	 * @param page ページオブジェクト
	 */
	void beforeCreateDocument(Page page);
	
	/**
	 * 接続先のデータをドキュメントクラスのオブジェクトに変換する際にエラーが発生した場合、呼び出されるイベント
	 * @param browzingException 生成された例外オブジェクト
	 */
	void errorCreateDocument(BrowzingException browzingException);
	
	/**
	 * 接続先のデータをドキュメントクラスのオブジェクトに変換した後に実行されるイベント
	 * @param page ページオブジェクト
	 */
	void afterCreateDocument(Page page);
}