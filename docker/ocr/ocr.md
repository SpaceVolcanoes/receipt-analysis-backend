# OCR container for receipt image to text

## Example usage

```
curl -X POST -F "image=@receipt.png" localhost:8800/?lang=est
```

## Production deploy

### Build container
```
docker build -t receiptanalysis/ocr .
echo $DOCKER_PASS | docker login -u$DOCKER_USER --password-stdin
docker push receiptanalysis/ocr
docker logout
```

### Update in production env
```
docker pull receiptanalysis/ocr
docker stop analysis-ocr && docker rm analysis-ocr && docker-compose -f docker-compose.backend.yml up -d ocr
```
