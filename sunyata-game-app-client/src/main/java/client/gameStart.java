package client;

import java.util.*;

public class gameStart {

    static // test1
            Map<String, Integer> missingSuit = new HashMap<String, Integer>() {{
        put("21", 1);
        put("22", 1);
        put("23", 1);
        put("24", 1);
        put("25", 1);
        put("26", 1);
        put("32", 3);
        put("39", 3);
        put("43", 2);
    }};

    static Map<String, Integer> missingTriple = new HashMap<String, Integer>() {{
        put("11", 1);
        put("12", 1);
        put("13", 1);
        put("14", 1);
        put("15", 1);
        put("16", 1);
        put("21", 1);
        put("22", 1);
        put("23", 1);
        put("24", 1);
        put("25", 1);
        put("26", 1);
        put("37", 2);
    }};

    static Map<String, Integer> missingRow = new HashMap<String, Integer>() {{
        put("11", 3);
        put("22", 3);
        put("23", 3);
        put("32", 3);
        put("37", 2);
    }};

    static Map<String, Integer> missing1or9 = new HashMap<String, Integer>() {{
        put("12", 1);
        put("13", 1);
        put("14", 2);
        put("15", 1);
        put("16", 1);
        put("22", 1);
        put("23", 1);
        put("24", 1);
        put("25", 3);
        put("37", 2);
    }};

    static Map<String, Integer> Double2or8 = new HashMap<String, Integer>() {{
        put("11", 1);
        put("12", 1);
        put("13", 1);
        put("14", 1);
        put("15", 1);
        put("16", 1);
        put("22", 1);
        put("23", 1);
        put("24", 1);
        put("34", 3);
        put("38", 2);
    }};


    // helper function - inputTaken
    // input: String, Map, int
    // this helper function takes the user input and correct amount for that card
    // in the avalableMa
    public static void inputTaken(String userIn, int availNum, Map<String, Integer> avail) {
        // the user input will be in the format, "SM"
        int left = avail.get(userIn);
        avail.put(userIn, left - 1);
        availNum = availNum - 1;
    }

