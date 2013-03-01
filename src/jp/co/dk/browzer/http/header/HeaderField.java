package jp.co.dk.browzer.http.header;

import jp.co.dk.browzer.http.header.HeaderFieldType;

/**
 * HeaderFieldは、HTTP通信にて使用されるヘッダを定義する定数クラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public enum HeaderField {
	ACCEPT("Accept", HeaderFieldType.REQUEST, 1.1), 
	ACCEPT_CHARSET("Accept-Charset", HeaderFieldType.REQUEST, 1.1), 
	ACCEPT_ENCODING("Accept-Encoding", HeaderFieldType.REQUEST, 1.1), 
	ACCEPT_LANGUAGE("Accept-Language", HeaderFieldType.REQUEST, 1.1), 
	AUTHORIZATION("Authorization", HeaderFieldType.REQUEST, 1.0), 
	EXPECT("Expect", HeaderFieldType.REQUEST, 1.1), 
	FROM("From", HeaderFieldType.REQUEST, 1.0), 
	HOST("Host", HeaderFieldType.REQUEST, 1.1), 
	IF_MODIFIED_SINCE("If-Modified-Since", HeaderFieldType.REQUEST, 1.0), 
	IF_MATCH("If-Match", HeaderFieldType.REQUEST, 1.1), 
	IF_NONE_MATCH("If-None-Match", HeaderFieldType.REQUEST, 1.1), 
	IF_RANGE("If-Range", HeaderFieldType.REQUEST, 1.1), 
	IF_UNMODIFIED_SINCE("If-Unmodified-Since", HeaderFieldType.REQUEST, 1.1), 
	MAX_FORWARDS("Max-Forwards", HeaderFieldType.REQUEST, 1.1), 
	PROXY_AUTHORIZATION("Proxy-Authorization", HeaderFieldType.REQUEST, 1.1), 
	RANGE("Range", HeaderFieldType.REQUEST, 1.1), 
	REFERER("Referer", HeaderFieldType.REQUEST, 1.0), 
	TE("TE", HeaderFieldType.REQUEST, 1.1), 
	USER_AGENT("User-Agent", HeaderFieldType.REQUEST, 1.0), 
	ALLOW("Allow", HeaderFieldType.ENTITY, 1.0), 
	CONTENT_ENCODING("Content-Encoding", HeaderFieldType.ENTITY, 1.0), 
	CONTENT_LANGUAGE("Content-Language", HeaderFieldType.ENTITY, 1.1), 
	CONTENT_LENGTH("Content-Length", HeaderFieldType.ENTITY, 1.0), 
	CONTENT_LOCATION("Content-Location", HeaderFieldType.ENTITY, 1.1), 
	CONTENT_MD5("Content-MD5", HeaderFieldType.ENTITY, 1.1), 
	CONTENT_RANGE("Content-Range", HeaderFieldType.ENTITY, 1.1), 
	CONTENT_TYPE("Content-Type", HeaderFieldType.ENTITY, 1.0), 
	EXPIRES("Expires", HeaderFieldType.ENTITY, 1.0), 
	LAST_MODIFIED("Last-Modified", HeaderFieldType.ENTITY, 1.0), 
	ACCEPT_RANGES("Accept-Ranges", HeaderFieldType.RESPONSE, 1.1), 
	AGE("Age", HeaderFieldType.RESPONSE, 1.1), 
	ETAG("ETag", HeaderFieldType.RESPONSE, 1.1), 
	LOCATION("Location", HeaderFieldType.RESPONSE, 1.0), 
	PROXY_AUTHENTICATE("Proxy-Authenticate", HeaderFieldType.RESPONSE, 1.1), 
	RETRY_AFTER("Retry-After", HeaderFieldType.RESPONSE, 1.1), 
	SERVER("Server", HeaderFieldType.RESPONSE, 1.0), 
	VARY("Vary", HeaderFieldType.RESPONSE, 1.1), 
	WWW_AUTHENTICATE("WWW-Authenticate", HeaderFieldType.RESPONSE, 1.0), 
	CACHE_CONTROL("Cache-Control", HeaderFieldType.GENERAL, 1.1), 
	CONNECTION("Connection", HeaderFieldType.GENERAL, 1.1), 
	DATE("Date", HeaderFieldType.GENERAL, 1.0), 
	PRAGMA("Pragma", HeaderFieldType.GENERAL, 1.0), 
	TRAILER("Trailer", HeaderFieldType.GENERAL, 1.1), 
	TRANSFER_ENCODING("Transfer-Encoding", HeaderFieldType.GENERAL, 1.1), 
	UPGRADE("Upgrade", HeaderFieldType.GENERAL, 1.1), 
	VIA("Via", HeaderFieldType.GENERAL, 1.1), 
	WARNING("Warning", HeaderFieldType.GENERAL, 1.1),
	
	SET_COOKIE("Set-Cookie", HeaderFieldType.RESPONSE, 1.1), 
	COOKIE("Cookie", HeaderFieldType.REQUEST, 1.1), 
	;
	
	private String headerField;
	
	private HeaderFieldType fieldType;
	
	private double version;
	
	private HeaderField(String headerField, HeaderFieldType fieldType, double version) {
		this.headerField = headerField;
		this.fieldType   = fieldType;
		this.version     = version;
	}
	
	/**
	 * ヘッダーフィールド文字列を取得します。
	 * 
	 * @return ヘッダーフィールド文字列
	 */
	public String getHeaderField() {
		return this.headerField;
	}
	
	/**
	 * ヘッダーフィールドの種別を取得します。
	 * 
	 * @return ヘッダーフィールド種別
	 */
	public HeaderFieldType getHeaderFieldType() {
		return this.fieldType;
	}
	
	/**
	 * このヘッダーフィールドが使用できるHTTPのバージョン番号を取得します。
	 * 
	 * @return バージョン番号
	 */
	public double getVersion() {
		return this.version;
	}
}

enum HeaderFieldType {
	REQUEST, 
	ENTITY, 
	RESPONSE, 
	GENERAL, 
	;
}