import pytesseract
from pytesseract import Output
import cv2

img = cv2.imread('threshold.jpg')

# Save the extracted features to text file
extracted_text = pytesseract.image_to_string(img, lang = 'deu')

text_file = open('extracted.txt', 'wt')
n = text_file.write(extracted_text)
text_file.close()

# Draw boxes around the features and save it as a new image
d = pytesseract.image_to_data(img, output_type=Output.DICT)
n_boxes = len(d['level'])
for i in range(n_boxes):
    (x, y, w, h) = (d['left'][i], d['top'][i], d['width'][i], d['height'][i])
    img = cv2.rectangle(img, (x, y), (x + w, y + h), (0, 0, 255), 1)

cv2.imwrite('detected.jpg', img)
