package jp.co.dk.browzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.PageRedirectHandler;
import jp.co.dk.browzer.download.DownloadJudge;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.exception.MoveActionException;
import jp.co.dk.browzer.exception.MoveActionFatalException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageMovableLimitException;
import jp.co.dk.browzer.exception.PageRedirectException;
import jp.co.dk.browzer.exception.PageSaveException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.html.element.MovableElement;
import jp.co.dk.browzer.scenario.MoveScenario;
import jp.co.dk.browzer.scenario.action.MoveAction;
import jp.co.dk.document.exception.DocumentException;
import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;
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
	protected PageManager pageManager;
	
	/** ページリダイレクト制御オブジェクト */
	protected PageRedirectHandler pageRedirectHandler;
	
	/** ロガーインスタンス */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURLを元にブラウザを生成します。
	 * 
	 * @param url URL文字列
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public Browzer(String url) throws PageIllegalArgumentException, PageAccessException {
		this.logger.constractor(this.getClass(), url);
		this.pageRedirectHandler  = this.createPageRedirectHandler();
		this.pageManager          = this.createPageManager(url, this.pageRedirectHandler);
	}
	
	/**
	 * コンストラクタ<p/>
	 * 指定のURL,指定の最大ネスト数を元にブラウザを生成します。<br/>
	 * 
	 * @param url URL文字列
	 * @param maxNestLevel 最大ネスト数
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	public Browzer(String url, int maxNestLevel) throws PageIllegalArgumentException, PageAccessException {
		this.logger.constractor(this.getClass(), url, new Integer(maxNestLevel));
		this.pageRedirectHandler  = this.createPageRedirectHandler();
		this.pageManager          = this.createPageManager(url, this.pageRedirectHandler, maxNestLevel);
	}
	
	/**
	 * ページ遷移実行<p/>
	 * 指定された遷移可能な要素に設定されているURLへページ遷移を実行します。<br/>
	 * <br/>
	 * 以下の状態の場合、例外を送出します。<br/>
	 * ・遷移先アンカーが設定されていなかった場合<br/>
	 * ・遷移先アンカーが現在アクティブになっているページから取得したものでない場合<br/>
	 * ・遷移先アンカーに遷移先URLが設定されていなかった場合(hrefが設定されていなかった場合)<br/>
	 * 
	 * @param movable 遷移先要素
	 * @return ページオブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 * @throws PageRedirectException 遷移に失敗した場合
	 * @throws PageMovableLimitException ページ遷移可能上限数に達した場合
	 */
	public Page move(MovableElement movable) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException {
		try {
			if (movable == null) throw new PageIllegalArgumentException(ERROR_SPECIFIED_ANCHOR_IS_NOT_SET);
			if (!this.pageManager.getPage().equals(movable.getPage()))throw new PageIllegalArgumentException(ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
			String url = movable.getUrl();
			
			this.logger.debug("move url=[" + url + "]");
			
			if (url.equals("")) throw new PageIllegalArgumentException(ERROR_ANCHOR_HAS_NOT_URL);
			this.pageManager = this.pageManager.move(url);
			return this.pageManager.getPage();
		} catch (BrowzingException e ) {
			throw e;
		}
	}
	
	public Page move(MovableElement movable, MoveAction moveAction) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException, MoveActionFatalException, MoveActionException {
		try {
			moveAction.beforeAction(movable, this);
			Page returnPage = this.move(movable);
			moveAction.afterAction(movable, this);
			return returnPage;
		} catch (PageIllegalArgumentException | PageAccessException | PageRedirectException | PageMovableLimitException e) {
			moveAction.errorAction(movable, this);
			throw e;
		}
	}

	public void start(MoveScenario scenario) throws MoveActionException, MoveActionFatalException, PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException, DocumentException {
		List<A> moveAnchor = scenario.getMoveAnchor(this);
		if (moveAnchor == null) return;
		for (A anchor : moveAnchor) {
			this.move(anchor, scenario.getAction());
			if (scenario.hasChildScenario()) this.start(scenario.getChildScenario());
			this.back();
		}
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
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 * @throws PageRedirectException 遷移に失敗した場合
	 * @throws PageMovableLimitException ページ遷移可能上限数に達した場合
	 */
	public Page move(Form form) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException {
		try {
			if (form == null) throw new PageIllegalArgumentException(ERROR_SPECIFIED_FORM_IS_NOT_SET);
			if (!this.pageManager.getPage().equals(form.getPage()))throw new PageIllegalArgumentException(ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
			
			this.logger.debug("move form=[" + form + "]");
			
			this.pageManager = this.pageManager.move(form);
			return this.pageManager.getPage();
		} catch (BrowzingException e ) {
			throw e;
		}
	}
	
	/**
	 * ページ戻り実行<p/>
	 * 現在アクティブになっているページの遷移元ページをアクティブ状態にした上で、ページを取得します。
	 * 
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchor() throws PageAccessException, DocumentException {
		List<A> returnAnchorList = new ArrayList<A>();
		List<jp.co.dk.document.html.element.A> anchorList = this.pageManager.getPage().getAnchor();
		for (jp.co.dk.document.html.element.A anchor : anchorList) {
			if (anchor instanceof A) returnAnchorList.add((A)anchor);
		}
		return returnAnchorList;
	}

	/**
	 * 現在アクティブなページのURLとドメインが同一のアンカー一覧を取得します。<p/>
	 * 現在アクティブなページに存在するすべてのアンカータグを取り出し、このページと同じドメインのURLのみ抽出し返却します。<br/>
	 * 現在アクティブなページと同じドメインのアンカータグが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return 同じドメインのアンカー一覧 
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchorSameDomain() throws PageAccessException, DocumentException {
		List<A> returnAnchorList = new ArrayList<A>();
		List<jp.co.dk.document.html.element.A> anchorList = this.pageManager.getPage().getAnchorSameDomain();
		for (jp.co.dk.document.html.element.A anchor : anchorList) {
			if (anchor instanceof A) returnAnchorList.add((A)anchor);
		}
		return returnAnchorList;
	}
	
	/**
	 * 現在アクティブなページのURLとドメインとパスが同一のアンカー一覧を取得します。<p/>
	 * 現在アクティブなページに存在するすべてのアンカータグを取り出し、このページと同じドメインとパスのURLのみ抽出し返却します。<br/>
	 * 現在アクティブなページと同じドメインとパスのアンカータグが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @return 同じドメインとパスのアンカー一覧 
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchorSamePath() throws PageAccessException, DocumentException {
		List<A> returnAnchorList = new ArrayList<A>();
		List<jp.co.dk.document.html.element.A> anchorList = this.pageManager.getPage().getAnchorSamePath();
		for (jp.co.dk.document.html.element.A anchor : anchorList) {
			if (anchor instanceof A) returnAnchorList.add((A)anchor);
		}
		return returnAnchorList;
	}
	
	/**
	 * 指定の正規表現に合致するURLを保持するアンカーを取得します。
	 * このページに存在するすべてのアンカータグを取り出し、指定の正規表現に合致するアンカーを取得します。<br/>
	 * このページと同じドメインとパスのアンカータグが存在しなかった場合、空のリストを返却します。<br/>
	 * このページがHTMLでない場合、例外を送出します。
	 * 
	 * @param pattern URL正規表現パターン
	 * @return 正規表現に合致したアンカー一覧
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<A> getAnchor(Pattern p) throws PageAccessException, DocumentException {
		List<A> returnAnchorList = new ArrayList<A>();
		List<jp.co.dk.document.html.element.A> anchorList = this.pageManager.getPage().getAnchor(p);
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した、またはこのページがHTMLでない場合
	 */
	public List<Form> getForm() throws PageAccessException, DocumentException {
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 * @throws PageSaveException ページの保存に失敗した場合
	 */
	public File save(File path) throws PageAccessException, DocumentException, PageSaveException {
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 * @throws PageSaveException ページの保存に失敗した場合
	 */
	public File save(File path, String fileName) throws PageAccessException, DocumentException, PageSaveException {
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
	 * @throws PageAccessException ページデータの取得に失敗した場合
	 * @throws DocumentException ドキュメントオブジェクトの生成に失敗した場合
	 * @throws PageSaveException ページの保存に失敗した場合
	 */
	public boolean save(File path, DownloadJudge downloadJudge) throws PageAccessException, DocumentException, PageSaveException {
		if (downloadJudge != null && downloadJudge.judge(this.pageManager.getPage())){
			this.pageManager.getPage().save(path);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * このページの遷移元である親ページを削除します。
	 */
	public void removeParent() {
		this.pageManager.removeParent();
	}
	
	/**
	 * このページから遷移したページオブジェクトを空に設定します。
	 */
	public void removeChild() {
		this.pageManager.removeChild();
	}
	
	/**
	 * 指定のURL、ページリダイレクトハンドラからページマネージャを作成する。
	 * 
	 * @param url URL
	 * @param handler ページリダイレクトハンドラ
	 * @return ページマネージャオブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected PageManager createPageManager(String url, PageRedirectHandler handler) throws PageIllegalArgumentException, PageAccessException {
		return new PageManager(url, handler);
	}
	
	/**
	 * 指定のURL、ページリダイレクトハンドラ、最大遷移数からページマネージャを作成する。
	 * 
	 * @param url URL
	 * @param handler ページリダイレクトハンドラ
	 * @param maxNestLevel 最大遷移数
	 * @return ページマネージャオブジェクト
	 * @throws PageIllegalArgumentException URLが指定されていない、不正なURLが指定されていた場合
	 * @throws PageAccessException ページにアクセスした際にサーバが存在しない、ヘッダが不正、データの取得に失敗した場合
	 */
	protected PageManager createPageManager(String url, PageRedirectHandler handler, int maxNestLevel) throws PageIllegalArgumentException, PageAccessException {
		return new PageManager(url, handler, maxNestLevel);
	}
	
	/**
	 * ページリダイレクトハンドラを生成する。
	 * @return ページリダイレクトハンドラ
	 */
	protected PageRedirectHandler createPageRedirectHandler() {
		return new PageRedirectHandler();
	}
		
	@Override
	public String toString() {
		return this.pageManager.toString();
	}
}
