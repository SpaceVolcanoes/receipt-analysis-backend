#!/bin/bash

if [ -z "$1" ]
  then
    echo "Supply image file name as the argument"
    exit 1
fi

docker run -v $(pwd):/home tess:latest python3 threshold.py $1

docker run -v $(pwd):/home tess:latest python3 detect.py $1
