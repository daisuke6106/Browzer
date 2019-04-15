# -*- coding: utf-8 -*-
import sys
import os
import shutil
import argparse
import re

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
# 関数定義部
#====================================================================================================
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
    