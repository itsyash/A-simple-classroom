#!/bin/bash

echo "Please enter your username";
read USERNAME;

echo "Please enter your password";
read -s PASSWORD

RESULT=$(curl 'https://login.iiit.ac.in/cas/login?service=http%3A%2F%2Fcourses.iiit.ac.in%2FEdgeNet%2Fhome.php' -H 'Host: login.iiit.ac.in' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate' -H 'Connection: keep-alive' -kv 2>&1 | grep -e JSESSION -e "\"lt\"")
JSESSION=$(echo $RESULT | grep -oEi '[0-9A-Z]{32}')
echo $JSESSION
LT_VALUE=$(echo $RESULT | grep -oEi '[0-9A-Z_-]{76}')
echo $LT_VALUE

TICKET=$(curl "https://login.iiit.ac.in/cas/login;jsessionid=$JSESSIONID?service=http%3A%2F%2Fcourses.iiit.ac.in%2FEdgeNet%2Fhome.php" -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: en-US,en;q=0.5' -H 'Connection: keep-alive' -H "Cookie: JSESSIONID=$JSESSION" -H 'Host: login.iiit.ac.in' -H 'Referer: https://login.iiit.ac.in/cas/login?service=http%3A%2F%2Fcourses.iiit.ac.in%2FEdgeNet%2Fhome.php' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0' -H 'Content-Type: application/x-www-form-urlencoded' --data "username=$USERNAME&password=$PASSWORD&lt=$LT_VALUE&_eventId=submit" -kv 2>&1 | grep Location: | awk -F'[ =]' '{print $4}' | tr -d '\r')

echo $TICKET 

PHPSESSID=$(curl "http://courses.iiit.ac.in/EdgeNet/home.php?ticket=$TICKET" -H 'Host: courses.iiit.ac.in' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate' -H 'Connection: keep-alive' -kv 2>&1 | grep PHPSESSID | tail -1 | awk -F'[ =;]' '{print $4}' | tr -d '\r')

echo $PHPSESSID

OUTPUT=$(curl 'http://courses.iiit.ac.in/EdgeNet/marks.php?select=1329&page=0' -H 'Host: courses.iiit.ac.in' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate' -H "Cookie: PHPSESSID=$PHPSESSID" -H 'Connection: keep-alive')

echo $OUTPUT > 1.txt

python 1.py
