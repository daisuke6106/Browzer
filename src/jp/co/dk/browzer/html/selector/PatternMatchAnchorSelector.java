package jp.co.dk.browzer.html.selector;

import java.util.regex.Pattern;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;

import jp.co.dk.document.Element;
import jp.co.dk.document.ElementSelector;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

public class PatternMatchAnchorSelector implements ElementSelector {
	
	private Page page ;
	
	private Pattern pattern;
	
	public PatternMatchAnchorSelector(Page page, Pattern pattern) throws PageIllegalArgumentException {
		if (page    == null) throw new PageIllegalArgumentException(ERROR_PAGE_IS_NOT_SET);
		if (pattern == null) throw new PageIllegalArgumentException(ERROR_SEARCH_TARGET_FILE_NAME_IS_NOT_SET);
		this.page    = page;
		this.pattern = pattern;
	}
	
	@Override
	public boolean judgment(Element element) {
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
