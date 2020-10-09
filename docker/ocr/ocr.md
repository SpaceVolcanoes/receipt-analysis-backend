# OCR container for receipt image to text

## Building

```
docker build -t tess .
```

## Example container usage

```
docker run -d -p 8800:5000 --name=tess tess:latest

curl -X POST -F "image=@/path/to/receipt.png" localhost:8800/
```

## Script usage in container

```
docker exec -it tess bash

python3 threshold.py receipt.png
python3 detect.py receipt.png
```
