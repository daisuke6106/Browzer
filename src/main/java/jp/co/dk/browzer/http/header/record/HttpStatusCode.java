package jp.co.dk.browzer.http.header.record;

import jp.co.dk.browzer.message.BrowzingMessage;
import jp.co.dk.message.MessageInterface;

/**
 * HttpStatusCodeは、HTTP通信にて使用されるHTTPレスポンスヘッダのステータスコードを定義する定数クラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public enum HttpStatusCode {
	
	/** HTTPステータスコード：100 クライアントにリクエストを継続することを指示します。 */
	STATUS_100("100", HttpStatusType.INFOMATIONAL, BrowzingMessage.HTTP_STATUS_CODE_100),
	
	/** HTTPステータスコード：101 上位バージョンのHTTPでのリクエストを要求します。 */
	STATUS_101("101", HttpStatusType.INFOMATIONAL, BrowzingMessage.HTTP_STATUS_CODE_101),
	
	/** HTTPステータスコード：200 リクエストは成功しました。 */
	STATUS_200("200", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_200),
	
	/** HTTPステータスコード：201 リクエスト成功/異なる場所にリソースが作成されました。 */
	STATUS_201("201", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_201),
	
	/** HTTPステータスコード：202 要求は受け付けられましたが処理は未完了です。 */
	STATUS_202("202", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_202),
	
	/** HTTPステータスコード：203 リクエスト成功/返される結果はオリジナルのものでありません。 */
	STATUS_203("203", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_203),
	
	/** HTTPステータスコード：204 リクエスト成功/返すべきリソースはありません。 */
	STATUS_204("204", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_204),
	
	/** HTTPステータスコード：204 リクエスト成功/返すべきリソースはありません。 */
	STATUS_205("205", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_205),
	
	/** HTTPステータスコード：206 リクエスト成功/部分的な内容を返します。 */
	STATUS_206("206", HttpStatusType.SUCCESS, BrowzingMessage.HTTP_STATUS_CODE_206),
	
	/** HTTPステータスコード：300 複数の場所に置かれたリソースが利用可能です。 */
	STATUS_300("300", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_300),
	
	/** HTTPステータスコード：301 他の場所に移転しました。 */
	STATUS_301("301", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_301),
	
	/** HTTPステータスコード：302 一時的に他の場所に移転しました。 */
	STATUS_302("302", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_302),
	
	/** HTTPステータスコード：303 他の場所を参照してください。 */
	STATUS_303("303", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_303),
	
	/** HTTPステータスコード：303 他の場所を参照してください。 */
	STATUS_304("304", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_304),
	
	/** HTTPステータスコード：305 特定のプロキシ経由でのみ要求を受け付けます。 */
	STATUS_305("305", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_305),
	
	/** HTTPステータスコード：307 要求されたリソースは一時的に異なるアドレスに置かれています。 */
	STATUS_307("307", HttpStatusType.REDIRECTION, BrowzingMessage.HTTP_STATUS_CODE_307),
	
	/** HTTPステータスコード：400 リクエストの書式にエラーがあります。 */
	STATUS_400("400", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_400),
	
	/** HTTPステータスコード：401 ユーザー認証が必要です。 */
	STATUS_401("401", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_401),
	
	/** HTTPステータスコード：402 メッセージ未設定　(将来拡張用)。 */
	STATUS_402("402", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_402),
	
	/** HTTPステータスコード：403 アクセス権限がありません。 */
	STATUS_403("403", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_403),
	
	/** HTTPステータスコード：404 要求されたリソースは存在しません。 */
	STATUS_404("404", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_404),
	
	/** HTTPステータスコード：405 許可されていない要求メソッドです。 */
	STATUS_405("405", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_405),
	
	/** HTTPステータスコード：406 受け入れられないリソースです。 */
	STATUS_406("406", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_406),
	
	/** HTTPステータスコード：407 プロキシ認証が必要です。 */
	STATUS_407("407", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_407),
	
	/** HTTPステータスコード：408 時間内にリクエストが完了しませんでした。 */
	STATUS_408("408", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_408),
	
	/** HTTPステータスコード：409 リソースに対するリクエストに矛盾があります。 */
	STATUS_409("409", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_409),
	
	/** HTTPステータスコード：410 要求されたリソースは現在はサーバーに存在しません(移転先不明)。 */
	STATUS_410("410", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_410),
	
	/** HTTPステータスコード：411 Content-Lengthに満たない長さのリクエストです。 */
	STATUS_411("411", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_411),
	
	/** HTTPステータスコード：412 リクエストヘッダの条件が満たされませんでした。 */
	STATUS_412("412", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_412),
	
	/** HTTPステータスコード：413 許容長さを超えるリクエストエンティティが含まれています。 */
	STATUS_413("413", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_413),
	
	/** HTTPステータスコード：414 要求されたアドレスが長すぎます。 */
	STATUS_414("414", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_414),
	
	/** HTTPステータスコード：415 サーバーでサポートされていないメディア形式を要求しました。 */
	STATUS_415("415", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_415),
	
	/** HTTPステータスコード：416 If-Rangeリクエストヘッダーで指定された範囲が無効です。 */
	STATUS_416("416", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_416),
	
	/** HTTPステータスコード：417 Expectリクエストヘッダフィールド拡張が合致しません。 */
	STATUS_417("417", HttpStatusType.CLIENT_ERROR, BrowzingMessage.HTTP_STATUS_CODE_417),
	
	/** HTTPステータスコード：500 リクエスト処理中に予期せぬエラーが発生しました。 */
	STATUS_500("500", HttpStatusType.SERVER_ERROR, BrowzingMessage.HTTP_STATUS_CODE_500),
	
	/** HTTPステータスコード：501 サーバーはリクエストを処理する機能を有していません。 */
	STATUS_501("501", HttpStatusType.SERVER_ERROR, BrowzingMessage.HTTP_STATUS_CODE_501),
	
	/** HTTPステータスコード：502 上位サーバーから無効な応答を受けました。 */
	STATUS_502("502", HttpStatusType.SERVER_ERROR, BrowzingMessage.HTTP_STATUS_CODE_502),
	
	/** HTTPステータスコード：503 過負荷またはメンテナンス中のため要求を処理できません。 */
	STATUS_503("503", HttpStatusType.SERVER_ERROR, BrowzingMessage.HTTP_STATUS_CODE_503),
	
	/** HTTPステータスコード：504 上位サーバーとの間でタイムアウトが発生しました。 */
	STATUS_504("504", HttpStatusType.SERVER_ERROR, BrowzingMessage.HTTP_STATUS_CODE_504),
	
	/** HTTPステータスコード：505 サポートされていないバージョンのHTTPです。 */
	STATUS_505("505", HttpStatusType.SERVER_ERROR, BrowzingMessage.HTTP_STATUS_CODE_505),
	;
	
	private String code;
	
	private HttpStatusType type;
	
	private MessageInterface message;
	
	private HttpStatusCode(String code, HttpStatusType type, MessageInterface message) {
		this.code = code;
		this.type = type;
		this.message = message;
	}
	
	/**
	 * HTTPステータスコードを返却します。
	 * 
	 * @return HTTPステータスコード
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * 指定されたHTTPステータスコードがこのステータスコードと一致するか判定します。
	 * 
	 * @param code 判定対象のコード
	 * @return 判定結果（true=コードが一致、false=コードが不一致）
	 */
	public boolean isCode(String code) {
		return this.code.equals(code);
	}
	
	/**
	 * このHTTPステータスコードの種別を取得します。
	 * 
	 * @return HTTPステータスコード種別
	 */
	public HttpStatusType getStatusType() {
		return this.type;
	}
	
	/**
	 * このHTTPステータスコードに該当するメッセージを取得します。
	 * 
	 * @return メッセージオブジェクト
	 */
	public MessageInterface getMessage() {
		return this.message;
	}
}
