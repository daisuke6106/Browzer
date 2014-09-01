package jp.co.dk.browzer;

import jp.co.dk.browzer.html.element.ATest;
import jp.co.dk.browzer.html.element.FormTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	BrowserTest.class,
	PageTest.class,
	
	jp.co.dk.browzer.html.element.AllTest.class,
	jp.co.dk.browzer.http.header.AllTest.class,
	jp.co.dk.browzer.property.AllTest.class,
})
public class AllTest {}
 