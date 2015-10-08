package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN;
import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_URL_IS_NOT_SET;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.dk.browzer.exception.PageIllegalArgumentException;

/**
 * URLは、URLを元にそのURLのプロトコル名、ホスト名、パス、パラメータの文字列などを取得するメソッドを保有するクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Url {
	
	/** URL文字列 */
	protected String url;
	
	/** URL文字列を元に作成されたURLオブジェクト */
	protected URL urlObj;
	
	/** プロトコル名 */
	protected String protocol;
	
	/** ホスト名 */
	protected String host;
	
	/** パス文字列 */
	protected String path;
	
	/** パス一覧 */
	protected List<String> pathList;
	
	/** パラメータ一覧 */
	protected Map<String, String> parameter;
	
	/** フラグメントパターン */
	protected static Pattern fragmentPattern = Pattern.compile("^(.+?)#(.+?)$");
	
	/**
	 * <p>URL文字列からこのURLオブジェクトを生成する。</p>
	 * URL文字列を基にホスト、パス、パラメータを保持する。<br/>
	 * フラグメントが設定されている場合、フラグメントを除外してオブジェクトが生成される。<br/>
	 * 
	 * @param url URL文字列
	 * @throws PageIllegalArgumentException URL文字列がnullまたは、空文字だった場合
	 */
	public Url(String url) throws PageIllegalArgumentException {
		if (url == null || url.equals("")) throw new PageIllegalArgumentException(ERROR_URL_IS_NOT_SET);
		Matcher fragmentMatcher = fragmentPattern.matcher(url);
		if (fragmentMatcher.find()) url = fragmentMatcher.group(1);
		this.url        = url;
		this.urlObj     = this.createURL(url);
		this.protocol   = this.getProtocol(this.urlObj);
		this.host       = this.getHost(this.urlObj);
		this.path       = this.getPath(this.urlObj);
		this.pathList   = this.getPathList(this.urlObj);
		this.parameter  = this.getParameter(this.urlObj);
	}
	
	/**
	 * プロトコルを取得します。<p>
	 * 
	 * @return プロトコルを表す文字列
	 */
	public String getProtocol() {
		return this.protocol;
	}

	/**
	 * ホスト取得<p>
	 * このページのホスト名を取得する。<br/>
	 * 例：http://www.google.com/aaa/bbbの場合、www.google.comが取得されます。<br/>
	 * 
	 * 
	 * @return URL文字列
	 */
	public String getHost() {
		return this.host;
	}
	
	/**
	 * URL取得<p>
	 * URLを取得する。
	 * 
	 * @return URL文字列
	 */
	public String getURL() {
		return this.urlObj.toString();
	}

	/**
	 * URLに設定されているパラメータを取得する。<p>
	 * パラメータが設定されていない場合、空のマップを返却します。
	 * 
	 * @return パラメータのマップ
	 */
	public Map<String, String> getParameter() {
		return new HashMap<String,String>(this.parameter);
	}
	
	
	/**
	 * このURLのパス一覧を取得します。
	 * 例：<br/>
	 * http://xxx.jp/aaa/bbb 、 http://xxx.jp/aaa/bbb/ 、http://xxx.jp/aaa/bbb/ccc.html の場合、List["aaa","bbb"]をを返却します。<br/>
	 * http://xxx.jp/ や http://xxx.jp の場合、空のリストを返却します。<br/>
	 * @return パス一覧
	 * 
	 */
	public List<String> getPathList() {
		return new ArrayList<String>(this.pathList);
	}
	
	/**
	 * このURLのURLオブジェクトを返却する。
	 * @return URLオブジェクト
	 */
	URL getUrlObject() {
		return this.urlObj;
	}
	
	/**
	 * 指定のURL文字列からURLオブジェクトを生成します。<p>
	 * 
	 * @param url URL文字列
	 * @return URLオブジェクト
	 * @throws PageIllegalArgumentException 指定されたURL文字列に指定されたプロトコルが未知である場合
	 */
	protected URL createURL(String url) throws PageIllegalArgumentException{
		URL urlObj;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e1) {
			throw new PageIllegalArgumentException(ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN, url, e1);
		}
		return urlObj;
	}
	
	/**
	 * URLオブジェクトからプロトコルを取得します。
	 * 
	 * @param urlObject URLオブジェクト
	 * @return プロトコル名
	 */
	protected String getProtocol(URL urlObject) {
		return urlObject.getProtocol();
	}
	
	/**
	 * URLオブジェクトからホスト名を取得します。
	 * 
	 * @param urlObject URLオブジェクト
	 * @return ホスト名
	 */
	protected String getHost(URL urlObject) {
		return urlObject.getHost();
	}
	
	/**
	 * URLオブジェクトからパスを表す文字列を取得します。
	 * 
	 * @param urlObject URLオブジェクト
	 * @return パスを表す文字列
	 */
	protected String getPath(URL urlObject) {
		return urlObject.getPath();
	}
	
	/**
	 * 指定のURLオブジェクトからパス部分を取り出し、各パス名をリストにして返却します。<p>
	 * 
	 * 例：<br/>
	 * /path1/path2/path3/file.html<br/>
	 * <br/>
	 * の場合、<br/>
	 * [path1],[path2],[path3],[file.html]<br/>
	 * のリストを返却します。
	 * 
	 * @param urlObj URLオブジェクト
	 * @return パスリスト
	 */
	protected List<String> getPathList(URL urlObj) {
		String path = urlObj.getPath();
		List<String> list = new ArrayList<String>();
		String[] pathElements = path.split("/");
		for (String pathElement : pathElements) {
			if (pathElement == null || pathElement.equals("")) {
				continue;
			} else {
				list.add(pathElement);
			}
		}
		return list;
	}
	
	/**
	 * 指定のURLに設定されているパラメータ部を取得し、マップ形式に整形し、返却します。<p>
	 * 指定のURLにパラメータが設定されていなかった場合は空のマップを返却します。
	 * 
	 * @param url URLオブジェクト
	 * @return パラメータマップ
	 */
	protected Map<String, String> getParameter(URL url) {
		Map<String, String> parameters = new HashMap<String, String>();
		String query = url.getQuery();
		if (query == null) {
			return parameters;
		}
		String[] queries = query.split("&");
		for (String param : queries) {
			String[] val = param.split("=");
			if (val.length == 1) {
				parameters.put(val[0], "");
			} else if (val.length == 2) {
				parameters.put(val[0], val[1]);
			} else {
				continue;
			}
		}
		return parameters;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlObj == null) ? 0 : urlObj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Url other = (Url) obj;
		if (urlObj == null) {
			if (other.urlObj != null)
				return false;
		} else if (!urlObj.equals(other.urlObj))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.url;
	}
	
}
