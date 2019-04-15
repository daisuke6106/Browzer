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
import jp.co.dk.browzer.LinkedPage as LinkedPage
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
        if isinstance( doc, jp.co.dk.document.html.HtmlDocument ):
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
        if isinstance( doc, jp.co.dk.document.html.HtmlDocument ):
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
        if isinstance( doc, jp.co.dk.document.html.HtmlDocument ):
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
   
class PyLinkedPage(LinkedPage):
    
    def __init__(self, url):
        LinkedPage.__init__(self, url)
    
    def createPage(self, before_page, url):
        return PyChildLinkedPage(before_page, url)
    
    def getNode(self, selector):
        doc = self.getDocument()
        if isinstance( doc, jp.co.dk.document.html.HtmlDocument ):
           element_list      = doc.getNode( selector )
           element_list_size = len( element_list )
           if element_list_size == 0:
               print( "element not found." )
               return ()
           else : 
               print( "hit [" + str( element_list_size ) +"] element." )
               print( "=====element-info=====" )
               for count, element in enumerate( element_list ):
                   print( "[" + str( count ) + "]:" + "tag=[" + element.getElementType().getName() + "] " + element.getContent() )
               print( "======================" )
               if element_list_size == 1:
                   return element_list[0]
               else :
                   return element_list
        else:
           return ()

class PyChildLinkedPage(PyLinkedPage):

	def __init__(self, before_page, url):
		LinkedPage.__init__(self, before_page, url)

#====================================================================================================
# 関数定義部
#====================================================================================================
def open_browzer(url):
    browzer = PyBrowzer( url )
    return browzer;

def open_page(url):
    page = PyLinkedPage( url )
    return page;
  
#====================================================================================================
# メイン処理部
#====================================================================================================
if __name__ == "__main__":
    pass
    