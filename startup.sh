#!/usr/bin/env bash
start(){
 now=`date "+%Y%m%d%H%M%S"`
 exec java -Xms512m -Xmx1024m -jar /Users/young/opensources/springvert3x_microServer/target/soloVertx3_microserver.jar 5 >"$now"_bidcheck.log &
 #java -Xms128m -Xmx2048m -jar test2.jar 5 > log.log &  
 #tail -f result.log  
}
#停止方法  
stop(){
 ps -ef|grep java|awk '{print $2}'|while read pid  
 do
    kill -9 $pid
 done
}

case "$1" in
start)
start
;;
stop)
stop
;;
restart)
stop
start
;;
*)
printf 'Usage: %s {start|stop|restart}\n' "$prog"
exit 1
;;
esac