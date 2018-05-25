#!/bin/bash
#
# @Author:
#     zk
#
# @Usage:
#     ./package.sh
#

cd ../backend/coupletserver;
mvn clean package;
cp target/coupletserver-release-all.zip ../../
