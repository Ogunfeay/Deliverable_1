/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unogame;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private Deck deck;
    private HashMap<String, ArrayList<Card>> players;
    private String currentPlayer;
    private ArrayList<Card> discardPile;
    private int direction;

    public Game(String[] playerNames) {
        deck = new Deck();
        players = new HashMap<>();
        for (String name : playerNames) {
            players.put(name, new ArrayList<>());
        }
        currentPlayer = playerNames[0];
        discardPile = new ArrayList<>();
        discardPile.add(deck.draw());
        direction = 1;
    }

    public void dealCards(int count) {
        for (String player : players.keySet()) {
            for (int i = 0; i < count; i++) {
                players.get(player).add(deck.draw());
            }
        }
    }

    public void playCard(String player, Card card) {
        ArrayList<Card> hand = players.get(player);
        hand.remove(card); // Remove the played card from the player's hand
        discardPile.add(card); // Add the played card to the discard pile
    }

    public Card drawCard(String player) {
        ArrayList<Card> hand = players.get(player);
        Card drawnCard = deck.draw(); // Draw a card from the deck
        hand.add(drawnCard); // Add the drawn card to the player's hand
        return drawnCard;
    }

    public HashMap<String, ArrayList<Card>> getPlayers() {
        return players;
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }
}