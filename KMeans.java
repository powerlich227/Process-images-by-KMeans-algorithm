/*** Author :Vibhav Gogate
The University of Texas at Dallas
*****/


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class KMeans {
    public static void main(String [] args){
	if (args.length < 3){
	    System.out.println("Usage: Kmeans <input-image> <k> <output-image>");
	    return;
	}
	try{
	    BufferedImage originalImage = ImageIO.read(new File(args[0]));
	    int k=Integer.parseInt(args[1]);
	    BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
	    ImageIO.write(kmeansJpg, "jpg", new File(args[2])); 
	    
	}catch(IOException e){
	    System.out.println(e.getMessage());
	}	
    }
    
    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k){
	int w=originalImage.getWidth();
	int h=originalImage.getHeight();
	BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
	Graphics2D g = kmeansImage.createGraphics();
	g.drawImage(originalImage, 0, 0, w,h , null);
	// Read rgb values from the image
	int[] rgb=new int[w*h];
	int count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		rgb[count++]=kmeansImage.getRGB(i,j);
	    }
	}
	// Call kmeans algorithm: update the rgb values
	kmeans(rgb,k);

	// Write the new rgb values to the image
	count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		kmeansImage.setRGB(i,j,rgb[count++]);
	    }
	}
	return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private static void kmeans(int[] rgb, int k){
    	int len = rgb.length;
    	
    	// the previous and current centers and for each pixel assign a current center
    	int[] prevCenter = new int[k];
    	int[] currCenter = new int[k];
    	int[] centerAssigned = new int[len];

    	// for the first round, randomly choose k points as the centers
    	for (int i = 0; i < k; i++) {
    		int center = 0;
    		do {
    			center = rgb[new Random().nextInt(len)];
    		}
    		while (contains(prevCenter, center));
    		currCenter[i] = center;
    	}

    	// get the rgb values of image
    	int[] pixelCount;
    	int[] alpha;
    	int[] red;
    	int[] green;
    	int[] blue;
    	
    	// k-means
    	do {
    		pixelCount = new int[k];
    		alpha = new int[k];
    		red = new int[k];
    		green = new int[k];
    		blue = new int[k];
    		
    		for (int i = 0; i < k; i++) {
    			prevCenter[i] = currCenter[i];
    		}
    		
    		for (int i = 0; i < len; i++) {
    			double maxDistance = Double.MAX_VALUE;
    			double currDistance = 0.0;
    			int index = 0;						// get the index of center
    			
    			for (int j = 0; j < k; j++) {
    				currDistance = calcuDistance(rgb[i], currCenter[j]);
    				if (currDistance < maxDistance) {
    					maxDistance = currDistance;
    					index = j;
    				}
    			}
    			
    			centerAssigned[i] = index;
    			pixelCount[index]++;
    			
    			// Referece: http://stackoverflow.com/questions/20442653/any-one-tell-me-why-we-write-this-0xff000000-in-filters
    			alpha[index] += ((rgb[i] & 0xFF000000) >> 24);
    			red[index] += ((rgb[i] & 0x00FF0000) >> 16);
    			green[index] += ((rgb[i] & 0x0000FF00) >> 8);
    			blue[index] += (rgb[i] & 0x000000FF);
    		}
    		
    		for (int i = 0; i < k; i++) {
    			int count = pixelCount[i];
    			int a = alpha[i] / count;
    			int r = red[i] / count;
    			int g = green[i] / count;
    			int b = blue[i] / count;
    			
    			currCenter[i] = ((a & 0x000000FF) << 24) | ((r & 0x000000FF) << 16) | 
    					((g & 0x000000FF) << 8) | (b & 0x000000FF);
    		}
    	}
    	while (!converged(prevCenter, currCenter));
    	
    	// assign the values in the image
    	for (int i = 0; i < len; i++) {
    		rgb[i] = currCenter[centerAssigned[i]];
    	}
    }
    
    // this function check whether the previous centers contains the current center
    public static boolean contains(int[] a, int center) {
    	for (int i = 0; i < a.length; i++) {
    		if (a[i] == center) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // stop k-mean implementation, when the centers will not change
    public static boolean converged(int[] a, int[] b) {
    	for (int i = 0; i < a.length; i++) {
    		if (a[i] != b[i]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    // calculate the distance between two pixels
    public static double calcuDistance(int pixelA, int pixelB) {
    	int a = ((pixelA & 0xFF000000) - (pixelB & 0xFF000000)) >> 24;
    	int r = ((pixelA & 0x00FF0000) - (pixelB & 0x00FF0000)) >> 16;
    	int g = ((pixelA & 0x0000FF00) - (pixelB & 0x0000FF00)) >> 8;
    	int b = (pixelA & 0x000000FF) - (pixelB & 0x000000FF);
    	return a * a + r * r + g * g + b * b;
    }
}
