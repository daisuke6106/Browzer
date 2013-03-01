package jp.co.dk.browzer.contents;

/**
 * BrowzingExtensionは、ブラウザ操作にて参照できる拡張子を定義する定数クラス。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public enum BrowzingExtension {
	
	/** HTML */
	HTML(new String[]{"html", "php"}),
	
	/** XML */
	XML(new String[]{"xml"})
	;
	
	private String[] extension;
	
	private BrowzingExtension(String[] extension) {
		this.extension      = extension;
	}
	
	/**
	 * 対応する拡張子一覧を取得する。
	 * 
	 * @return 拡張子文字列
	 */
	public String[] getExtension() {
		return this.extension;
	}
	
	/**
	 * 指定の拡張子がこの拡張子一覧に保持しているか判定する。
	 * 
	 * @param extension 拡張子
	 * @return 判定結果
	 */
	public boolean hasExtension(String targetExtension) {
		for (String extension : this.extension) {
			if (extension.equals(targetExtension)) {
				return true;
			}
		}
		return false;
	}	
}
