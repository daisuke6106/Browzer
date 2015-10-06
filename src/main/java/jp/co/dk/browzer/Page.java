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
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageHeaderImproperException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageSaveException;
import jp.co.dk.browzer.http.header.ContentsType;
import jp.co.dk.browzer.http.header.RequestHeader;
import jp.co.dk.browzer.http.header.ResponseHeader;
import jp.co.dk.browzer.http.header.HeaderField;
import jp.co.dk.browzer.property.BrowzerProperty;
import jp.co.dk.document.ByteDump;
import jp.co.dk.document.Element;
import jp.co.dk.document.exception.DocumentException;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.constant.HtmlRequestMethodName;
import jp.co.dk.document.html.element.A;
import jp.co.dk.document.html.element.Form;
import jp.co.dk.document.html.exception.HtmlDocumentException;
import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;
import jp.co.dk.property.Property;
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
public class Page {
	
	/** URLオブジェクト */
	protected Url url;
	
	/** リクエストヘッダ */
	protected RequestHeader requestHeader;
	
	/** レスポンスヘッダ */
	protected ResponseHeader responseHeader;
	
	/** リードデータフラグ */
	protected boolean readDataFlg;
	
	/** ページデータ */
	protected ByteDump byteDump;
	
	/** ページのドキュメントオブジェクト */
	protected jp.co.dk.document.File document;
	
	/** URLコネクション */
	protected URLConnection connection;
	
	/** ページイベントハンドラ */
	protected List<PageEventHandler> eventHandler = new ArrayList<PageEventHandler>();
	
