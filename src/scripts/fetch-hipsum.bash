#!/bin/bash

URL="http://localhost:8080/betvictor/text?p=3"

while true; do
  curl -s "$URL" > /dev/null
  sleep 1
done
