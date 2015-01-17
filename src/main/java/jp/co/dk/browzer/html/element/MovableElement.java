package jp.co.dk.browzer.html.element;

import jp.co.dk.browzer.Page;

/**
 * MovableElementは、URLなどの外部参照属性を保持する可能なHTML要素が実装する遷移可能要素インターフェースです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public interface MovableElement {
	
	/**
	 * この要素に定義されているURLを取得します。<p/>
	 * URLが定義されている属性の値を取得します。<br/>
	 * 取得に失敗した場合、空文字を返却します。
	 * 
	 * @return URLを表す文字列
	 */
	public String getUrl();
	
	/**
	 * 本アンカーの取得元であるページを取得します。
	 * 
	 * @return ページ
	 */
	public Page getPage();
}
