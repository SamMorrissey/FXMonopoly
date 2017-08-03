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
 * The deck of Community Chest cards, automatically shuffled on instantiation.
 * @author Sam P. Morrissey
 */
public class CommunityChestDeck {
        
    private final ArrayList<Card> deck;
    private final ArrayList<Card> shuffledDeck;
    
    /**
     * Creates a deck of Community Chest cards to the specified elements, then takes
     * this initial deck and creates a shuffled deck, of which the top card can
     * be accessed externally.
     */
    public CommunityChestDeck() {
        deck = new ArrayList<>();
        
        deck.add(new MoveToCard("Advance to Go. \n" + "Collect £200.", 0));
        deck.add(new PayableCard("Bank error in your favour. \n" + "Collect £200.", 200, false));
        deck.add(new PayableCard("Doctor's fees. \n" + "Pay £50.", -50, false));
        deck.add(new PayableCard("From sale of stock you get £50.", 50, false));
        deck.add(new GOJFCard());
        deck.add(new MoveToCard("Go to Jail. \n" + "Do not pass Go, do not collect £200", 10));
        deck.add(new PayableCard("Grand Opera Night. \n" + "Collect £50 from every player for opening night seats.", 50, true));
        deck.add(new PayableCard("Xmas fund matures. \n" + "Collect £200", 200, false));
        deck.add(new PayableCard("Income tax refund.", 20, false));
        deck.add(new PayableCard("Life insurance matures. \n" + "Collect £100.", 100, false));
        deck.add(new PayableCard("Pay hospital fees of £100.", -100, false));
        deck.add(new PayableCard("Pay school fees of £150", -150, false));
        deck.add(new PayableCard("Receive £25 consultancy fee.", 25, false));
        deck.add(new DoublePayableCard("You are assessed for street repairs. \n" + "£40 per house, £115 per hotel.", 40, 115));
        deck.add(new PayableCard("You have won second prize in a beauty contest. \n" + "Collect £10.", 10, false));
        deck.add(new PayableCard("You inherit £100.", 100, false));
        
        shuffledDeck = getShuffledDeck();
    }
    
    /**
     * Utilises the deck created in the constructor and returns a shuffled version,
     * which can (although highly unlikely to) be in the same order as the original.
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
     * Returns the provided card to the bottom of the pile. Done this way to
     * allow for Get Out Of Jail Free cards to be returned when they are actually
     * used, not immediately after being picked up.
     * @param card The card 
     */
    public void returnCard(Card card) {
        shuffledDeck.add(card);
    }
}
