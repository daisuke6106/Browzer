package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

import java.util.List;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.element.A;
import jp.co.dk.browzer.html.element.Form;

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
		
		try {
			Browzer browzer = new Browzer(null);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_URL_IS_NOT_SET) fail(e);
			success(e);
		}
		
		try {
			Browzer browzer = new Browzer("");
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_URL_IS_NOT_SET) fail(e);
			success(e);
		}
		
		try {
			Browzer browzer = new Browzer("abcd");
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_PROTOCOL_OF_THE_URL_SPECIFIED_IS_UNKNOWN) fail(e);
			success(e);
		}
		
		try {
			Browzer browzer = new Browzer("http://blog.livedoor.jp/kinisoku/archives/sonzaishinai.html");
		} catch (BrowzingException e) {
			fail(e);
		}
		
	}
	
	@Test
	public void move() {
		
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer = super.getBrowzer(url);
			A anhor = null;
			browzer.move(anhor);
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_SPECIFIED_ANCHOR_IS_NOT_SET) ;
		}
		
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url);
			Browzer browzer2 = super.getBrowzer(url);
			Page page = browzer2.move(browzer1.getAnchor().get(0));
			fail();
		} catch (BrowzingException e) {
			if ( e.getMessageObj() != ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE) fail(e);
		}
		
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
		
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 0);
			browzer1.move(browzer1.getAnchor().get(0));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
		try {
			String url = "http://blog.livedoor.jp/kinisoku/archives/3334999.html";
			Browzer browzer1 = super.getBrowzer(url, 1);
			browzer1.move(browzer1.getAnchor().get(0));
			browzer1.move(browzer1.getAnchor().get(0));
			fail();
		} catch (BrowzingException e) {
			if (e.getMessageObj() != ERROR_REACHED_TO_THE_MAXIMUM_LEVEL) fail(e);
		}
		
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
	}
	
	@Test
	public void back() {
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
		fail();
	}
	
	@Test
	public void getForm() throws BrowzingException {
		Browzer browzer = super.getBrowzer("http://www.gppgle.co.jp");
		List<Form> formList = browzer.getForm();
		fail();
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
