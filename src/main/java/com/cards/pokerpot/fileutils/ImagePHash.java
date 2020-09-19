package com.cards.pokerpot.fileutils;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cards.pokerpot.dct.DCTUtils;

@Component
public class ImagePHash {
	
	@Autowired
	private DCTUtils dCTUtils;
	
	public void setDCTUtils(DCTUtils dCTUtils) {
		this.dCTUtils = dCTUtils;
	}
	
	private int size = 25;
	private int smallerSize = 8;

	public String getHash(BufferedImage img) {
		img = resize(img, size, size);
        double[][] vals = new double[size][size];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
                vals[x][y] = img.getRGB(x, y) & 0xff;
			}
		}
        double[][] dctVals = dCTUtils.applyDCT(vals);
        double total = 0;

        for (int x = 0; x < smallerSize; x++) {
            for (int y = 0; y < smallerSize; y++) {
                total += dctVals[x][y];
            }
        }
        total -= dctVals[0][0];

        double avg = total / (double) ((smallerSize * smallerSize) - 1);

		String hash = "";

        for (int x = 0; x < smallerSize; x++) {
            for (int y = 0; y < smallerSize; y++) {
                if (x != 0 && y != 0) {
                    hash += (dctVals[x][y] > avg ? "1" : "0");
                }
            }
        }
		return hash;
	}

	private BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}	
}