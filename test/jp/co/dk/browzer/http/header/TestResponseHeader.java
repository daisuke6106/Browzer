package jp.co.dk.browzer.http.header;

import java.util.ArrayList;
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
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List list1 = new ArrayList<String>();
		list1.add("HTTP/1.0 200 OK");
		map.put(null, list1);
		
		List list2 = new ArrayList<String>();
		list2.add("ja");
		map.put("Content-Language", list2);
		
//		List list2 = new ArrayList<String>();
//		list2.add("ja");
//		map.put("Content-Language", list2);
//		
//		List list2 = new ArrayList<String>();
//		list2.add("Age");
//		map.put("Age", list2);
	}
}

//null=[HTTP/1.0 200 OK], 
//Content-Language=[ja], 
//Age=[39972], 
//Content-Length=[125447], 
//Last-Modified=[Fri, 25 Oct 2013 22:28:00 GMT], 
//X-Cache-Lookup=[MISS from cp1020.eqiad.wmnet:80, HIT from cp1016.eqiad.wmnet:3128], 
//Connection=[keep-alive], 
//Server=[Apache], 
//X-Cache=[MISS from cp1020.eqiad.wmnet, HIT from cp1016.eqiad.wmnet], 
//X-Content-Type-Options=[nosniff], 
//Cache-Control=[private, s-maxage=0, max-age=0, must-revalidate], 
//Date=[Fri, 01 Nov 2013 07:22:22 GMT], 
//Vary=[Accept-Encoding,Cookie], 
//Content-Type=[text/html; charset=UTF-8]