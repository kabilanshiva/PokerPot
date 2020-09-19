package com.cards.pokerpot;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cards.pokerpot.handler.PokerPotCardHandler;

@SpringBootApplication
public class PokerPotApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(PokerPotApplication.class, args);
		try (Scanner scanner = new Scanner(System.in);) {
			System.out.println("Folder path : ");
			String folderPath = scanner.nextLine();
			PokerPotCardHandler pokerPotCardHandler = context.getBean(PokerPotCardHandler.class);
			pokerPotCardHandler.findThePot(folderPath);
			System.out.print("Enter EXIT to exit...");
			scanner.hasNext();
		}
	}
}
