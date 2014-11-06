#!/bin/bash

LIQUIBASE_FILE=db.xml

DB_URL=jdbc:h2:./moodikud
DB_USER=sa
DB_PASS=sa

java -cp 'lib/*' liquibase.integration.commandline.Main \
  --url=$DB_URL --username=$DB_USER --password=$DB_PASS --changeLogFile=$LIQUIBASE_FILE "$@"
