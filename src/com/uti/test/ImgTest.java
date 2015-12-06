package com.uti.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ImgTest {

	public static void main(String[] args){
		try {
		    File img = new File("D://test.jpg");
		    BufferedImage image = ImageIO.read(img ); 
		    BufferedImage testImage =createThumbnail(image);
		    ImageIO.write(testImage,"png",new File("D://TEST.png"));
		} catch (IOException e) { 
		    e.printStackTrace(); 
		}
	}
	
	public static BufferedImage createThumbnail(BufferedImage img){
		BufferedImage aimg = Scalr.resize(img,50);
		return aimg;
	}
}
