# -*- coding: utf-8 -*-
import sys
import os
import shutil
import argparse

#====================================================================================================
# 初期処理
#====================================================================================================
# sysモジュールをリロードする
reload(sys)
# デフォルトの文字コードを変更する．
sys.setdefaultencoding('utf-8')

#====================================================================================================
# Javaライブラリ郡の読み込み
#====================================================================================================
import jp.co.dk.browzer.Browzer as Browzer
import jp.co.dk.browzer.Page as Page

import java.util.regex.Pattern as Pattern

import java.io.File as File

#====================================================================================================
# クラス郡
#====================================================================================================
class PyBrowzer(Browzer):
    
    def __init__(self, url):
        Browzer.__init__(self, url)
        self.print_pageinfo( self.getPage() )
        
    def print_pageinfo(self, page):
        httpstatuscode = page.getResponseHeader().getResponseRecord().getHttpStatusCode()
        print("http status = " + httpstatuscode.getCode() + ":" + httpstatuscode.getMessage().getMessage())
        
    def get_anchor(self, pattern_str):
        anchor_rlt = []
        pattern_obj = Pattern.compile(pattern_str)
        anchor_list = self.getAnchor()
        for anchor in anchor_list:
            if pattern_obj.matcher( anchor.getUrl() ).find() :
                anchor_rlt.append(anchor)
        return anchor_rlt
        
    def save(self, file_path):
        file = File( file_path )
        Browzer.save(self, file)
        
#====================================================================================================
# 関数定義部
#====================================================================================================
def open_browzer(url):
    browzer = PyBrowzer( url )
    return browzer;

def open_page(url):
    page = Page( url )
    return page;

#====================================================================================================
# メイン処理部
#====================================================================================================
if __name__ == "__main__":
    print("initialize OK")
    