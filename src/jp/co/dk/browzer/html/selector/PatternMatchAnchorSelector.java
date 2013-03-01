package jp.co.dk.browzer.html.selector;

import java.util.regex.Pattern;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.exception.BrowzingException;

import jp.co.dk.document.Element;
import jp.co.dk.document.ElementSelector;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

public class PatternMatchAnchorSelector implements ElementSelector {
	
	private Page page ;
	
	private Pattern pattern;
	
	public PatternMatchAnchorSelector(Page page, Pattern pattern) throws BrowzingException {
		if (page    == null) throw new BrowzingException(ERROR_PAGE_IS_NOT_SET);
		if (pattern == null) throw new BrowzingException(ERROR_SEARCH_TARGET_FILE_NAME_IS_NOT_SET);
		this.page    = page;
		this.pattern = pattern;
	}
	
	@Override
	public boolean judgment(Element element) {
		String host = this.page.getHost();
		if (element instanceof HtmlElement) {
			HtmlElement htmlElement = (HtmlElement) element;
			if (HtmlElementName.A  == htmlElement.getElementType()){
				jp.co.dk.document.html.element.A anchor = (jp.co.dk.document.html.element.A) element;
				String anchorHost = anchor.getHref();
				if (this.pattern.matcher(anchorHost).matches()) return true ;
			}
			return false;
		}
		return false;
	}

}