	/** ロガーインスタンス */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * コンストラクタ<p>
	 * 指定のURL文字列からページのインスタンスを生成する。<br/>
	 * ページの情報はインスタンス生成時に読み込みます。<br/>
	 * 
	 * @param url URLを表す文字列
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public Page(String url) throws PageIllegalArgumentException, PageAccessException {
		this(url, new HashMap<String,String>(), true, new ArrayList<PageEventHandler>());
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURL文字 列と、リクエストヘッダ、データ読み込みフラグを元にページのインスタンスを生成する。<br/>
	 * ページの情報はインスタンス生成時に読み込みます。<br/>
	 * 
	 * @param url URLを表す文字列
	 * @param requestHeader リクエストヘッダ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public Page(String url, Map<String, String> requestHeader) throws PageIllegalArgumentException, PageAccessException {
		this(url, requestHeader, true, new ArrayList<PageEventHandler>());
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURL文字 列と、リクエストヘッダ、データ読み込みフラグを元にページのインスタンスを生成する。<br/>
	 * ページの情報はインスタンス生成時に読み込みます。<br/>
	 * 
	 * @param url URLを表す文字列
	 * @param requestHeader リクエストヘッダ
	 * @param readDataFlg データ読み込みフラグ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public Page(String url, Map<String, String> requestHeader, boolean readDataFlg) throws PageIllegalArgumentException, PageAccessException {
		this(url, requestHeader, readDataFlg, new ArrayList<PageEventHandler>());
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURL文字列と、リクエストヘッダ、データ読み込みフラグを元にページのインスタンスを生成する。<br/>
	 * <br/>
	 * データ読み込みフラグにtrueが設定されていた場合、このインスタンスが生成された際にページ情報を取得しメモリ上に保持します。<br/>
	 * falseの場合、getDataメソッドが呼ばれるまでページ情報の取得は行いません。<br/>
	 * 
	 * @param url URLを表す文字列
	 * @param requestHeader リクエストヘッダ
	 * @param readDataFlg データ読み込みフラグ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public Page(String url, Map<String, String> requestHeader, boolean readDataFlg, List<PageEventHandler> pageEventHandlerList) throws PageIllegalArgumentException, PageAccessException {
		this.logger.constractor(this.getClass(), url, requestHeader, new Boolean(readDataFlg), pageEventHandlerList);
		
		if (url == null || url.equals("")) throw new PageIllegalArgumentException(ERROR_URL_IS_NOT_SET);
		if (requestHeader == null) requestHeader = new HashMap<String, String>();
		this.url                 = this.createUrl(url);
		this.readDataFlg         = readDataFlg;
		this.eventHandler        = pageEventHandlerList;
		
		URLConnection connection = this.createURLConnection(this.url.getUrlObject(), HtmlRequestMethodName.GET);
		
		Map<String, String> requestHeaderByProperty = this.getRequestHeaderByPorperty();
		requestHeaderByProperty.putAll(requestHeader);
		try {
			this.requestHeader = this.createRequestHeader(requestHeaderByProperty);
			this.logger.info("request_header=[" + this.requestHeader + "]");
		} catch (PageHeaderImproperException e) {
			throw new PageIllegalArgumentException(ERROR_REQUEST_HEADER_IS_INVALID, e);
		}
		this.setRequestProperty(connection, requestHeaderByProperty);
		try {
			this.logger.info("connection start url=[" + this.url + "]");
			connection.connect();
			this.logger.info("connection fin");
			this.connection = connection;
		} catch (IOException e) {
			throw new PageAccessException( ERROR_INPUT_OUTPUT_EXCEPTION_OCCURRED_WHEN_CONNECTING_TO_A_URL, this.url.toString(), e );
		}
		Map<String, List<String>> responseHeader = connection.getHeaderFields();
		try {
			this.responseHeader = this.createResponseHeader(responseHeader);
			this.logger.info("response_header=[" + this.responseHeader + "]");
		} catch (PageHeaderImproperException e) {
			throw new PageIllegalArgumentException(ERROR_RESPONSE_HEADER_IS_INVALID, e);
		}
		if (readDataFlg) {
			this.logger.info("download start");
			this.byteDump = this.getByteDump(connection);
			this.logger.info("download fin size=[" + this.byteDump.length() + " Byte]");
		}
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のFORM要素からページのインスタンスを生成します。<br/>
	 * ページの情報はインスタンス生成時に読み込みます。<br/>
	 * 
	 * @param form FORM要素
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected Page(Form form) throws PageIllegalArgumentException, PageAccessException {
		this(form, new HashMap<String, String>(), true, new ArrayList<PageEventHandler>());
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のFORM要素からページのインスタンスを生成します。<br/>
	 * ページの情報はインスタンス生成時に読み込みます。<br/>
	 * 
	 * @param form FORM要素
	 * @param requestProperty リクエストヘッダマップ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected Page(Form form, Map<String, String> requestProperty) throws PageIllegalArgumentException, PageAccessException {
		this(form, requestProperty, true, new ArrayList<PageEventHandler>());
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のFORM要素からページのインスタンスを生成します。<br/>
	 * ページの情報はインスタンス生成時に読み込みます。<br/>
	 * 
	 * @param form FORM要素
	 * @param requestProperty リクエストヘッダマップ
	 * @param readDataFlg データ読み込みフラグ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected Page(Form form, Map<String, String> requestProperty, boolean readDataFlg) throws PageIllegalArgumentException, PageAccessException {
		this(form, requestProperty, readDataFlg, new ArrayList<PageEventHandler>());
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のFORM要素からページのインスタンスを生成します。<br/>
	 * <br/>
	 * データ読み込みフラグにtrueが設定されていた場合、このインスタンスが生成された際にページ情報を取得しメモリ上に保持します。<br/>
	 * falseの場合、getDataメソッドが呼ばれるまでページ情報の取得は行いません。
	 * 
	 * @param form FORM要素
	 * @param requestProperty リクエストヘッダマップ
	 * @param readDataFlg データ読み込みフラグ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected Page(Form form, Map<String, String> requestProperty, boolean readDataFlg, List<PageEventHandler> pageEventHandlerList) throws PageIllegalArgumentException, PageAccessException {
		this.logger.constractor(this.getClass(), form, requestProperty, new Boolean(readDataFlg), pageEventHandlerList);
		
		if (form == null) throw new PageIllegalArgumentException(ERROR_FORM_IS_NOT_SPECIFIED);
		if (requestProperty == null) requestProperty = new HashMap<String, String>();
		
		try {
			this.url = this.createUrl(form.getAction().getURL().toString());
		} catch (HtmlDocumentException e) {
			throw new PageIllegalArgumentException(ERROR_AN_INVALID_URL_WAS_SPECIFIED, e.getEmbeddedStrList(), e);
		}
		this.readDataFlg    = readDataFlg;
		this.eventHandler   = pageEventHandlerList;
		 
		try {
			this.requestHeader = this.createRequestHeader(requestProperty);
			this.logger.info("request_header=[" + this.requestHeader + "]");
		} catch (PageHeaderImproperException e) {
			throw new PageIllegalArgumentException(ERROR_REQUEST_HEADER_IS_INVALID, e);
		}
		URLConnection connection = this.createURLConnection(this.url.getUrlObject(), form.getMethod());
		this.connection = this.setRequestProperty(connection, requestProperty);
		try {
			this.logger.info("connection start url=[" + this.url + "]");
			BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream()));
			outputStream.write(form.createMessage());
			outputStream.close();
			this.logger.info("connection fin");
		} catch (IOException e) {
			throw new PageAccessException(ERROR_FAILED_TO_SEND_MESSAGE, new String[]{this.url.getURL(), form.getMethod().toString()}, e);
		}
		Map<String, List<String>> responseHeader = connection.getHeaderFields();
		try {
			this.responseHeader = this.createResponseHeader(responseHeader);
			this.logger.info("response_header=[" + this.responseHeader + "]");
			
		} catch (PageHeaderImproperException e) {
			throw new PageIllegalArgumentException(ERROR_RESPONSE_HEADER_IS_INVALID, e);
		}
		if (readDataFlg) {
			this.logger.info("download start");
			this.byteDump = this.getByteDump(connection);
			this.logger.info("download fin size=[" + this.byteDump.length() + " Byte]");
		}
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のURL文字列、通信した際のヘッダ、ページデータといった過去に通信した際に取得したデータからページのインスタンスを生成する。
	 * 
	 * @param url            URLを表す文字列
	 * @param requestHeader  リクエストヘッダ
	 * @param responseHeader レスポンスヘッダ
	 * @param data           ページデータ
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageHeaderImproperException  リクエストヘッダ、またはレスポンスヘッダが不正であった場合
	 */
	protected Page(String url, Map<String,String> requestHeader, Map<String, List<String>> responseHeader, ByteDump data, List<PageEventHandler> pageEventHandlerList) throws PageIllegalArgumentException, PageHeaderImproperException {
		this.logger.constractor(this.getClass(), url, requestHeader, responseHeader, data, pageEventHandlerList);
		if (url == null || url.equals("")) throw new PageIllegalArgumentException(ERROR_URL_IS_NOT_SET);
		this.url            = this.createUrl(url);
		this.requestHeader  = this.createRequestHeader(requestHeader);
		this.responseHeader = this.createResponseHeader(responseHeader);
		this.byteDump       = data;
		this.eventHandler   = pageEventHandlerList;
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
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	Page move(jp.co.dk.browzer.html.element.Form form, Map<String, String> requestHeader) throws PageIllegalArgumentException, PageAccessException {
		if (form == null) throw new PageIllegalArgumentException(ERROR_SPECIFIED_FORM_IS_NOT_SET);
		if (!this.equals(form.getPage())) throw new PageIllegalArgumentException(ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
		if (requestHeader == null) requestHeader = new HashMap<String, String>();
		Map<String, String> defaultRequestHeader  = this.getRequestHeaderByPorperty();
		Map<String, String> cookieRequestHeader   = this.getCookies();
		defaultRequestHeader.putAll(cookieRequestHeader);
		defaultRequestHeader.putAll(requestHeader);
		return new Page(form, defaultRequestHeader, this.readDataFlg, this.eventHandler);
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
		return this.url.getHost();
	}
	
	/**
	 * URL取得<p>
	 * URLを取得する。
	 * 
	 * @return URL文字列
	 */
	public String getURL() {
		return this.url.toString();
	}
	
	/**
	 * URLオブジェクトを取得<p>
	 * 
	 * @return URL文字列
	 */
	public URL getURLObject() {
		return this.url.getUrlObject();
	}
	
	/**
	 * このページのURLオブジェクトを返却します。
	 * @return URLオブジェクト
	 */
	public Url getUrl() {
		return this.url;
	}
	
	/**
	 * ドキュメントオブジェクトを取得する。<p>
	 * デフォルトのドキュメントファクトリを使用し、このページが表すドキュメントオブジェクトを生成、返却します。<br/>
	 * すでにドキュメントが生成されていた場合、キャッシュされているドキュメントオブジェクトを返却します。
	 * 
	 * @return ドキュメントオブジェクト
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 */
	public jp.co.dk.document.File getDocument() throws PageAccessException, DocumentException {
		if (this.document != null) return this.document;
		for (PageEventHandler handler : this.eventHandler) handler.beforeCreateDocument(this);
		try {
			this.document = this.getDocument(new DocumentFactory(this));
			return this.document;
		} catch (PageAccessException e) {
			for (PageEventHandler handler : this.eventHandler) handler.errorCreateDocument(e);
			throw e;
		}finally {
			for (PageEventHandler handler : this.eventHandler) handler.afterCreateDocument(this);
		}
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 */
	public jp.co.dk.document.File getDocument(DocumentFactory documentFactory) throws PageAccessException, DocumentException {
		InputStream inputStream = this.getData().getStream();
		ContentsType contentsType = this.responseHeader.getContentsType();
		if (contentsType != null) {
			this.document = documentFactory.create(contentsType, inputStream);
			return this.document;
		} else {
			BrowzingExtension extension = this.getExtension(this.getFileName());
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
		return this.url.getParameter();
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
		List<String> pathList = this.url.getPathList();
		if (pathList.size() == 0) return "";
		String fileName = this.getFileName();
		StringBuilder sb = new StringBuilder();
		for (String path : pathList) {
			if (fileName.equals(path)) break; 
			sb.append(path).append('/');
		}
		return sb.toString();
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
		return this.url.getPathList();
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
		List<String> pathList = this.url.getPathList();
		if (pathList == null || pathList.size() == 0) return this.defaultFileName(responseHeader);
		String lastPath = pathList.get(pathList.size()-1);
		if (lastPath.lastIndexOf('.') == -1) {
			return this.defaultFileName(responseHeader);
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
		return this.url.getProtocol();
	}
	
	/**
	 * <p>このページ内の文字列を取得する。</p>
	 * このページがHTMLの場合に限り、このページの文字列を取得する。<br/>
	 * それ以外の場合、空文字を返却する。
	 * 
	 * @return ページ内の文字列
	 */
	public String getText() {
		if (!(this.document instanceof HtmlDocument)) return "";
		HtmlDocument htmlDocument = (HtmlDocument)this.document;
		return htmlDocument.getContent();
	}
	
	/**
	 * アンカー一覧を取得します。<p/>
	 * このページに存在するすべてのアンカータグを取得します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return アンカー一覧 
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchor() throws PageAccessException, DocumentException {
		jp.co.dk.document.File document = this.getDocument();
		if (!(document instanceof HtmlDocument)) throw new DocumentException(ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML); 
		HtmlDocument htmlDocument = (HtmlDocument)document;
		List<Element> elementList = htmlDocument.getElement(HtmlElementName.A);
		List<A> anchorList = new ArrayList<A>();
		for (Element element : elementList){
			if (element instanceof A) anchorList.add((A)element);
		}
		return anchorList;
	}
	
	/**
	 * このページのURLとドメインが同一のアンカー一覧を取得します。<p/>
	 * このページに存在するすべてのアンカータグを取り出し、このページと同じドメインのURLのみ抽出し返却します。<br/>
	 * このページと同じドメインのアンカータグが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return 同じドメインのアンカー一覧 
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchorSameDomain() throws PageAccessException, DocumentException {
		List<A> allAnchorList        = this.getAnchor();
		List<A> sameDomainAnchorList = new ArrayList<A>();
		String  thisPageDmain        = this.url.getHost();
		for (A anchor : allAnchorList) {
			try {
				Url url = this.createUrl(anchor.getUrl());
				String anchorsDomain = url.getHost();
				if (thisPageDmain.equals(anchorsDomain)) sameDomainAnchorList.add(anchor);
			} catch (PageIllegalArgumentException e) {
				continue;
			}
		}
		return sameDomainAnchorList;
	}
	
	/**
	 * このページのURLとドメインとパスが同一のアンカー一覧を取得します。<p/>
	 * このページに存在するすべてのアンカータグを取り出し、このページと同じドメインとパスのURLのみ抽出し返却します。<br/>
	 * このページと同じドメインとパスのアンカータグが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return 同じドメインとパスのアンカー一覧 
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchorSamePath() throws PageAccessException, DocumentException {
		List<A>      allAnchorList        = this.getAnchor();
		List<A>      sameDomainAnchorList = new ArrayList<A>();
		String       thisPageDmain        = this.url.getHost();
		List<String> thisPagePathList     = this.url.getPathList();
		for (A anchor : allAnchorList) {
			try {
				Url url = this.createUrl(anchor.getUrl());
				String anchorsDomain         = url.getHost();
				List<String> anchorsPathList = url.getPathList();
				if (thisPageDmain.equals(anchorsDomain) && thisPagePathList.equals(anchorsPathList)) sameDomainAnchorList.add(anchor);
			} catch (PageIllegalArgumentException e) {
				continue;
			}
		}
		return sameDomainAnchorList;
	}
	
	/**
	 * このページに存在するアンカーで拡張子がhtml,htmのアンカー一覧を取得します。<p/>
	 * このページに存在するすべてのアンカータグを取り出し、アンカーに定義されているURLでhtml,htmと定義されているアンカーを抽出し返却します。<br/>
	 * 該当のアンカーが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return このページに存在するアンカーで拡張子がhtml,htmのアンカー一覧 
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchorIncludeHtml() throws PageAccessException, DocumentException {
		List<A>      allAnchorList        = this.getAnchor();
		List<A>      sameDomainAnchorList = new ArrayList<A>();
		for (A anchor : allAnchorList) {
			try {
				Url url = this.createUrl(anchor.getUrl());
				List<String> anchorsPathList = url.getPathList();
				if (anchorsPathList.size() != 0) {
					String lastPath = anchorsPathList.get(anchorsPathList.size()-1);
					if (lastPath.endsWith(".html") || lastPath.endsWith(".htm")) sameDomainAnchorList.add(anchor);
				}
			} catch (PageIllegalArgumentException e) {
				continue;
			}
		}
		return sameDomainAnchorList;
	}
	
	/**
	 * このページに存在するアンカーでhtml,htm以外の拡張子を保持するアンカー一覧を取得します。<p/>
	 * このページに存在するすべてのアンカータグを取り出し、アンカーに定義されているURLで拡張子が定義されているアンカーを抽出し返却します。<br/>
	 * 該当のアンカーが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return このページに存在するアンカーでhtml,htm以外の拡張子を保持するアンカー一覧
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchorExcludeHtml() throws PageAccessException, DocumentException {
		List<A>      allAnchorList        = this.getAnchor();
		List<A>      sameDomainAnchorList = new ArrayList<A>();
		for (A anchor : allAnchorList) {
			try {
				Url url = this.createUrl(anchor.getUrl());
				List<String> anchorsPathList = url.getPathList();
				if (anchorsPathList.size() != 0) {
					String lastPath = anchorsPathList.get(anchorsPathList.size()-1);
					if (lastPath.indexOf(".") != -1 && !(lastPath.endsWith(".html") || lastPath.endsWith(".htm"))) sameDomainAnchorList.add(anchor);
				}
			} catch (PageIllegalArgumentException e) {
				continue;
			}
		}
		return sameDomainAnchorList;
	}
	
	/**
	 * フォーム一覧を取得します。<p/>
	 * このページに存在するすべてのフォームタグを取得します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return フォーム一覧
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<Form> getForm() throws PageAccessException, DocumentException {
		jp.co.dk.document.File document = this.getDocument();
		if (!(document instanceof HtmlDocument)) throw new DocumentException(ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML); 
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
		return this.responseHeader.getContentLength();
	}
	
	/**
	 * このページのデータを取得します。
	 * 
	 * このページがすでにデータを取得しており、メモリー内に読み込み済みだった場合、そのデータを返却します。<br/>
	 * このページのデータを読み込んでいなかった場合、ページからそのデータをダウンロード、メモリー内に保存してから返却します。
	 * 
	 * @return バイトダンプのインスタンス
	 * @throws PageAccessException 読み込みにて例外が発生した場合
	 */
	public ByteDump getData() throws PageAccessException {
		if (this.byteDump == null) this.byteDump = this.getByteDump(this.connection); 
		return this.byteDump;
	}
	
	/**
	 * このページのリクエストヘッダを取得します。<p/>
	 * 
	 * @return リクエストヘッダ
	 */
	public RequestHeader getRequestHeader() {
		return this.requestHeader;
	}
	
	/**
	 * このページのレスポンスヘッダを取得します。<p/>
	 * 
	 * @return レスポンスヘッダ
	 */
	public ResponseHeader getResponseHeader() {
		return this.responseHeader;
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 * @throws PageSaveException ページの保存に失敗した場合
	 */
	public File save(File path) throws PageAccessException, DocumentException, PageSaveException {
		jp.co.dk.document.File document = this.getDocument();
		try {
			return document.save(path, this.getFileName());
		} catch (DocumentException e) {
			throw new PageSaveException(ERROR_FAILED_TO_DOWNLOAD, this.url.getURL(), e);
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 * @throws PageSaveException ページの保存に失敗した場合
	 */
	public File save(File path, String fileName) throws PageAccessException, DocumentException, PageSaveException {
		jp.co.dk.document.File document = this.getDocument();
		try {
			return document.save(path, fileName);
		} catch (DocumentException e) {
			throw new PageSaveException(ERROR_FAILED_TO_DOWNLOAD, this.url.getURL(), e);
		}
	}
	
	/**
	 * 指定のURLの文字列を元にUrlオブジェクトを生成し、返却します。
	 * @param url URL文字列
	 * @return Urlオブジェクト
	 * @throws PageIllegalArgumentException URL文字列がnullまたは、空文字だった場合
	 */
	protected Url createUrl(String url) throws PageIllegalArgumentException {
		return new Url(url);
	}
	
	/**
	 * このURLが示すリンク先を指定のディレクトリへダウンロードを実行します。<p>
	 * 指定のパスにディレクトリ以外が設定された場合、例外を発生します。<br/>
	 * 
	 * @param path ダウンロード先ディレクトリパス
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 * @throws PageSaveException ページの保存に失敗した場合
	 */
	protected void download(File path) throws PageAccessException, DocumentException, PageSaveException {
		StringBuilder pathStr = new StringBuilder(path.getAbsolutePath()).append('/').append(this.url.getHost()).append('/').append(this.getPath());
		File downloadPath = new File(pathStr.toString());
		try {
			downloadPath.mkdirs();
		} catch (SecurityException e) {
			throw new PageSaveException(ERROR_COULD_NOT_CREATE_DOWNLOAD_DIRECTORY, pathStr.toString(), e);
		}
		jp.co.dk.document.File document = this.getDocument();
		try {
			document.save(downloadPath, this.getFileName());
		} catch (DocumentException e) {
			throw new PageSaveException(ERROR_FAILED_TO_DOWNLOAD, this.url.getURL(), e);
		}
	}
	
	/**
	 * このページのURLから引数に設定されたURLで補完し、新しいURLを返却します。
	 * 
	 * @param url 補完対象URL
	 * @return 補完済みURL
	 * @throws PageIllegalArgumentException 補完に失敗した場合
	 */
	public String completionURL(String url) throws PageIllegalArgumentException {
		try {
			return new URL(this.url.getUrlObject(), url).toString();
		} catch (MalformedURLException e) {
			throw new PageIllegalArgumentException(ERROR_COMPRETION_URL, new String[]{this.url.getURL(), url}, e);
		}
	}
	
	/**
	 * 指定のURLオブジェクトからURLコネクションを指定メソッドで生成します。<p>
	 * 
	 * @param urlObj URLオブジェクト
	 * @param method HTTPメソッド
	 * @return URLコネクション
	 * @throws BrowzingException URLが参照するリモートオブジェクトへの接続時に入出力例外が発生した場合
	 */
	protected HttpURLConnection createURLConnection(URL urlObj, HtmlRequestMethodName method) throws PageAccessException{
		for (PageEventHandler handler : this.eventHandler) handler.beforeOpenConnection(this.url, method);
		HttpURLConnection urlConnection;
		try {
			urlConnection = (HttpURLConnection)urlObj.openConnection();
			urlConnection.setRequestMethod(method.getMethod());
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setFollowRedirects(false);
		} catch (IOException e) {
			PageAccessException pageAccessException = new PageAccessException( ERROR_INPUT_OUTPUT_EXCEPTION_OCCURRED_WHEN_CONNECTING_TO_A_URL, urlObj.toString(), e );
			for (PageEventHandler handler : this.eventHandler) handler.errorOpenConnection(pageAccessException);
			throw pageAccessException;
		} finally {
			for (PageEventHandler handler : this.eventHandler) handler.afterOpenConnection();
		}
		return urlConnection;
	}
	
	
	/**
	 * 指定のURLコネクションから入力ストリームを取得します。<p>
	 * 
	 * @param connection URLコネクション
	 * @return 入力ストリーム
	 * @throws PageAccessException 入力ストリームの作成中に入出力エラーが発生した場合
	 */
	protected InputStream getUrlInputStream( URLConnection connection ) throws PageAccessException {
		InputStream inputStream;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			throw new PageAccessException(ERROR_IO_ERROR_WAS_OCCURRED_WHILE_CREATING_THE_INPUT_STREAM, this.url.toString() ,e);
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
	 * 指定のURLコネクションへ指定のリクエストヘッダマップに設定されているリクエストヘッダを設定します。<p>
	 * リクエストヘッダが未指定の場合はそのまま返却します。
	 * 
	 * @param connection URLコネクション
	 * @param requestMap リクエストヘッダマップ
	 * @return リクエストヘッダが設定されたURLコネクション
	 */
	protected URLConnection setRequestProperty(URLConnection connection ,Map<String,String> requestMap) {
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
		String path=this.getPath();
		return path.indexOf('/') == path.length();
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
		List<String> coolies = this.responseHeader.getHeader(HeaderField.SET_COOKIE);
		if (coolies == null || coolies.size() == 0) return map;
		StringBuilder sb = new StringBuilder();
		for (String cookie : coolies) sb.append(cookie).append(',');
		map.put(HeaderField.COOKIE.getHeaderField(), sb.substring(0, sb.length()-1));
		return map;
	}
	
	/**
	 * このページのURLにファイル名が指定されていない場合に限り、ファイル名を取得します。<p/>
	 * ファイル名は固定値で"default"が設定されます。<br/>
	 * <br/>
	 * 変更する場合はオーバーライドし、変更してください。<br/>
	 * <br/>
	 * 拡張子はレスポンスヘッダの"Content-Type"のデフォルト拡張子より取得できます。<br/>
	 * （responseHeader.getContentsType().getDefaultExtension()にて取得）<br/>
	 * "Content-Type"が設定されていない場合、nullが返却されます。<br/>
	 * 
	 * @param responseHeader レスポンスヘッダ
	 * @return ファイル名
	 */
	protected String defaultFileName(ResponseHeader responseHeader) {
		StringBuilder sb = new StringBuilder("index");
		ContentsType contentType = responseHeader.getContentsType();
		if (contentType != null) {
			String extension = contentType.getDefaultExtension();
			sb.append('.').append(extension);
		}
		return sb.toString();
	}
	
	/**
	 * URLコネクションを元にバイトダンプのクラスオブジェクトを取得します。<p/>
	 * 読み込みにて例外が発生した場合、BrowzingExceptionをthrowします。
	 * 
	 * @param connection URLコネクションオブジェクト
	 * @return バイトダンプのインスタンス
	 * @throws PageAccessException 読み込みにて例外が発生、またはドキュメント解析に失敗した場合
	 */
	protected ByteDump getByteDump(URLConnection connection) throws PageAccessException {
		for (PageEventHandler handler : this.eventHandler) handler.beforeGetData(this);
		InputStream pageDataStream = this.getUrlInputStream(connection);
		try {
			ByteDump data = new ByteDump(pageDataStream);
			return data;
		} catch (DocumentException e) {
			PageAccessException pageAccessError = new PageAccessException(ERROR_READ_PROCESS_FAILED, this.url.getURL(), e);
			for (PageEventHandler handler : this.eventHandler) handler.errorGetData(pageAccessError);
			throw pageAccessError;
		} finally {
			for (PageEventHandler handler : this.eventHandler) handler.afterGetData(this);
		}
		
	}
	
	/**
	 * リクエストヘッダのマップオブジェクトからリクエストヘッダオブジェクトのインスタンスを生成して返却します。
	 * 
	 * @param requestHeader リクエストヘッダのマップオブジェクト
	 * @return リクエストヘッダオブジェクト
	 * @throws PageHeaderImproperException リクエストヘッダのインスタンス生成に失敗した場合
	 */
	protected RequestHeader createRequestHeader(Map<String, String> requestHeader) throws PageHeaderImproperException {
		return new RequestHeader(requestHeader);
	}
	
	/**
	 * レスポンスヘッダのマップオブジェクトからレスポンスヘッダオブジェクトのインスタンスを生成して返却します。
	 * 
	 * @param responseHeader レスポンスヘッダのマップオブジェクト
	 * @return レスポンスヘッダオブジェクト
	 * @throws PageHeaderImproperException レスポンスヘッダのインスタンス生成に失敗した場合
	 */
	protected ResponseHeader createResponseHeader(Map<String, List<String>> responseHeader) throws PageHeaderImproperException {
		return new ResponseHeader(responseHeader);
	}
	
	@Override
	public String toString() {
		return this.url.toString();
	}

}
