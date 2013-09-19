package jp.co.dk.browzer;

import java.io.InputStream;

import jp.co.dk.browzer.contents.BrowzingExtension;
import jp.co.dk.browzer.exception.BrowzingException;
import jp.co.dk.browzer.html.HtmlElementFactory;
import jp.co.dk.browzer.http.header.ContentsType;
import jp.co.dk.document.File;
import jp.co.dk.document.exception.DocumentException;
import jp.co.dk.document.html.exception.HtmlDocumentException;
import jp.co.dk.document.xml.exception.XmlDocumentException;

import static jp.co.dk.browzer.message.BrowzingMessage.*;

/**
 * DocumentFactoryは、Documentを生成するためのFactoryクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
class DocumentFactory {
	
	private Page page;
	
	/**
	 * コンストラクタ<p/>
	 * Documentを生成するためのFactoryクラスを生成する。
	 * 
	 * @param page ページオブジェクト
	 */
	DocumentFactory(Page page) {
		this.page = page;
	}
	
	/**
	 * URLのファイル拡張子を元にDocumentオブジェクトを生成します。<p/>
	 * 
	 * @param extension URLファイル拡張子
	 * @param inputStream 入力ストリーム
	 * @return Documentオブジェクト
	 * @throws BrowzingExtension Documentの生成に失敗した場合
	 */
	public File create(BrowzingExtension extension, InputStream inputStream) throws BrowzingException {
		try {
			if (extension == null) return new jp.co.dk.document.File(inputStream);
			switch (extension) {
				case HTML:
					return new jp.co.dk.document.html.HtmlDocument(inputStream, new HtmlElementFactory(this.page));
				case XML:
					return new jp.co.dk.document.xml.XmlDocument(inputStream);
				default:
					return new jp.co.dk.document.File(inputStream);
			}
		} catch (DocumentException e) {
			throw new BrowzingException(ERROR_FAILED_TO_CREATE_DOCUMENT_INSTANCE, e);
		}
	}
	
	/**
	 * HTTPヘッダに設定されているContentsTypeを元にDocumentオブジェクトを生成します。<p/>
	 * 
	 * @param contentsType コンテンツタイプ
	 * @param inputStream 入力ストリーム
	 * @return Documentオブジェクト
	 * @throws BrowzingExtension Documentの生成に失敗した場合
	 */
	public File create(ContentsType contentsType, InputStream inputStream) throws BrowzingException {
		try {
			if (contentsType == null) throw new BrowzingException(ERROR_NON_SUPPORTED_EXTENSION);
			switch (contentsType) {
				case TEXT_HTML:
					return new jp.co.dk.document.html.HtmlDocument(inputStream, new HtmlElementFactory(this.page));
				case TEXT_XML:
					return new jp.co.dk.document.xml.XmlDocument(inputStream);
				default:
					return new jp.co.dk.document.File(inputStream);
			}
		} catch (DocumentException e) {
			throw new BrowzingException(ERROR_FAILED_TO_CREATE_DOCUMENT_INSTANCE, e);
		}
	}
	
}
