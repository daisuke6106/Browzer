package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.util.List;
import java.util.Map;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.browzer.html.element.Image;
import jp.co.dk.document.Element;
import jp.co.dk.document.ElementName;
import jp.co.dk.document.html.HtmlDocument;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;

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
		
		// URLオブジェクトを指定した場合、正常に遷移できること。
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			A anchor = browzer1.getAnchor().get(0);
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
			browzer1.move(browzer1.getAnchor().get(0));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
		// 指定のページ遷移上限数を指定してページ遷移した場合、指定した上限を超えようとした場合、例外が送出されること。
		// ページ遷移上限数１で遷移した場合
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 1);
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
		// 指定のページ遷移上限数を指定してページ遷移した場合、指定した上限を超えようとした場合、例外が送出されること。
		// ページ遷移上限数５で遷移した場合
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 5);
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
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
			if (e.getMessageObj() != ERROR_SPECIFIED_ANCHOR_IS_NOT_SET) ;
		}
		
		// 遷移先にnullを指定した場合、例外が送出されることを確認。
		try {
			String url1 = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			String url2 = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url1);
			Browzer browzer2 = super.getBrowzer(url2);
			HtmlDocument document1 = (HtmlDocument)browzer1.getPage().getDocument();
			HtmlDocument document2 = (HtmlDocument)browzer2.getPage().getDocument();
			List<Element> imageList1 = document1.getElement(HtmlElementName.IMG);
			List<Element> imageList2 = document2.getElement(HtmlElementName.IMG);
			
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_ANCHOR_IS_NOT_SET) ;
		}
	}
	
	@Test
	public void back() {
		// ページ遷移後、戻るメソッドを実施した場合、以前の画面に戻ること
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			A anchor = browzer1.getAnchor().get(0);
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
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			if (!browzer1.ableMoveNextPage()) fail(); 
			browzer1.move(browzer1.getAnchor().get(0));
			if (browzer1.ableMoveNextPage()) fail(); 
			
			Browzer browzer2 = super.getBrowzer(url);
			browzer2.move(browzer2.getAnchor().get(0));
			if (!browzer2.ableMoveNextPage()) fail();
			browzer2.move(browzer2.getAnchor().get(0));
			if (!browzer2.ableMoveNextPage()) fail(); 
			browzer2.move(browzer2.getAnchor().get(0));
			if (!browzer2.ableMoveNextPage()) fail(); 
			browzer2.move(browzer2.getAnchor().get(0));
			if (!browzer2.ableMoveNextPage()) fail(); 
		} catch (BrowzingException e) {
			fail(e);
		}
	}
}
