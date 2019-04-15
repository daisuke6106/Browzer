# -*- coding: utf-8 -*-

from common import crawler
from common import io_utils
from time import sleep

###################################################################################################
# リクナビクローリング用スクリプト
###################################################################################################

#====================================================================================================
# 指定の職種、URLでクローリングを開始する。
#====================================================================================================
def crawle_job(url, job_type, output_dir, output_file):
    
    # 巡回済みURLリストを保存するためのリスト
    vigited_url_list = []
    
    # 会社一覧が記載されているページの１ページ目を開く
    company_list_page = crawler.open_page(url)
    sleep(3)
    
    # 現在のページは訪れたので訪問済みリストに追加する。
    vigited_url_list.append(company_list_page.getURL())

    # 会社一覧から各社のページを訪れて対象の情報を取得、保存する。
    crawle_company_list_page(company_list_page, job_type, output_dir, output_file)
    
    # 次のページへ移動し巡回を継続する。
    crawle_page(vigited_url_list, company_list_page, job_type, output_dir, output_file)


#====================================================================================================
# 会社一覧から各社のページを訪れて対象の情報を取得、保存する。
#====================================================================================================
def crawle_page(vigited_url_list, company_list_page, job_type, output_dir, output_file):
    
    # 会社一覧の下部にあるページ一覧から各ページの要素を取得する。
    pagination_anchor_list = company_list_page.getNode(".rnn-pagination a")
    
    # 各会社一覧のNページ目を訪れる。
    for pagination_anchor in pagination_anchor_list:
        # すでに訪れているページだったら何もしない
        if pagination_anchor.getUrl() in vigited_url_list :
            continue
        
        # 会社一覧からNページを訪れる
        next_company_list_page = company_list_page.move(pagination_anchor)
        # Nページ目は訪れたので訪問済みリストに追加する。
        vigited_url_list.append(next_company_list_page.getURL())
        
        # 対象の情報を取得、保存する。
        crawle_company_list_page(next_company_list_page, job_type, output_dir, output_file)
        
        # Nページ目からまたN+1ページ目へ巡回する
        crawle_page(vigited_url_list, next_company_list_page, job_type, output_dir, output_file)
    

#====================================================================================================
# 会社一覧から各社のページを訪れて対象の情報を取得、保存する。
#====================================================================================================
def crawle_company_list_page(company_list_page, job_type, output_dir, output_file):
    company_anchor_list = company_list_page.getNode(".rnn-textLl a")
    for company_anchor in company_anchor_list:
      company_page = company_list_page.move(company_anchor)
      sleep(3)
      output_company_page_data(company_page, job_type, output_dir, output_file)

#====================================================================================================
# 対象の会社のページから必要な情報を抜き出し保存する。
#====================================================================================================
def output_company_page_data(company_page, job_type, output_dir, output_file):
    try :
        # 今いるページが求人情報のタブでない場合、求人情報タブへ移動する。
        now_page = company_page.getNode(".is-current")[0].getContent()
        if now_page != u"求人情報" :
            company_page = company_page.move(company_page.getNode(u"a !求人情報")[0])
            sleep(3)
        # 会社名
        company_name = ""
        try:
            company_name = company_page.getNode(".js-cmpnyInfo .rnn-offerCompanyName").getContent()
        except:
            pass
        # 資本金
        shihonkin = ""
        try:
            shihonkin = company_page.getNode(u"!資本金").getParentElement().getNode("td")[0].getContent()
        except:
            pass
        # 連絡先
        renrakusaki = ""
        try:
            renrakusaki = company_page.getNode(u"!連絡先").getParentElement().getNode("td")[0].getContent()
        except:
            pass
        # ホームページ
        company_homepage_url = ""
        try:
            company_homepage_url = company_page.getNode(u"!ホームページ").getUrl()
        except:
            pass
        # 求人ページのURL
        recruit_url = ""
        try:
            recruit_url = company_page.getURL()
        except:
            pass
        csv_record = "\"" + job_type + "\"" + "," + "\"" + company_name + "\"" + "," + "\"" + shihonkin + "\"" + "," + "\"" + renrakusaki + "\"" + "," + "\"" + company_homepage_url + "\"" + "," + "\"" + recruit_url + "\"" + "\r\n"
        
        # CSV形式でファイルに出力する。
        io_utils.append_str(output_dir, output_file, csv_record)
        
    # ページ情報取得操作で想定外のエラーが発生したら何もしない。（エラーになったURLだけでも出しておきます？おまかせ！）
    except:
        pass

#====================================================================================================
# メイン処理部
#====================================================================================================
if __name__ == "__main__":
    # クリエイティブ（メディア・アパレル・デザイン）-> 広告・グラフィック関連の一覧をクローリング
    crawle_job(\
        "https://next.rikunabi.com/creative/lst_jb0303010000/", \
        u"クリエイティブ（メディア・アパレル・デザイン）-> 広告・グラフィック",\
        u"/home/develop/デスクトップ/crawler/data",\
        "rikunabi.csv",\
    )
   
    # クリエイティブ（メディア・アパレル・デザイン）-> 出版・印刷関連関連の一覧をクローリング
    crawle_job(\
        "https://next.rikunabi.com/creative/lst_jb0303020000/", \
        u"クリエイティブ（メディア・アパレル・デザイン）-> 出版・印刷関連",\
        u"/home/develop/デスクトップ/crawler/data",\
        "rikunabi.csv",\
    )
   
    # クリエイティブ（メディア・アパレル・デザイン）-> 映像・音響・イベント・芸能・テレビ・放送関連をクローリング
    crawle_job(\
        "https://next.rikunabi.com/creative/lst_jb0303030000/",\
        u"クリエイティブ（メディア・アパレル・デザイン）-> 映像・音響・イベント・芸能・テレビ・放送関連", \
        u"/home/develop/デスクトップ/crawler/data",\
        "rikunabi.csv",\
    )
    
    # クリエイティブ（メディア・アパレル・デザイン）-> ファッション・インテリア・空間・プロダクトデザインをクローリング
    crawle_job(\
        "https://next.rikunabi.com/creative/lst_jb0303060000/",\
        u"クリエイティブ（メディア・アパレル・デザイン）-> ファッション・インテリア・空間・プロダクトデザイン", \
        u"/home/develop/デスクトップ/crawler/data",\
        "rikunabi.csv",\
    )