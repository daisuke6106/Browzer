package jp.co.dk.browzer.event;

import java.util.Date;

import jp.co.dk.browzer.Page;
import jp.co.dk.browzer.PageEventHandler;
import jp.co.dk.browzer.Url;
import jp.co.dk.document.html.constant.HtmlRequestMethodName;

public class PrintPageEventHandler implements PageEventHandler{
	
	protected long openConnectionTime;
	protected long getDateTime;
	protected long createDocuemntTime;
	
	@Override
	public void beforeOpenConnection(Url urlObj, HtmlRequestMethodName method) {
		this.openConnectionTime = getDate();
		StringBuilder sb = new StringBuilder();
		sb.append("[start]");
		sb.append(":seq1:openconnection:");
		sb.append("URL=[").append(urlObj.toString()).append("],");
		sb.append("METHOD=[").append(method.toString()).append("]");
		this.print(sb.toString());
	}

	@Override
	public void afterOpenConnection() {
		long now = getDate();
		long time = now - this.openConnectionTime;
		StringBuilder sb = new StringBuilder();
		sb.append("[ fin ]");
		sb.append(":seq1:openconnection:");
		sb.append("TIME=[").append(time).append("]");
		this.print(sb.toString());
	}

	@Override
	public void beforeGetData(Page page) {
		this.getDateTime = getDate();
		StringBuilder sb = new StringBuilder();
		sb.append("[start]");
		sb.append(":seq2:readdate:");
		this.print(sb.toString());
	}

	@Override
	public void afterGetData(Page page) {
		long now = getDate();
		long time = now - this.getDateTime;
		StringBuilder sb = new StringBuilder();
		sb.append("[ fin ]");
		sb.append(":seq2:readdate:");
		sb.append("TIME=[").append(time).append("]");
		this.print(sb.toString());
	}

	@Override
	public void beforeCreateDocument(Page page) {
		this.createDocuemntTime = getDate();
		StringBuilder sb = new StringBuilder();
		sb.append("[start]");
		sb.append(":seq3:createdocument:");
		this.print(sb.toString());
	}

	@Override
	public void afterCreateDocument(Page page) {
		long now = getDate();
		long time = now - this.createDocuemntTime;
		StringBuilder sb = new StringBuilder();
		sb.append("[ fin ]");
		sb.append(":seq3:createdocument:");
		sb.append("TIME=[").append(time).append("]");
		this.print(sb.toString());
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
