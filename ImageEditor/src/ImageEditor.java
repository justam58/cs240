import java.io.*;
import java.util.*;


public class ImageEditor {

	public static Pixel[][] image;
	public static Pixel[][] newImage;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// not enough argument
		if(args[0] == null || args[1] == null || args[2] == null ){
			System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
	        System.exit(0);
		}
		
		// input file
		File srcFile = new File(args[0]);
		File destFile = new File(args[1]);
		String operation = args[2];
		
		Scanner sc = new Scanner(srcFile);
		PrintWriter pw = new PrintWriter(destFile);
		
//		sc.useDelimiter(" ");
		int width = 0;
		int height = 0;
		
		int count = 0;
		while(count < 4){
			String temp = sc.next();
			if(temp.charAt(0) == '#'){
			}
			else{
				if(count == 1){
					int temp_n = Integer.parseInt(temp);
					width = temp_n;
				}
				if(count == 2){
					int temp_n = Integer.parseInt(temp);
					height = temp_n;
				}
				count++;
			}
		}
		
		image = new Pixel[width][height];
		newImage = new Pixel[width][height];
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				int red = 0;
				int green = 0;
				int blue = 0;
				int countt = 0;
				while(countt < 3){
					String temp = sc.next();
					if(temp.charAt(0) == '#'){
						sc.nextLine();
					}
					else{
						if(countt == 0){
							int temp_n = Integer.parseInt(temp);
							red = temp_n;
						}
						if(countt == 1){
							int temp_n = Integer.parseInt(temp);
							green = temp_n;
						}
						if(countt == 2){
							int temp_n = Integer.parseInt(temp);
							blue = temp_n;
						}
						countt++;
					}
				}
				image[i][j] = new Pixel(red,green,blue);
			}
		}	
		
		
		// gray scale
		if(operation.equals("grayscale")){
			for(int j = 0; j < height; j++){
				for(int i = 0; i < width; i++){
					int average = (image[i][j].getRed() + image[i][j].getGreen() + image[i][j].getBlue()) / 3;
					image[i][j].setRed(average);
					image[i][j].setGreen(average);
					image[i][j].setBlue(average);
				}
			}
		}
		
		// invert
		else if(operation.equals("invert")){
			for(int j = 0; j < height; j++){
				for(int i = 0; i < width; i++){
					image[i][j].setRed(255-image[i][j].getRed());
					image[i][j].setGreen(255-image[i][j].getGreen());
					image[i][j].setBlue(255-image[i][j].getBlue());
				}
			}
		}
		
		// emboss
		else if(operation.equals("emboss")){
			for(int j = 0; j < height; j++){
				for(int i = 0; i < width; i++){
					if((j-1) < 0 || (i-1) < 0){
						newImage[i][j] = new Pixel(128,128,128);
					}
					else{
						int redDiff  = image[i][j].getRed() - image[i-1][j-1].getRed();
						int greenDiff = image[i][j].getGreen() - image[i-1][j-1].getGreen();
						int blueDiff = image[i][j].getBlue() - image[i-1][j-1].getBlue();
						
						int redABS = Math.abs(redDiff);
						int greenABS = Math.abs(greenDiff);
						int blueABS = Math.abs(blueDiff);
						
						int biggest1 = Math.max(redABS, greenABS);
						int biggest2 = Math.max(biggest1, blueABS);
						
						int maxDifference = 0;
						if(biggest2 == redABS){
							maxDifference = redDiff;
						}
						else if(biggest2 == greenABS){
							maxDifference = greenDiff;
						}
						else if(biggest2 == blueABS){
							maxDifference = blueDiff;
						}
						
						int v = 128 + maxDifference;
						if(v < 0){
							v = 0;
						}
						if(v > 255){
							v = 255;
						}
						newImage[i][j] = new Pixel(v,v,v);
					}
				}
			}
		}
		
		// motion blur
		else if(operation.equals("motionblur")){
			// no blur length
			if(args[3] == null){
				System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
		        System.exit(0);
			}
			
			String nString = args[3];
			int n = Integer.parseInt(nString);
			
			for(int j = 0; j < height; j++){
				for(int i = 0; i < width; i++){
					int N = 0;
					if((i+n) <= width){
						N = n;
					}
					else{
						N = width - i;
					}
					int redTotal = 0;
					for(int k = 0; k < N; k++){
						redTotal += image[i+k][j].getRed();
					}
					int redAverage = redTotal / N;
					
					int greenTotal = 0;
					for(int k = 0; k < N; k++){
						greenTotal += image[i+k][j].getGreen();
					}
					int greenAverage = greenTotal / N;
					
					int blueTotal = 0;
					for(int k = 0; k < N; k++){
						blueTotal += image[i+k][j].getBlue();
					}
					int blueAverage = blueTotal / N;
					
					image[i][j].setRed(redAverage);
					image[i][j].setGreen(greenAverage);
					image[i][j].setBlue(blueAverage);
				}
			}
		}
		
		// output file
		pw.printf("P3 %d %d 255 ", width, height);
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				if(operation.equals("emboss")){
					pw.print(newImage[i][j]);
				}
				else{
					pw.print(image[i][j]);
				}
			}
		}
		
		sc.close();
		pw.close();
	}
}
