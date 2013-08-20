package jp.co.dk.browzer;

import jp.co.dk.browzer.html.element.TestA;
import jp.co.dk.browzer.html.element.TestForm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestBrowser.class,
	TestPage.class,
	TestA.class,
	TestForm.class,
})
public class TestAll {}
 