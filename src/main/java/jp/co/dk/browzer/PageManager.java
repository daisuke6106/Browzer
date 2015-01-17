package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageMovableLimitException;
import jp.co.dk.browzer.exception.PageRedirectException;
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
	protected PageManager parentPage;
	
	/** 現在のページのオブジェクト */
	protected Page page;
	
	/** ページ接続エラー */
	protected BrowzingException error;
	
	/** ページリダイレクトハンドラ */
	protected PageRedirectHandler pageRedirectHandler;
	
	/** ページイベントハンドラ一覧 */
	protected List<PageEventHandler> pageEventHandlerList;
	
	/** この画面に属する小画面のページオブジェクト一覧 */
	protected List<PageManager> childPageList;
	
	/** 現在のネストレベル */
	protected int nestLevel = 0;
	
	/** 最大ネストレベル */
	protected int maxNestLevel = -1;
	
	/**
	 * コンストラクタ<p/>
	 * 指定のページを元に本ページ管理マネージャーを生成します。
	 * 
	 * @param url URL文字列
	 * @param pageRedirectHandler  ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public PageManager(String url, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList) throws PageIllegalArgumentException, PageAccessException {
		this.pageRedirectHandler  = pageRedirectHandler;
		this.pageEventHandlerList = pageEventHandlerList;
		this.page                 = this.createPage(url);
		this.childPageList        = new ArrayList<PageManager>();
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のページ、ページ遷移上限数を元に本ページ管理マネージャーを生成します。
	 * 
	 * @param url URL文字列
	 * @param pageRedirectHandler ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param maxNestLevel ページ遷移上限数
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public PageManager(String url, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int maxNestLevel) throws PageIllegalArgumentException, PageAccessException {
		this.pageRedirectHandler  = pageRedirectHandler;
		this.pageEventHandlerList = pageEventHandlerList;
		this.page                 = this.createPage(url);
		this.maxNestLevel         = maxNestLevel;
		this.childPageList        = new ArrayList<PageManager>();
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のページ、現在のページ遷移数、ページ遷移上限数を元に本ページ管理マネージャーを生成します。
	 * 
	 * @param parentPage           遷移元ページのページマネージャ
	 * @param page                 遷移先のページオブジェクト
	 * @param pageRedirectHandler  ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param nestLevel            現在のページ遷移数
	 * @param maxNestLevel         ページ遷移上限数
	 */
	protected PageManager(PageManager parentPage, Page page, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int nestLevel, int maxNestLevel) {
		this.parentPage           = parentPage;
		this.page                 = page;
		this.pageRedirectHandler  = pageRedirectHandler;
		this.pageEventHandlerList = pageEventHandlerList;
		this.nestLevel            = nestLevel;
		this.maxNestLevel         = maxNestLevel;
		this.childPageList        = new ArrayList<PageManager>();
	}
	
	/**
	 * コンストラクタ<p/>
	 * @param parentPage           遷移元ページのページマネージャ
	 * @param error                ページインスタンスの生成に失敗した際に発生した例外
	 * @param pageRedirectHandler  ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param nestLevel            現在のページ遷移数
	 * @param maxNestLevel         ページ遷移上限数
	 */
	protected PageManager(PageManager parentPage, BrowzingException error, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int nestLevel, int maxNestLevel) {
		this.parentPage           = parentPage;
		this.error                = error;
		this.pageRedirectHandler  = pageRedirectHandler;
		this.pageEventHandlerList = pageEventHandlerList;
		this.nestLevel            = nestLevel;
		this.maxNestLevel         = maxNestLevel;
		this.childPageList        = new ArrayList<PageManager>();
	}
	
	/**
	 * コンストラクタ<p/>
	 * @param parentPage           遷移元ページのページマネージャ
	 * @param page                 遷移先のページオブジェクト
	 * @param error                リダイレクトに失敗した際に発生した例外
	 * @param pageRedirectHandler  ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param nestLevel            現在のページ遷移数
	 * @param maxNestLevel         ページ遷移上限数
	 */
	protected PageManager(PageManager parentPage, Page page, BrowzingException error, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int nestLevel, int maxNestLevel) {
		this.parentPage           = parentPage;
		this.page                 = page;
		this.error                = error;
		this.pageRedirectHandler  = pageRedirectHandler;
		this.pageEventHandlerList = pageEventHandlerList;
		this.nestLevel            = nestLevel;
		this.maxNestLevel         = maxNestLevel;
		this.childPageList        = new ArrayList<PageManager>();
	}
	
	/**
	 * 遷移先ページ追加<p/>
	 * ページオブジェクトを元に子要素としてページを追加します。
	 * 
	 * @param page 遷移先ページ追加
	 * @return 遷移先ページ管理オブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 * @throws PageRedirectException 遷移に失敗した場合
	 * @throws PageMovableLimitException ページ遷移可能上限数に達した場合
	 */
	PageManager move(String url) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException {
		int nextLevel = this.nestLevel+1;
		if ( !(this.maxNestLevel<0) && this.maxNestLevel < nextLevel) throw new PageMovableLimitException(ERROR_REACHED_TO_THE_MAXIMUM_LEVEL, Integer.toString(nextLevel));
		Page nextPage;
		try {
			nextPage = this.createPage(url);
		} catch (PageIllegalArgumentException | PageAccessException e) {
			PageManager errorPageManager = this.createPageManager(this, e, this.pageRedirectHandler, this.pageEventHandlerList, nextLevel, this.maxNestLevel);
			this.childPageList.add(errorPageManager);
			return this;
		}
		try {
			nextPage = pageRedirectHandler.redirect(nextPage);
		} catch (PageRedirectException e) {
			PageManager errorPageManager = this.createPageManager(this, nextPage, e, this.pageRedirectHandler, this.pageEventHandlerList, nextLevel, this.maxNestLevel);
			this.childPageList.add(errorPageManager);
			return this;
		}
		PageManager childPageManager = this.createPageManager(this, nextPage, this.pageRedirectHandler, this.pageEventHandlerList, nextLevel, this.maxNestLevel);
		this.childPageList.add(childPageManager);
		return childPageManager;
	}
	
	/**
	 * 遷移先ページ追加<p/>
	 * ページオブジェクトを元に子要素としてページを追加します。
	 * 
	 * @param page 遷移先ページ追加
	 * @return 遷移先ページ管理オブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 * @throws PageRedirectException 遷移に失敗した場合
	 * @throws PageMovableLimitException ページ遷移可能上限数に達した場合
	 */
	PageManager move(Form form) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException {
		int nextLevel = this.nestLevel+1;
		if ( !(this.maxNestLevel<0) && this.maxNestLevel < nextLevel) throw new PageMovableLimitException(ERROR_REACHED_TO_THE_MAXIMUM_LEVEL, Integer.toString(nextLevel));
		Page nextPage;
		try {
			nextPage = this.createPage(form);
		} catch (BrowzingException e) {
			PageManager errorPageManager = this.createPageManager(this, e, this.pageRedirectHandler, this.pageEventHandlerList, nextLevel, this.maxNestLevel);
			this.childPageList.add(errorPageManager);
			return this;
		}
		try {
			nextPage = pageRedirectHandler.redirect(nextPage);
		} catch (BrowzingException e) {
			PageManager errorPageManager = this.createPageManager(this, nextPage, e, this.pageRedirectHandler, this.pageEventHandlerList, nextLevel, this.maxNestLevel);
			this.childPageList.add(errorPageManager);
			return this;
		}
		PageManager childPageManager = this.createPageManager(this, nextPage, this.pageRedirectHandler, this.pageEventHandlerList, nextLevel, this.maxNestLevel);
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
	 * このページの遷移元である親ページを取得します。
	 * 
	 * @return 親ページオブジェクト
	 */
	public Page getParentPage() {
		return this.parentPage.getPage();
	}
	
	/**
	 * このページの遷移元である親ページを削除します。
	 */
	public void removeParent() {
		this.parentPage = null;
		this.nestLevel = 0;
	}
	
	/**
	 * このページの遷移先である子ページの一覧を取得します。
	 * 
	 * @return 親ページオブジェクト
	 */
	public List<Page> getChildPages() {
		List<Page> childPagesList = new ArrayList<Page>();
		for (PageManager pagemanager : this.childPageList) {
			childPagesList.add(pagemanager.getPage());
		}
		return childPagesList;
	}
	
	/**
	 * このページから遷移したページオブジェクトを空に設定します。
	 */
	public void removeChild() {
		this.childPageList = new ArrayList<PageManager>();
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
	
	public PageStatus getPageStatus() {
		if (this.page != null && this.error == null) {
			return PageStatus.SUCCESS;
		} else if (this.page != null && this.error != null) {
			return PageStatus.ERROR_REDIRECT;
		} else {
			return PageStatus.ERROR_ACCESS;
		}
	}
	
	/**
	 * 指定のURLからページオブジェクトを作成する。
	 * 
	 * @param url URL文字列
	 * @return ページオブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected Page createPage(String url) throws PageIllegalArgumentException, PageAccessException {
		for (PageEventHandler pageEventHandler : pageEventHandlerList) pageEventHandler.beforeMove(this, url);
		Page nextPage = new Page(url, new HashMap<String, String>(), false, this.pageEventHandlerList);
		for (PageEventHandler pageEventHandler : pageEventHandlerList) pageEventHandler.afterMove();
		return nextPage;
	}
	
	/**
	 * 指定のFORMからページオブジェクトを作成する。
	 * 
	 * @param form Formオブジェクト
	 * @return ページオブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected Page createPage(Form form) throws PageIllegalArgumentException, PageAccessException {
		for (PageEventHandler pageEventHandler : pageEventHandlerList) pageEventHandler.beforeMove(this, form.getPage().toString());
		Page nextPage = new Page(form, new HashMap<String, String>(), false, this.pageEventHandlerList);
		for (PageEventHandler pageEventHandler : pageEventHandlerList) pageEventHandler.afterMove();
		return nextPage;
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
	 * @param pageManager          遷移元ページのページマネージャ
	 * @param page                 遷移先のページオブジェクト
	 * @param pageRedirectHandler  ページリダイレクトハンドラ
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param nextLevel            次ページのページ遷移数
	 * @param maxNestLevel         ページ遷移上限数
	 * @return ページマネージャ
	 */
	protected PageManager createPageManager(PageManager pageManager, Page page, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int nextLevel, int maxNestLevel) {
		return new PageManager(pageManager, page, pageRedirectHandler, pageEventHandlerList, nextLevel, maxNestLevel);
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
	 * @param parentPage           遷移元ページのページマネージャ
	 * @param error                ページインスタンスの生成に失敗した際に発生した例外
	 * @param pageRedirectHandler  ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param nestLevel            現在のページ遷移数
	 * @param maxNestLevel         ページ遷移上限数
	 * @return ページマネージャ
	 */
	protected PageManager createPageManager(PageManager parentPage, BrowzingException error, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int nestLevel, int maxNestLevel) {
		return new PageManager(parentPage, error, pageRedirectHandler, pageEventHandlerList, nestLevel, maxNestLevel);
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
	 * @param parentPage           遷移元ページのページマネージャ
	 * @param page                 遷移先のページオブジェクト
	 * @param error                リダイレクトに失敗した際に発生した例外
	 * @param pageRedirectHandler  ページリダイレクト制御オブジェクト
	 * @param pageEventHandlerList ページイベントハンドラ一覧
	 * @param nestLevel            現在のページ遷移数
	 * @param maxNestLevel         ページ遷移上限数
	 * @return ページマネージャ
	 */
	protected PageManager createPageManager(PageManager parentPage, Page page, BrowzingException error, PageRedirectHandler pageRedirectHandler, List<PageEventHandler> pageEventHandlerList, int nestLevel, int maxNestLevel) {
		return new PageManager(parentPage, page, error, pageRedirectHandler, pageEventHandlerList, nestLevel, maxNestLevel);
	}
	
	@Override
	public jp.co.dk.xml.Element convert() throws jp.co.dk.xml.exception.XmlDocumentException {
		jp.co.dk.xml.Element xmlElement = this.page.convert();
		for (PageManager pageManager : this.childPageList) {
			xmlElement.appendChild(pageManager);
		}
		return xmlElement;
	}
	
	@Override
	public String toString() {
		PageManager pageManager;
		for (pageManager = this; pageManager.parentPage != null; pageManager = pageManager.parentPage);
		
		boolean[] islasted= {};
		
		String newline = System.getProperty("line.separator");
		Page activePage = this.getPage();
		
		StringBuilder sb = new StringBuilder();
		pageManager.toStringUrl(sb, islasted, newline, activePage);
		return sb.toString();
	}
	
	private void toStringUrl(StringBuilder stringBuilder, boolean[] islasts, String newline, Page activePage) {
		for (int i=0; i<islasts.length-1; i++) {
			if (islasts[i]) {
				stringBuilder.append("  ");
			} else {
				stringBuilder.append('│');
			}
		}
		if (islasts.length != 0) {
			if (islasts[islasts.length-1]) {
				stringBuilder.append('└');
			} else {
				stringBuilder.append('├');
			}
		}
		stringBuilder.append(this.pageInfomationToString(this, activePage));
		stringBuilder.append(newline);
		for (int i=0, size = this.childPageList.size(); i<size ;i++) {
			boolean[] newIslasts = new boolean[islasts.length+1];
			for (int k=0; k<islasts.length; k++) newIslasts[k] = islasts[k];
			if (i==size-1) {
				newIslasts[newIslasts.length-1] = true;
				
			} else {
				newIslasts[newIslasts.length-1] = false;
			}
			this.childPageList.get(i).toStringUrl(stringBuilder, newIslasts, newline, activePage);
		}
	}
	
	protected String pageInfomationToString(PageManager activePageManager, Page activePage) {
		StringBuilder sb = new StringBuilder();
		Page thisPage  = activePageManager.getPage();
		if (thisPage != null) { 
			if (activePage == thisPage) sb.append("[[[active]]]"); 
			sb.append(activePageManager.getPageStatus()).append(":URL=[").append(thisPage.getURL().toString()).append("]:");
			String fileName  = thisPage.getFileName();
			String extension = thisPage.getExtension();
			sb.append("FILE=[").append("filename=").append(fileName).append(", extension=").append(extension).append("]");
		} else {
			sb.append(activePageManager.getPageStatus()).append(":MESSAGE=[").append(activePageManager.error.getMessage());
		}
		return sb.toString();
	}
}

/**
 * 
 * @version 1.0
 * @author D.Kanno
 */
enum PageStatus {
	
	/**
	 * 正常<p/>
	 * 指定のURLに正常にアクセスできた場合
	 */
	SUCCESS,
	
	/**
	 * アクセスエラー<p/>
	 * 指定のURLのサーバが存在しない等、サーバ接続自体行えなかった場合
	 */
	ERROR_ACCESS,
	
	/**
	 * リダイレクトエラー<p/>
	 * 指定のURLにアクセスした結果、HTTPステータスコードにて異常が発生した場合
	 */
	ERROR_REDIRECT
}