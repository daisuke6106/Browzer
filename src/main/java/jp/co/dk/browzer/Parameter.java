package jp.co.dk.browzer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Queryは、URLのパラメータ部をマップ形式で保持する不変クラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Parameter {

	/** パラメータを表す文字列 */
	protected String queryStr;
	
	/** パラメータをキー、値で分解したマップ */
	protected Map<String, String> quieryMap = new LinkedHashMap<>();
	
	/**
	 * <p>コンストラクタ</p>
	 * URLに設定されているパラメータ部を引数を基に、マップ形式に整形し、保持します。<p>
	 * 指定のURLにパラメータが設定されていなかった場合は空のマップを保持します。
	 * 
	 * @param queryStr クエリの文字列
	 */
	Parameter(String queryStr) {
		if (queryStr == null) {
			this.queryStr = "";
		} else {
			String[] queries = queryStr.split("&");
			for (String param : queries) {
				String[] val = param.split("=");
				if (val.length == 1) {
					quieryMap.put(val[0], "");
				} else if (val.length == 2) {
					quieryMap.put(val[0], val[1]);
				} else {
					continue;
				}
			}
		}
	}
	
	/**
	 * クエリをマップ形式にて返却します。
	 * @return マップ形式に変換したクエリ
	 */
	public Map<String, String> getParameter() {
		return new LinkedHashMap<>(this.quieryMap);
	}
	
	/**
	 * パラメータがあるか判定判定し、その結果を返却します。
	 * @return true=パラメータ無し、false=パラメータ有り
	 */
	public boolean isEmpty() {
		return this.quieryMap.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((queryStr == null) ? 0 : queryStr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (queryStr == null) {
			if (other.queryStr != null)
				return false;
		} else if (!queryStr.equals(other.queryStr))
			return false;
		return true;
	}
	
	
}
