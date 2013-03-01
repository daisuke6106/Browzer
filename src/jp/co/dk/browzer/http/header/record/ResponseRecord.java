package jp.co.dk.browzer.http.header.record;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.message.BrowzingMessage;

/**
 * ResponseRecordは、HTTP通信にて使用されるHTTPヘッダに含まれるレスポンスレコードを表すクラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class ResponseRecord {
	
	private Pattern pattern = Pattern.compile("HTTP/(.*?) +(.*?) +(.*?)");
	
	private String responseRecord;
	
	private String httpVersion;
	
	private String httpStatusCodeStr;
	
	private String responsePhrase;
	
	private HttpStatusCode httpStatusCode;
	
	/**
	 * コンストラクタ<br/>
	 * 
	 * URLコネクションより取得したヘッダーフィールドを元にレスポンスレコードインスタンスを生成する。
	 * 
	 * @param headerMap
	 * @throws BrowzingException
	 */
	public ResponseRecord(Map<String, List<String>> headerMap) throws BrowzingException {
		List<String> responseRecordList = headerMap.get(null);
		if (responseRecordList == null || responseRecordList.size() != 1) {
			throw new BrowzingException(BrowzingMessage.ERROR_RECORD_RESPONSE_COULD_NOT_BE_OBTAINED);
		}
		this.responseRecord = responseRecordList.get(0);
		Matcher matcher = pattern.matcher(this.responseRecord);
		if (matcher.find()) {
			this.httpVersion = matcher.group(1);
			this.httpStatusCodeStr = matcher.group(2);
			this.responsePhrase = matcher.group(3);
		} else {
			throw new BrowzingException(BrowzingMessage.ERROR_RECORD_RESPONSE_IS_NOT_A_PRESCRIBED_FORMAT, this.responseRecord);
		}
		
		for (HttpStatusCode httpStatusCode : HttpStatusCode.values()) {
			if (httpStatusCode.isCode(this.httpStatusCodeStr)) {
				this.httpStatusCode = httpStatusCode;
			}
		}
		
		if (this.httpStatusCode == null) {
			throw new BrowzingException(BrowzingMessage.ERROR_HTTP_STATUS_CODE_IS_NOT_SPECIFIED_IN_THE_CODE_VALUE, this.httpStatusCodeStr);
		}
	}
	
	/**
	 * レスポンスレコードに設定されたHTTPバージョンを取得する。
	 * 
	 * @return HTTPバージョン
	 */
	public String getHttpVersion() {
		return this.httpVersion;
	}
	
	/**
	 * レスポンスレコードに設定されたHTTPステータスコードを取得する。
	 * 
	 * @return HTTPステータスコード
	 */
	public HttpStatusCode getHttpStatusCode() {
		return this.httpStatusCode;
	}
	
	/**
	 * レスポンスレコードに設定された応答フレーズを取得する。
	 * 
	 * @return 応用フレーズ
	 */
	public String getResponsePhrase() {
		return this.responsePhrase;
	}
	
	
}