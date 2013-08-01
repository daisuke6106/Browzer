package jp.co.dk.browzer.html.element;

import java.net.MalformedURLException;
import java.net.URL;

import jp.co.dk.browzer.Page;
import jp.co.dk.document.html.exception.HtmlDocumentException;
import jp.co.dk.document.message.DocumentMessage;

/**
 * Actionは、FORM要素のアクションの要素を表すクラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Action extends jp.co.dk.document.html.element.Action{
	
	/**
	 * コンストラクタ
	 * @param form
	 */
	Action(Form form){
		super(form);
	}
	
	@Override
	public URL getURL() throws HtmlDocumentException {
		String url = this.toString();
		try {
			Form form = (Form)super.form;
			Page parentPage = form.getPage();
			return new URL(parentPage.getURLObject(), url);
		} catch (MalformedURLException e) {
			throw new HtmlDocumentException(DocumentMessage.ERROR_AN_INVALID_URL_WAS_SPECIFIED, url, e);
		}
	}
}
