package jp.co.dk.browzer.html.element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import jp.co.dk.browzer.Page;
import jp.co.dk.document.html.HtmlElement;

/**
 * Aは、HTMLのアンカー要素を表す要素クラス。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class A extends jp.co.dk.document.html.element.A {
	
	private Page page;
	
	/**
	 * コンストラクタ
	 * 
	 * HTMLの要素のインスタンスを生成する。
	 * 
	 * @param element HTMLパーサの要素インスタンス
	 * @param page ページオブジェクト
	 */
	public A(HtmlElement element, Page page) {
		super(element);
		this.page = page;
	}
	
	@Override
	public String getHref() {
		try {
			URL url = new URL(this.page.getURLObject(),  super.getHref());
			return url.toString();
		} catch (MalformedURLException e) {
			return super.getHref();
		}
	}
	
	/**
	 * 本アンカーの取得元であるページを取得します。
	 * 
	 * @return ページ
	 */
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
			URL url = new URL(this.getHref());
			return url.getHost();
		} catch (MalformedURLException e) {
			return super.getHref();
		}
	}
	
	
	public double samelevel() {
		String url1 = this.page.getURL();
		String url2 = this.getHref();
		char[] chararray1 = url1.toCharArray();
		char[] chararray2 = url2.toCharArray();
		int cost = 0;
		int d[][] = new int[chararray1.length+1][chararray2.length+1];
		for (int i=0; i<chararray1.length; i++) d[i][0]=i;
		for (int i=0; i<chararray2.length; i++) d[0][i]=i;
		for (int i1=1; i1<=chararray1.length; i1++) {
			for (int i2=1; i2<=chararray2.length; i2++) {
				if (chararray1[i1] == chararray2[i2]) {
					cost = 0;
				} else {
					cost = 1;
				}
				int add = d[i1-1][i2  ]+1; // 文字の挿入
				int del = d[i1  ][i2-1]+1; // 文字の削除
				int rep = d[i1-1][i2-1]+1+cost; // 文字の置換
				int[] sort = {add, del, rep};
				Arrays.sort(sort);
				d[i1][i2] = sort[0];
			}
		}
		return d[chararray1.length+1][chararray2.length+1];
	}
}
