package jp.co.dk.browzer.property;

import static org.junit.Assert.*;
import jp.co.dk.property.Property;

import org.junit.Test;

public class BrowzerPropertyTest {

	@Test
	public void test() {
		java.util.List<Property> requestHeader = BrowzerProperty.REQUEST_HEADER.getPropertyList();
		for (Property property : requestHeader) {
			String[] list = property.getStringArray();
			StringBuilder sb = new StringBuilder();
			for (String value : list)sb.append(value).append(',');
			System.out.println(property.getKey() + "=" + sb.toString().substring(0, sb.length()-1));
		}
	}

}
