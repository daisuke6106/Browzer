package jp.co.dk.browzer.html.element;

import static org.junit.Assert.*;

import java.util.List;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.TestBrowzerFoundation;
import jp.co.dk.browzer.exception.BrowzingException;

import org.junit.Test;

public class TestForm extends TestBrowzerFoundation {

	@Test
	public void getAction() throws BrowzingException {
		Browzer browzer = super.getBrowzer("http://www.google.co.jp");
		List<Form> formList = browzer.getForm();
		Form form = formList.get(0);
		
		Action action = form.getAction();
		assertThat(action, is (action));
		fail();
	}
	
	

}
