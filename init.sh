# tarkvarakool-moodikud auto-start
#
### BEGIN INIT INFO
# Provides:          tarkvarakool-moodikud
# Required-Start:    $network
# Required-Stop:     
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Description:       Infoühiskonna mõõdikud (Tarkvarakool)
### END INIT INFO

APP_NAME=tarkvarakool-moodikud

cd `dirname $(readlink -f $0)`

PID=`cat server.pid`
PS=`ps -p $PID | grep $PID | awk '{ print $1 }' | head -1`

EXEC="java -cp classes:lib/* framework.Launcher 11001 >> $APP_NAME.out 2>> $APP_NAME.err &"

start () {
  if [ "$PS" = "" ]
  then
    echo starting $APP_NAME
    su tarkvarakool -c "$EXEC"
  else 
    echo $APP_NAME already running
  fi
}

stop () {
  if [ "$PS" != "" ]
  then
    echo stopping $APP_NAME
    kill $PID
    while [ "$PS" != "" ] 
    do
      sleep 1
      PS=`ps -p $PID | grep $PID | awk '{ print $1 }' | head -1`
    done
    echo $APP_NAME stopped
  else 
    echo $APP_NAME not running
  fi
}

case $1 in
start)
  start
  ;;
stop)  
  stop
  ;;
status)
  if [ "$PS" = "" ]
  then
    echo $APP_NAME not running
  else 
    echo $APP_NAME running
  fi
  ;;
restart)
  stop
  start
  ;;
esac   
exit 0

