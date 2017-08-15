/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks;

import fxmonopoly.gamedata.decks.cards.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The deck of chance cards, automatically shuffled on instantiation.
 * @author Sam P. Morrissey
 */
public class ChanceDeck {
    
    private final ArrayList<Card> deck;
    
    private final ArrayList<Card> shuffledDeck;
    
    /**
     * Creates a deck of Chance cards to the specified elements, then takes the 
     * initial deck and creates a shuffled deck of which the top card can be 
     * accessed externally.
     */
    public ChanceDeck() {
        deck = new ArrayList<>();
        
        deck.add(new MoveToCard("Advance to Go. \n" + "Collect £200", 0));
        deck.add(new MoveToCard("Advance to Trafalgar Square. \n" + "If you pass Go, collect £200", 24));
        deck.add(new MoveToCard("Advance to Pall Mall \n" + "If you pass Go, collect £200", 11));
        deck.add(new NearestUtilityCard("Advance token to nearest Utility. \n" + "If unowned, you may buy it from the Bank. If owned, throw die and pay owner a total ten times the amount thrown."));
        deck.add(new NearestRailwayCard("Advance token to the nearest Railway, and pay the owner twice the rental to which he/she is otherwise entitled."));
        deck.add(new NearestRailwayCard("Advance token to the nearest Railway, and pay the owner twice the rental to which he/she is otherwise entitled."));
        deck.add(new PayableCard("Bank pays you a dividend of £50.", 50, false));
        deck.add(new GOJFCard());
        deck.add(new MoveByCard("Go back three spaces.", -3));
        deck.add(new MoveToCard("Go to Jail. \n" + "Do not pass Go, do not collect £200.", 10));
        deck.add(new DoublePayableCard("Make general repairs on all your property. \n" + "For each house pay £25, for each hotel pay £100.", 25, 100));
        deck.add(new PayableCard("Pay speeding fine of £15.", -15, false));
        deck.add(new MoveToCard("Take a trip to King's Cross Station. \n" + "If you pass Go, collect £200.", 25));
        deck.add(new MoveToCard("Take a walk to Mayfair.", 39));
        deck.add(new PayableCard("You have been elected Chariman of the Board. \n" + "Pay each player £50.", -50, true));
        deck.add(new PayableCard("Your building loan matures. \n" + "Collect £150.", 150, false));
        
        shuffledDeck = getShuffledDeck();
    }
    
    /**
     * Utilises the deck created in the constructor and returns a shuffled version,
     * which can (although highly unlikely) be in the same order as the original.
     * @return shuffled The shuffled deck of cards.
     */
    private ArrayList<Card> getShuffledDeck() {
        ArrayList<Card> deckCopy = (ArrayList) deck.clone();
        ArrayList<Card> shuffled = new ArrayList<>();
        Random generator = new Random();
        
        int i;
        for(i = 0; i < deck.size(); i++) {
            int j = generator.nextInt(deckCopy.size());
            shuffled.add(deckCopy.get(j));
            deckCopy.remove(j);
        }
        
        return shuffled;
    }
    
    /**
     * Gets the next card on the pile. Also removes it from the pile, thus
     * returnCard must be called to place it back at the bottom of the pile again.
     * @return card The card at the top of the pile.
     */
    public Card getNextCard() {
        Card card = shuffledDeck.get(0);
        shuffledDeck.remove(0);
        return card;
    }
    
    /**
     * Returns the provided card to the bottom of the deck. Done this way to allow
     * for Get Out Of Jail Free cards to be returned when they are actually used,
     * not immediately after being picked up.
     * @param card 
     */
    public void returnCard(Card card) {
        shuffledDeck.add(card);
    }
    
    /**
     * Retrieves the boolean value regarding whether this deck currently contains
     * a GOJFCard.
     * @return True if it contains a GOJFCard, false otherwise.
     */
    public boolean containsGOJFCard() {
        return shuffledDeck.stream().anyMatch((card) -> (card instanceof GOJFCard));
    }
    
}
