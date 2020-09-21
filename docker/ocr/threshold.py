import cv2
import numpy as np
from matplotlib import pyplot as plt

# Read the image
img = cv2.imread('receipt.png',0)

# Simple thresholding
ret,thresh1 = cv2.threshold(img,210,255,cv2.THRESH_BINARY)
cv2.imwrite('threshold.jpg', thresh1)
