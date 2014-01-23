package jp.co.dk.browzer.event;

import java.util.Date;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.PageEventHandler;
import jp.co.dk.browzer.PageManager;
import jp.co.dk.browzer.Url;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.http.header.ResponseHeader;
import jp.co.dk.document.html.constant.HtmlRequestMethodName;

public class PrintPageEventHandler implements PageEventHandler{
	
	protected long openConnectionTime;
	protected long getDateTime;
	protected long createDocuemntTime;
	
	// ==================================================[PageRedirectHandlerクラス関連イベント]==================================================
	@Override
	public void beforeRedirect(ResponseHeader header, Page page) {
		StringBuilder sb = new StringBuilder();
//		sb.append("[start]");
//		sb.append(":redirect:").append(header.toString());
//		this.print(sb.toString());
	}

	@Override
	public void afterRedirect() {
		StringBuilder sb = new StringBuilder();
//		sb.append("[ fin ]");
//		sb.append(":redirect:");
//		this.print(sb.toString());
	}
	
	// ==================================================[PageManagerクラス関連イベント]==================================================
	@Override
	public void beforeMove(PageManager pageManager, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("  [start]");
		if (pageManager.getPage() != null) { 
			sb.append(":move:BEFORE_URL=[").append(pageManager.getPage().toString()).append("]\r\n");
			sb.append("NEXT_URL=[").append(url).append(']');
			this.print(pageManager.toString());
		} else {
			sb.append(":move:BEFORE_URL=[non]\r\n");
			sb.append("NEXT_URL=[").append(url).append(']');
			this.print(sb.toString());
		}
	}

	@Override
	public void afterMove() {
		StringBuilder sb = new StringBuilder();
//		sb.append("  [start]");
//		sb.append(":move:");
//		this.print(sb.toString());
	}
	
	// ==================================================[Pageクラス関連イベント]==================================================
	@Override
	public void beforeOpenConnection(Url urlObj, HtmlRequestMethodName method) {
		this.openConnectionTime = getDate();
		StringBuilder sb = new StringBuilder();
//		sb.append("    [start]");
//		sb.append(":openconnection:");
//		sb.append("URL=[").append(urlObj.toString()).append("],");
//		sb.append("METHOD=[").append(method.toString()).append("]");
//		this.print(sb.toString());
	}
	
	@Override
	public void errorOpenConnection(BrowzingException browzingException) {
		this.print(browzingException.toString());
	}

	@Override
	public void afterOpenConnection() {
		long now = getDate();
		long time = now - this.openConnectionTime;
		StringBuilder sb = new StringBuilder();
//		sb.append("    [ fin ]");
//		sb.append(":openconnection:");
//		sb.append("TIME=[").append(time).append("]");
//		this.print(sb.toString());
	}

	@Override
	public void beforeGetData(Page page) {
		this.getDateTime = getDate();
		StringBuilder sb = new StringBuilder();
//		sb.append("    [start]");
//		sb.append(":readdate      :");
//		this.print(sb.toString());
	}
	
	@Override
	public void errorGetData(BrowzingException browzingException) {
		this.print(browzingException.toString());
	}
	
	@Override
	public void afterGetData(Page page) {
		long now = getDate();
		long time = now - this.getDateTime;
		StringBuilder sb = new StringBuilder();
//		sb.append("    [ fin ]");
//		sb.append(":readdate      :");
//		sb.append("TIME=[").append(time).append("]");
//		this.print(sb.toString());
	}

	@Override
	public void beforeCreateDocument(Page page) {
		this.createDocuemntTime = getDate();
		StringBuilder sb = new StringBuilder();
//		sb.append("    [start]");
//		sb.append(":createdocument:");
//		this.print(sb.toString());
	}
	
	@Override
	public void errorCreateDocument(BrowzingException browzingException) {
		this.print(browzingException.toString());
	}
	
	@Override
	public void afterCreateDocument(Page page) {
		long now = getDate();
		long time = now - this.createDocuemntTime;
		StringBuilder sb = new StringBuilder();
//		sb.append("    [ fin ]");
//		sb.append(":createdocument:");
//		sb.append("TIME=[").append(time).append("]");
//		this.print(sb.toString());
	}
	
	/**
	 * 現在時刻のミリ秒を取得します。
	 * @return 現在時刻のミリ秒
	 */
	protected long getDate() {
		return new Date().getTime();
	}
	
	/**
	 * 指定の文字列を出力します。
	 * @param str 出力対象文字列
	 */
	protected void print(String str) {
		System.out.println(str);
	}
}
