package jp.co.dk.browzer.html.selector;

import jp.co.dk.document.Element;
import jp.co.dk.document.ElementSelector;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;
import jp.co.dk.document.html.constant.HttpEquivName;

public class RedirectMetaSelector implements ElementSelector {
	
	@Override
	public boolean judgment(Element element) {
		if (element instanceof HtmlElement) {
			HtmlElement htmlElement = (HtmlElement) element;
			if (HtmlElementName.META  == htmlElement.getElementType()){
				jp.co.dk.document.html.element.Meta meta = (jp.co.dk.document.html.element.Meta) element;
				HttpEquivName httpEquivName = meta.getHttpEquiv();
				if (HttpEquivName.REFRESH == httpEquivName) return true; 
			}
			return false;
		}
		return false;
	}

}
