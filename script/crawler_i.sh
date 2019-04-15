#!/bin/sh
# set -x
# ====================================================================================================
# プロジェクト情報読み込み
# ====================================================================================================
. env/project_env.sh

# ====================================================================================================
# スクリプト実行
# ====================================================================================================
CURRENT=$(cd $(dirname $0) && pwd)
MAIN_JAR="${CURRENT}/../browzer_0.1.7_all.jar"
MESSAGE_DIR="${CURRENT}/../messages"
PROPERTY_DIR="${CURRENT}/../properties"
LOG_PROPERTY="${CURRENT}/../properties/Logger.properties"
java \
-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n \
-Dpython.console.encoding=UTF-8 \
-Dpython.path="${MAIN_JAR}:${LIB_JAR}:${MESSAGE_DIR}" \
-Dlogger_property_file=${LOG_PROPERTY} \
-Dbrowzer_property_file=${PROPERTY_DIR}/BrowzerProperty.properties \
-jar ${JYTHON_HOME}/jython.jar \
-i ./common/crawler.py 
