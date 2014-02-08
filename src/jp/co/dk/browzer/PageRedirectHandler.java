package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.exception.PageRedirectException;
import jp.co.dk.browzer.exception.PageRedirectException;
import jp.co.dk.browzer.http.header.ResponseHeader;
import jp.co.dk.browzer.http.header.record.HttpStatusCode;
import jp.co.dk.document.Element;
import jp.co.dk.document.File;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.constant.HttpEquivName;
import jp.co.dk.document.html.element.Meta;

/**
 * PageRedirectHandlerは、ページ接続完了後、その結果に対してのイベントを定義するクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class PageRedirectHandler {
	
	/** イベントハンドラ */
	List<PageEventHandler> eventHandler;
	
	/**
	 * コンストラクタ<p/>
	 * 指定のイベントハンドラ一覧を元にページリダイレクトハンドラを生成します。
	 * 
	 * @param eventHandler イベントハンドラ一覧
	 */
	PageRedirectHandler(List<PageEventHandler> eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	/**
	 * ページへの接続時、ヘッダ情報やページ内容を解析し、新しいページへのリダイレクトを行う。<p/>
	 * <br/>
	 * リダイレクト判定は以下のように行われます。<br/> 
	 * ・サーバより返却されたHTTPヘッダのHTTPステータスコード<br/>
	 * 1XX…情報返却として、そのままのページオブジェクトを返却します。<br/>
	 * 2XX…正常に通信成功として、そのままのページオブジェクトを返却します。<br/>
	 * 3XX…リダイレクトが実施され、リダイレクト先のページを返却します。<br/>
	 * 4XX…クライアントエラーとして PageRedirectException を throw します。<br/>
	 * 5XX…サーバエラーとして PageRedirectException を throw します。<br/>
	 * 
	 * @param page 遷移元ページ
	 * @return 遷移先ページ
	 * @throws PageRedirectException 遷移に失敗した場合
	 */
	Page redirect(Page page) throws PageRedirectException {
		ResponseHeader header = page.getResponseHeader();
		HttpStatusCode httpStatusCode = header.getResponseRecord().getHttpStatusCode();
		switch(httpStatusCode.getStatusType()) {
			case INFOMATIONAL:
				return this.redirectBy_REDIRECTION(header, page);
				
			case SUCCESS:
				return this.redirectBy_SUCCESS(header, page);
				
			case REDIRECTION:
				return this.redirectBy_REDIRECTION(header, page);
				
			case CLIENT_ERROR:
				return this.redirectBy_CLIENT_ERROR(header, page);
				
			case SERVER_ERROR:
				return this.redirectBy_SERVER_ERROR(header, page);
				
			default:
				break;
		}
		return page;
	}
	
	/**
	 * HTTPサーバより返却されたHTTPステータスコードが「1XX」を返却した場合の制御を定義するメソッドです。<br/>
	 * 情報返却として、そのままのページオブジェクトを返却します。
	 * 
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 * @return ページオブジェクト
	 * @throws PageRedirectException 遷移に失敗した場合
	 */
	protected Page redirectBy_INFOMATIONAL(ResponseHeader header, Page page) throws PageRedirectException {
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.beforeRedirect(header, page);
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.afterRedirect();
		return page;
	}
	
	/**
	 * HTTPサーバより返却されたHTTPステータスコードが「2XX」を返却した場合の制御を定義するメソッドです。<br/>
	 * 正常に通信成功として、そのままのページオブジェクトを返却します。<br/>
	 * <br/>
	 * ただし、ページがHTMLであり、METAタグに「refresh」が設定されていた場合、定義された指定秒数スリープ後、「url」に定義された<br/>
	 * URLへ遷移し、そのページのオブジェクトを返却します。
	 * 
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 * @return ページオブジェクト
	 * @throws PageRedirectException 遷移に失敗した場合
	 */
	protected Page redirectBy_SUCCESS(ResponseHeader header, Page page) throws PageRedirectException {
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.beforeRedirect(header, page);
		File file = page.getDocument();
		if (file instanceof HtmlDocument) {
			HtmlDocument htmlDocument = (HtmlDocument)file;
			List<Element> metaElementList = htmlDocument.getElement(HtmlElementName.META);
			for (Element element : metaElementList) {
				if (element instanceof Meta) {
					Meta meta = (Meta)element;
					if (HttpEquivName.REFRESH == meta.getHttpEquiv()) {
						String contents = meta.getContent();
						if (contents.equals("")) contents = "1"; 
						int sleepType = Integer.parseInt(contents);
						try {
							Thread.sleep(sleepType * 1000);
						} catch (InterruptedException e) {
							throw new PageRedirectException(ERROR_THREAD_STOP, e); 
						}
						String url = meta.getAttribute("url");
						return this.ceatePage(page.completionURL(url));
					}
				}
			}
			for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.afterRedirect();
			return page;
		} else {
			for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.afterRedirect();
			return page;
		}
	}
	
	/**
	 * HTTPサーバより返却されたHTTPステータスコードが「3XX」を返却した場合の制御を定義するメソッドです。<br/>
	 * リダイレクトが実施され、リダイレクト先のページを返却します。<br/>
	 * <br/>
	 * リダイレクト先が指定されていなかった場合、遷移失敗とし、例外を送出します。
	 * 
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 * @return ページオブジェクト
	 * @throws PageRedirectException 遷移に失敗した場合
	 */
	protected Page redirectBy_REDIRECTION(ResponseHeader header, Page page) throws PageRedirectException {
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.beforeRedirect(header, page);
		String location = header.getLocation();
		if (location == null || location.equals("")) throw new PageRedirectException(ERROR_REDIRECT_LOCATION_NOT_FOUND); 
		Page nextPage = this.ceatePage(page.completionURL(location));
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.afterRedirect();
		return nextPage;
	}
	
	/**
	 * HTTPサーバより返却されたHTTPステータスコードが「4XX」を返却した場合の制御を定義するメソッドです。<br/>
	 * クライアントエラーとして PageRedirectException を throw します。<br/>
	 * 「401」が返却された場合、アクセス認証を行います。（未実装）
	 * 
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 * @return ページオブジェクト
	 * @throws PageRedirectException 遷移に失敗した場合
	 */
	protected Page redirectBy_CLIENT_ERROR(ResponseHeader header, Page page) throws PageRedirectException {
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.beforeRedirect(header, page);
		HttpStatusCode httpStatusCode = header.getResponseRecord().getHttpStatusCode();
		if (httpStatusCode == HttpStatusCode.STATUS_401) {
			BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
			System.out.print(INFO_PLEASE_INPUT_USER_AND_PASSWORD.getMessage());
			System.out.print(INFO_USER.getMessage());
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// throw new PageRedirectException(e);
			}
			
			System.out.print(INFO_USER.getMessage());
		}
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.afterRedirect();
		throw new PageRedirectException(ERROR_HTTP_STATUS_CODE_IS_SENT_BACK_FROM_SERVER_HAS_RETURNED_NON_NORMAL, 
				new String[]{ httpStatusCode.getCode(), httpStatusCode.getMessage().getMessage(), page.getURL()});
		
	}
	
	/**
	 * HTTPサーバより返却されたHTTPステータスコードが「5XX」を返却した場合の制御を定義するメソッドです。<br/>
	 * サーバエラーとして PageRedirectException を throw します。<br/>
	 * 
	 * @param header ページのレスポンスヘッダ
	 * @param page   ページオブジェクト
	 * @return ページオブジェクト
	 * @throws PageRedirectException 遷移に失敗した場合
	 */
	protected Page redirectBy_SERVER_ERROR(ResponseHeader header, Page page) throws PageRedirectException {
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.beforeRedirect(header, page);
		HttpStatusCode httpStatusCode = header.getResponseRecord().getHttpStatusCode();
		for (PageEventHandler pageEventHandler : eventHandler) pageEventHandler.afterRedirect();
		throw new PageRedirectException(ERROR_HTTP_STATUS_CODE_IS_SENT_BACK_FROM_SERVER_HAS_RETURNED_NON_NORMAL, 
				new String[]{ httpStatusCode.getCode(), httpStatusCode.getMessage().getMessage(), page.getURL()});
		
	}
	
	/**
	 * 指定のURLからページオブジェクトを作成する。
	 * 
	 * @param url URL文字列
	 * @return ページオブジェクト
	 * @throws PageRedirectException ページクラスの生成に失敗した場合
	 */
	protected Page ceatePage(String url) throws PageRedirectException {
		return new Page(url);
	}
}