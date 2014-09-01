package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageSaveException;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.http.header.ResponseHeader;
import jp.co.dk.document.Element;
import jp.co.dk.document.exception.DocumentException;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.element.A;
import jp.co.dk.document.html.element.form.FormText;
import jp.co.dk.document.html.exception.HtmlDocumentException;
import jp.co.dk.document.message.DocumentMessage;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Test;

public class PageTest extends BrowzerFoundationTest {
	
	@Test
	public void constructor() {
		
		// 引数にnullを設定した場合、例外が送出されること。
		try {
			String nullString = null;
			Page page = new Page(nullString);
			fail();
		} catch (PageIllegalArgumentException e) {
			assertEquals(e.getMessage(), ERROR_URL_IS_NOT_SET.getMessage());
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// 引数に不正な文字列を設定した場合、例外が送出されること。
		try {
			Page page = new Page("aaa");
			fail();
		} catch (PageIllegalArgumentException e) {
			assertEquals(e.getMessage(), ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN.getMessage("aaa"));
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// 引数に存在するURLを設定された場合、正常にインスタンスが生成できること。
		try {
			Page page = new Page("https://www.google.com");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// 引数に存在するURLを設定された場合、正常にインスタンスが生成できること。(パラメータあり)
		try {
			Page page = new Page("http://www.google.co.jp/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&ved=0CCUQFjAA&url=http%3A%2F%2Ftest.jp%2F&ei=if9NULqAIanBiQf1uIHACg&usg=AFQjCNFAiQ33DWa0llvM8UoNTY_aKUckbg&sig2=VmamtFiwcu4ABy2tGn5hfQ");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// FORM指定して遷移する際に、FORMが指定されていなかった場合、指定の例外が出力されること。
		try {
			Form form = null;
			new Page(form, new HashMap<String, String>());
			fail();
		} catch (PageIllegalArgumentException e) {
			assertEquals(e.getMessage(), ERROR_FORM_IS_NOT_SPECIFIED.getMessage());
 		} catch (PageAccessException e) {
			fail(e);
		}
		
		// FORM指定して遷移する際に、URLの作成に失敗した場合、指定の例外が出力されること。
		try {
			Page page = new Page("http://www.tohoho-web.com/html/form.htm");
			HtmlDocument document1 = (HtmlDocument)page.getDocument();
			List<Element> formList1 = document1.getElement(HtmlElementName.FORM);
			final Form formElement = (Form)formList1.get(0);
			String uel = formElement.getAction().getURL().toString();
			new Expectations(formElement) {{
				formElement.getAction();
				result = new HtmlDocumentException(DocumentMessage.ERROR_AN_INVALID_URL_WAS_SPECIFIED, "Dummy URL", new Exception());
			}};
			Page newPage = new Page(formElement, new HashMap<String, String>());
			fail();
		} catch (PageIllegalArgumentException e) {
			assertEquals(e.getMessage(), ERROR_AN_INVALID_URL_WAS_SPECIFIED.getMessage("Dummy URL"));
 		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		}
		
		// FORM指定して遷移する際に、引数のリクエストヘッダマップにnullが設定された場合でも正常に遷移できること。
		try {
			Page page = new Page("http://www.tohoho-web.com/html/form.htm");
			HtmlDocument document1 = (HtmlDocument)page.getDocument();
			List<Element> formList1 = document1.getElement(HtmlElementName.FORM);
			final Form formElement = (Form)formList1.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			FormText txt1 = (FormText)formList.get(0);
			FormText txt2 = (FormText)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			Page newPage = new Page(formElement, null);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		}
	}
	
	@Test
	public void getHost(){
		// URLにホスト名のみ指定して作成していた場合でもホスト名のみ抽出されること。
		try {
			Page page1 = new Page("http://ja.wikipedia.org");
			assertEquals(page1.getHost(), "ja.wikipedia.org");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}		
		
		// URLにホスト名＋パスを指定して作成した場合でも、ホスト名を正常に取得できること。
		try {
			Page page2 = new Page("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
			assertEquals(page2.getHost(), "ja.wikipedia.org");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
	}
	
	@Test
	public void getURL(){
		// URLにホスト名のみ指定して作成していた場合、正常にURLが返却されること。
		try {
			Page page1 = new Page("http://ja.wikipedia.org");
			assertEquals("http://ja.wikipedia.org", page1.getURL());
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// URLにホスト名＋パスを指定して作成していた場合、正常にURLが返却されること。
		try {
			Page page2 = new Page("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
			assertEquals("http://ja.wikipedia.org/wiki/HyperText_Markup_Language", page2.getURL());
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}

		// URLにホスト名＋パス＋パラメータを指定して作成していた場合、正常にURLが返却されること。
		try {
			Page page3 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
			assertEquals("http://phpjavascriptroom.com/?t=js&p=location4", page3.getURL());
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
	}
	
	@Test
	public void getURLObject(){
		// URLにホスト名のみ指定して作成していた場合、正常にURLが返却されること。
		try {
			Page page1 = new Page("http://ja.wikipedia.org");
			assertEquals("http://ja.wikipedia.org", page1.getURLObject().toString());
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// URLにホスト名＋パスを指定して作成していた場合、正常にURLが返却されること。
		try {
			Page page2 = new Page("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
			assertEquals("http://ja.wikipedia.org/wiki/HyperText_Markup_Language", page2.getURLObject().toString());
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
		// URLにホスト名＋パス＋パラメータを指定して作成していた場合、正常にURLが返却されること。
		try {
			Page page3 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
			assertEquals("http://phpjavascriptroom.com/?t=js&p=location4", page3.getURLObject().toString());
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
	}
	
	@Test
	public void getDocument(){
		// 通常にURLからドキュメンとオブジェクトを取得した場合、正常に取得できること。(HTMLの場合)
		try {
			Page page1 = new Page("http://phpjavascriptroom.com/?t=js&p=location4");
			jp.co.dk.document.File document1 = page1.getDocument();
			if (!(document1 instanceof jp.co.dk.document.html.HtmlDocument)) fail(); 
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		}
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、正常に取得できること。(XMLの場合)
		try {
			Page page2 = new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
			jp.co.dk.document.File document2 = page2.getDocument();
			if (!(document2 instanceof jp.co.dk.document.xml.XmlDocument)) fail(); 
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		}
		
		// 通常にURLからドキュメンとオブジェクトを取得した場合、正常に取得できること。(IMGの場合)
		try {
			Page page3 = new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg");
			jp.co.dk.document.File document3 = page3.getDocument();
			if (!(document3 instanceof jp.co.dk.document.File)) fail(); 
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		}
		
		// 通常にURLからドキュメントオブジェクトを取得した場合、かつ、拡張子が存在しない場合、
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
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
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
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
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
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
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
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
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
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
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
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
	}
	
	@Test
	public void getFileName() {
		try {
			assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getFileName(), "http-header.html");
			assertEquals(new Page("http://gigazine.net/").getFileName(), "index.html");
			assertEquals(new Page("http://gigazine.net").getFileName(), "index.html");
			assertEquals(new Page("http://d.hatena.ne.jp/teraliso/20081202/").getFileName(), "index.html");
			assertEquals(new Page("http://d.hatena.ne.jp/teraliso/20081202").getFileName(), "index.html");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
	}
	
	@Test
	public void getExtension() {
		try {
			assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getExtension(), "html");
			assertEquals(new Page("http://gigazine.net").getExtension(), "html");
			assertEquals(new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getExtension(), "jpg");
			assertEquals(new Page("http://www.tohoho-web.com/html/meta.htm").getExtension(), "htm");
			final Page page = new Page("http://gigazine.net");
			new Expectations(page) {{
				page.getFileName();
				result = "aaa";
			}};
			assertEquals(page.getExtension(), "");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
	}
	
	@Test
	public void getProtocol() {
		try {
			assertEquals(new Page("http://www.kanzaki.com/docs/sw/http-header.html").getProtocol(), "http");
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
	}
	
	@Test
	public void getAnchor() {
		
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getAnchor();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
		
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml").getAnchor();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
		
		// 正常なHTMLのページの場合、正常に処理が完了すること。
		try {
			List<A> anchors = new Page("http://www.google.co.jp").getAnchor();
			if (anchors.size() == 0) fail();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
	}
	
	@Test
	public void getAnchorSameDomain() {
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getAnchorSameDomain();
			fail();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
		
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml").getAnchorSameDomain();
			fail();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
		
		// 正常なHTMLのページの場合、正常に処理が完了すること。
		try {
			List<A> anchors = new Page("http://news.2chblog.jp/archives/51702010.html").getAnchorSameDomain();
			for (A anchor : anchors){
				Url thisAnchorUrl = new Url(anchor.getUrl());
				assertEquals(thisAnchorUrl.getHost(), "news.2chblog.jp");
			}
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
	}
	
	@Test
	public void getForm() {
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg").getForm();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
		
		// URLがHTMLでない場合、例外が送出されること。
		try {
			new Page("http://rss.dailynews.yahoo.co.jp/fc/rss.xml").getForm();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			if (e.getMessageObj() != ERROR_THIS_PAGE_CAN_NOT_BE_USERD_TO_OBTAIN_THE_ANCHOR_BECAUSE_IT_IS_NOT_HTML) fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
		
		// 正常なHTMLのページの場合、正常に処理が完了すること
		try {
			Page page = new Page("http://www.htmq.com/html/form.shtml");
			List<jp.co.dk.document.html.element.Form> formList = page.getForm();
			assertEquals(formList.size(), 3);
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		}
	}
	
	@Test
	public void getSize(){
		try {
			Page page = new Page("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
			long size = page.getSize();
			if (size == 110109) fail(); 
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageAccessException e) {
			fail(e);
		}
		
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
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageSaveException e) {
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
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageSaveException e) {
			fail(e);
		}
		
		// 保存に失敗した場合、例外が発生すること。
		// 例外はもう一度同じ名前で保存し、エラーとすることで発生させる。
		try {
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = page1.save(super.getTestTmpDir());
			fail();
		}catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageSaveException e) {
			if (e.getMessageObj() != ERROR_FAILED_TO_DOWNLOAD) fail(e);
		}
		
		// 保存に失敗した場合、例外が発生すること。
		// 例外はもう一度同じ名前で保存し、エラーとすることで発生させる。
		try {
			Page page1 = new Page("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = page1.save(super.getTestTmpDir(), "aaa.html");
			fail();
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageSaveException e) {
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
		} catch (PageAccessException e) {
			fail(e);
		} catch (DocumentException e) {
			fail(e);
		} catch (PageIllegalArgumentException e) {
			fail(e);
		} catch (PageSaveException e) {
			fail(e);
		}
	}
}