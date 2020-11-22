#!/bin/bash
echo 'Pull de la derniere version de selenium-chrome image'
docker pull selenium/standalone-chrome-debug

echo 'Creation de l’image specifique fake-selenium-flash'
docker build -t fake-selenium-flash/chromenav ../docker
