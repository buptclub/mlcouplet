#!/bin/bash
# 
# @Author:
#     zk
# @Usage:
#     ./start.sh {start | stop}
#

CONF_FOLDER=conf
CRAWL_CONFIG=$CONF_FOLDER/crawl_config.properties
LOG4J_CONFIG=$CONF_FOLDER/log4j.properties

# Base dir
BASEDIR=`pwd`

run_jar() {
    MAIN_CLASS="$1"
	APPNAME="$2"
    jarName="coupletserver.jar"

    if [[ ! -e $jarName ]]; then
        echo "$jarName not exist !"
        exit 1
    fi

	echo "Starting $APPNAME..."

    java -Dfile.encoding=UTF-8 \
		 -cp "$BASEDIR/lib/*:$BASEDIR/$jarName" \
		 $MAIN_CLASS &

	pid="$!"
	echo "Started $APPNAME ($pid)."
}

stop_jar() {
	MAIN_CLASS=$1
	APPNAME=$2

	echo "Stopping $APPNAME ..."

	pid=`ps ax | grep -i "$MAIN_CLASS" | grep -i "$BASEDIR" | grep java | grep -v grep | awk '{print $1}'`
    if [ -z "$pid" ] ; then
		echo "No $APPNAME running."
		exit -1;
    fi

	echo "The $APPNAME(${pid}) is running..."
    kill ${pid}

	if [ $? -ne 0 ]; then
		echo "Unable to stop $APPNAME"
		exit 1
	fi

    echo "Stopped $APPNAME(${pid}) OK"
}

startit() {
	run_jar "com.baidubupt.coupletserver.CoupletServer" "coupletserver"
}

stopit() {
	stop_jar "com.baidubupt.coupletserver.CoupletServer" "coupletserver"
}

case "$1" in
	'start')
		startit
		;;
	'stop')
		stopit
		;;
	*)
		echo "Usage: $0 { start | stop }"
		exit 1
		;;
esac

exit 0

