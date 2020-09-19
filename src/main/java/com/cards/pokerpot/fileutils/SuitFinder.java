package com.cards.pokerpot.fileutils;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

@Component
public class SuitFinder {
	
	 public String getSuites(BufferedImage image, int rgb) {
	        String suite = "";
	        switch (rgb) {
	            case -10477022:
	            case -3323575:
	                suite = getRedSuite(image);
	                break;
	            case -14474458:
	            case -15724526:
	                suite = getBlackSuite(image);
	                break;
	        }
	        return suite;
	    }

	    private String getRedSuite(BufferedImage image) {
	        String suite = "";
	        int firstColor;
	        int secondColor;
	        for (int i = 0; i < 8; i++) {
	            firstColor = image.getRGB(15, 12 - i);
	            secondColor = image.getRGB(15, 12 - i - 1);
	            if (firstColor != secondColor) {
	                suite = "h";
	                break;
	            } else {
	                suite = "d";
	            }
	        }
	        return suite;
	    }

	    private String getBlackSuite(BufferedImage image) {
	        String suite = "";
	        int firstColor;
	        int secondColor;
	        for (int i = 0; i < 8; i++) {
	            firstColor = image.getRGB(16 - i, 7);
	            secondColor = image.getRGB(16 - i - 1, 7);
	            if (firstColor != secondColor) {
	                suite = "c";
	                break;
	            } else {
	                suite = "s";
	            }
	        }
	        return suite;
	    }

}