    // helper function - addCard
    // input: String, Map
    // add the given card into the Map
    public static void addCard(String userIn, Map<String, Integer> myCard) {
        if (myCard.containsKey(userIn)) {
            // if already got the card, add 1
            int perviousV = myCard.get(userIn);
            myCard.put(userIn, perviousV + 1);
        } else {
            // if not put the key
            myCard.put(userIn, 1);
        }
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

    // Function name: sortCards
    // Input: Map
    // Output: List of string
    // Description: sort the cards given, suit first and mag next
    public static List<String> sortCards(Map<String, Integer> cards) {
        List<String> afterSort = new ArrayList<String>();
        List<Integer> a = new ArrayList<Integer>();
        for (String k : cards.keySet()) {
            a.add(Integer.parseInt(k));
        }

        Collections.sort(a);
        for (int i = 0; i < a.size(); i++) {
            afterSort.add(Integer.toString(a.get(i)));
        }

        return afterSort;
    }

    // Function name: checkRest
    // Input: Map,Map
    // Output: Boolean
    // Description: check the rest of cards and see if they can be mix to triple or short straight
    public static boolean checkRest(Map<String, Integer> cards, Map<Integer, Boolean> winR) {
        Map<String, Integer> currentCard = new HashMap<String, Integer>(cards);

        for (String k : cards.keySet()) {
            // check if one card has a tripple
            if (currentCard.get(k) == 3) {
                winR.put(8, true);
                winR.put(getSuit(k), true);
                // check if 1or9
                if (getMag(k) == 1 || getMag(k) == 9) {
                    winR.put(7, true);
                }
                currentCard.remove(k);
            }
        }

        // sort everything that are not in a tripple
        List<String> sortedCard = sortCards(currentCard);

        // if there are suit 4 or 5 left, then it is not a winning hand
        for (String k : currentCard.keySet()) {
            if (getSuit(k) == 4 || getSuit(k) == 5) {
                return false;
            }
        }

        // now all cards left should be able to arrange to 3 in a row
        while (!sortedCard.isEmpty()) {
            String thisCard = sortedCard.get(0);
            String nextCard = Integer.toString(Integer.parseInt(thisCard) + 1);
            String next2Card = Integer.toString(Integer.parseInt(nextCard) + 1);
            List<String> inRow = new ArrayList<String>();
            inRow.add(thisCard);
            inRow.add(nextCard);
            inRow.add(next2Card);
            // if there are no other cards left, then it is not a winning hand
            if (currentCard.get(nextCard) == null || currentCard.get(next2Card) == null) {
                return false;
            } else {
                // check if there are more cards left
                for (String k : inRow) {
                    if (getMag(k) == 1 || getMag(k) == 9) {
                        winR.put(7, true);
                    }

                    winR.put(getSuit(k), true);
                    winR.put(9, true);

                    if (currentCard.get(k) == 1) {
                        currentCard.remove(k);
                        sortedCard.remove(k);
                    } else {
                        currentCard.put(k, currentCard.get(k) - 1);
                    }

                }
            }
        }

        return true;
    }

    // Functio name: checkR
    // Input: Map
    // Output: Boolean
    // Description: This function takes a all the Winning Requirnment
    public static boolean checkR(Map<Integer, Boolean> currentW) {
        // check if we have open house
        if (!currentW.get(6)) {
            return false;
        }

        // check if we hae all three suits we need
        if (!(currentW.get(1) && currentW.get(2) && currentW.get(3))) {
            return false;
        }

        // check if we have suit5 OR we have both Peng and 1or9
        if (!(currentW.get(5) || (currentW.get(8) && currentW.get(7)))) {
            return false;
        }

        if (!currentW.get(9)) {
            return false;
        }

        return true;
    }

    // Function name: checkWin
    // Input: Map, Map
    // Output: Boolean
    // This function takes a map of all the cards one player has and the current
    // situation and determine if this play
    // won the game or not by returning a boolean
    // key 1 for hasSuit1
    // key 2 for hasSuit2
    // key 3 for hasSuit3
    // key 4 for hasSuit4
    // key 5 for hasSuit5
    // key 6 for openHand
    // key 7 for has1or9
    // key 8 for hasTriple
    public static boolean checkWin(Map<String, Integer> cards, Map<Integer, Boolean> winR) {
        // set two vairable to change
        Map<String, Integer> Cards = new HashMap<String, Integer>(cards);

        // Check what we can add to the currentW list
        // loop through everything in the current Cards
        for (String k : Cards.keySet()) {
            Map<String, Integer> currentCards = new HashMap<String, Integer>(Cards);
            Map<Integer, Boolean> currentW = new HashMap<Integer, Boolean>(winR);
            // if the current card has more than 2 cards, then it has the chance to be the double
            // therefore anything with more than 2 cards can be checked if they are double
            if (currentCards.get(k) >= 2 && getMag(k) != 2 && getMag(k) != 8) {
                // seperate the cases of 2 and more than 2
                currentW.put(getSuit(k), true);
                if (currentCards.get(k) == 2) {
                    // check if we have two Suit 5 card
                    if (getSuit(k) == 5) {
                        if (currentCards.get(k) == 2) {
                            currentW.put(5, true);
                            currentCards.remove(k);
                        }
                    }
                    currentCards.remove(k);
                } else {
                    currentCards.put(k, currentCards.get(k) - 2);
                }
            }

            if (checkRest(currentCards, currentW)) {
                if (checkR(currentW)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Function name: pftc ( possibility for this card)
    // Input: String -- card
    //        Map -- available card left
    //        Integer -- numOfavailablecard
    //        Map -- cards that the player has
    // Output: double
    // Description: Given the card and what cards are left, return the possibility of getting this
    //              card

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

    // Function name: takeAvail
    // Input: Map -- all available card
    //        String -- card
    // Out: Integer  0 for pass, 1 for none exist
    // Description: Take this card out of the available card
    public static int takeAvail(Map<String, Integer> availCard, String card) {
        if (!availCard.containsKey(card)) {
            System.out.println("There are no more this card");
            return 1;
        }
        if (availCard.get(card) == 1) {
            availCard.remove(card);
            return 0;
        } else {
            availCard.put(card, availCard.get(card) - 1);
            return 0;
        }
    }

    // Function name: getTriple
    // Input: Map --- myCards
    //        String -- cardPlayed
    // Output: List<String> --- null if none 3 can be made.
    //                          otherwise will be a list of strings (of cards) that can make the combination of
    public static List<String> findTriple(Map<String, Integer> myCards, String cardPlayed) {
        // find if there are same card in the cards the play has
        List<String> result = new ArrayList<String>();

        // check if this card is a suit 5, if yes, return null
        if (getSuit(cardPlayed) == 5) {
            return result;
        }

        if (myCards.containsKey(cardPlayed)) {
            if (myCards.get(cardPlayed) == 3) {
                // can make a 4 of a kind
                // make the string for result
                String fourofakind = "";
                for (int i = 0; i < 4; i++) {
                    fourofakind += cardPlayed;
                    fourofakind += " ";
                }
                result.add(fourofakind);
            } else {
                // check if any 3 of a kind
                if (myCards.get(cardPlayed) == 2) {
                    // make the string for result
                    String threeofakind = "";
                    for (int i = 0; i < 3; i++) {
                        threeofakind += cardPlayed;
                        threeofakind += " ";
                    }
                    result.add(threeofakind);
                }
            }
        }

        return result;
    }

    // Function name: findRow
    // Input: Map --- myCards
    //        String -- cardPlayed
    // Output: List<String>
    // Description: This function will find all the cards that can put together in a row
    public static List<String> findRow(Map<String, Integer> myCards, String cardPlayed) {

        List<String> result = new ArrayList<String>();
        // check if this card is suit 4 or suit 5, if yes, return current result
        if (getSuit(cardPlayed) == 4 || getSuit(cardPlayed) == 5) {
            return result;
        }

        // check for this card in a row
        // check this card being the left most card
        if (getMag(cardPlayed) + 2 < 10) {
            String thisMagright1 = Integer.toString(Integer.parseInt(cardPlayed) + 1);
            String thisMagright2 = Integer.toString(Integer.parseInt(cardPlayed) + 2);
            if (myCards.containsKey(thisMagright1) && myCards.containsKey(thisMagright2)) {
                String row = "";
                row += cardPlayed + " " + thisMagright1 + " " + thisMagright2;
                result.add(row);
            }
        }

        // check this card being the middle one
        if (getMag(cardPlayed) != 1 && getMag(cardPlayed) != 9) {
            String thisMagleft1 = Integer.toString(Integer.parseInt(cardPlayed) - 1);
            String thisMagright1 = Integer.toString(Integer.parseInt(cardPlayed) + 1);
            if (myCards.containsKey(thisMagright1) && myCards.containsKey(thisMagleft1)) {
                String row = "";
                row += thisMagleft1 + " " + cardPlayed + " " + thisMagright1;
                result.add(row);
            }
        }

        // check this card being the right most card
        if (getMag(cardPlayed) - 2 > 0) {
            String thisMagleft1 = Integer.toString(Integer.parseInt(cardPlayed) - 1);
            String thisMagleft2 = Integer.toString(Integer.parseInt(cardPlayed) - 2);
            if (myCards.containsKey(thisMagleft1) && myCards.containsKey(thisMagleft2)) {
                String row = "";
                row += thisMagleft1 + " " + thisMagleft2 + " " + cardPlayed;
                result.add(row);
            }
        }

        // return the list back
        return result;
    }

    // Function name: removeNum
    // Input: Map --- myCard
    //        Integer -- Number of cards
    //        String -- card
    // Output: void
    // Description: Remove the givem number of given card from current cards
    public static void removeNum(Map<String, Integer> myCards, int num, String thisCard) {
        if (myCards.get(thisCard) == num) {
            myCards.remove(thisCard);
        } else {
            myCards.put(thisCard, myCards.get(thisCard) - num);
        }
    }


    public static void main(String[] args) {
        // get all available majiang
        Map<String, Integer> availableMa = new HashMap<String, Integer>();
        // build the map starting with first three suits which each have 9
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 10; j++) {
                String thisM = Integer.toString(i * 10 + j);
                availableMa.put(thisM, 4);
            }
        }

        // build suit 4 which have 4 elements
        // 1-4 stands for east, south, west, north
        for (int i = 1; i < 5; i++) {
            String thisM = Integer.toString(40 + i);
            availableMa.put(thisM, 4);
        }

        // build suit 5 which have 3 elements
        // 1 stands for middle
        // 2 stands for rich
        // 3 stands for white
        for (int i = 1; i < 4; i++) {
            String thisM = Integer.toString(50 + i);
            availableMa.put(thisM, 4);
        }

        // ask for which position the user is at, range from 1-4
        Scanner r = new Scanner(System.in);
        System.out.println("Which position are you seating?");
        int position = r.nextInt();
        int current = 1;
        int availCard = 136;

        // set some winning requirnments
        // key 1 for hasSuit1
        // key 2 for hasSuit2
        // key 3 for hasSuit3
        // key 4 for hasSuit4
        // key 5 for hasSuit5
        // key 6 for openHand
        // key 7 for has1or9
        // key 8 for hasTriple
        // key 9 for hasRow
        Map<Integer, Boolean> winRequire = new HashMap<Integer, Boolean>();
        for (int i = 1; i < 10; i++) {
            winRequire.put(i, false);
        }
        winRequire.put(6, false);

        boolean jumpTo = false;
        boolean readyHand = false;

        Map<String, Integer> myCard = new HashMap<String, Integer>();
        //------------------------------------------------------------
        /*if(checkWin(Double2or8, winRequire)) {
            System.out.println("this is a winning hand");
		}else {
			System.out.println("this is not a winning hand");
		}
		*/

        // get first round of deal which should be 13 cards
//        for (int i = 1; i < 14; i++) {
//            System.out.println("What is your" + i + "card?");
//            Scanner s = new Scanner(System.in);
//            String cardInput = s.nextLine();
//            inputTaken(cardInput, availCard, availableMa);
//            addCard(cardInput, myCard);
//        }
        for (String key : Double2or8.keySet()) {
            Integer orDefault = Double2or8.getOrDefault(key, 1);
            for (int num = 0; num < orDefault; num++) {
                addCard(key, myCard);
            }
        }

        // loop until someone won the game
        boolean gameOver = false;
        String[] cardsWant = new String[1];
        String priceCard = "";
        while (!gameOver) {
            // check if the other three people are playing or if the user is playing
            if (current == position) {

                // if the user is playing then first thing to do is ask what is the new card
                String cardInput = "";
                if (!jumpTo) {
                    System.out.println("What is the card you picked up?");
                    Scanner s = new Scanner(System.in);
                    cardInput = s.nextLine();
                    inputTaken(cardInput, availCard, availableMa);
                    addCard(cardInput, myCard);

                    // Check if win from readyHand
                    if (readyHand) {
                        if (cardInput.equals(priceCard)) {
                            System.out.print("You won big time!");
                            break;
                        } else {
                            for (int i = 0; i < cardsWant.length; i++) {
                                if (cardInput.equals(cardsWant[i])) {
                                    System.out.println("You won by getting the last card!");
                                    break;
                                }
                            }
                        }
                    }
                    //for(int i = 0; i < sortCards(myCard).size(); i++) {
                    //  System.out.println(sortCards(myCard).get(i));
                    //}
                }

                jumpTo = false;


                // see if there are four cards that are same.
                if (myCard.containsValue(4)) {
                    // call 4 of a kind
                    winRequire.put(6, true);
                    // remove this card beacause we can not do anything to them
                    for (String k : myCard.keySet()) {
                        if (myCard.get(k) == 4) {
                            // set boolean to true
                            winRequire.put(getSuit(k), true);
                            // remove these from myCard
                            myCard.remove(k);
                        }
                    }

                    // and player will draw one more card, ask for input

                    System.out.print("What card did you just draw?");
                    Scanner a = new Scanner(System.in);
                    String newCard = a.nextLine();
                    inputTaken(newCard, availCard, availableMa);
                    addCard(newCard, myCard);
                }

                // check if the player won or not
                if (!readyHand) {
                    if (checkWin(myCard, winRequire)) {
                        System.out.println("You won the game!!");
                        gameOver = true;
                        break;
                    }
                }


                // if player did not won the game, ask player if they want to call a ready hand
                if (!readyHand) {
                    System.out.println("Do you want to call a ready hand?");
                    Scanner b = new Scanner(System.in);
                    String readyHands = b.nextLine();
                    if (readyHands.equals("yes")) {
                        System.out.println("Tell me what card are you trying to get? use space to seprate");
                        // find what card or cards player need
                        Scanner g = new Scanner(System.in);
                        String cardsNeed = g.nextLine();
                        cardsWant = cardsNeed.split(" ");
                        System.out.println("Tell me what card you draw for price");
                        Scanner l = new Scanner(System.in);
                        priceCard = l.nextLine();
                        readyHand = true;
                        continue;
                    }

                    // when a readyHand, you can only play the card that you just draw
                    if (readyHand) {
                        removeNum(myCard, 1, cardInput);
                        continue;
                    }

                    System.out.println("I think you should play card " + whichCard(myCard, availableMa, availCard));
                    System.out.println("What did you decide to play?");
                    Scanner c = new Scanner(System.in);
                    String cardPlayed = c.nextLine();
                    while (takeAvail(availableMa, cardPlayed) == 1) {
                        Scanner d = new Scanner(System.in);
                        cardPlayed = d.nextLine();
                    }
                    myCard.remove(cardPlayed);
                    availCard--;

                }
            } else {
                // other players
                System.out.println("What did the player play?");
                Scanner e = new Scanner(System.in);
                String cardPlayed = e.nextLine();
                while (takeAvail(availableMa, cardPlayed) == 1) {
                    Scanner d = new Scanner(System.in);
                    cardPlayed = d.nextLine();
                }
                availCard--;

                // check if readyhand, if yes, if the player play the card we need, we win
                if (readyHand) {
                    for (int i = 0; i < cardsWant.length; i++) {
                        if (cardPlayed.equals(cardsWant[i])) {
                            System.out.println("You won by getting the last card!");
                            break;
                        }
                    }
                }

                // use check method see if there are 3 cards can make
                List<String> comCards = findTriple(myCard, cardPlayed);
                if (position == current + 1 || (position == 1 && current == 4)) {
                    comCards.addAll(findRow(myCard, cardPlayed));
                }

                if (!comCards.equals(new ArrayList<String>())) {
                    System.out.println("You can make these combinations:");
                    for (int i = 0; i < comCards.size(); i++) {
                        System.out.print(comCards.get(i) + " ");
                    }
                    System.out.println("");
                    System.out.println("Which combination do you want to play? enter 0 if none");
                    Scanner f = new Scanner(System.in);
                    int order = f.nextInt();
                    if (order > 0) {
                        String combination = comCards.get(order - 1);
                        String[] allCards = combination.split(" ");
                        //System.out.println(allCards.length);
                        if (allCards[0].equals(allCards[1])) {
                            winRequire.put(8, true);
                            winRequire.put(getSuit(allCards[0]), true);
                            winRequire.put(6, true);
                            if (allCards.length == 4) {
                                removeNum(myCard, 3, allCards[0]);
                            } else {
                                removeNum(myCard, 2, allCards[0]);
                            }

                        } else {
                            winRequire.put(9, true);
                            winRequire.put(getSuit(allCards[0]), true);
                            winRequire.put(6, true);
                        }
                        for (int i = 0; i < allCards.length; i++) {
                            if (getMag(allCards[0]) == 1 || getMag(allCards[0]) == 9) {
                                winRequire.put(7, true);
                            }
                            if (!allCards[i].equals(cardPlayed)) {
                                removeNum(myCard, 1, allCards[i]);
                            }
                        }
                        current = position;
                        jumpTo = true;
                        continue;
                    }
                }
            }

            // ask if anyone triple or eat the last card
            System.out.println("Did someone else use that card for triple or row?");
            Scanner f = new Scanner(System.in);
            String reply = f.nextLine();
            if (reply.equals("no")) {
                System.out.println("Moving on to next player");
                if (current == 4) {
                    current = 1;
                } else {
                    current++;
                }
                System.out.println(current);
            } else if (reply.equals("triple")) {
                System.out.println("What card did they tripled?");
                Scanner g = new Scanner(System.in);
                String cardPlayed = g.nextLine();
                takeAvail(availableMa, cardPlayed);
                takeAvail(availableMa, cardPlayed);
                availCard = availCard - 2;
                System.out.println("Which player play now?");
                Scanner k = new Scanner(System.in);
                current = k.nextInt();
            } else if (reply.equals("row")) {
                System.out.println("What card did they put together?");
                Scanner i = new Scanner(System.in);
                String cardPlayed = i.nextLine();
                takeAvail(availableMa, cardPlayed);
                cardPlayed = i.nextLine();
                takeAvail(availableMa, cardPlayed);
                availCard = availCard - 2;
                System.out.println("Which player play now?");
                Scanner k = new Scanner(System.in);
                current = k.nextInt();
            }

            if (readyHand) {
                // check how many priceHand left
                if (!availableMa.containsKey(priceCard)) {
                    System.out.println("Draw another priceCard");
                    Scanner h = new Scanner(System.in);
                    priceCard = h.nextLine();
                }
            }

        }


    }

}