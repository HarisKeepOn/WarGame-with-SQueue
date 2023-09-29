package ass1;

import java.util.Collections;
import java.util.ArrayList;

public class WarGame {
    // Set a static final variable for max rounds in game
    private static final int MAX_ROUNDS = 100;

    public static void main(String[] args) {
        // Create the game with given max rounds
        int maxRounds = MAX_ROUNDS;
        if (args.length > 0) {
            maxRounds = Integer.parseInt(args[0]);
        }

        int rounds = 0;
        SQueue<Card> player1 = new SQueue<Card>(26);
        SQueue<Card> player2 = new SQueue<Card>(26);

        // Initialize a queue of cards
        ArrayList<Card> cardDeck = new ArrayList<Card>();
        for (Card.Ranks rank : Card.Ranks.values()) {
            for (Card.Suits suit : Card.Suits.values()) {
                cardDeck.add(new Card(suit, rank));
            }
        }

        // Shuffle the deck
        Collections.shuffle(cardDeck);

        // Distribute the cards
        boolean turn = true;
        for (Card card : cardDeck) {
            if (turn) {
                player1.enqueue(card);
            } else {
                player2.enqueue(card);
            }
            turn = !turn;
        }

        // Play the game
        while (!player1.isEmpty() && !player2.isEmpty() && rounds < maxRounds) {
            Card player1Card = player1.dequeue();
            Card player2Card = player2.dequeue();

            System.out.println("Player 1 plays " + player1Card.toString());
            System.out.println("Player 2 plays " + player2Card.toString());

            int compareResult = player1Card.compareTo(player2Card);
            if (compareResult > 0) {
                System.out.println("Player 1 wins the round");
                // Player 1 wins the round
                player1.enqueue(player1Card);
                player1.enqueue(player2Card);
            } else if (compareResult < 0) {
                System.out.println("Player 2 wins the round");
                // Player 2 wins the round
                player2.enqueue(player1Card);
                player2.enqueue(player2Card);

                // handle 'war' situation

                // Additional code for handling "war" goes here
                while (compareResult == 0) {
                    if (player1.isEmpty() || player2.isEmpty()) {
                        break;
                    }
                    System.out.println("War continues");
                    Card player1WarCard = player1.dequeue();
                    Card player2WarCard = player2.dequeue();
                    System.out.println("Player 1 plays war card " + player1WarCard.toString());
                    System.out.println("Player 2 plays war card " + player2WarCard.toString());

                    compareResult = player1WarCard.compareTo(player2WarCard);
                    if (compareResult > 0) {
                        System.out.println("Player 1 wins the war");
                        player1.enqueue(player1Card);
                        player1.enqueue(player2Card);
                        player1.enqueue(player1WarCard);
                        player1.enqueue(player2WarCard);
                    } else if (compareResult < 0) {
                        System.out.println("Player 2 wins the war");
                        player2.enqueue(player1Card);
                        player2.enqueue(player2Card);
                        player2.enqueue(player1WarCard);
                        player2.enqueue(player2WarCard);
                    }
                }
            } else {
                System.out.println("War");
                // Additional code for handling "war" goes here
                while (compareResult == 0) {
                    if (player1.isEmpty() || player2.isEmpty()) {
                        break;
                    }
                    System.out.println("War continues");
                    Card player1WarCard = player1.dequeue();
                    Card player2WarCard = player2.dequeue();
                    System.out.println("Player 1 plays war card " + player1WarCard.toString());
                    System.out.println("Player 2 plays war card " + player2WarCard.toString());

                    compareResult = player1WarCard.compareTo(player2WarCard);
                    if (compareResult > 0) {
                        System.out.println("Player 1 wins the war");
                        player1.enqueue(player1Card);
                        player1.enqueue(player2Card);
                        player1.enqueue(player1WarCard);
                        player1.enqueue(player2WarCard);
                    } else if (compareResult < 0) {
                        System.out.println("Player 2 wins the war");
                        player2.enqueue(player1Card);
                        player2.enqueue(player2Card);
                        player2.enqueue(player1WarCard);
                        player2.enqueue(player2WarCard);
                    }
                }
            }
            rounds++;
        }

        // Print final result
        if (player1.getSize() > player2.getSize()) {
            System.out.println("Player 1 wins the game");
        } else if (player1.getSize() < player2.getSize()) {
            System.out.println("Player 2 wins the game");
        } else {
            System.out.println("The game is a draw");
        }
    }
}
