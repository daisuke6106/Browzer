package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE;
import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_SPECIFIED_ANCHOR_IS_NOT_SET;

import java.util.List;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.document.html.element.A;
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
