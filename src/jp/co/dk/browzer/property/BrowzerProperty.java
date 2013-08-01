package jp.co.dk.browzer.property;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.dk.property.AbstractProperty;
import jp.co.dk.property.Property;
import jp.co.dk.property.exception.PropertyException;

public class BrowzerProperty extends AbstractProperty {
	
	public static BrowzerProperty REQUEST_HEADER = new BrowzerProperty("header.request.");
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキーをもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 */
	protected BrowzerProperty (String key) throws PropertyException {
		super(key);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, boolean defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, byte defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, double defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, float defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, int defaltValue) throws PropertyException {
		super(key, defaltValue);
	}

	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, long defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, short defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, BigDecimal defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (String key, String defaltValue) throws PropertyException {
		super(key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキーをもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 */
	protected BrowzerProperty (File file, String key) throws PropertyException {
		super(file, key);
	}
	

	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, boolean defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, byte defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, double defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, float defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, int defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, long defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, short defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, BigDecimal defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティファイルパス、プロパティキー、デフォルト値をもとにプロパティ定数クラスを生成します。
	 * 
	 * @param file プロパティファイルパス
	 * @param key  プロパティキー
	 * @param defaltValue デフォルト値
	 */
	protected BrowzerProperty (File file, String key, String defaltValue) throws PropertyException {
		super(file, key, defaltValue);
	}
	
}
