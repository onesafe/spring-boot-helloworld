#!/usr/bin/env bash

export LANG=en_US.UTF-8

cur=$(cd `dirname $0`/..; pwd)
cd $cur


echo "starting hello world:"

java -jar /app/hello-world-0.0.1-SNAPSHOT.jar
