package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.http.header.ResponseHeader;
import jp.co.dk.document.Element;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.element.A;
import jp.co.dk.document.html.element.form.Text;
import jp.co.dk.document.html.exception.HtmlDocumentException;
import jp.co.dk.document.message.DocumentMessage;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Test;

public class TestPage extends TestBrowzerFoundation {
	
	@Test
	public void constructor() throws BrowzingException {
		// 引数にnullを設定した場合、例外が送出されること。
		Page page = null ;
		try {
			page = new Page(null);
			fail();
		} catch (BrowzingException e) {
			assertEquals(e.getMessageObj(), jp.co.dk.browzer.message.BrowzingMessage.ERROR_URL_IS_NOT_SET);
		}
		
		// 引数に不正な文字列を設定した場合、例外が送出されること。
		try {
			page = new Page("aaa");
			fail();
		} catch (BrowzingException e) {
			success(e);
		}
		
		// 引数に存在するURLを設定された場合、正常にインスタンスが生成できること。
		try {
			page = new Page("https://www.google.com");
		} catch (BrowzingException e) {
			fail(e);
		}
		// 引数に存在するURLを設定された場合、正常にインスタンスが生成できること。(パラメータあり)
		try {
			page = new Page("http://www.google.co.jp/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&ved=0CCUQFjAA&url=http%3A%2F%2Ftest.jp%2F&ei=if9NULqAIanBiQf1uIHACg&usg=AFQjCNFAiQ33DWa0llvM8UoNTY_aKUckbg&sig2=VmamtFiwcu4ABy2tGn5hfQ");
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// FORM指定して遷移する際に、URLの作成に失敗した場合、指定の例外が出力されること。
		try {
			page = new Page("http://www.tohoho-web.com/html/form.htm");
			HtmlDocument document1 = (HtmlDocument)page.getDocument();
			List<Element> formList1 = document1.getElement(HtmlElementName.FORM);
			final Form formElement = (Form)formList1.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			Text txt1 = (Text)formList.get(0);
			Text txt2 = (Text)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			
			new Expectations(formElement) {{
				formElement.getAction();
				result = new HtmlDocumentException(DocumentMessage.ERROR_AN_INVALID_URL_WAS_SPECIFIED, "Dummy URL", new Exception());
			}};
			Page newPage = new Page(formElement, new HashMap<String, String>());
			fail();
		} catch (BrowzingException e) {
			if(e.getMessageObj() != ERROR_AN_INVALID_URL_WAS_SPECIFIED) fail();
		}
		
		// FORM指定して遷移する際に、Formにnullが設定された場合、例外が発生すること。
		try {
			Form form = null;
			Page newPage = new Page(form, null);
			fail();
		} catch (BrowzingException e) {
			if(e.getMessageObj() != ERROR_FORM_IS_NOT_SPECIFIED) fail();
		}
		
		// FORM指定して遷移する際に、引数のリクエストヘッダマップにnullが設定された場合でも正常に遷移できること。
		try {
			page = new Page("http://www.tohoho-web.com/html/form.htm");
			HtmlDocument document1 = (HtmlDocument)page.getDocument();
			List<Element> formList1 = document1.getElement(HtmlElementName.FORM);
			final Form formElement = (Form)formList1.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			Text txt1 = (Text)formList.get(0);
			Text txt2 = (Text)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			Page newPage = new Page(formElement, null);
		} catch (BrowzingException e) {
			fail();
		}
	}
	
	@Test
	public void move() throws BrowzingException {
		// ２つのページのインスタンスを生成する。
		Page page1 = super.createPage("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		Page page2 = super.createPage("http://ja.wikipedia.org/wiki/HTML5");
		List<A> elementPage1 = page1.getAnchor();
		List<A> elementPage2 = page2.getAnchor();
		
		// movePageメソッドにnullを設定した場合、例外を送出すること。
		try {
			jp.co.dk.browzer.html.element.A nullAnchor = null;
			page1.move(nullAnchor);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_ANCHOR_IS_NOT_SET) fail(e);
		}
		
		// movePageメソッドに異なるページのアンカーオブジェクトを設定した場合、例外が送出されること。
		try {
			page1.move((jp.co.dk.browzer.html.element.A)elementPage2.get(0));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE) fail(e);
		}
		
		// movePageメソッドにHrefが記載されていないアンカーオブジェクトを設定した場合、例外が送出されること。
		try {
			final jp.co.dk.browzer.html.element.A anchor = (jp.co.dk.browzer.html.element.A)super.getRandomElement(page1.getAnchor());
			new Expectations(anchor) {{
				anchor.getHref();
                returns("");
	        }};
			Page newPage = page1.move(anchor);
			fail();
		} catch (BrowzingException e) {
			if(e.getMessageObj() != ERROR_ANCHOR_HAS_NOT_URL) fail(e);
		}
		
		// movePageメソッドに正常なアンカーオブジェクトを設定した場合、遷移できること。
		try {
			jp.co.dk.browzer.html.element.A anchor = (jp.co.dk.browzer.html.element.A)super.getRandomElement(page1.getAnchor());
			Page newPage = page1.move(anchor);
			assertEquals(newPage.getURL(), anchor.getHref());
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 送信用FORMにnullの状態で遷移を行おうとした場合、例外が送出されること。
		Page page3 = super.createPage("http://www.tohoho-web.com/html/form.htm");
		try {
			jp.co.dk.browzer.html.element.Form nullForm = null;
			page3.move(nullForm);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_FORM_IS_NOT_SET) fail(e);
		}
		
		// 送信先FORMに異なるページのFORMオブジェクトが指定された場合、例外が送出されること。
		try {
			HtmlDocument htmlDocument = (HtmlDocument)page3.getDocument();
			List<Element> form = htmlDocument.getElement(HtmlElementName.FORM);
			Form formElement = (Form)form.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			Text txt1 = (Text)formList.get(0);
			Text txt2 = (Text)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			Page page4 = super.createPage("http://www.tohoho-web.com/html/form.htm");
			page4.move(formElement);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE) fail(e);
		}
		
