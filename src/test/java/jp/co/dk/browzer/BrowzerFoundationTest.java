package jp.co.dk.browzer;

import java.util.ArrayList;
import java.util.List;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.test.template.TestCaseTemplate;

public class BrowzerFoundationTest extends TestCaseTemplate {
	
	protected String getRandomUrl() {
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://news020.blog13.fc2.com/blog-entry-3080.html");
		urlList.add("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		urlList.add("http://michaelsan.livedoor.biz/archives/51798684.html");
		urlList.add("http://www.watch2chan.com/archives/32013869.html");
		urlList.add("http://burusoku-vip.com/archives/1694675.html");
		urlList.add("http://ip.w69b.com/");
		urlList.add("http://avaronsukareto.blog120.fc2.com/");
		return super.getRandomElement(urlList);
	}
	
	protected Browzer getBrowzer() throws BrowzingException {
		return getBrowzer(getRandomUrl());
	}
	
	protected Browzer getBrowzer(String url) throws BrowzingException{
		return new Browzer(url);
	}
	
	protected Browzer getBrowzer(int nestLevel) throws BrowzingException {
		return new Browzer(getRandomUrl(), nestLevel);
	}
	
	protected Browzer getBrowzer(String url, int nestLevel) throws BrowzingException {
		return new Browzer(url, nestLevel);
	}
	
}
