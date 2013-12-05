package jp.co.dk.browzer.html.element;

import java.net.MalformedURLException;
import java.net.URL;

import jp.co.dk.browzer.Page;
import jp.co.dk.document.html.HtmlElement;

/**
 * Scriptは、HTMLのScript要素を表す要素クラス。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Script extends jp.co.dk.document.html.element.Script implements MovableElement{
	
	private Page page;
	
	/**
	 * コンストラクタ
	 * 
	 * HTMLの要素のインスタンスを生成する。
	 * 
	 * @param element HTMLパーサの要素インスタンス
	 * @param page ページオブジェクト
	 */
	public Script(HtmlElement element, Page page) {
		super(element);
		this.page = page;
	}
	
	@Override
	public String getSrc() {
		try {
			URL url = new URL(this.page.getURLObject(),  super.getSrc());
			return url.toString();
		} catch (MalformedURLException e) {
			return super.getSrc();
		}
	}
	
	@Override
	public Page getPage() {
		return this.page;
	}
	
	/**
	 * このアンカーからホスト名を取得する。
	 * 
	 * @return ホスト名
	 */
	public String getHost() {
		try {
			URL url = new URL(this.getSrc());
			return url.getHost();
		} catch (MalformedURLException e) {
			return super.getSrc();
		}
	}
}
