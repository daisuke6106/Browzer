package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import jp.co.dk.browzer.download.DownloadJudge;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.html.element.Image;
import jp.co.dk.document.Element;
import jp.co.dk.document.ElementName;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.element.form.Text;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

public class TestBrowser extends TestBrowzerFoundation {
	
	@Test
	public void constractor() {
		
		// 存在するURLを引数に設定した場合、接続に成功すること
		try {
			Browzer browzer = new Browzer("http://blog.livedoor.jp/kinisoku/archives/3334999.html");
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 接続先にnullを指定した場合、例外が発生すること。
		try {
			Browzer browzer = new Browzer(null);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_URL_IS_NOT_SET) fail(e);
			success(e);
		}
		
		// 接続先にnullを指定した場合、例外が発生すること。
		try {
			Browzer browzer = new Browzer("");
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_URL_IS_NOT_SET) fail(e);
			success(e);
		}
		
		// 存在しないURLが指定された場合、例外が発生すること。
		try {
			Browzer browzer = new Browzer("abcd");
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN) fail(e);
			success(e);
		}
		
	}
	
	@Test
	public void move() {
		// 遷移先にnullを指定した場合、例外が送出されることを確認。
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer = super.getBrowzer(url);
			A anhor = null;
			browzer.move(anhor);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_ANCHOR_IS_NOT_SET) ;
		}
		
		// 異なるページオブジェクトにあるURLを指定した場合、例外が発生すること。
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			Browzer browzer2 = super.getBrowzer(url);
			Page page = browzer2.move(browzer1.getAnchor().get(0));
			fail();
		} catch (BrowzingException e) {
			if ( e.getMessageObj() != ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE) fail(e);
		}
		
		// 遷移先に指定したアンカーに遷移先が設定されていなかった場合、例外が送出されることを確認。
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			final A anchor = (A) super.getRandomElement(browzer1.getAnchor());
			new Expectations(anchor) {{
				anchor.getHref();
	            returns("");
	        }};
			Page page = browzer1.move(anchor);
			fail();
		} catch (BrowzingException e) {
			if ( e.getMessageObj() != ERROR_ANCHOR_HAS_NOT_URL) fail(e);
		}
		
		// URLオブジェクトを指定した場合、正常に遷移できること。
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			A anchor = super.getRandomElement(browzer1.getAnchor());
			browzer1.move(anchor);
			Page page = browzer1.getPage();
			assertEquals(page.getURL(), anchor.getHref());
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 指定のページ遷移上限数を指定してページ遷移した場合、指定した上限を超えようとした場合、例外が送出されること。
		// ページ遷移上限数０で遷移した場合
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 0);
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
		// 指定のページ遷移上限数を指定してページ遷移した場合、指定した上限を超えようとした場合、例外が送出されること。
		// ページ遷移上限数１で遷移した場合
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 1);
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
		// 指定のページ遷移上限数を指定してページ遷移した場合、指定した上限を超えようとした場合、例外が送出されること。
		// ページ遷移上限数５で遷移した場合
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 5);
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
		// イメージ要素に遷移する
		// 遷移先にnullを指定した場合、例外が送出されることを確認。
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer = super.getBrowzer(url);
			Image anhor = null;
			browzer.move(anhor);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_ANCHOR_IS_NOT_SET) fail(e);
		}
		
		// イメージ要素に遷移する
		// 異なるページオブジェクトにあるURLを指定した場合、例外が発生すること。
		try {
			String url1 = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			String url2 = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url1);
			Browzer browzer2 = super.getBrowzer(url2);
			HtmlDocument document1 = (HtmlDocument)browzer1.getPage().getDocument();
			HtmlDocument document2 = (HtmlDocument)browzer2.getPage().getDocument();
			List<Element> imageList1 = document1.getElement(HtmlElementName.IMG);
			Element randomElement = super.getRandomElement(imageList1);
			browzer2.move((Image)randomElement);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE) ;
		}
		
		// イメージ要素に遷移する
		// 遷移先が指定されていなかった（空文字）場合、例外を送出すること。
		try {
			String url1 = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url1);
			HtmlDocument document1 = (HtmlDocument)browzer1.getPage().getDocument();
			List<Element> imageList1 = document1.getElement(HtmlElementName.IMG);
			final Image image = (Image) super.getRandomElement(imageList1);
			new Expectations(image) {{
            	image.getSrc();
                returns("");
	        }};
	        browzer1.move(image);
			fail();
		}catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_ANCHOR_HAS_NOT_URL) fail(e);
		}
		
		// イメージ要素に遷移する
		// 引数に指定された値が不正でない場合、正常に遷移できること。
		try {
			String url1 = "http://d.hatena.ne.jp/torutk/20110118/p1";
			Browzer browzer1 = super.getBrowzer(url1);
			HtmlDocument document1 = (HtmlDocument)browzer1.getPage().getDocument();
			List<Element> imageList1 = document1.getElement(HtmlElementName.IMG);
			Image image = (Image) super.getRandomElement(imageList1);
	        Page imagePage = browzer1.move(image);
	        assertEquals(imagePage.getURL(), image.getSrc());
		}catch (BrowzingException e) {
			fail(e);
		}
		
		
		// FORM要素に遷移する
		// 遷移先にnullを指定した場合、例外が送出されることを確認。
		try {
			String url = "http://www.tohoho-web.com/html/form.htm";
			Browzer browzer = super.getBrowzer(url);
			Form form = null;
			browzer.move(form);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_FORM_IS_NOT_SET) fail(e);
		}
		
		// FROM要素に遷移する
		// 異なるページオブジェクトにあるURLを指定した場合、例外が発生すること。
		try {
			String url1 = "http://www.tohoho-web.com/html/form.htm";
			String url2 = "http://www.tohoho-web.com/html/form.htm";
			Browzer browzer1 = super.getBrowzer(url1);
			Browzer browzer2 = super.getBrowzer(url2);
			HtmlDocument document1 = (HtmlDocument)browzer1.getPage().getDocument();
			HtmlDocument document2 = (HtmlDocument)browzer2.getPage().getDocument();
			List<Element> formList1 = document1.getElement(HtmlElementName.FORM);
			browzer2.move((Form)formList1.get(0));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_FORM_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE) ;
		}
		
		// FROM要素に遷移する
		// 正常な値が設定された場合、正常に遷移できること。
		try {
			String url1 = "http://www.tohoho-web.com/html/form.htm";
			Browzer browzer1 = super.getBrowzer(url1);
			HtmlDocument document1 = (HtmlDocument)browzer1.getPage().getDocument();
			List<Element> formList1 = document1.getElement(HtmlElementName.FORM);
			Form formElement = (Form)formList1.get(0);
			List<HtmlElement> formList = formElement.getFormElementList();
			Text txt1 = (Text)formList.get(0);
			Text txt2 = (Text)formList.get(1);
			txt1.setValue("test");
			txt2.setValue("value");
			browzer1.move(formElement);
			HtmlDocument document2 = (HtmlDocument)browzer1.getPage().getDocument();
			assertHasString(document2.toString(), "NAME = test");
			assertHasString(document2.toString(), "ADDR = value");
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void back() {
		// ページ遷移後、戻るメソッドを実施した場合、以前の画面に戻ること
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			A anchor = super.getRandomElement(browzer1.getAnchor());
			browzer1.move(anchor);
			browzer1.back();
			Page page = browzer1.getPage();
			assertEquals(page.getURL(), url);
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getAnchor() {
		// ページ遷移後、戻るメソッドを実施した場合、以前の画面に戻ること
		try {
			String  url        = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1   = super.getBrowzer(url);
			List<A> anchorList = browzer1.getAnchor();
			for (A anchor : anchorList ) {
				assertThat(anchor.getTagName(), is("a") );
			}
		} catch(BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getForm() throws BrowzingException {
		Browzer browzer = super.getBrowzer("http://www.htmq.com/html/form.shtml");
		List<Form> formList = browzer.getForm();
		assertThat(formList.get(0).getAction().toString(), is("http://www.htmq.com/search/"));
		assertThat(formList.get(1).getAction().toString(), is("cgi-bin/formmail.cgi"));
	}
	
	@Test
	public void getPage() {
		String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
		Browzer browzer1 = super.getBrowzer(url);
		Page page = browzer1.getPage();
		assertEquals(page.getURL(), url);
	}
	
	@Test
	public void ableMoceNextPage() {
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 5);
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			if (!browzer1.ableMoveNextPage()) fail(); 
			browzer1.move(super.getRandomElement(browzer1.getAnchor()));
			if (browzer1.ableMoveNextPage()) fail(); 
			
			Browzer browzer2 = super.getBrowzer(url);
			browzer2.move(super.getRandomElement(browzer2.getAnchor()));
			if (!browzer2.ableMoveNextPage()) fail();
			browzer2.move(super.getRandomElement(browzer2.getAnchor()));
			if (!browzer2.ableMoveNextPage()) fail(); 
			browzer2.move(super.getRandomElement(browzer2.getAnchor()));
			if (!browzer2.ableMoveNextPage()) fail(); 
			browzer2.move(super.getRandomElement(browzer2.getAnchor()));
			if (!browzer2.ableMoveNextPage()) fail(); 
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void save() {
		// HTMLファイルを指定ディレクトリへ保存する
		try {
			// Shift_JIS
			Browzer browzer1 = new Browzer("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1 = browzer1.save(super.getTestTmpDir());
			if(!file1.exists() && file1.getName().equals("meta.htm")) fail();
			
			// UTF-8
			Browzer browzer2 = new Browzer("http://news.2chblog.jp/archives/51702010.html");
			java.io.File file2 = browzer2.save(super.getTestTmpDir());
			if(!file2.exists() && file2.getName().equals("51702010.html")) fail();
			
			// EUC-JP
			Browzer browzer3 = new Browzer("http://d.hatena.ne.jp/teraliso/20081202");
			java.io.File file3 = browzer3.save(super.getTestTmpDir());
			if(!file3.exists() && file3.getName().equals("default.html")) fail();
			
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// HTMLファイルを指定ディレクトリへ指定名称にて保存する
		try {
			// Shift_JIS
			// ２回ダウンロードを実行した場合でも正常にダウンロードできること
			Browzer browzer1 = new Browzer("http://www.tohoho-web.com/html/meta.htm");
			java.io.File file1_1 = browzer1.save(super.getTestTmpDir(), "aaaaaa1.html");
			java.io.File file1_2 = browzer1.save(super.getTestTmpDir(), "aaaaaa2.html");
			if(!file1_1.exists() && file1_1.getName().equals("aaaaaa1.html")) fail();
			if(!file1_2.exists() && file1_2.getName().equals("aaaaaa2.html")) fail();
			
			// UTF-8
			Browzer browzer2 = new Browzer("http://news.2chblog.jp/archives/51702010.html");
			java.io.File file2 = browzer2.save(super.getTestTmpDir(), "bbbbbb.html");
			if(!file2.exists() && file2.getName().equals("bbbbbb.html")) fail();
			
			// EUC-JP
			Browzer browzer3 = new Browzer("http://d.hatena.ne.jp/teraliso/20081202");
			java.io.File file3 = browzer3.save(super.getTestTmpDir(), "cccccc.html");
			if(!file3.exists() && file3.getName().equals("cccccc.html")) fail();
			
		} catch (BrowzingException e) {
			fail(e);
		}
		
		// 指定の条件でダウンロード実行した場合、正常にダウンロードできること。
		try {
			// HTMLの場合のみ、実行すること。
			DownloadJudge downloadJudge = new DownloadJudge() {
				@Override
				public boolean judge(Page page) {
					jp.co.dk.document.File file;
					try {
						file = page.getDocument();
					} catch (BrowzingException e) {
						return false;
					}
					if(file instanceof HtmlDocument) return true;
					return false;
				}
			};
			
			// ダウンロード条件にnullが指定された場合、saveは実行されず、falseが返却されること。
			Browzer browzer1 = new Browzer("http://www.tohoho-web.com/html/meta.htm");
			DownloadJudge nullDownloadJudge = null;
			boolean result1 = browzer1.save(super.getTestTmpDir(), nullDownloadJudge);
			assertFalse(result1);
			
			// イメージの場合、saveは実行されず、falseが返却されること。
			Browzer browzer2 = new Browzer("http://blog-imgs-21-origin.fc2.com/v/i/p/vipvipblogblog/13944.jpg");
			boolean result2 = browzer2.save(super.getTestTmpDir(), downloadJudge);
			assertFalse(result2);
			
			// XMLの場合、saveは実行されず、falseが返却されること。
			Browzer browzer3 = new Browzer("http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
			boolean result3 = browzer3.save(super.getTestTmpDir(), downloadJudge);
			assertFalse(result3);
			
			// HTMLの場合、saveは実行され、trueが返却されること。
			Browzer browzer4 = new Browzer("http://www.tohoho-web.com/html/form.htm");
			boolean result4 = browzer4.save(super.getTestTmpDir(), downloadJudge);
			assertTrue(result4);
			
		} catch (BrowzingException e) {
			fail(e);
		}
	}
}
