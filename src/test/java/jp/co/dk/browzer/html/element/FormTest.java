package jp.co.dk.browzer.html.element;

import static org.junit.Assert.*;

import java.util.List;

import jp.co.dk.browzer.Browzer;
import jp.co.dk.browzer.BrowzerFoundationTest;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.document.exception.DocumentException;

import org.junit.Test;

public class FormTest extends BrowzerFoundationTest {

	@Test
	public void getAction() throws BrowzingException, DocumentException {
		Browzer browzer = super.getBrowzer("http://www.tohoho-web.com/html/form.htm");
		List<Form> formList = browzer.getForm();
		Form form = formList.get(0);
		
		Action action = form.getAction();
		assertThat(action, is (action));
	}
	
	

}
