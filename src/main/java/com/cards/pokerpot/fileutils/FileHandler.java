package com.cards.pokerpot.fileutils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class FileHandler {

	public File[] handleImageFiles(String folderPath) throws IOException {
			File directory = new File(folderPath);
			File[] files = directory.listFiles();
			return files;
	}

	public int calculationOfCards(BufferedImage image, int xCoordinateForCard, int yCoordinateForCard,
			int distanceBetweenCards) {
		int totalCardsOnPot = 0;
		for (int i = 0; i < 5; i++) {
			int rgb = image.getRGB(xCoordinateForCard, yCoordinateForCard);
			if (rgb == -1 || rgb == -8882056) {
				totalCardsOnPot++;
				xCoordinateForCard = xCoordinateForCard + distanceBetweenCards;
			}
		}
		return totalCardsOnPot;
	}

}
