#!/bin/sh
# set -x

# ====================================================================================================
# 引数チェック
# ====================================================================================================
TARGET_PYTHON_SCRIPT_FILE=${1}

# 引数の設定確認
if [ "${TARGET_PYTHON_SCRIPT_FILE}" = "" ]; then
	echo "argument not set."
	exit 1
fi

# 実行対象スクリプトファイルの存在確認
if [ ! -f "${TARGET_PYTHON_SCRIPT_FILE}" ]; then
	echo "target script not founds. [${TARGET_PYTHON_SCRIPT_FILE}]"
	exit 1
fi

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
${TARGET_PYTHON_SCRIPT_FILE} 