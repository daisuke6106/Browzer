package jp.co.dk.browzer.html.element;

import jp.co.dk.browzer.Page;
import jp.co.dk.document.html.HtmlElement;

/**
 * Formは、HTMLのForm要素を表す要素クラス。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Form extends jp.co.dk.document.html.element.Form {
	
	private Page page;
	
	/**
	 * コンストラクタ
	 * 
	 * HTMLの要素のインスタンスを生成する。
	 * 
	 * @param element HTMLパーサの要素インスタンス
	 * @param page ページオブジェクト
	 */
	public Form(HtmlElement element, Page page) {
		super(element);
		this.page = page;
	}
	
	@Override
	public Action getAction(){
		return new Action(this);
	}
	
	/**
	 * 本FORM要素を保持するページオブジェクトを返却します。<p/>
	 * 
	 * @return ページオブジェクト
	 */
	public Page getPage() {
		return this.page;
	}
}
