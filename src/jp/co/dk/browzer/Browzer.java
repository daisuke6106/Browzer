
package jp.co.dk.browzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.PageRedirectHandler;
import jp.co.dk.browzer.download.DownloadJudge;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.html.element.Image;
import jp.co.dk.xml.Attribute;
import jp.co.dk.xml.XmlConvertable;
import static jp.co.dk.browzer.message.BrowzingMessage.*;

/**
 * Browzerは、ページの遷移を管理するクラスです。<p/>
 * 
 * 本クラスはGoogle Chromeや、FireFox等のインターネットブラウザと同様に指定のURLを元にホームページを開き、<br/>
 * そのページに設定されているリンク（アンカー）をクリック、またはsubmitボタンを押下することでページ遷移していくのと同等の処理を行うためのクラスです。<br/>
 * 本クラス生成時に指定されたURLを元にオブジェクトを生成し、そのURLのページを親画面とし、そのページに設定されたアンカータグやフォームを元にmoveメソッドの呼び出しを行うことでページ遷移を行なっていきます。<br/>
 * backメソッドを呼び出すことで遷移先のページから遷移元ページに戻ります。<br/>
 * また、指定のディレクトリへのパスを元にsaveメソッドを呼び出すことで現在アクティブになっているページを指定のディレクトリへ保存。<br/>
 * <br/>
 * 最大ネスト数指定<br/>
 * 親画面から遷移可能な画面の深さを指定することができます。<br/>
 * 例えば、ブラウザをURLを http://www.google.co.jp にて起動するとそのページはネストレベル0で起動します。<br/>
 * http://www.google.co.jpに設定されているリンクを元にmoveメソッドでページ遷移を行った場合、ネストレベルを1が設定、そのページから更にページ遷移を行った場合、ネストレベル2といった順でネストレベルが増えていきます。<br/>
 * <br/>
 * backメソッドでページを戻った場合、ネストレベルも戻ります。<br/>
 * <br/>
 * ブラウザ起動時に最大ネスト数を設定することで、その最大ネスト数以上に遷移をおこなおうとした場合、例外を発生します。<br/>
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Browzer {
	
	/** ページ管理オブジェクト */
	private PageManager pageManager;
	
	/** ページリダイレクト制御オブジェクト */
	private PageRedirectHandler pageRedirectHandler;
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURLを元にブラウザを生成します。
	 * 
	 * @param url URL文字列
	 * @throws BrowzingException ページの表示に失敗した場合
	 */
	public Browzer(String url) throws BrowzingException {
		this(url, new PageRedirectHandler());
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURLを元にブラウザを生成します。
	 * 
	 * @param url URL文字列
	 * @param pageRedirectHandler ページリダイレクト制御オブジェクト
	 * @throws BrowzingException ページの表示に失敗した場合
	 */
	public Browzer(String url, PageRedirectHandler pageRedirectHandler) throws BrowzingException {
		this.pageRedirectHandler = pageRedirectHandler;
		this.pageManager         = new PageManager(new Page(url), pageRedirectHandler);
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURL,指定の最大ネスト数を元にブラウザを生成します。<br/>
	 * 
	 * @param url URL文字列
	 * @param maxNestLevel 最大ネスト数
	 * @throws BrowzingException ページの表示に失敗した場合
	 */
	public Browzer(String url, int maxNestLevel) throws BrowzingException {
		this(url, new PageRedirectHandler(), maxNestLevel);
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURL,指定の最大ネスト数を元にブラウザを生成します。<br/>
	 * 
	 * @param url URL文字列
	 * @param pageRedirectHandler ページリダイレクト制御オブジェクト
	 * @param maxNestLevel 最大ネスト数
	 * @throws BrowzingException ページの表示に失敗した場合
	 */
	public Browzer(String url, PageRedirectHandler pageRedirectHandler, int maxNestLevel) throws BrowzingException {
		this.pageRedirectHandler = pageRedirectHandler;
		this.pageManager         = new PageManager(new Page(url), pageRedirectHandler, maxNestLevel);
	}
	
	/**
	 * ページ遷移実行<p/>
	 * 指定されたアンカーに設定されているURLへページ遷移を実行します。<br/>
	 * <br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先アンカーが設定されていなかった場合<br/>
	 * ・遷移先アンカーが現在アクティブになっているページから取得したものでない場合<br/>
	 * ・遷移先アンカーに遷移先URLが設定されていなかった場合(hrefが設定されていなかった場合)<br/>
	 * 
	 * @param anchor 遷移先アンカー
	 * @return ページオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	public Page move(A anchor) throws BrowzingException {
		if (anchor == null) throw new BrowzingException(ERROR_SPECIFIED_ANCHOR_IS_NOT_SET);
		if (!this.pageManager.getPage().equals(anchor.getPage()))throw new BrowzingException(ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
		String url = anchor.getHref();
		if (url.equals("")) throw new BrowzingException(ERROR_ANCHOR_HAS_NOT_URL);
		this.pageManager = this.pageManager.move(url);
		return this.pageManager.getPage();
	}
	
	/**
	 * ページ遷移実行<p/>
	 * 指定されたイメージに設定されている画像へページ遷移を実行します。<br/>
	 * <br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先イメージが設定されていなかった場合<br/>
	 * ・遷移先イメージが現在アクティブになっているページから取得したものでない場合<br/>
	 * ・遷移先イメージに遷移先URLが設定されていなかった場合(srcが設定されていなかった場合)<br/>
	 * 
	 * @param image イメージURL
	 * @return ページオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	public Page move(Image image) throws BrowzingException {
		if (image == null) throw new BrowzingException(ERROR_SPECIFIED_ANCHOR_IS_NOT_SET);
		if (!this.pageManager.getPage().equals(image.getPage()))throw new BrowzingException(ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
		String url = image.getSrc();
		if (url.equals("")) throw new BrowzingException(ERROR_ANCHOR_HAS_NOT_URL);
		this.pageManager = this.pageManager.move(url);
		return this.pageManager.getPage();
	}
	
	/**
	 * ページ遷移実行<p/>
	 * 指定されたFORMに設定されているURLへページ遷移を実行します。<br/>
	 * <br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先FORMが設定されていなかった場合<br/>
	 * ・遷移先FORMが現在アクティブになっているページから取得したものでない場合<br/>
	 * ・遷移先FORMに遷移先URLが設定されていなかった場合(srcが設定されていなかった場合)<br/>
	 * 
	 * @param form 遷移先FORM
	 * @return FORMオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	public Page move(Form form) throws BrowzingException {
		if (form == null) throw new BrowzingException(ERROR_SPECIFIED_FORM_IS_NOT_SET);
		if (!this.pageManager.getPage().equals(form.getPage()))throw new BrowzingException(ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
		this.pageManager = this.pageManager.move(form);
		return this.pageManager.getPage();
	}
	
	/**
	 * ページ戻り実行<p/>
	 * 現在アクティブになっているページの遷移元ページをアクティブ状態にした上で、ページを取得します。
	 * 
	 * @param anchor 遷移先アンカー
	 * @return ページオブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	public Page back() {
		this.pageManager =	this.pageManager.back();
		return this.pageManager.getPage();
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
		List<A> returnAnchorList = new ArrayList<A>();
		List<jp.co.dk.document.html.element.A> anchorList = this.pageManager.getPage().getAnchor();
		for (jp.co.dk.document.html.element.A anchor : anchorList) {
			if (anchor instanceof A) returnAnchorList.add((A)anchor);
		}
		return returnAnchorList;
	}
	
	/**
	 * フォーム一覧を取得します。
	 * このページに存在するすべてのフォームタグを取得します。
	 * 
	 * @return フォーム一覧
	 * @throws BrowzingException このページがHTMLでない場合
	 */
	public List<Form> getForm() throws BrowzingException {
		List<Form> returnFormList = new ArrayList<Form>();
		List<jp.co.dk.document.html.element.Form> formList = this.pageManager.getPage().getForm();
		for (jp.co.dk.document.html.element.Form form : formList) {
			if (form instanceof Form) returnFormList.add((Form)form);
		}
		return returnFormList;
	}
	
	/**
	 * ページオブジェクト取得<p/>
	 * 現在アクティブになっているページオブジェクトを返却します。
	 * 
	 * @return ページオブジェクト
	 */
	public Page getPage() {
		return this.pageManager.getPage();
	}
	
	/**
	 * 次ページ遷移可能判定<p/>
	 * 最大ネスト数を元に現在アクティブになっているページから次のページへ遷移できるかどうかを判定します。
	 * 
	 * @return 判定結果（true=遷移可能、false=最大ネスト数に達しているため遷移不可能）
	 */
	public boolean ableMoveNextPage() {
		return this.pageManager.ableMoveNextPage();
	}
	
	/**
	 * ページ保存<p/>
	 * 現在アクティブになっているページを指定のディレクトリへ保存します。<br/>
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
		return this.pageManager.getPage().save(path);
	}
	
	/**
	 * ページ保存<p/>
	 * 現在アクティブになっているページを指定のディレクトリへ指定のファイル名で保存します。<br/>
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
		return this.pageManager.getPage().save(path, fileName);
	}
	
	/**
	 * ページ保存<p/>
	 * 現在アクティブになっているページを指定のディレクトリへ保存します。<br/>
	 * 保存する際は指定の判定条件で判定し、trueの場合は保存を実行する。falseの場合は保存は行われない。<br/>
	 * <br/>
	 * 以下の条件の場合、例外を発生する。<br/>
	 * ・指定された保存先ディレクトリが存在しない場合<br/>
	 * ・指定された保存先ディレクトリがディレクトリが存在しない場合<br/>
	 * ・保存先のファイルがすでに存在する場合<br/>
	 * 
	 * @param path ダウンロード先ディレクトリパス
	 * @return 保存条件判定結果(true=保存を実行された、false=保存は実行されなかった)
	 * @throws ダウンロードに失敗した場合
	 */
	public boolean save(File path, DownloadJudge downloadJudge) throws BrowzingException {
		if (downloadJudge != null && downloadJudge.judge(this.pageManager.getPage())){
			this.pageManager.getPage().save(path);
			return true;
		} else {
			return false;
		}
	}
	
}