		// FORMに値を設定して遷移した場合、正常に遷移できること。
		// また、送信したパラメータに設定した値が設定されていること。
		try {
			HtmlDocument htmlDocument = (HtmlDocument)page3.getDocument();
			List<Element> form = htmlDocument.getElement(HtmlElementName.FORM);
			Form formElement = (Form)form.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			Text txt1 = (Text)formList.get(0);
			Text txt2 = (Text)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			Page page5 = page3.move(formElement);
			HtmlDocument htmlDocument1 = (HtmlDocument)page5.getDocument();
			assertHasString(htmlDocument1.toString(), "NAME = test");
			assertHasString(htmlDocument1.toString(), "ADDR = value");
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// FORMに値を設定して遷移した場合、正常に遷移できること。
		// また、送信したパラメータに設定した値が設定されていること。
		try {
			HtmlDocument htmlDocument = (HtmlDocument)page3.getDocument();
			List<Element> form = htmlDocument.getElement(HtmlElementName.FORM);
			Form formElement = (Form)form.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			Text txt1 = (Text)formList.get(0);
			Text txt2 = (Text)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			Page page5 = page3.move(formElement, null);
			HtmlDocument htmlDocument1 = (HtmlDocument)page5.getDocument();
			assertHasString(htmlDocument1.toString(), "NAME = test");
			assertHasString(htmlDocument1.toString(), "ADDR = value");
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getHost() throws BrowzingException {
		// URLにホスト名のみ指定して作成していた場合でもホスト名のみ抽出されること。
		Page page1 = super.createPage("http://ja.wikipedia.org");
		assertEquals(page1.getHost(), "ja.wikipedia.org");
		
		// URLにホスト名＋パスを指定して作成した場合でも、ホスト名を正常に取得できること。
		Page page2 = super.createPage("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		assertEquals(page2.getHost(), "ja.wikipedia.org");
	}
	
	@Test
	public void getURL() throws BrowzingException{
		// URLにホスト名のみ指定して作成していた場合、正常にURLが返却されること。
		Page page1 = new Page("http://ja.wikipedia.org");
		assertEquals("http://ja.wikipedia.org", page1.getURL());
		
		// URLにホスト名＋パスを指定して作成していた場合、正常にURLが返却されること。
		Page page2 = super.createPage("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		assertEquals("http://ja.wikipedia.org/wiki/HyperText_Markup_Language", page2.getURL());
		
		// URLにホスト名＋パス＋パラメータを指定して作成していた場合、正常にURLが返却されること。
		Page page3 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
		assertEquals("http://phpjavascriptroom.com/?t=js&p=location4", page3.getURL());
		
	}
	
	@Test
	public void getURLObject() throws MalformedURLException, BrowzingException {
		// URLにホスト名のみ指定して作成していた場合、正常にURLが返却されること。
		Page page1 = new Page("http://ja.wikipedia.org");
		assertEquals("http://ja.wikipedia.org", page1.getURLObject().toString());
		
		// URLにホスト名＋パスを指定して作成していた場合、正常にURLが返却されること。
		Page page2 = super.createPage("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		assertEquals("http://ja.wikipedia.org/wiki/HyperText_Markup_Language", page2.getURLObject().toString());
		
		// URLにホスト名＋パス＋パラメータを指定して作成していた場合、正常にURLが返却されること。
		Page page3 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
		assertEquals("http://phpjavascriptroom.com/?t=js&p=location4", page3.getURLObject().toString());
	}
	
	@Test
	public void getDocument() throws BrowzingException{
		// 通常にURLからドキュメンとオブジェクトを取得した場合、正常に取得できること。(HTMLの場合)
		Page page1 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
		jp.co.dk.document.File document1 = page1.getDocument();
		if (!(document1 instanceof jp.co.dk.document.html.HtmlDocument)) fail(); 
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、正常に取得できること。(XMLの場合)
		Page page2 = new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
		jp.co.dk.document.File document2 = page2.getDocument();
		if (!(document2 instanceof jp.co.dk.document.xml.XmlDocument)) fail(); 
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、正常に取得できること。(IMGの場合)
		Page page3 = new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg");
		jp.co.dk.document.File document3 = page3.getDocument();
		if (!(document3 instanceof jp.co.dk.document.File)) fail(); 
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、かつ、拡張子が存在しない場合、
		// 判断不可能として例外が発生すること。
		try {
			Page page4 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
			final ResponseHeader header = page4.getResponseHeader();
			new Expectations(header) {
				{
					header.getContentsType();
					result = null;
				}
			};
			Deencapsulation.setField(page4, header);
			jp.co.dk.document.File document4 = page4.getDocument();
			if (!(document4 instanceof jp.co.dk.document.File)) fail(); 
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_NON_SUPPORTED_EXTENSION) fail(e);
		}
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、かつ、拡張子が存在する場合、
		// ドキュメントファクトリが正常に呼び出されること。
		try {
			Page page4 = new Page("http://docs.oracle.com/javase/jp/6/api/java/io/Serializable.html");
			final ResponseHeader header = page4.getResponseHeader();
			new Expectations(header) {
				{
					header.getContentsType();
					result = null;
				}
			};
			Deencapsulation.setField(page4, header);
			jp.co.dk.document.File document4 = page4.getDocument();
			if (!(document4 instanceof jp.co.dk.document.html.HtmlDocument)) fail(); 
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、かつ、拡張子が存在する場合、
		// ドキュメントファクトリが正常に呼び出されること。
		try {
			Page page4 = new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
			final ResponseHeader header = page4.getResponseHeader();
			new Expectations(header) {
				{
					header.getContentsType();
					result = null;
				}
			};
			Deencapsulation.setField(page4, header);
			jp.co.dk.document.File document4 = page4.getDocument();
			if (!(document4 instanceof jp.co.dk.document.xml.XmlDocument)) fail(); 
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、かつ、拡張子が存在する場合、
		// ドキュメントファクトリが正常に呼び出されること。
		try {
			Page page4 = new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg");
			final ResponseHeader header = page4.getResponseHeader();
			new Expectations(header) {
				{
					header.getContentsType();
					result = null;
				}
			};
			Deencapsulation.setField(page4, header);
			jp.co.dk.document.File document4 = page4.getDocument();
			if (!(document4 instanceof jp.co.dk.document.File)) fail(); 
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getParameter() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("t", "js");
		map.put("p", "location4");
		try {
			Page page = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
			assertEquals(page.getParameter(), map);
		} catch (BrowzingException e) {
			fail(e);
		}
		
	}
	
	@Test
	public void getPath() {
		try {
			assertEquals(new Page("http://gigazine.net/").getPath(), "");
			assertEquals(new Page("http://gigazine.net").getPath(), "");
			assertEquals(new Page("http://d.hatena.ne.jp/teraliso/20081202/").getPath(), "teraliso/20081202/");
			assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getPath(), "docs/sw/");
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getFileName() throws BrowzingException {
		assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getFileName(), "http-header.html");
		assertEquals(new Page("http://gigazine.net/").getFileName(), "default.html");
		assertEquals(new Page("http://gigazine.net").getFileName(), "default.html");
		assertEquals(new Page("http://d.hatena.ne.jp/teraliso/20081202/").getFileName(), "default.html");
		assertEquals(new Page("http://d.hatena.ne.jp/teraliso/20081202").getFileName(), "default.html");
	}
	
	@Test
	public void getExtension() throws BrowzingException {
		assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getExtension(), "html");
		assertEquals(new Page("http://gigazine.net").getExtension(), "html");
		assertEquals(new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getExtension(), "jpg");
		assertEquals(new Page("http://www.tohoho-web.com/html/meta.htm").getExtension(), "htm");
		
		final Page page = new Page("http://gigazine.net");
		new Expectations(page) {
			{
				page.getFileName();
				result = "aaa";
			}
		};
		assertEquals(page.getExtension(), "");
	}
	
	@Test
	public void getProtocol() {
		try {
			assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getProtocol(), "http");
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getAnchor() {
		
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getAnchor();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		}
		try {
			new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml").getAnchor();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		}
		
		// 正常なHTMLのページの場合、正常に処理が完了すること。
		try {
			List<A> anchors = new Page("http://www.google.co.jp").getAnchor();
			if (anchors.size() == 0) fail();
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getForm() {
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getForm();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		}
		try {
			new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml").getForm();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		}
		
		// 正常なHTMLのページの場合、正常に処理が完了すること
		try {
			Page page = super.createPage("http://www.google.co.jp");
			List<jp.co.dk.document.html.element.Form> formList = page.getForm();
			if (formList.size() == 0) fail();
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getSize() throws BrowzingException {
		Page page = new Page("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		long size = page.getSize();
		if (size == 110109) fail(); 
	}
	
	@Test
	public void save() {
		try {
			// Shift_JIS
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = page1.save(super.getTestTmpDir());
			if(!file1.exists() && file1.getName().equals("meta.htm")) fail();
			
			// UTF-8
			Page page2 = new Page("http://news.2chblog.jp/archives/51702010.html");
			java.io.File file2 = page2.save(super.getTestTmpDir());
			if(!file2.exists() && file2.getName().equals("51702010.html")) fail();
			
			// EUC-JP
			Page page3 = new Page("http://d.hatena.ne.jp/teraliso/20081202");
			java.io.File file3 = page3.save(super.getTestTmpDir());
			if(!file3.exists() && file3.getName().equals("default.html")) fail();
		} catch (BrowzingException e) {
			fail(e);
		}
		
		try {
			// Shift_JIS
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = page1.save(super.getTestTmpDir(), "aaa.html");
			if(!file1.exists() && file1.getName().equals("aaa.html")) fail();
			
			// UTF-8
			Page page2 = new Page("http://news.2chblog.jp/archives/51702010.html");
			java.io.File file2 = page2.save(super.getTestTmpDir(), "bbb,html");
			if(!file2.exists() && file2.getName().equals("bbb.html")) fail();
			
			// EUC-JP
			Page page3 = new Page("http://d.hatena.ne.jp/teraliso/20081202");
			java.io.File file3 = page3.save(super.getTestTmpDir(), "ccc,html");
			if(!file3.exists() && file3.getName().equals("ccc.html")) fail();
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 保存に失敗した場合、例外が発生すること。
		// 例外はもう一度同じ名前で保存し、エラーとすることで発生させる。
		try {
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = page1.save(super.getTestTmpDir());
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_FAILED_TO_DOWNLOAD) fail(e);
		}
		
		// 保存に失敗した場合、例外が発生すること。
		// 例外はもう一度同じ名前で保存し、エラーとすることで発生させる。
		try {
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = page1.save(super.getTestTmpDir(), "aaa.html");
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_FAILED_TO_DOWNLOAD) fail(e);
		}
	}
	
	@Test
	public void download() {
		try {
			// Shift_JIS
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			page1.download(super.getTestTmpDir());
			
			// UTF-8
			Page page2 = new Page("http://news.2chblog.jp/archives/51702010.html");
			page2.download(super.getTestTmpDir());
			
			// EUC-JP
			Page page3 = new Page("http://d.hatena.ne.jp/teraliso/20081202");
			page3.download(super.getTestTmpDir());
		} catch (BrowzingException e) {
			fail(e);
		}
	}
}