package jp.co.dk.browzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.dk.browzer.contents.BrowzingExtension;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.http.header.ContentsType;
import jp.co.dk.browzer.http.header.Header;
import jp.co.dk.browzer.http.header.HeaderField;
import jp.co.dk.browzer.property.BrowzerProperty;
import jp.co.dk.document.Element;
import jp.co.dk.document.exception.DocumentException;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.constant.HtmlRequestMethodName;
import jp.co.dk.document.html.element.A;
import jp.co.dk.document.html.element.Form;
import jp.co.dk.document.html.exception.HtmlDocumentException;
import jp.co.dk.property.Property;
import jp.co.dk.xml.XmlConvertable;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

/**
 * Pageは、指定URLのページを表すオブジェクトです。<p/>
 * 
 * 本クラスはGoogle Chromeや、FireFox等のインターネットブラウザのページタブと同等であり、
 * 指定されたURLを元にそのページへ接続を行い、そのページ内の情報取得や、画面遷移、ダウンロード等の処理を行うためのクラスです。<br/>
 * <br/>
 * 接続先のURLがHTMLを表す場合、そのページ内のアンカーやFormを元に新たなページへの遷移、本ページの様々な情報の取得、ページのダウンロード等を行うことができます。<br/>
 * URLの接続先は、HTMLに限らずXML、画像やzipや通常のテキストファイル等への接続も行うことができます。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Page implements XmlConvertable{
	
	private String url;
	
	private URL urlObj;
	
	private HttpURLConnection connection;
	
	private jp.co.dk.document.File document;
	
	private String protocol;
	
	private String host;
	
	private String path;
	
	private List<String> pathList;
	
	private Map<String, String> parameter;
	
	private Header header;
	
	/**
	 * コンストラクタ<p>
	 * 指定のURL文字列からページのインスタンスを生成する。
	 * 
	 * @param url URLを表す文字列
	 * @throws BrowzingException ページインスタンス生成に失敗した場合
	 */
	public Page(String url) throws BrowzingException {
		if (url == null || url.equals("")) throw new BrowzingException(ERROR_URL_IS_NOT_SET);
		
		this.url        = url;
		this.urlObj     = this.createURL(url);
		this.connection = this.createURLConnection(this.urlObj, HtmlRequestMethodName.GET);
		this.protocol   = this.getProtocol(this.urlObj);
		this.host       = this.getHost(this.urlObj);
		this.path       = this.getPath(this.urlObj);
		this.pathList   = this.getPathList(this.urlObj);
		
		this.parameter  = this.getParameter(this.urlObj);
		this.header     = this.createHeader(this.connection);
		
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のFORM要素からページのインスタンスを生成します。
	 * 
	 * @param form FORM要素
	 * @param requestProperty リクエストヘッダマップ
	 * @throws BrowzingException ページインスタンス生成に失敗した場合
	 */
	private Page(Form form, Map<String, String> requestProperty) throws BrowzingException {
		try {
			this.urlObj = form.getAction().getURL();
		} catch (HtmlDocumentException e) {
			throw new BrowzingException(ERROR_AN_INVALID_URL_WAS_SPECIFIED, e);
		}
		this.url  = urlObj.toString();
		this.connection = this.createURLConnection(this.urlObj, form.getMethod());
		this.protocol   = this.getProtocol(this.urlObj);
		this.host       = this.getHost(this.urlObj);
		this.path       = this.getPath(this.urlObj);
		this.pathList   = this.getPathList(this.urlObj);
		this.connection = this.setRequestProperty(this.connection, requestProperty);
		try {
			BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream()));
			outputStream.write(form.createMessage());
			outputStream.close();
		} catch (IOException e) {
			throw new BrowzingException(ERROR_FAILED_TO_SEND_MESSAGE, new String[]{this.url, form.getMethod().getMethod()}, e);
		}
		this.header     = this.createHeader(this.connection);
		
	}
	
	/**
	 * ページ遷移実施<p/>
	 * 指定されたアンカーを元にページ遷移を実行、新たなページオブジェクトを生成し、返却します。<br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先アンカーが設定されていなかった場合<br/>
	 * ・遷移先アンカーの取得元が異なる場合（ページ1のアンカーを取得し、ページ2にてページ1のアンカーを使用してmoveした場合など）<br/>
	 * ・遷移先アンカーに遷移先URLが設定されていなかった場合(hrefが設定されていなかった場合)<br/>
	 * 
	 * @param anchor 遷移先アンカー
	 * @return 遷移先のページオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	Page move (jp.co.dk.browzer.html.element.A anchor) throws BrowzingException {
		if (anchor == null) throw new BrowzingException(ERROR_SPECIFIED_ANCHOR_IS_NOT_SET);
		if (!this.equals(anchor.getPage()))throw new BrowzingException(ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
		String url = anchor.getHref();
		if (url.equals("")) throw new BrowzingException(ERROR_ANCHOR_HAS_NOT_URL);
		return new Page(url);
	}
	
	/**
	 * ページ遷移実施<p/>
	 * 指定されたFORMを元にページ遷移を実行、新たなページオブジェクトを生成し、返却します。<br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先FORMが設定されていなかった場合<br/>
	 * ・遷移先FORMの取得元が異なる場合（ページ1のFORMを取得し、ページ2にてページ1のformを使用してmoveした場合など）<br/>
	 * ・遷移先FORMに遷移先URLが設定されていなかった場合(actionが設定されていなかった場合)<br/>
	 * <br/>
	 * リクエストヘッダはプロパティに設定された{@link jp.co.dk.browzer.property.BrowzerProperty.REQUEST_HEADER}の値がデフォルトとして設定されます。<br/>
	 * <br/>
	 * @param form 遷移先FORM
	 * @return 遷移先のページオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	Page move(jp.co.dk.browzer.html.element.Form form) throws BrowzingException {
		return this.move(form, new HashMap<String, String>());
	}
	
	/**
	 * ページ遷移実施<p/>
	 * 指定されたFORMを元にページ遷移を実行、新たなページオブジェクトを生成し、返却します。<br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先FORMが設定されていなかった場合<br/>
	 * ・遷移先FORMの取得元が異なる場合（ページ1のFORMを取得し、ページ2にてページ1のformを使用してmoveした場合など）<br/>
	 * ・遷移先FORMに遷移先URLが設定されていなかった場合(actionが設定されていなかった場合)<br/>
	 * <br/>
	 * 遷移の際に指定されたリクエストヘッダ、及びデフォルトのリクエストヘッダにて接続されます。<br/>
	 * デフォルトリクエストヘッダはプロパティに設定された{@link jp.co.dk.browzer.property.BrowzerProperty.REQUEST_HEADER}の値がデフォルトとして設定されます。<br/>
	 * <br/>
	 * 例：指定されたリクエストヘッダ<br/>
	 * header.request.header1=arg_example1<br/>
	 * header.request.header2=arg_example2<br/>
	 * <br/>
	 * デフォルト（プロパティ）のリクエストヘッダ<br/>
	 * header.request.header1=property_example1<br/>
	 * header.request.header3=property_example3<br/>
	 * <br/>
	 * 上記の設定の場合、以下のリクエストヘッダを生成し、接続します。<br/>
	 * header.request.header1=arg_example1<br/>
	 * header.request.header2=arg_example2<br/>
	 * header.request.header3=property_example3<br/>
	 * <br/>
	 * 
	 * @param form 遷移先FORM
	 * @param requestHeader リクエストヘッダ
	 * @return 遷移先のページオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	Page move(jp.co.dk.browzer.html.element.Form form, Map<String, String> requestHeader) throws BrowzingException {
		if (form == null) throw new BrowzingException(ERROR_SPECIFIED_FORM_IS_NOT_SET);
		if (!this.equals(form.getPage())) throw new BrowzingException(ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
		if (requestHeader == null) requestHeader = new HashMap<String, String>();
		Map<String, String> defaultRequestHeader  = this.getRequestHeaderByPorperty();
		Map<String, String> cookieRequestHeader   = this.getCookies();
		defaultRequestHeader.putAll(cookieRequestHeader);
		defaultRequestHeader.putAll(requestHeader);
		return new Page(form, requestHeader);
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
	 * URLオブジェクトを取得<p>
	 * 
	 * @return URL文字列
	 */
	public URL getURLObject() {
		return this.urlObj;
	}
	
	/**
	 * ドキュメントオブジェクトを取得する。<p>
	 * デフォルトのドキュメントファクトリを使用し、このページが表すドキュメントオブジェクトを生成、返却します。<br/>
	 * すでにドキュメントが生成されていた場合、キャッシュされているドキュメントオブジェクトを返却します。
	 * 
	 * @return ドキュメントオブジェクト
	 * @throws BrowzingException ドキュメントオブジェクトの生成に失敗した場合
	 */
	public jp.co.dk.document.File getDocument() throws BrowzingException {
		if (this.document != null) return this.document;
		return this.getDocument(new DocumentFactory(this));
	}
	
	/**
	 * ドキュメントオブジェクトを取得する。<p>
	 * 指定のドキュメントファクトリを使用し、このページが表すドキュメントオブジェクトを生成、返却します。<br/>
	 * <br/>
	 * 生成は以下の順で行われます。<br/>
	 * <br/>
	 * 1.このページの接続時に得られたHTTPヘッダのContents-Typeを取得、そのContents-Typeを元にドキュメントファクトリからドキュメントを生成します。<br/>
	 * 2.Contents-Typeが取得できなかった場合、URLに設定されたファイルの拡張子を元にドキュメントファクトリからドキュメントを生成します。<br/>
	 * 3.上記にてドキュメントが生成できなかった場合、例外を発生します。
	 * <br/>
	 * @param documentFactory ドキュメント生成ファクトリ
	 * @return ドキュメントオブジェクト
	 * @throws BrowzingException ドキュメントオブジェクトの生成に失敗した場合
	 */
	public jp.co.dk.document.File getDocument(DocumentFactory documentFactory) throws BrowzingException {
		InputStream inputStream = this.getUrlInputStream(this.connection);
		ContentsType contentsType = this.header.getContentsType();
		if (contentsType != null) {
			this.document = documentFactory.create(contentsType, inputStream);
			return this.document;
		} else {
			BrowzingExtension extension = this.getExtension(this.path);
			if (extension == null) 	throw new BrowzingException(ERROR_NON_SUPPORTED_EXTENSION, url);
			this.document = documentFactory.create(extension, inputStream);
			return this.document;
		}
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
	 * このURLのパス文字列を取得します。<p>
	 * 例：<br/>
	 * http://xxx.jp/aaa/bbb 、 http://xxx.jp/aaa/bbb/ 、http://xxx.jp/aaa/bbb/ccc.html の場合、aaa/bbb/をを返却します。<br/>
	 * http://xxx.jp/ や http://xxx.jp の場合、空文字を返却します。<br/>
	 * 
	 * @return パス文字列
	 */
	public String getPath() {
		if (this.pathList == null || this.pathList.size() == 0) return "";
		String fileName = this.getFileName();
		StringBuilder sb = new StringBuilder();
		for (String path : this.pathList) {
			if (fileName.equals(path)) break; 
			sb.append(path).append('/');
		}
		return sb.toString();
	}
	
	/**
	 * このURLのファイル名を取得する。<p>
	 * URLにファイル名が設定されていない場合、デフォルトのファイル名を返却する。
	 * 
	 * 例：<br/>
	 * http://xxx.jp、http://xxx.jp/、http://xxx.jp/aaa/bbb 、 http://xxx.jp/aaa/bbb/ の場合、デフォルトのファイル名（index.html）を返却します。<br/>
	 * http://xxx.jp/aaa/bbb/ccc.htmlの場合、ccc.htmlを返却します。<br/>
	 * 
	 * @return ファイル名
	 */
	public String getFileName() {
		if (pathList == null || pathList.size() == 0) return this.defaultFileName(header);
		String lastPath = pathList.get(pathList.size()-1);
		if (lastPath.lastIndexOf('.') == -1) {
			return this.defaultFileName(header);
		} else {
			return lastPath;
		}
	}
	
	/**
	 * このURLのファイルの拡張子を取得する。<p>
	 * URLにファイル名が設定されていない場合、空文字を返却する。<br/>
	 * 
	 * 例：<br/>
	 * http://xxx.jp、http://xxx.jp/、http://xxx.jp/aaa/bbb 、 http://xxx.jp/aaa/bbb/ の場合、デフォルトのファイル名（index.html）の拡張子、"html"を返却、<br/>
	 * http://xxx.jp/aaa/bbb/ccc.htmlの場合、"html"を返却します。<br/>
	 * http://xxx.jp/aaa/bbb/ccc.jpgの場合、"jpg"を返却します。<br/>
	 * 
	 * @return 拡張子名
	 */
	public String getExtension() {
		String fileName = this.getFileName();
		int extentionIndex = fileName.lastIndexOf(".");
		if (extentionIndex == -1) return "";
		return fileName.substring(extentionIndex + 1);
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
	 * アンカー一覧を取得します。<p/>
	 * このページに存在するすべてのアンカータグを取得します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return アンカー一覧 
	 * @throws BrowzingException このページがHTMLでない場合 
	 */
	public List<A> getAnchor() throws BrowzingException {
		jp.co.dk.document.File document = this.getDocument();
		if (!(document instanceof HtmlDocument)) throw new BrowzingException(ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML); 
		HtmlDocument htmlDocument = (HtmlDocument)document;
		List<Element> elementList = htmlDocument.getElement(HtmlElementName.A);
		List<A> anchorList = new ArrayList<A>();
		for (Element element : elementList){
			if (element instanceof A) anchorList.add((A)element);
		}
		return anchorList;
	}
	
	/**
	 * フォーム一覧を取得します。<p/>
	 * このページに存在するすべてのフォームタグを取得します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return フォーム一覧
	 * @throws BrowzingException このペーゾがHTMLでない場合
	 */
	public List<Form> getForm() throws BrowzingException {
		jp.co.dk.document.File document = this.getDocument();
		if (!(document instanceof HtmlDocument)) throw new BrowzingException(ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML); 
		HtmlDocument htmlDocument = (HtmlDocument)document;
		List<Element> elementList = htmlDocument.getElement(HtmlElementName.FORM);
		List<Form> formList = new ArrayList<Form>();
		for (Element element : elementList){
			if (element instanceof Form) formList.add((Form)element);
		}
		return formList;
	}
	
	/**
	 * このページのサイズ(byte)を取得します。<p/>
	 * このページ接続時のヘッダ情報（レスポンスヘッダ内のContent-Length）からこのページのサイズ（byte）をlongで取得します。<br/>
	 * ヘッダにContent-Lengthが設定されていなかった場合、または不正な値が設定されていた場合、Content-Lengthが複数設定されており正しい値が判定できない場合は-1が返却されます。
	 * 
	 * @return サイズ（byte）
	 */
	public long getSize() {
		return this.header.getContentLength();
	}
	
	/**
	 * このページのヘッダを取得します。<p/>
	 * 
	 * @return ヘッダ
	 */
	public Header getHeader() {
		return this.header;
	}
	
	/**
	 * このURLが示すリンク先を指定のディレクトリへ保存を実行します。<p>
	 * <br/>
	 * 以下の条件の場合、例外を発生する。<br/>
	 * ・指定された保存先ディレクトリが存在しない場合<br/>
	 * ・指定された保存先ディレクトリがディレクトリが存在しない場合<br/>
	 * ・保存先のファイルがすでに存在する場合<br/>
	 * 
	 * @param path ダウンロード先ディレクトリパス
	 * @return 保存したファイルのオブジェクト
	 * @throws ダウンロードに失敗した場合
	 */
	public File save(File path) throws BrowzingException {
		try {
			return this.getDocument().save(path, this.getFileName());
		} catch (DocumentException e) {
			throw new BrowzingException(ERROR_FAILED_TO_DOWNLOAD, url, e);
		}
	}
	
	/**
	 * このURLが示すリンク先を指定のディレクトリへ指定のファイル名で保存を実行します。<p>
	 * <br/>
	 * 以下の条件の場合、例外を発生する。<br/>
	 * ・指定された保存先ディレクトリが存在しない場合<br/>
	 * ・指定された保存先ディレクトリがディレクトリが存在しない場合<br/>
	 * ・保存先のファイルがすでに存在する場合<br/>
	 * 
	 * @param path ダウンロード先ディレクトリパス
	 * @return 保存したファイルのオブジェクト
	 * @throws ダウンロードに失敗した場合
	 */
	public File save(File path, String fileName) throws BrowzingException {
		try {
			return this.getDocument().save(path, fileName);
		} catch (DocumentException e) {
			throw new BrowzingException(ERROR_FAILED_TO_DOWNLOAD, url, e);
		}
	}
	
	/**
	 * このURLが示すリンク先を指定のディレクトリへダウンロードを実行します。<p>
	 * 指定のパスにディレクトリ以外が設定された場合、例外を発生します。<br/>
	 * 
	 * @param path ダウンロード先ディレクトリパス
	 * @throws ダウンロードに失敗した場合
	 */
	protected void download(File path) throws BrowzingException {
		StringBuilder pathStr = new StringBuilder(path.getAbsolutePath()).append('/').append(this.host).append('/').append(this.getPath());
		File downloadPath = new File(pathStr.toString());
		try {
			downloadPath.mkdirs();
		} catch (SecurityException e) {
			throw new BrowzingException(ERROR_COULD_NOT_CREATE_DOWNLOAD_DIRECTORY, pathStr.toString(), e);
		}
		try {
			this.getDocument().save(downloadPath, this.getFileName());
		} catch (DocumentException e) {
			throw new BrowzingException(ERROR_FAILED_TO_DOWNLOAD, url, e);
		}
	}
	
	/**
	 * このページのURLから引数に設定されたURLで補完し、新しいURLを返却します。
	 * 
	 * @param url 補完対象URL
	 * @return 補完済みURL
	 * @throws BrowzingException 補完に失敗した場合
	 */
	public String completionURL(String url) throws BrowzingException {
		try {
			return new URL(this.urlObj, url).toString();
		} catch (MalformedURLException e) {
			throw new BrowzingException(ERROR_COMPRETION_URL, new String[]{this.url, url}, e);
		}
	}
	
	/**
	 * 指定のURL文字列からURLオブジェクトを生成します。<p>
	 * 
	 * @param url URL文字列
	 * @return URLオブジェクト
	 * @throws BrowzingException 指定されたURL文字列に指定されたプロトコルが未知である場合
	 */
	protected URL createURL(String url) throws BrowzingException{
		URL urlObj;
		try {
			urlObj = new URL(url);
			
		} catch (MalformedURLException e1) {
			throw new BrowzingException( ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN, url, e1 );
		}
		return urlObj;
	}
	
	/**
	 * 指定のURLオブジェクトからURLコネクションを指定メソッドで生成します。<p>
	 * 
	 * @param urlObj URLオブジェクト
	 * @param method HTTPメソッド
	 * @return URLコネクション
	 * @throws BrowzingException URLが参照するリモートオブジェクトへの接続時に入出力例外が発生した場合
	 */
	protected HttpURLConnection createURLConnection(URL urlObj, HtmlRequestMethodName method) throws BrowzingException{
		HttpURLConnection urlConnection;
		try {
			urlConnection = (HttpURLConnection)urlObj.openConnection();
			urlConnection.setRequestMethod(method.getMethod());
			urlConnection.setDoOutput(true);
			urlConnection.setFollowRedirects(false);
			
		} catch (IOException e) {
			throw new BrowzingException( ERROR_INPUT_OUTPUT_EXCEPTION_OCCURRED_WHEN_CONNECTING_TO_A_URL, urlObj.toString(), e );
		}
		return urlConnection;
	}
	
	
	/**
	 * 指定のURLコネクションから入力ストリームを取得します。<p>
	 * 
	 * @param connection URLコネクション
	 * @return 入力ストリーム
	 * @throws BrowzingException 入力ストリームの作成中に入出力エラーが発生した場合
	 */
	protected InputStream getUrlInputStream( URLConnection connection ) throws BrowzingException {
		InputStream inputStream;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			throw new BrowzingException(ERROR_IO_ERROR_WAS_OCCURRED_WHILE_CREATING_THE_INPUT_STREAM ,e);
		}
		return inputStream;
	}
	
	/**
	 * 指定のパスにあるファイル名の拡張子文字列を取得します。<p>
	 * 拡張子が設定されていない場合、空文字を返却します。
	 * 
	 * @param path パス文字列
	 * @return 拡張子
	 */
	protected BrowzingExtension getExtension(String path) {
		String urlExtension;
		int index = path.lastIndexOf('.');
		if (index == -1 ) {
			urlExtension = "";
		} else {
			urlExtension = path.substring(index+1);
		}
		for (BrowzingExtension extension : BrowzingExtension.values()) {
			if (extension.hasExtension(urlExtension)) {
				return extension;
			}
		}
		return null;
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
	 * 指定のURLコネクションへ指定のリクエストヘッダマップに設定されているリクエストヘッダを設定します。<p>
	 * リクエストヘッダが未指定の場合はそのまま返却します。
	 * 
	 * @param connection URLコネクション
	 * @param requestMap リクエストヘッダマップ
	 * @return リクエストヘッダが設定されたURLコネクション
	 */
	protected HttpURLConnection setRequestProperty(HttpURLConnection connection ,Map<String,String> requestMap) {
		if ( connection == null || requestMap == null) {
			return connection;
		}
		for (String key : requestMap.keySet()) {
			connection.setRequestProperty(key, requestMap.get(key));
		}
		return connection;
	}
	
	/**
	 * このページのURLがトレイリングスラッシュを保持するかを判定する。<p>
	 * 
	 * @return 判定結果（true=スラッシュあり、false=スラッシュなし）
	 */
	protected boolean hasTrailingSlash() { 
		return this.path.indexOf('/') == this.path.length();
	}
	
	/**
	 * リクエストヘッダ生成<p/>
	 * プロパティのBrowzerProperty.REQUEST_HEADERからリクエストヘッダを取得し、マップへ変換し返却します。
	 * 
	 * @return map リクエストヘッダマップ
	 */
	protected Map<String, String> getRequestHeaderByPorperty() {
		Map<String, String> map = new HashMap<String, String>();
		java.util.List<Property> requestHeader = BrowzerProperty.REQUEST_HEADER.getPropertyList();
		for (Property property : requestHeader) {
			String[] list = property.getStringArray();
			StringBuilder sb = new StringBuilder();
			for (String value : list)sb.append(value).append(',');
			map.put(property.getKey(), sb.toString().substring(0, sb.length()-1));
		}
		return map;
	}
	
	/**
	 * クッキー取得<p/>
	 * 本ページのレスポンスヘッダに設定されたクッキーをリクエストヘッダへ変換し返却します。
	 * 
	 * @return リクエストヘッダ
	 */
	protected Map<String, String> getCookies() {
		Map<String, String> map = new HashMap<String, String>();
		List<String> coolies = this.header.getHeader(HeaderField.SET_COOKIE);
		if (coolies == null || coolies.size() == 0) return map;
		StringBuilder sb = new StringBuilder();
		for (String cookie : coolies) sb.append(cookie).append(',');
		map.put(HeaderField.COOKIE.getHeaderField(), sb.substring(0, sb.length()-1));
		return map;
	}
	
	protected String defaultFileName(Header header) {
		StringBuilder sb = new StringBuilder("default");
		ContentsType contentType = header.getContentsType();
		if (contentType != null) {
			String extension = contentType.getDefaultExtension();
			sb.append('.').append(extension);
		}
		return sb.toString();
	}

	protected String getProtocol(URL urlObject) {
		return urlObject.getProtocol();
	}
	
	protected String getHost(URL urlObject) {
		return urlObject.getHost();
	}
	
	protected String getPath(URL urlObject) {
		return urlObject.getPath();
	}
	
	protected Header createHeader(HttpURLConnection connection) throws BrowzingException {
		return new Header(this.connection);
	}
	
	@Override
	public String toString() {
		return this.url;
	}

	@Override
	public jp.co.dk.xml.Element convert() throws jp.co.dk.xml.exception.XmlDocumentException {
		jp.co.dk.xml.Element xmlElement = new jp.co.dk.xml.Element("page");
		xmlElement.addAttribute(new jp.co.dk.xml.Attribute("url",this.url));
		xmlElement.addAttribute(new jp.co.dk.xml.Attribute("protocol",this.protocol));
		xmlElement.addAttribute(new jp.co.dk.xml.Attribute("host",this.host));
		xmlElement.addAttribute(new jp.co.dk.xml.Attribute("path",this.path));
		long size = this.getSize();
		if (size != -1) xmlElement.addAttribute(new jp.co.dk.xml.Attribute("size", Long.toString(size))); 
		return xmlElement;
	}
}
