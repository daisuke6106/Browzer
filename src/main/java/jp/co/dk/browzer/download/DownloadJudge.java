package jp.co.dk.browzer.download;

import jp.co.dk.browzer.Page;

/**
 * DownloadJudgeは、ダウンロードを行うか否かの判定を行うクラスが実装するインターフェースです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
@FunctionalInterface
public interface DownloadJudge {
	
	/**
	 * ダウンロード対象のページを元にダウンロードを行うか否かの判定を行います。
	 * 
	 * @param page ページ
	 * @return 判定結果（true=ダウンロードを行う、false=ダウンロードを行わない）
	 */
	public boolean judge(Page page);
}
