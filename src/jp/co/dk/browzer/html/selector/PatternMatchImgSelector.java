package jp.co.dk.browzer.html.selector;

import java.util.regex.Pattern;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;

import jp.co.dk.document.Element;
import jp.co.dk.document.ElementSelector;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

public class PatternMatchImgSelector implements ElementSelector {
	
	private Page page ;
	
	private Pattern pattern;
	
	public PatternMatchImgSelector(Page page, Pattern pattern) throws PageIllegalArgumentException {
		if (page    == null) throw new PageIllegalArgumentException(ERROR_PAGE_IS_NOT_SET);
		if (pattern == null) throw new PageIllegalArgumentException(ERROR_SEARCH_TARGET_FILE_NAME_IS_NOT_SET);
		this.page    = page;
		this.pattern = pattern;
	}
	
	@Override
	public boolean judgment(Element element) {
		if (element instanceof HtmlElement) {
			HtmlElement htmlElement = (HtmlElement) element;
			if (HtmlElementName.IMG  == htmlElement.getElementType()){
				jp.co.dk.browzer.html.element.Image image = (jp.co.dk.browzer.html.element.Image) element;
				String imageSrc = image.getSrc();
				if (this.pattern.matcher(imageSrc).matches()) return true ;
			}
			return false;
		}
		return false;
	}

}
