package jp.co.dk.browzer;

import java.io.IOException;
import java.io.InputStream;

import jp.co.dk.browzer.contents.BrowzingExtension;
import jp.co.dk.browzer.exception.PageAccessException;
import jp.co.dk.browzer.html.HtmlElementFactory;
import jp.co.dk.browzer.http.header.CharSet;
import jp.co.dk.browzer.http.header.ContentsType;
import jp.co.dk.document.File;
import jp.co.dk.document.exception.DocumentException;
import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

/**
 * DocumentFactoryは、Documentを生成するためのFactoryクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class DocumentFactory {
	
	protected Page page;

	/** ロガーインスタンス */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * コンストラクタ<p/>
	 * Documentを生成するためのFactoryクラスを生成する。
	 * 
	 * @param page ページオブジェクト
	 */
	public DocumentFactory(Page page) {
		this.page = page;
	}
	
	/**
	 * URLのファイル拡張子を元にDocumentオブジェクトを生成します。<p/>
	 * 
	 * @param extension URLファイル拡張子
	 * @param inputStream 入力ストリーム
	 * @return Documentオブジェクト
	 * @throws PageAccessException Documentの生成に失敗した場合
	 */
	public File create(BrowzingExtension extension, InputStream inputStream) throws DocumentException {
		if (extension == null) return new jp.co.dk.document.File(inputStream);
		switch (extension) {
			case HTML:
				CharSet charset = this.page.getResponseHeader().getCharSet();
				if ( charset == null ) {
					this.logger.debug("charset is not set.");
					return new jp.co.dk.document.html.HtmlDocument(inputStream, new HtmlElementFactory(this.page)) {
						@Override
						protected String getEncodeByData() throws IOException {
							String encode = super.getEncodeByData();
							if ( encode == null || encode.equals( "" ) ) {
								this.logger.warn("Encoding could not be determined from the data.　We will temporarily return UTF-8.");
								return "UTF-8";
							} else {
								return encode;
							}
						}
					};
				} else {
					this.logger.debug("charset=[" + charset.getEncoding() + "]");
					return new jp.co.dk.document.html.HtmlDocument(inputStream, charset.getEncoding(), new HtmlElementFactory(this.page));
				}
			case XML:
				return new jp.co.dk.document.xml.XmlDocument(inputStream);
			case JSON:
				return new jp.co.dk.document.json.JsonDocument(inputStream);
			default:
				return new jp.co.dk.document.File(inputStream);
		}
	}
	
	/**
	 * HTTPヘッダに設定されているContentsTypeを元にDocumentオブジェクトを生成します。<p/>
	 * 
	 * @param contentsType コンテンツタイプ
	 * @param inputStream 入力ストリーム
	 * @return Documentオブジェクト
	 * @throws DocumentException Documentの生成に失敗した場合
	 */
	public File create(ContentsType contentsType, InputStream inputStream) throws DocumentException {
		if (contentsType == null) throw new DocumentException(ERROR_EXTENSION_IS_NOT_SET);
		switch (contentsType) {
			case TEXT_HTML:
				CharSet charset = this.page.getResponseHeader().getCharSet();
				if ( charset == null ) {
					this.logger.debug("charset is not set.");
					return new jp.co.dk.document.html.HtmlDocument(inputStream, new HtmlElementFactory(this.page)) {
						@Override
						protected String getEncodeByData() throws IOException {
							String encode = super.getEncodeByData();
							if ( encode == null || encode.equals( "" ) ) {
								this.logger.warn("Encoding could not be determined from the data.　We will temporarily return UTF-8.");
								return "UTF-8";
							} else {
								return encode;
							}
						}
					};
				} else {
					this.logger.debug("charset=[" + charset.getEncoding() + "]");
					return new jp.co.dk.document.html.HtmlDocument(inputStream, charset.getEncoding(), new HtmlElementFactory(this.page));
				}
			case TEXT_XML:
				return new jp.co.dk.document.xml.XmlDocument(inputStream);
			case APPLICATION_JSON:
				return new jp.co.dk.document.json.JsonDocument(inputStream);
			default:
				return new jp.co.dk.document.File(inputStream);
		}
	}
	
}
