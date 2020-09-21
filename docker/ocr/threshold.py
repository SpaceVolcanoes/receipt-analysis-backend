import cv2
import numpy as np
from matplotlib import pyplot as plt
import sys
import os

filename = sys.argv[len(sys.argv) - 1]
original_name = os.path.splitext(filename)[0]

# Read the image
img = cv2.imread(filename, 0)

# Simple thresholding
ret,thresh1 = cv2.threshold(img, 210, 255, cv2.THRESH_BINARY)
cv2.imwrite(original_name + '-threshold.jpg', thresh1)
