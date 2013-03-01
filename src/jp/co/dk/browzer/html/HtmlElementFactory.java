package jp.co.dk.browzer.html;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.html.element.Image;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.document.html.HtmlElement;

/**
 * HtmlElementFactoryは、ブラウザにて使用するHTML要素の生成を行うファクトリクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class HtmlElementFactory extends jp.co.dk.document.html.HtmlElementFactory {
	
	private Page page;
	
	public HtmlElementFactory(Page page) {
		this.page = page;
	}
	
	@Override
	protected A createAnchor(HtmlElement htmlElement) {
		return new A(htmlElement, this.page);
	}
	
	@Override
	protected Image createImage(HtmlElement htmlElement) {
		return new Image(htmlElement, this.page);
	}
	
	protected Form createForm(HtmlElement htmlElement) {
		return new Form(htmlElement, this.page);
	}
}
