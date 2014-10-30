# moodikud auto-start
#
### BEGIN INIT INFO
# Provides:          moodikud
# Required-Start:    $network
# Required-Stop:     
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Description:       Infoühiskonna mõõdikud (Tarkvarakool)
### END INIT INFO

cd `dirname $(readlink -f $0)`

PID=`cat server.pid`
PS=`ps -p $PID | grep $PID | awk '{ print $1 }' | head -1`

EXEC="java -cp classes:lib/* Launcher 11001 >> moodikud.out 2>> moodikud.err &"

start () {
  if [ "$PS" = "" ]
  then
    echo starting moodikud
    su xp -c "$EXEC"
  else 
    echo already running
  fi
}

stop () {
  if [ "$PS" != "" ]
  then
    echo stopping moodikud
    kill $PID
    while [ "$PS" != "" ] 
    do
      sleep 1
      PS=`ps -p $PID | grep $PID | awk '{ print $1 }' | head -1`
    done
    echo moodikud stopped
  else 
    echo moodikud not running
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
    echo moodikud not running
  else 
    echo moodikud running
  fi
  ;;
restart)
  stop
  start
  ;;
esac   
exit 0

