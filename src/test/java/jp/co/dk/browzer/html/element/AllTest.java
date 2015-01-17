package jp.co.dk.browzer.html.element;

import jp.co.dk.browzer.html.element.ATest;
import jp.co.dk.browzer.html.element.FormTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ATest.class,
	FormTest.class,
})
public class AllTest {}
 