package jp.co.dk.browzer;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.test.template.TestCaseTemplate;

public class TestBrowzerFoundation extends TestCaseTemplate {
	
	protected Browzer getBrowzer(String url) {
		try {
			return new Browzer(url);
		} catch (BrowzingException e) {
			fail(e);
		}
		return null;
	}
	
	protected Browzer getBrowzer(String url, int nestLevel) {
		try {
			return new Browzer(url, nestLevel);
		} catch (BrowzingException e) {
			fail(e);
		}
		return null;
	}
	
	protected Page createPage(String url) {
		try {
			return new Page(url);
		} catch (BrowzingException e) {
			fail(e);
		}
		return null;
	}
}
