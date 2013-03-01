package jp.co.dk.browzer.http.header.record;

/**
 * HttpStatusTypeは、HTTPヘッダに内包されるHTTPステータスコードの種別を定義する定数クラス。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public enum HttpStatusType {
	
	/** 情報 */
	INFOMATIONAL,
	
	/** 成功 */
	SUCCESS,
	
	/** リダイレクション */
	REDIRECTION,
	
	/** クライアントエラー */
	CLIENT_ERROR,
	
	/** サーバエラー */
	SERVER_ERROR,
	;
}
