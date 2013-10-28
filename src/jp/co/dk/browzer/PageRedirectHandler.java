package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.exception.BrowzingException;
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
	
	/**
	 * ページへの接続時、ヘッダ情報やページ内容を解析し、新しいページへのリダイレクトを行う。<p/>
	 * <br/>
	 * リダイレクト判定は以下のように行われます。<br/> 
	 * ・サーバより返却されたHTTPヘッダのHTTPステータスコード<br/>
	 * 1XX…情報返却として、そのままのページオブジェクトを返却します。<br/>
	 * 2XX…正常に通信成功として、そのままのページオブジェクトを返却します。<br/>
	 * 3XX…リダイレクトが実施され、リダイレクト先のページを返却します。<br/>
	 * 4XX…クライアントエラーとして BrowzingException を throw します。<br/>
	 * 5XX…サーバエラーとして BrowzingException を throw します。<br/>
	 * 
	 * @param page 遷移元ページ
	 * @return 遷移先ページ
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	Page redirect(Page page) throws BrowzingException {
		ResponseHeader header = page.getResponseHeader();
		HttpStatusCode httpStatusCode = header.getResponseRecord().getHttpStatusCode();
		switch(httpStatusCode.getStatusType()) {
			case INFOMATIONAL:
				return page;
				
			case SUCCESS:
				
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
									throw new BrowzingException(ERROR_THREAD_STOP, e); 
								}
								String url = meta.getAttribute("url");
								return new Page(page.completionURL(url));
							}
						}
					}
					return page;
				} else {
					return page;
				}
				
			case REDIRECTION:
				String location = header.getLocation();
				if (location == null || location.equals("")) throw new BrowzingException(ERROR_REDIRECT_LOCATION_NOT_FOUND); 
				return new Page(page.completionURL(location));
				
			case CLIENT_ERROR:
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
						// throw new BrowzingException(e);
					}
					
					System.out.print(INFO_USER.getMessage());
				}
				
				throw new BrowzingException(ERROR_HTTP_STATUS_CODE_IS_SENT_BACK_FROM_SERVER_HAS_RETURNED_NON_NORMAL, 
						new String[]{ httpStatusCode.getCode(), httpStatusCode.getMessage().getMessage(), page.getURL()});
				
			case SERVER_ERROR:
				throw new BrowzingException(ERROR_HTTP_STATUS_CODE_IS_SENT_BACK_FROM_SERVER_HAS_RETURNED_NON_NORMAL, 
						new String[]{ httpStatusCode.getCode(), httpStatusCode.getMessage().getMessage(), page.getURL()});
				
			default:
				break;
		}
		return page;
	}
}