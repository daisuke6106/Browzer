package jp.co.dk.browzer.html.element;

import java.util.List;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.TestBrowzerFoundation;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.document.exception.DocumentException;

import org.junit.Test;

public class TestA extends TestBrowzerFoundation {
	
	@Test
	public void getHref() throws BrowzingException {
		
	}
	
	@Test
	public void getPage() throws BrowzingException {
		Browzer browzer = super.getBrowzer("http://takenokosokuhou.com/archives/18579834.html");
		assertThat(browzer.getPage().getURL(), is ("http://takenokosokuhou.com/archives/18579834.html"));
	}
	
	@Test
	public void getHost() throws BrowzingException, DocumentException {
		Browzer browzer = super.getBrowzer("http://takenokosokuhou.com/archives/18579834.html");
		assertThat(browzer.getAnchor().get(0).getHost(), is ("takenokosokuhou.com"));
	}
	
}
