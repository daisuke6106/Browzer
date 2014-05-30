package jp.co.dk.browzer;

import java.util.ArrayList;
import java.util.List;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageMovableLimitException;
import jp.co.dk.browzer.exception.PageRedirectException;
import jp.co.dk.browzer.html.element.Form;
import jp.co.dk.message.MessageInterface;
import jp.co.dk.test.template.TestCaseTemplate;

public class TestBrowzerFoundation extends TestCaseTemplate {
	
	protected String getRandomUrl() {
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://news020.blog13.fc2.com/blog-entry-3080.html");
		urlList.add("http://ja.wikipedia.org/wiki/HyperText_Markup_Language");
		urlList.add("http://michaelsan.livedoor.biz/archives/51798684.html");
		urlList.add("http://www.watch2chan.com/archives/32013869.html");
		urlList.add("http://burusoku-vip.com/archives/1694675.html");
		urlList.add("http://kazetaka.com/archives/52044363.html");
		urlList.add("http://www.a-kabe.com/aria/");
		urlList.add("http://ip.w69b.com/");
		urlList.add("http://avaronsukareto.blog120.fc2.com/");
		return super.getRandomElement(urlList);
	}
	
	protected Browzer getBrowzer() throws BrowzingException {
		return getBrowzer(getRandomUrl());
	}
	
	protected Browzer getBrowzer(String url) throws BrowzingException{
		return new BrowzerForTest(url);
	}
	
	protected Browzer getBrowzer(int nestLevel) throws BrowzingException {
		return new BrowzerForTest(getRandomUrl(), nestLevel);
	}
	
	protected Browzer getBrowzer(String url, int nestLevel) throws BrowzingException {
		return new BrowzerForTest(url, nestLevel);
	}
	
	protected Page createPage(String url) throws BrowzingException{
		return new Page(url);
	}
	
	protected void assertEquals(MessageInterface e1, MessageInterface e2) {
		super.assertEquals(e1.getMessage(), e2.getMessage());
	}
	
	protected void fail(BrowzingException e) {
		e.printStackTrace();
		super.fail(e);
	}
	
}

class BrowzerForTest extends Browzer{

	public BrowzerForTest(String url) throws PageIllegalArgumentException, PageAccessException {
		super(url);
	}
	
	public BrowzerForTest(String url, int nestLebel) throws PageIllegalArgumentException, PageAccessException {
		super(url,nestLebel);
	}
	
	@Override
	public Page move(Form form) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException{
		return super.move(form);
	}
	
}
