package com.cards.pokerpot.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import com.cards.pokerpot.fileutils.FileHandler;
import com.cards.pokerpot.fileutils.ImagePHash;
import com.cards.pokerpot.fileutils.SuitFinder;
import com.cards.pokerpot.hash.HashOfValue;

@PropertySource(value = "classpath:application.properties")
@Controller
public class PokerPotCardHandler {

	@Value("${xcoordinateforcard}")
	private Integer xCoordinateForCard;

	@Value("${ycoordinateforcard}")
	private Integer yCoordinateForCard;

	@Value("${xcoordinateforrank}")
	private Integer xCoordinateForRank;

	@Value("${ycoordinateforrank}")
	private Integer yCoordinateForRank;

	@Value("${xcoordinateforsuite}")
	private Integer xCoordinateForSuite;

	@Value("${ycoordinateforsuite}")
	private Integer yCoordinateForSuite;

	@Value("${distancebetweencards}")
	private Integer distanceBetweenCards;

	@Autowired
	private FileHandler fileHandler;

	@Autowired
	private ImagePHash imagePHash;

	@Autowired
	private SuitFinder suitFinder;

	public void findThePot(String folderPath) {
		try {
			File[] images = fileHandler.handleImageFiles(folderPath);
			if (images != null) {
				for (File image : images) {
					BufferedImage bufferedImage = ImageIO.read(image);
					System.out.print(image.getName().substring(0, image.getName().length() - 4) + "-");
					Integer xCoordinateForSuiteForThisCard = xCoordinateForSuite;
					Integer xCoordinateForRankForThisCard = xCoordinateForRank;
					int totalCardsInPot = fileHandler.calculationOfCards(bufferedImage, xCoordinateForCard,
							yCoordinateForCard, distanceBetweenCards);
					for (int i = 0; i < totalCardsInPot; i++) {
						BufferedImage rankSubImage = bufferedImage.getSubimage(xCoordinateForRankForThisCard,
								yCoordinateForRank, 32, 32);
						String hash = imagePHash.getHash(rankSubImage);
						String valueOfCard = getRankOfCardFromHash(hash);

						BufferedImage suiteSubImage = bufferedImage.getSubimage(xCoordinateForSuiteForThisCard,
								yCoordinateForSuite, 30, 30);
						int rgbOfSuite = suiteSubImage.getRGB(15, 15);
						String suites = suitFinder.getSuites(suiteSubImage, rgbOfSuite);
						xCoordinateForSuiteForThisCard += distanceBetweenCards;

						System.out.print(valueOfCard + suites);
						xCoordinateForRankForThisCard += distanceBetweenCards;
					}
					System.out.println("");
				}
			}
		} catch (IOException e) {
			System.out.println("Invalid folder/files");
		}
	}

	private String getRankOfCardFromHash(String hashInCard) {
		int minError = 32;
		String valueOfCard = "";
		for (HashOfValue hash : HashOfValue.values()) {
			int error = calculateHammingDistance(hashInCard, hash.getValue());
			if (minError > error) {
				minError = error;
				valueOfCard = hash.getName();
			}
		}
		return valueOfCard;
	}

	private int calculateHammingDistance(String hashIncard, String hashFromEnum) {
		if (hashIncard.length() != hashFromEnum.length())
			return -1;
		int error = 0;
		for (int i = 0; i < hashIncard.length(); i++) {
			if (hashIncard.charAt(i) != hashFromEnum.charAt(i))
				error++;
		}
		return error;
	}
}
