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
import jp.co.dk.document.html.HtmlDocument


import hashlib

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
        
    def get_url(self):
        return self.getURL()
        
    def get_anchor(self, pattern_str):
        doc = self.getPage().getDocument()
        if type(doc) is jp.co.dk.document.html.HtmlDocument :
            anchor_rlt = []
            pattern_obj = Pattern.compile(pattern_str)
            anchor_list = self.getAnchor()
            for anchor in anchor_list:
                if pattern_obj.matcher( anchor.getUrl() ).find() :
                    anchor_rlt.append(anchor)
            return anchor_rlt
        else:
            return()
        
    def get_title(self):
        doc = self.getPage().getDocument()
        if type(doc) is jp.co.dk.document.html.HtmlDocument :
           return doc.getTitle()
        else:
           return ""

    def get_element(self, selector):
        doc = self.getPage().getDocument()
        if type(doc) is jp.co.dk.document.html.HtmlDocument :
           return doc.getNode( selector )
        else:
           return ()

    def save(self, file_path): 
        Browzer.save(self, File( file_path ) )
        
#====================================================================================================
# 関数定義部
#====================================================================================================
def open_browzer(url):
    browzer = PyBrowzer( url )
    return browzer;

def sha256(str):
    return hashlib.sha256(str).hexdigest()

def write_str(output_dir, output_file, content):
    try :
        os.makedirs(output_dir, mode=0777)
    except OSError:
        # print("dir is exsts already dir=[" + output_dir + "]")
        pass
    
    with open( output_dir + '/' + output_file, mode='w' ) as file:
       file.write( content )
        
def write_list(output_dir, output_file, content):
    try :
        os.makedirs(output_dir, mode=0777)
    except OSError:
        # print("dir is exsts already dir=[" + output_dir + "]")
        pass
    
    with open( output_dir + '/' + output_file, mode='w' ) as file:
       file.write( '\n'.join(content) )
        
#====================================================================================================
# メイン処理部
#====================================================================================================
if __name__ == "__main__":
    pass
    