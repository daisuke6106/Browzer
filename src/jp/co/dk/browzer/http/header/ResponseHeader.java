package jp.co.dk.browzer.http.header;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.http.header.record.ResponseRecord;

/**
 * ResponseHeaderは、HTTP通信にて使用されるHTTPヘッダを表すクラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class ResponseHeader implements Serializable{
	
	/** シリアルバージョンID */
	private static final long serialVersionUID = 3560283562725038491L;

	private Map<String, List<String>> headerMap;
	
	private ResponseRecord responseRecord;
	
	private ContentsType contentsType;
	
	private CharSet charSet;
	
	/**
	 * コンストラクタ<br/>
	 * 
	 * マップを元にその通信にて使用されるHTTPヘッダを取り出し本オブジェクトに保持します。
	 * 
	 * @param responseHeader レスポンスヘッダー
	 * @throws BrowzingException ヘッダ設定値が不正、またはサポートできない場合
	 */
	public ResponseHeader(Map<String, List<String>> responseHeader) throws BrowzingException {
		this.headerMap      = new HashMap<String, List<String>>(responseHeader);
		this.responseRecord = new ResponseRecord(this.headerMap);
		
		List<String> contentsTypeList = this.getHeader(HeaderField.CONTENT_TYPE);
		if (contentsTypeList != null && contentsTypeList.size() == 1 ) {
			for (ContentsType contentsType : ContentsType.values()) {
				if (contentsType.isType(contentsTypeList.get(0))){
					this.contentsType = contentsType;
					break;
				}
			}
			for (CharSet charSet : CharSet.values()) {
				if (charSet.isEncording(contentsTypeList.get(0))){
					this.charSet = charSet;
					break;
				}
			}
		}
	}
	
	/**
	 * 指定のヘッダを保持するか判定する。
	 * 
	 * @param headerField ヘッダーフィールド
	 * @return 判定結果（true=有、false=無）
	 */
	public boolean hasHeader(HeaderField headerField) {
		if (headerField == null) {
			return false;
		}
		List<String> headerValue = this.headerMap.get(headerField.getHeaderField());
		if (headerValue != null && headerValue.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 指定のヘッダの設定値を取得する。<br/>
	 * 
	 * 取得対象のヘッダが存在しない場合、または引数にnullが設定された場合、空のリストを返却する。
	 * 
	 * @param headerField 取得対象ヘッダ
	 * @return ヘッダ情報一覧
	 */
	public List<String> getHeader(HeaderField headerField) {
		if (headerField == null) return new ArrayList<String>();
		List<String> list = this.headerMap.get(headerField.getHeaderField());
		if (list == null) return new ArrayList<String>();
		return list;
	}
	
	/**
	 * ヘッダに設定されたレスポンスレコードを取得する。<br/>
	 * 
	 * @return レスポンスレコードオブジェクト
	 */
	public ResponseRecord getResponseRecord() {
		return this.responseRecord;
	}
	
	/**
	 * ヘッダに設定されたContents-Typeを取得する。<br/>
	 * 
	 * ヘッダにContents-Typeが設定されていなかった場合、nullを返却する。
	 * 
	 * @return Contents-Typeオブジュクト
	 */
	public ContentsType getContentsType() {
		return this.contentsType;
	}
	
	/**
	 * ヘッダのContents-Typeの値に設定されたcharsetを取得する。<br/>
	 * 
	 * ヘッダにContents-Typeが設定されていなかった場合、もしくはContents-Type内にcharsetが設定されていなかった場合、<br/>
	 * nullを返却する。
	 * 
	 */
	public CharSet getCharSet() {
		return this.charSet;
	}
	
	/**
	 * ヘッダーのlocationの値に設定されたURLを取得する。<p/>
	 * locationが設定されていない場合、空文字を返却する。<br/>
	 * 複数設定されている場合、最初の設定値を返却する。
	 * 
	 * @return location値
	 */
	public String getLocation() {
		List<String> locationList = this.getHeader(HeaderField.LOCATION);
		if (locationList == null || locationList.size() == 0) return ""; 
		String location = locationList.get(0);
		return location;
	}
	
	/**
	 * Content-Length取得<p/>
	 * ヘッダに設定されているContent-Lengthからこのページのサイズを取得する。<br/>
	 * ヘッダにContent-Lengthが設定されていなかった場合、または不正な値が設定されていた場合、Content-Lengthが複数設定されており正しい値が判定できない場合は-1が返却されます。<br/>
	 * 
	 * @return サイズ
	 */
	public long getContentLength() {
		List<String> header = this.getHeader(HeaderField.CONTENT_LENGTH);
		if (header.size() == 1) {
			String contentLength = header.get(0);
			try {
				return Long.parseLong(contentLength);
			} catch (NumberFormatException e) {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	/**
	 * レスポンスヘッダのマップオブジェクトを取得します。
	 * 
	 * @return レスポンスヘッダのマップオブジェクト
	 */
	public Map<String, List<String>> getHeaderMap() {
		return new HashMap<String, List<String>>(this.headerMap);
	}
	
	@Override
	public String toString() {
		return this.headerMap.toString();
	}
}
