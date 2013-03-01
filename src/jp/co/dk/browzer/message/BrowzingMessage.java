package jp.co.dk.browzer.message;

import jp.co.dk.message.AbstractMessage;

/**
 * BrowzingMessageは、ブラウザ操作にて使用するメッセージを定義する定数クラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class BrowzingMessage extends AbstractMessage {
	
	/** アクセスを行うページに認証を要求されました。ユーザ、パスワードを入力してください。 */
	public static final BrowzingMessage INFO_PLEASE_INPUT_USER_AND_PASSWORD = new BrowzingMessage("I001");
	
	/** ユーザ＝　*/
	public static final BrowzingMessage INFO_USER = new BrowzingMessage("I002");
	
	/** パスワード＝　*/
	public static final BrowzingMessage INFO_PASSWORD = new BrowzingMessage("I003");
	
	/** URLが設定されていません。 */
	public static final BrowzingMessage ERROR_URL_IS_NOT_SET = new BrowzingMessage("E001");
	
	/** 指定されたURLのプロトコルが不明です。URL=[{0}] */
	public static final BrowzingMessage ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN = new BrowzingMessage("E002");
	
	/** URLが参照するリモートオブジェクトへの接続時に入出力例外が発生しました。URL=[{0}] */
	public static final BrowzingMessage ERROR_INPUT_OUTPUT_EXCEPTION_OCCURRED_WHEN_CONNECTING_TO_A_URL = new BrowzingMessage("E003");
	
	/** 入力ストリームの作成中に入出力エラーが発生しました。 */
	public static final BrowzingMessage ERROR_IO_ERROR_WAS_OCCURRED_WHILE_CREATING_THE_INPUT_STREAM = new BrowzingMessage("E004");
	
	/** HTMLの解析に失敗しました。*/
	public static final BrowzingMessage ERROR_FAILED_TO_PARSE_HTML = new BrowzingMessage("E005");
	
	/** XMLの解析に失敗しました。*/
	public static final BrowzingMessage ERROR_FAILED_TO_PARSE_XML = new BrowzingMessage("E006");
	
	/** ドキュメントインスタンスの生成に失敗しました。class=[{0}]*/
	public static final BrowzingMessage ERROR_FAILED_TO_CREATE_DOCUMENT_INSTANCE = new BrowzingMessage("E007");
	
	/** 指定のURLの拡張子はサポートしていません。URL=[{0}]*/
	public static final BrowzingMessage ERROR_NON_SUPPORTED_EXTENSION = new BrowzingMessage("E008");
	
	/** 指定のディレクトリはすでに存在します。PATH=[{0}]*/
	public static final BrowzingMessage ERROR_THE_SPECIFIED_DIRECTORY_ALREADY_EXISTS = new BrowzingMessage("E009");
	
	/** 指定のディレクトリの作成に失敗しました。PATH=[{0}]*/
	public static final BrowzingMessage ERROR_FAILED_TO_CREATE_THE_SPECIFIED_DIRECTORY = new BrowzingMessage("E010");
	
	/** ダウンロードディレクトリが存在しません。PATH=[{0}] */
	public static final BrowzingMessage ERROR_DOWNLOAD_DIR_IS_NOT_FOUND = new BrowzingMessage("E011");
	
	/** 指定のパスはディレクトリではありません。PATH=[{0}] */
	public static final BrowzingMessage ERROR_SPECIFIED_PATH_IS_NOT_DIRECTORY = new BrowzingMessage("E012");
	
	/** 指定のパスにすでにファイルが存在します。PATH=[{0}] */
	public static final BrowzingMessage ERROR_FILE_APLREADY_EXISTS_IN_THE_SPECIFIED_PATH = new BrowzingMessage("E013");
	
	/** ダウンロードに失敗しました。URL=[{0}] */
	public static final BrowzingMessage ERROR_FAILED_TO_DOWNLOAD = new BrowzingMessage("E014");
	
	/** レスポンスレコードが取得できませんでした。 */
	public static final BrowzingMessage ERROR_RECORD_RESPONSE_COULD_NOT_BE_OBTAINED = new BrowzingMessage("E015");
	
	/** レスポンスレコードが規定のフォーマットではありません。レスポンスヘッダ=[{0}] */
	public static final BrowzingMessage ERROR_RECORD_RESPONSE_IS_NOT_A_PRESCRIBED_FORMAT = new BrowzingMessage("E016");
	
	/** HTTPステータスコードが規定のコード値ではありません。HTTPステータスコード=[{0}] */
	public static final BrowzingMessage ERROR_HTTP_STATUS_CODE_IS_NOT_SPECIFIED_IN_THE_CODE_VALUE = new BrowzingMessage("E017");
	
	/** サーバより返信されたHTTPステータスコードが正常値以外を返却しました。HTTPステータスコード=[{0}],詳細=[{1}],URL=[{2}] */
	public static final BrowzingMessage ERROR_HTTP_STATUS_CODE_IS_SENT_BACK_FROM_SERVER_HAS_RETURNED_NON_NORMAL = new BrowzingMessage("E018");
	
	/** 指定されたアンカーにはHREF要素が設定されていません。 */
	public static final BrowzingMessage ERROR_SPECIFIED_ANCHOR_HAS_NOT_URL = new BrowzingMessage("E019");
	
	/** 指定のメソッドはサポートしていません。METHOD=[{0}] */
	public static final BrowzingMessage ERROR_SPECIFIED_METHOD_IS_NOT_SUPPORTED = new BrowzingMessage("E020");
	
	/** 不正なURLが指定されました。URI=[{0}] */
	public static final BrowzingMessage ERROR_AN_INVALID_URL_WAS_SPECIFIED = new BrowzingMessage("E021");
	
	/** メッセージの送信に失敗しました。URL=[{0}],METHOD=[{1}] */
	public static final BrowzingMessage ERROR_FAILED_TO_SEND_MESSAGE = new BrowzingMessage("E022");
	
	/** FORMが指定されていません。*/
	public static final BrowzingMessage ERROR_FORM_IS_NOT_SPECIFIED = new BrowzingMessage("E023");
	
	/** 遷移先のアンカーが指定されていません。 */
	public static final BrowzingMessage ERROR_SPECIFIED_ANCHOR_IS_NOT_SET = new BrowzingMessage("E024");
	
	/** ダウンロードディレクトリを作成できませんでした。PATH=[{0}] */
	public static final BrowzingMessage ERROR_COULD_NOT_CREATE_DOWNLOAD_DIRECTORY = new BrowzingMessage("E025");
	
	/** 指定のアンカーにURLが設定されていません。 */
	public static final BrowzingMessage ERROR_ANCHOR_HAS_NOT_URL = new BrowzingMessage("E026");
	
	/** ページが設定されていません。 */
	public static final BrowzingMessage ERROR_PAGE_IS_NOT_SET = new BrowzingMessage("E027");
	
	/** 検索対象のファイル名が設定されていません。 */
	public static final BrowzingMessage ERROR_SEARCH_TARGET_FILE_NAME_IS_NOT_SET = new BrowzingMessage("E028");
	
	/** 指定されたアンカーは現在アクティブになっているページには存在しません。 */
	public static final BrowzingMessage ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE = new BrowzingMessage("E029");
	
	/** このページはHTMLではないためアンカーを取得することはできません。 */
	public static final BrowzingMessage ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML = new BrowzingMessage("E030");
	
	/** 遷移先のアンカーが指定されていません。 */
	public static final BrowzingMessage ERROR_SPECIFIED_FORM_IS_NOT_SET = new BrowzingMessage("E031");
	
	/** 指定されたFORMは現在アクティブになっているページには存在しません。 */
	public static final BrowzingMessage ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE = new BrowzingMessage("E032");
	
	/** URL補完に失敗しました。補完元URL=[{0}] 補完対象URL=[{1}] */
	public static final BrowzingMessage ERROR_COMPRETION_URL = new BrowzingMessage("E033");
	
	/** スレッド停止に失敗しました。 */
	public static final BrowzingMessage ERROR_THREAD_STOP = new BrowzingMessage("E034");

	/** リダイレクト先のURLが設定されていません。 */
	public static final BrowzingMessage ERROR_REDIRECT_LOCATION_NOT_FOUND = new BrowzingMessage("E035");
	
	/** 最大遷移レベルに達しました。LEVEL=[{0}] */
	public static final BrowzingMessage ERROR_REACHED_TO_THE_MAXIMUM_LEVEL = new BrowzingMessage("E036");
	
	/** 入力処理にて異常が発生しました。 */
	public static final BrowzingMessage ERROR_READ_PROCESS_FAILED = new BrowzingMessage("E037");
	
	/** HTTPステータスコード：100 クライアントにリクエストを継続することを指示します。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_100 = new BrowzingMessage("STATUS100");
	
	/** HTTPステータスコード：101 上位バージョンのHTTPでのリクエストを要求します。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_101 = new BrowzingMessage("STATUS101");
	
	/** HTTPステータスコード：200 リクエストは成功しました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_200 = new BrowzingMessage("STATUS200");
	
	/** HTTPステータスコード：201 リクエスト成功/異なる場所にリソースが作成されました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_201 = new BrowzingMessage("STATUS201");
	
	/** HTTPステータスコード：202 要求は受け付けられましたが処理は未完了です。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_202 = new BrowzingMessage("STATUS202");
	
	/** HTTPステータスコード：203 リクエスト成功/返される結果はオリジナルのものでありません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_203 = new BrowzingMessage("STATUS203");
	
	/** HTTPステータスコード：204 リクエスト成功/返すべきリソースはありません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_204 = new BrowzingMessage("STATUS204");
	
	/** HTTPステータスコード：205 リクエスト成功/クライアントにリセットを要求します。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_205 = new BrowzingMessage("STATUS205");
	
	/** HTTPステータスコード：206 リクエスト成功/部分的な内容を返します。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_206 = new BrowzingMessage("STATUS206");
	
	/** HTTPステータスコード：300 複数の場所に置かれたリソースが利用可能です。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_300 = new BrowzingMessage("STATUS300");
	
	/** HTTPステータスコード：301 他の場所に移転しました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_301 = new BrowzingMessage("STATUS301");
	
	/** HTTPステータスコード：302 一時的に他の場所に移転しました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_302 = new BrowzingMessage("STATUS302");
	
	/** HTTPステータスコード：303 他の場所を参照してください。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_303 = new BrowzingMessage("STATUS303");
	
	/** HTTPステータスコード：304 要求されたリソースに変更はありません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_304 = new BrowzingMessage("STATUS304");
	
	/** HTTPステータスコード：305 特定のプロキシ経由でのみ要求を受け付けます。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_305 = new BrowzingMessage("STATUS305");
	
	/** HTTPステータスコード：307 要求されたリソースは一時的に異なるアドレスに置かれています。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_307 = new BrowzingMessage("STATUS307");
	
	/** HTTPステータスコード：400 リクエストの書式にエラーがあります。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_400 = new BrowzingMessage("STATUS400");
	
	/** HTTPステータスコード：401 ユーザー認証が必要です。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_401 = new BrowzingMessage("STATUS401");
	
	/** HTTPステータスコード：402 メッセージ未設定　(将来拡張用)。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_402 = new BrowzingMessage("STATUS402");
	
	/** HTTPステータスコード：403 アクセス権限がありません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_403 = new BrowzingMessage("STATUS403");

	/** HTTPステータスコード：404 要求されたリソースは存在しません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_404 = new BrowzingMessage("STATUS404");
	
	/** HTTPステータスコード：405 許可されていない要求メソッドです。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_405 = new BrowzingMessage("STATUS405");
	
	/** HTTPステータスコード：406 受け入れられないリソースです。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_406 = new BrowzingMessage("STATUS406");
	
	/** HTTPステータスコード：407 プロキシ認証が必要です。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_407 = new BrowzingMessage("STATUS407");
	
	/** HTTPステータスコード：408 時間内にリクエストが完了しませんでした。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_408 = new BrowzingMessage("STATUS408");
	
	/** HTTPステータスコード：409 リソースに対するリクエストに矛盾があります。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_409 = new BrowzingMessage("STATUS409");
	
	/** HTTPステータスコード：410 要求されたリソースは現在はサーバーに存在しません(移転先不明)。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_410 = new BrowzingMessage("STATUS410");
	
	/** HTTPステータスコード：411 Content-Lengthに満たない長さのリクエストです。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_411 = new BrowzingMessage("STATUS411");
	
	/** HTTPステータスコード：412 リクエストヘッダの条件が満たされませんでした。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_412 = new BrowzingMessage("STATUS412");
	
	/** HTTPステータスコード：413 許容長さを超えるリクエストエンティティが含まれています。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_413 = new BrowzingMessage("STATUS413");
	
	/** HTTPステータスコード：414 要求されたアドレスが長すぎます。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_414 = new BrowzingMessage("STATUS414");
	
	/** HTTPステータスコード：415 サーバーでサポートされていないメディア形式を要求しました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_415 = new BrowzingMessage("STATUS415");
	
	/** HTTPステータスコード：416 If-Rangeリクエストヘッダーで指定された範囲が無効です。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_416 = new BrowzingMessage("STATUS416");
	
	/** HTTPステータスコード：417 Expectリクエストヘッダフィールド拡張が合致しません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_417 = new BrowzingMessage("STATUS417");
	
	/** HTTPステータスコード：500 リクエスト処理中に予期せぬエラーが発生しました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_500 = new BrowzingMessage("STATUS500");
	
	/** HTTPステータスコード：501 サーバーはリクエストを処理する機能を有していません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_501 = new BrowzingMessage("STATUS501");
	
	/** HTTPステータスコード：502 上位サーバーから無効な応答を受けました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_502 = new BrowzingMessage("STATUS502");
	
	/** HTTPステータスコード：503 過負荷またはメンテナンス中のため要求を処理できません。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_503 = new BrowzingMessage("STATUS503");
	
	/** HTTPステータスコード：504 上位サーバーとの間でタイムアウトが発生しました。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_504 = new BrowzingMessage("STATUS504");
	
	/** HTTPステータスコード：505 サポートされていないバージョンのHTTPです。 */
	public static final BrowzingMessage HTTP_STATUS_CODE_505 = new BrowzingMessage("STATUS505");
	
	protected BrowzingMessage(String messageId) {
		super(messageId);
	}

}
