package jp.co.dk.browzer;

import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_ANCHOR_HAS_NOT_URL;
import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE;
import static jp.co.dk.browzer.message.BrowzingMessage.ERROR_SPECIFIED_ANCHOR_IS_NOT_SET;

import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.exception.PageIllegalArgumentException;
import jp.co.dk.browzer.exception.PageMovableLimitException;
import jp.co.dk.browzer.exception.PageRedirectException;
import jp.co.dk.browzer.html.element.MovableElement;

public class LinkedPage extends Page {
	
	protected Page beforePage;
	
	public LinkedPage(String url) throws PageIllegalArgumentException, PageAccessException {
		super(url);
	}
	
	public LinkedPage(Page beforePage, String url) throws PageIllegalArgumentException, PageAccessException {
		super(url);
		this.beforePage = beforePage;
	}
	
	public LinkedPage move(MovableElement movable) throws PageIllegalArgumentException, PageAccessException, PageRedirectException, PageMovableLimitException {
		try {
			if (movable == null) throw new PageIllegalArgumentException(ERROR_SPECIFIED_ANCHOR_IS_NOT_SET);
			if (!this.equals(movable.getPage()))throw new PageIllegalArgumentException(ERROR_ANCHOR_THAT_HAS_BEEN_SPECIFIED_DOES_NOT_EXISTS_ON_THE_PAGE_THAT_IS_CURRENTLY_ACTIVE);
			String url = movable.getUrl();
			
			if (url.equals("")) throw new PageIllegalArgumentException(ERROR_ANCHOR_HAS_NOT_URL);
			return this.createPage(this, url);
		} catch (BrowzingException e ) {
			throw e;
		}
	}
	
	protected LinkedPage createPage(Page beforePage, String url) throws PageIllegalArgumentException, PageAccessException {
		return new LinkedPage(beforePage, url);
	}
	
	public Page getBeforePage() {
		return this.beforePage;
	}
}
