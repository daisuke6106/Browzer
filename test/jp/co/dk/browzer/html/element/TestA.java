package jp.co.dk.browzer.html.element;

import java.util.List;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.TestBrowzerFoundation;
import jp.co.dk.browzer.exception.BrowzingException;

import org.junit.Test;

public class TestA extends TestBrowzerFoundation {
	
	@Test
	public void getHref() throws BrowzingException {
		Browzer browzer = super.getBrowzer("http://www.google.com");
		List<A> anchorList = browzer.getAnchor();
		assertThat(anchorList.get(0).getHref(), is ("http://www.google.co.jp/"));
	}
	
	@Test
	public void getPage() {
		Browzer browzer = super.getBrowzer("http://takenokosokuhou.com/archives/18579834.html");
		assertThat(browzer.getPage().getURL(), is ("http://takenokosokuhou.com/archives/18579834.html"));
	}
	
	@Test
	public void getHost() throws BrowzingException {
		Browzer browzer = super.getBrowzer("http://takenokosokuhou.com/archives/18579834.html");
		assertThat(browzer.getAnchor().get(0).getHost(), is ("takenokosokuhou.com"));
	}
	
}
