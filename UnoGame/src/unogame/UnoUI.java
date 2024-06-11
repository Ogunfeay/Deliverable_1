/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unogame;

import java.util.ArrayList;
import java.util.Scanner;

public class UnoUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the game with three players: Alice, Bob, and Charlie
        Game game = new Game(new String[]{"Alice", "Bob", "Charlie"});
        game.dealCards(7); // Deal 7 cards to each player

        // Simulate the game
        while (!isGameOver(game)) {
            for (String playerName : game.getPlayers().keySet()) {
                displayGameState(game);
                // Each player takes a turn
                playTurn(scanner, playerName, game);
                if (isGameOver(game)) {
                    break; // Exit loop if a player has emptied their hand
                }
            }
        }

        // Determine the winner
        String winner = getWinner(game);
        System.out.println("The winner is: " + winner);
    }

    // Method to simulate a player's turn
    private static void playTurn(Scanner scanner, String playerName, Game game) {
        ArrayList<Card> hand = game.getPlayers().get(playerName);
        Card topCard = game.getDiscardPile().get(game.getDiscardPile().size() - 1);

        // Display current player and their hand
        System.out.println("\n" + playerName + "'s turn");
        System.out.println("Your hand: " + hand);

        // Prompt the player to play a card or draw
        System.out.println("Enter the index of the card you want to play (0-" + (hand.size() - 1) + ") or -1 to draw a card:");
        int choice = scanner.nextInt();

        if (choice >= 0 && choice < hand.size()) {
            Card selectedCard = hand.get(choice);
            if (selectedCard.getValue().startsWith("Wild")) {
                // Handle Wild card
                System.out.println("Choose a color (Red, Blue, Green, Yellow):");
                String colorChoice = scanner.next();
                selectedCard.setColor(colorChoice);
            }
            if (isValidMove(selectedCard, topCard)) {
                // Play the selected card
                game.playCard(playerName, selectedCard);
                System.out.println(playerName + " played: " + selectedCard);
            } else {
                System.out.println("Invalid move. You must play a card of matching color or value.");
                playTurn(scanner, playerName, game); // Retry turn
            }
        } else if (choice == -1) {
            // Draw a card
            Card drawnCard = game.drawCard(playerName);
            System.out.println(playerName + " drew: " + drawnCard);
        } else {
            System.out.println("Invalid input. Please enter a valid index or -1 to draw a card.");
            playTurn(scanner, playerName, game); // Retry turn
        }
    }

    // Method to check if a move is valid
    private static boolean isValidMove(Card card, Card topCard) {
        return card.getValue().startsWith("Wild") || card.getColor().equals(topCard.getColor()) || card.getValue().equals(topCard.getValue());
    }

    // Method to check if the game is over (i.e., if any player has emptied their hand)
    private static boolean isGameOver(Game game) {
        for (ArrayList<Card> hand : game.getPlayers().values()) {
            if (hand.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Method to determine the winner of the game
    private static String getWinner(Game game) {
        for (String playerName : game.getPlayers().keySet()) {
            if (game.getPlayers().get(playerName).isEmpty()) {
                return playerName;
            }
        }
        return "No winner"; // This should not happen if the game is over
    }

    // Method to display the current game state
    private static void displayGameState(Game game) {
        System.out.println("\nCurrent game state:");
        for (String playerName : game.getPlayers().keySet()) {
            System.out.println(playerName + "'s hand: " + game.getPlayers().get(playerName));
        }
        System.out.println("Discard pile: " + game.getDiscardPile());
    }
}