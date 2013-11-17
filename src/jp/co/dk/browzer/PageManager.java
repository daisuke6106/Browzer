package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.util.ArrayList;
import java.util.List;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.xml.XmlConvertable;

/**
 * PageManagerは、ページ遷移状態管理を行うページ管理クラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class PageManager implements XmlConvertable{
	
	/** 親ページ管理オブジェクト */
	private PageManager parentPage;
	
	/** 現在のページのオブジェクト */
	private Page page;
	
	private PageRedirectHandler pageRedirectHandler;
	
	/** この画面に属する小画面のページオブジェクト一覧 */
	private List<PageManager> childPageList;
	
	/** 現在のネストレベル */
	private int nestLevel = 0;
	
	/** 最大ネストレベル */
	private int maxNestLevel = -1;
	
	/**
	 * コンストラクタ<p/>
	 * 指定のページを元に本ページ管理マネージャーを生成します。
	 * 
	 * @param url URL文字列
	 * @param pageRedirectHandler ページリダイレクト制御オブジェクト
	 * @throws BrowzingException ページクラスの生成に失敗した場合
	 */
	public PageManager(String url, PageRedirectHandler pageRedirectHandler) throws BrowzingException {
		this.page                = this.createPage(url);
		this.pageRedirectHandler = pageRedirectHandler;
		this.childPageList       = new ArrayList<PageManager>();
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のページ、ページ遷移上限数を元に本ページ管理マネージャーを生成します。
	 * 
	 * @param url URL文字列
	 * @param pageRedirectHandler ページリダイレクト制御オブジェクト
	 * @param maxNestLevel ページ遷移上限数
	 * @throws BrowzingException ページクラスの生成に失敗した場合 
	 */
	public PageManager(String url, PageRedirectHandler pageRedirectHandler, int maxNestLevel) throws BrowzingException {
		this.page                = this.createPage(url);
		this.pageRedirectHandler = pageRedirectHandler;
		this.maxNestLevel        = maxNestLevel;
		this.childPageList       = new ArrayList<PageManager>();
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のページ、現在のページ遷移数、ページ遷移上限数を元に本ページ管理マネージャーを生成します。
	 * 
	 * @param page ページオブジェクト
	 * @param nestLevel 現在のページ遷移数
	 * @param maxNestLevel ページ遷移上限数
	 */
	protected PageManager(PageManager parentPage, Page page,  PageRedirectHandler pageRedirectHandler, int nestLevel, int maxNestLevel) {
		this.parentPage          = parentPage;
		this.page                = page;
		this.pageRedirectHandler = pageRedirectHandler;
		this.nestLevel           = nestLevel;
		this.maxNestLevel        = maxNestLevel;
		this.childPageList       = new ArrayList<PageManager>();
	}
	
	/**
	 * 遷移先ページ追加<p/>
	 * ページオブジェクトを元に子要素としてページを追加します。
	 * 
	 * @param page 遷移先ページ追加
	 * @return 遷移先ページ管理オブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	PageManager move(String url) throws BrowzingException {
		int nextLevel = this.nestLevel+1;
		if ( !(this.maxNestLevel<0) && this.maxNestLevel < nextLevel) throw new BrowzingException(ERROR_REACHED_TO_THE_MAXIMUM_LEVEL, Integer.toString(nextLevel));
		Page nextPage = this.createPage(url);
		nextPage = pageRedirectHandler.redirect(nextPage);
		PageManager childPageManager = this.createPageManager(this, nextPage, this.pageRedirectHandler, nextLevel, this.maxNestLevel);
		this.childPageList.add(childPageManager);
		return childPageManager;
	}
	
	/**
	 * 遷移先ページ追加<p/>
	 * ページオブジェクトを元に子要素としてページを追加します。
	 * 
	 * @param page 遷移先ページ追加
	 * @return 遷移先ページ管理オブジェクト
	 * @throws BrowzingException 遷移に失敗した場合
	 */
	PageManager move(Form form) throws BrowzingException {
		int nextLevel = this.nestLevel+1;
		if ( !(this.maxNestLevel<0) && this.maxNestLevel < nextLevel) throw new BrowzingException(ERROR_REACHED_TO_THE_MAXIMUM_LEVEL, Integer.toString(nextLevel));
		Page nextPage = this.page.move(form);
		nextPage = pageRedirectHandler.redirect(nextPage);
		PageManager childPageManager = this.createPageManager(this, nextPage, this.pageRedirectHandler, nextLevel, this.maxNestLevel);
		this.childPageList.add(childPageManager);
		return childPageManager;
	}
	
	/**
	 * 遷移元のページ取得<p/>
	 * 現在のページの遷移元ページを取得する。<br/>
	 * 現在のページの遷移元がない場合、自身のページが返却される。
	 * 
	 * @return 遷移元ページ
	 */
	PageManager back() {
		if (this.nestLevel == 0) return this;
		return this.parentPage;
	}
	
	/**
	 * 現在アクティブになっているページを返却します。
	 * 
	 * @return ページオブジェクト
	 */
	public Page getPage() {
		return this.page;
	}
	
	/**
	 * 次ページに遷移することができるか判定します。<p/>
	 * 次回遷移した場合、最大ネストレベルに達するか判定した結果を返却します。<br/>
	 * 
	 * @return 判定結果
	 */
	public boolean ableMoveNextPage() {
		int nextNestLevel = this.nestLevel +1;
		if ( !(this.maxNestLevel<0) && this.maxNestLevel < nextNestLevel) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public jp.co.dk.xml.Element convert() throws jp.co.dk.xml.exception.XmlDocumentException {
		jp.co.dk.xml.Element xmlElement = this.page.convert();
		for (PageManager pageManager : this.childPageList) {
			xmlElement.appendChild(pageManager);
		}
		return xmlElement;
	}
	
	/**
	 * 指定のURLからページオブジェクトを作成する。
	 * 
	 * @param url URL文字列
	 * @return ページオブジェクト
	 * @throws BrowzingException ページクラスの生成に失敗した場合
	 */
	public Page createPage(String url) throws BrowzingException {
		return new Page(url);
	}
	
	/**
	 * このページから遷移したページオブジェクトを空に設定します。
	 */
	public void removeChild() {
		this.childPageList = new ArrayList<PageManager>();
	}
	
	/**
	 * このページマネージャクラスを生成するページマネージャインスタンス生成メソッドです。<p/>
	 * このページマネージャにてmoveが実行される際には遷移元、遷移先のページ情報を元に本メソッドが実施され遷移先のページマネージャクラスが作成されます。<br/>
	 * 本クラスを継承する場合、必ず実装してください。<br/>
	 * 呼び出し方法は以下の通りです。<br/>
	 * <code>
	 * [@]Override<br/>
	 * protected PageManager createPageManager(PageManager pageManager, Page page, PageRedirectHandler pageRedirectHandler, int nextLevel, int maxNestLevel) {<br/>
	 *     return new 継承したクラス名(pageManager, page, pageRedirectHandler, nextLevel, maxNestLevel);<br/>
	 * }<br/>
	 * </code>
	 * 
	 * @param pageManager         遷移元ページのページマネージャ
	 * @param page                遷移先のページオブジェクト
	 * @param pageRedirectHandler ページリダイレクトハンドラ
	 * @param nextLevel           次ページのページ遷移数
	 * @param maxNestLevel        ページ遷移上限数
	 * @return ページマネージャ
	 */
	protected PageManager createPageManager(PageManager pageManager, Page page, PageRedirectHandler pageRedirectHandler, int nextLevel, int maxNestLevel) {
		return new PageManager(pageManager, page, pageRedirectHandler, nextLevel, maxNestLevel);
	}
}