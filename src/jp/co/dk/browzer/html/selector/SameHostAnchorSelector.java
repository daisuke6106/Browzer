package jp.co.dk.browzer.html.selector;

import jp.co.dk.browzer.Page;
import jp.co.dk.document.Element;
import jp.co.dk.document.ElementSelector;
import jp.co.dk.document.html.HtmlElement;
import jp.co.dk.document.html.constant.HtmlElementName;

public class SameHostAnchorSelector implements ElementSelector {
	
	private Page page ;
	
	public SameHostAnchorSelector(Page page) {
		this.page = page;
	}
	
	@Override
	public boolean judgment(Element element) {
		String host = this.page.getHost();
		if (element instanceof HtmlElement) {
			HtmlElement htmlElement = (HtmlElement) element;
			if (HtmlElementName.A  == htmlElement.getElementType()){
				jp.co.dk.browzer.html.element.A anchor = (jp.co.dk.browzer.html.element.A) element;
				String anchorHost = anchor.getHost();
				if (host.equals(anchorHost)) return true ;
			}
			return false;
		}
		return false;
	}

}
