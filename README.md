
Program requirement:
K-means clustering on images
In this problem, you will use K-means clustering for image compression. We have provided you with two images.
• Display the images after data compression using K-means clustering for different values of K (2, 5, 10, 15, 20).
• What are the compression ratios for different values of K? Note that you have to repeat the experiment multiple times with different initializations and report the average as well as variance in the compression ratio.
• Is there a tradeoff between image quality and degree of compression? What would be a good value of K for each of the two images?
We have provided you java template KMeans.java which implements various image input/output operations. You have to implement the function k-means in the template. See the file for more details. Note that your program must compile and we should be able to replicate your results. Otherwise no credit will be given.


How to run the program:
Go to the src file, 
To compile and run:

javac KMeans.java
java KMeans "Koala.jpg" 2 "Koala_2.jpg"
java KMeans "Koala.jpg" 5 "Koala_5.jpg"
java KMeans "Koala.jpg" 10 "Koala_10.jpg"
java KMeans "Koala.jpg" 15 "Koala_15.jpg"
java KMeans "Koala.jpg" 20 "Koala_20.jpg"
java KMeans "Penguins.jpg" 2 "Penguins_2.jpg"
java KMeans "Penguins.jpg" 5 "Penguins_5.jpg"
java KMeans "Penguins.jpg" 10 "Penguins_10.jpg"
java KMeans "Penguins.jpg" 15 "Penguins_15.jpg"
java KMeans "Penguins.jpg" 20 "Penguins_20.jpg"


