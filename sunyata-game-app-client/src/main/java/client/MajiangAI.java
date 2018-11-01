package client;

import java.util.HashMap;
import java.util.Map;

public class MajiangAI {
    // Function name: whichCard
    // Input: Map ---- all the cards the player has
    // Output: String -- representing the card the player wants to play
    // Description: This is the core method for the program, this function should calculate the importence of
    //              every card the player has and determine which one has the least importence so that the
    //              player should throw this card away
    public static String whichCard(Map<String, Integer> myCards, Map<String, Integer> availMa,
                                   int numAvailma) {
        Map<String, Integer> currentCards = new HashMap<String, Integer>(myCards);
        Map<String, Double> importentRank = new HashMap<String, Double>();
        // Find special cases
        // Find if there are 1 or 9, if there are no 1 or 9, keep 2,3and 7,8
        int numOf19 = 0;
        for (String k : currentCards.keySet()) {
            if ((getMag(k) == 1 || getMag(k) == 9) && getSuit(k) != 4 && getSuit(k) != 5) {
                numOf19++;
            }
        }

        // if there are no 1 or 9, keep 2, 3, 8, and 9
        if (numOf19 == 0) {
            for (String k : myCards.keySet()) {
                if (getMag(k) == 2 || getMag(k) == 3 || getMag(k) == 7 || getMag(k) == 8 || getSuit(k) == 5) {
                    importentRank.put(k, 1.00);
                }
            }
        } else {
            // check if there is only one 1 or 9
            if (numOf19 == 1) {
                for (String k : myCards.keySet()) {
                    if (getMag(k) == 1 || getMag(k) == 9) {
                        importentRank.put(k, 1.00);
                    }
                }
            }
        }

        // remove all suit 5
        for (String k : myCards.keySet()) {
            if (getSuit(k) == 5) {
                currentCards.remove(k);
            }
        }

        // Caculate the possibility of this card getting tripple
        for (String k : currentCards.keySet()) {
            if (currentCards.get(k) == 3) {
                importentRank.put(k, 1.00);
            } else if (currentCards.get(k) == 2) {
                // times 3 because we can call triple for every card players play
                importentRank.put(k, 1.5 * (double) availMa.get(k) / (double) numAvailma);
            } else {
                double thisP = (double) availMa.get(k) / (double) numAvailma;
                importentRank.put(k, thisP * thisP);
            }
        }

        // remove all suit 4 because there is no row for suit 4
        for (String k : myCards.keySet()) {
            if (getSuit(k) == 4) {
                currentCards.remove(k);
            }
        }

        // Calculate the possibility of this card getting row
        for (String k : currentCards.keySet()) {
            // check for the the left two straight
            int thisMag = getMag(k);
            if (thisMag - 2 > 0) {
                String thisMagleft2 = Integer.toString(Integer.parseInt(k) - 2);
                String thisMagleft1 = Integer.toString(Integer.parseInt(k) - 1);
                double thisRowP = possC(thisMagleft2, availMa, numAvailma, currentCards)
                        * possC(thisMagleft1, availMa, numAvailma, currentCards);
                importentRank.put(k, importentRank.get(k) + thisRowP);
            }
            // check for the thisCard in the middle
            if (thisMag - 1 > 0 && thisMag + 1 < 10) {
                String thisMagright1 = Integer.toString(Integer.parseInt(k) + 1);
                String thisMagleft1 = Integer.toString(Integer.parseInt(k) - 1);
                double thisRowP = possC(thisMagright1, availMa, numAvailma, currentCards)
                        * possC(thisMagleft1, availMa, numAvailma, currentCards);
                importentRank.put(k, importentRank.get(k) + thisRowP);
            }
            // check for the right two row
            if (thisMag + 2 < 10) {
                String thisMagright1 = Integer.toString(Integer.parseInt(k) + 1);
                String thisMagright2 = Integer.toString(Integer.parseInt(k) + 2);
                double thisRowP = possC(thisMagright1, availMa, numAvailma, currentCards)
                        * possC(thisMagright2, availMa, numAvailma, currentCards);
                importentRank.put(k, importentRank.get(k) + thisRowP);
            }
        }

        // check for special cases, if 2 or 8 are close to other cards, then play 2 or 8
        String minCard = "";
        double minCardP = 1;
        for (String k : importentRank.keySet()) {
            System.out.println(k + "with possibility" + importentRank.get(k));
            if (importentRank.get(k) < minCardP) {
                minCard = k;
                minCardP = importentRank.get(k);
            }
        }

        return minCard;
    }

    // Function name: getSuit
    // Input: String
    // Output: int
    // Description: Given the card, check which suit it is and turn the corresponding suit
    public static int getSuit(String card) {
        return Integer.parseInt(card.substring(0, 1));
    }

    // Function name: getMag
    // Input: String
    // Output: int
    // Description: Given the card, return the magnitude of the card
    public static int getMag(String card) {
        return Integer.parseInt(card.substring(1, 2));
    }

    public static double possC(String thisCard, Map<String, Integer> availMa, int numAvailma,
                               Map<String, Integer> myCards) {
        if (availMa.containsKey(thisCard)) {
            if (myCards.containsKey(thisCard)) {
                return 1.00;
            } else {
                return (double) availMa.get(thisCard) / (double) numAvailma;
            }
        }
        return 0;
    }
}
