#!/bin/bash
DEBUT=1
FIN=$1
INITVAL=3351
VNCINIT=4951

#...

for ((c = $DEBUT; c <= $FIN; c++)); do
  portdock=$((INITVAL + c))
  portvnc=$((VNCINIT + c))

  docker run -d -p "$portdock":3333 -p "$portvnc":4900 -v c:/temp:/home/seluser/test --name chromenav-"$c" --rm fake-selenium-flash/chromenav
done
