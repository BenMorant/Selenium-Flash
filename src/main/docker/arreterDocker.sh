#!/bin/bash
DEBUT=1
FIN=$1

for ((c = $DEBUT; c <= $FIN; c++)); do
  docker stop chromenav-"$c"
done
