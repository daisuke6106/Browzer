package jp.co.dk.browzer.http.header;

import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_FAILED_TO_CONVERT_LAST_MODIFIED_FIRLD_MORE_THEN_ONE;
import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_FAILED_TO_CONVERT_LAST_MODIFIED_FORMAT_UNKNOWN;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.TestBrowzerFoundation;
import jp.co.dk.browzer.exception.BrowzingException;

public class TestResponseHeader extends TestBrowzerFoundation {
	
	@Test
	public void constractor() {
		// ＝＝＝＝＝テスト用データ作成＝＝＝＝＝
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HTTP/1.0 200 OK");
		map.put(null, list1);
		
		List<String> list2 = new ArrayList<String>();
		list2.add("ja");
		map.put("Content-Language", list2);
		
		List<String> list3 = new ArrayList<String>();
		list3.add("text/html; charset=UTF-8");
		map.put("Content-Type", list3);
		
		List<String> list4 = new ArrayList<String>();
		list4.add("39972");
		map.put("Age", list4);
		
		List<String> list5 = new ArrayList<String>();
		list5.add("Fri, 25 Oct 2013 22:28:00 GMT");
		map.put("Last-Modified", list5);
		
		try {
			ResponseHeader target = new ResponseHeader(map);
		} catch (BrowzingException e) {
			fail(e);
		}
	}
	
	@Test
	public void getLastModified_01() {
		// ＝＝＝＝＝テスト用データ作成＝＝＝＝＝
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HTTP/1.0 200 OK");
		map.put(null, list1);
		List<String> list5 = new ArrayList<String>();
		list5.add("Fri, 25 Oct 2013 22:28:00 GMT");
		map.put("Last-Modified", list5);
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		
		// ＝＝＝＝＝テスト実行＝＝＝＝＝
		try {
			ResponseHeader target = new ResponseHeader(map);
			Date date = target.getLastModified();
			assertEquals(date.toString(), "Sat Oct 26 07:28:00 JST 2013");
		} catch (BrowzingException e) {
			fail(e);
		}
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	}
	
	@Test
	public void getLastModified_02() {
		// ＝＝＝＝＝テスト用データ作成＝＝＝＝＝
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HTTP/1.0 200 OK");
		map.put(null, list1);
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		
		// ＝＝＝＝＝テスト実行＝＝＝＝＝
		try {
			ResponseHeader target = new ResponseHeader(map);
			Date date = target.getLastModified();
			assertNull(date);
		} catch (BrowzingException e) {
			fail(e);
		}
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	}
	
	@Test
	public void getLastModified_03() {
		// ＝＝＝＝＝テスト用データ作成＝＝＝＝＝
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HTTP/1.0 200 OK");
		map.put(null, list1);
		List<String> list5 = new ArrayList<String>();
		list5.add("Fri, 25 Oct 2013 22:28:00 GMT");
		list5.add("Fri, 25 Oct 2013 22:28:00 GMT");
		map.put("Last-Modified", list5);
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		
		// ＝＝＝＝＝テスト実行＝＝＝＝＝
		try {
			ResponseHeader target = new ResponseHeader(map);
			Date date = target.getLastModified();
			fail();
		} catch (BrowzingException e) {
			assertEquals(e.getMessageObj(), ERROR_FAILED_TO_CONVERT_LAST_MODIFIED_FIRLD_MORE_THEN_ONE);
		}
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	}
	
	@Test
	public void getLastModified_04() {
		// ＝＝＝＝＝テスト用データ作成＝＝＝＝＝
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HTTP/1.0 200 OK");
		map.put(null, list1);
		List<String> list5 = new ArrayList<String>();
		list5.add("Fri, 25 Oct 2013 22:28:00");
		map.put("Last-Modified", list5);
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		
		// ＝＝＝＝＝テスト実行＝＝＝＝＝
		try {
			ResponseHeader target = new ResponseHeader(map);
			Date date = target.getLastModified();
			fail();
		} catch (BrowzingException e) {
			assertEquals(e.getMessageObj(), ERROR_FAILED_TO_CONVERT_LAST_MODIFIED_FORMAT_UNKNOWN);
		}
		// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	}
}

////Content-Length=[125447], 
//Last-Modified=[Fri, 25 Oct 2013 22:28:00 GMT], 
//X-Cache-Lookup=[MISS from cp1020.eqiad.wmnet:80, HIT from cp1016.eqiad.wmnet:3128], 
//Connection=[keep-alive], 
//Server=[Apache], 
//X-Cache=[MISS from cp1020.eqiad.wmnet, HIT from cp1016.eqiad.wmnet], 
//X-Content-Type-Options=[nosniff], 
//Cache-Control=[private, s-maxage=0, max-age=0, must-revalidate], 
//Date=[Fri, 01 Nov 2013 07:22:22 GMT], 
//Vary=[Accept-Encoding,Cookie], 