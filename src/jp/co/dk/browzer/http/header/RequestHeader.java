package jp.co.dk.browzer.http.header;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jp.co.dk.browzer.exception.PageHeaderImproperException;

/**
 * RequestHeaderは、HTTP通信にて使用されるHTTPヘッダを表すクラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class RequestHeader implements Serializable{

	/** シリアルバージョンID */
	private static final long serialVersionUID = 5547244168178351392L;
	
	private Map<String, String> headerMap;
	
	/**
	 * コンストラクタ<br/>
	 * 
	 * マップを元にその通信にて使用されるHTTPヘッダを取り出し本オブジェクトに保持します。
	 * 
	 * @param responseHeader レスポンスヘッダー
	 * @throws PageHeaderImproperException ヘッダ設定値が不正、またはサポートできない場合
	 */
	public RequestHeader(Map<String, String> requestHeader) throws PageHeaderImproperException {
		this.headerMap = new HashMap<String, String>(requestHeader);
	}
	
	/**
	 * リクエストヘッダのマップオブジェクトを取得します。
	 * 
	 * @return リクエストヘッダのマップオブジェクト
	 */
	public Map<String, String> getHeaderMap() {
		return new HashMap<String, String>(this.headerMap);
	}
	
	@Override
	public String toString() {
		return this.headerMap.toString();
	}
}
