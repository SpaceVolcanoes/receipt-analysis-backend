import random
import string
from flask import Flask, request, jsonify
import pytesseract
import cv2

SUPPORTED_LANGUAGES = {'eng', 'est', 'deu'}
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}
FILEPATH = '/home/files/'

app = Flask(__name__)
app.config["DEBUG"] = True


def get_random_string(length):
    letters = string.ascii_letters
    return ''.join(random.choice(letters) for i in range(length))


@app.route('/', methods=['POST'])
def home():
    if 'lang' not in request.args or request.args['lang'] not in SUPPORTED_LANGUAGES:
        response = jsonify({'message': 'Missing or invalid language'})
        response.status_code = 400
        return response

    if 'image' not in request.files:
        response = jsonify({'message': 'Missing file'})
        response.status_code = 400
        return response

    file = request.files['image']

    if file.filename == '' or '.' not in file.filename:
        response = jsonify({'message': 'Invalid file name'})
        response.status_code = 400
        return response

    extension = file.filename.rsplit('.', 1)[1].lower()

    if extension not in ALLOWED_EXTENSIONS:
        response = jsonify({'message': 'File extension not allowed'})
        response.status_code = 403
        return response

    basename = get_random_string(15)
    filename = basename + '.' + extension

    file.save(FILEPATH + filename)

    img = cv2.imread(FILEPATH + filename, 0)
    ret, img = cv2.threshold(img, 210, 255, cv2.THRESH_BINARY)

    extracted_text = pytesseract.image_to_string(img, lang=request.args['lang'])

    response = jsonify({'text': extracted_text})
    response.status_code = 200
    return response


app.run(host='0.0.0.0')
