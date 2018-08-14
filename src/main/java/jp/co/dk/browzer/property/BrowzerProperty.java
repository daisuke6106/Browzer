package jp.co.dk.browzer.property;

import java.io.File;

import jp.co.dk.property.AbstractProperty;
import jp.co.dk.property.exception.PropertyException;

/**
 * ブラウザに関するプロパティを定義するクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class BrowzerProperty extends AbstractProperty {
	
	public static final BrowzerProperty REQUEST_HEADER = new BrowzerProperty("header.request.");
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキーをもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 */
	protected BrowzerProperty (String key) throws PropertyException {
		super(new File(System.getProperty("browzer_property_file")), key);
	}
	
}
