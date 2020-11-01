FROM ubuntu

COPY ./api.py /home

RUN apt-get update && apt-get install -y software-properties-common tesseract-ocr

RUN tesseract -v

RUN add-apt-repository -y ppa:deadsnakes/ppa

RUN apt update

RUN apt install -y python3.8 python3-pip

RUN pip3 install pytesseract
RUN pip3 install opencv-python-headless
RUN pip3 install opencv-contrib-python-headless
RUN pip3 install matplotlib
RUN pip3 install flask

RUN apt-get install -y tesseract-ocr-est tesseract-ocr-eng tesseract-ocr-deu

WORKDIR /home

RUN mkdir -p /home/files

ENTRYPOINT python3 api.py

EXPOSE 5000
