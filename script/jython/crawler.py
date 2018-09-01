# -*- coding: utf-8 -*-
import sys
import os
import shutil
import argparse
import re
from time import sleep

#====================================================================================================
# 初期処理
#====================================================================================================
# sysモジュールをリロードする
reload(sys)
# デフォルトの文字コードを変更する．
sys.setdefaultencoding('utf-8')

#====================================================================================================
# Pythonライブラリ郡の読み込み
#====================================================================================================
import hashlib

#====================================================================================================
# Javaライブラリ郡の読み込み
#====================================================================================================
import jp.co.dk.browzer.Browzer as Browzer
import jp.co.dk.browzer.PageManager as PageManager
import jp.co.dk.browzer.Page as Page
import java.util.regex.Pattern as Pattern
import java.io.File as File
import jp.co.dk.document.html.HtmlDocument

#====================================================================================================
# クラス郡
#====================================================================================================
class PyBrowzer(Browzer):
    
    def __init__(self, url):
        Browzer.__init__(self, url)
        
    def print_pageinfo(self, page):
        pass
        
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

    def responseh_contents_type(self):
        return self.getPage().getResponseHeader().getContentsType()
    
    def responseh_charset(self):
        return self.getPage().getResponseHeader().getCharSet()
        
    def responseh_header(self):
        return self.getPage().getResponseHeader().toString()

    def get_element(self, selector):
        doc = self.getPage().getDocument()
        if type(doc) is jp.co.dk.document.html.HtmlDocument :
           return doc.getNode( selector )
        else:
           return ()

    def write(self, output_dir, output_file): 
        try :
            os.makedirs(output_dir, mode=0777)
        except OSError:
            # print("dir is exsts already dir=[" + output_dir + "]")
            pass
        self.getPage().save(File( output_dir ), output_file )
        
    def createPageManager(self, url, handler):
        return PyPageManager(url, handler)
    

class PyPageManager(PageManager):
    
    def __init__(self, url, pageRedirectHandler):
        PageManager.__init__(self, url, pageRedirectHandler)
    
    def createPage(self, url):
        return PyPage(url, {}, False)

class PyPage(Page):
    
    def __init__(self, url, requestHeader, readDataFlg):
        Page.__init__(self, url, requestHeader, readDataFlg)
    
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
        
def append_str(output_dir, output_file, content):
    try :
        os.makedirs(output_dir, mode=0777)
    except OSError:
        # print("dir is exsts already dir=[" + output_dir + "]")
        pass
    
    with open( output_dir + '/' + output_file, mode='a' ) as file:
       file.write( content )
       
def write_list(output_dir, output_file, content):
    try :
        os.makedirs(output_dir, mode=0777)
    except OSError:
        # print("dir is exsts already dir=[" + output_dir + "]")
        pass
    
    with open( output_dir + '/' + output_file, mode='w' ) as file:
       file.write( '\n'.join(content) )
        
def append_list(output_dir, output_file, content):
    try :
        os.makedirs(output_dir, mode=0777)
    except OSError:
        # print("dir is exsts already dir=[" + output_dir + "]")
        pass
    
    with open( output_dir + '/' + output_file, mode='a' ) as file:
       file.write( '\n'.join(content) )
        
#====================================================================================================
# メイン処理部
#====================================================================================================
if __name__ == "__main__":
    pass
    