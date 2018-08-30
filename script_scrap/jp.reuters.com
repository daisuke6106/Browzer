# ====================================================================================================
# ロイター（ビジネスニュース）
# ====================================================================================================
downloadpath="/media/daisuke6106/6fdc625a-0f9b-4cda-a50c-af5591ba0a5f/crawle_data/jp.reuters.com/business"

# トップページを開く
browzer   = open_browzer("https://jp.reuters.com/news/archive/businessNews?view=page&page=2&pageSize=10")

def download():
  news_list = browzer.get_element(".story-content a")
  for news in news_list:
    try:
      browzer.move(news)
    except:
      continue
    sleep(5)
    try:
      url     = browzer.getPage().getURL()
      title   = browzer.get_element(".ArticleHeader_headline")[0].getContent()
      content = browzer.get_element(".StandardArticleBody_body")[0].getContent()
      time    = browzer.get_element(".ArticleHeader_date")[0].getContent()
      url_sha256 = sha256(url)
      write_str(downloadpath     + "/" + url_sha256, "url"     , url)
      write_str(downloadpath     + "/" + url_sha256, "title"   , title)
      write_str(downloadpath     + "/" + url_sha256, "content" , content)
      write_str(downloadpath     + "/" + url_sha256, "time"    , time)
      browzer.write(downloadpath + "/" + url_sha256, "origin")
    except :
      print("保存に失敗 URL=" + news.getUrl())
      
    # 前ページに戻る
    browzer.back()
    # メモリ上のキャッシュを削除
    browzer.removeChild()

# トップページの子要素を保存
download()

# 到達済みURLを保存
visited_url_list = []
visited_url_list.append(browzer.getPage().getURL())
# スクレイピング開始
while True:
  next_page = None
  page_anchor_list = browzer.get_element(".control-nav-next a")
  for page_anchor in page_anchor_list:
    if page_anchor.getUrl() not in visited_url_list :
      next_page = page_anchor
  if next_page is None:
    break
  browzer.move(next_page)
  download()
  visited_url_list.append(browzer.getPage().getURL())
