package solutions.y2023;

import extra.CardHand;
import extra.CardScore;
import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Day7 extends Solution {
    public static void main(String[] args) throws Exception {
        Day7 day7 = new Day7();
        day7.part1();
        day7.part2();
    }

    @Override
    public void part1() throws Exception {
        HashMap<Character, Integer> strengthMap = new HashMap<>();
        strengthMap.put('A', 13);
        strengthMap.put('K', 12);
        strengthMap.put('Q', 11);
        strengthMap.put('J', 10);
        strengthMap.put('T', 9);
        strengthMap.put('9', 8);
        strengthMap.put('8', 7);
        strengthMap.put('7', 6);
        strengthMap.put('6', 5);
        strengthMap.put('5', 4);
        strengthMap.put('4', 3);
        strengthMap.put('3', 2);
        strengthMap.put('2', 1);

        System.out.println(getTotalWinnings(strengthMap, Day7::getScoreWithoutJoker));
    }

    @Override
    public void part2() throws Exception {
        HashMap<Character, Integer> strengthMap = new HashMap<>();
        strengthMap.put('A', 13);
        strengthMap.put('K', 12);
        strengthMap.put('Q', 11);
        strengthMap.put('T', 10);
        strengthMap.put('9', 9);
        strengthMap.put('8', 8);
        strengthMap.put('7', 7);
        strengthMap.put('6', 6);
        strengthMap.put('5', 5);
        strengthMap.put('4', 4);
        strengthMap.put('3', 3);
        strengthMap.put('2', 2);
        strengthMap.put('J', 1);

        System.out.println(getTotalWinnings(strengthMap, Day7::getScoreWithJoker));
    }

    private long getTotalWinnings(HashMap<Character, Integer> strengthMap, Function<HashMap<Character, Integer>, CardScore> cardScoreFunction) throws IOException {
        List<CardHand> cardHands = Files.readAllLines(this.inputFile())
                .stream()
                .map(input -> {
                    String[] s = input.split(" ");

                    return new CardHand(s[0], Long.parseLong(s[1]), cardScoreFunction.apply(getCardCount(s[0])));
                })
                .sorted((cardHand1, cardHand2) -> {
                    if (cardHand1.score() != cardHand2.score()) {
                        return cardHand1.score().ordinal() - cardHand2.score().ordinal();
                    }

                    for (int i = 0; i < 5; i++) {
                        char c1 = cardHand1.cards().charAt(i);
                        char c2 = cardHand2.cards().charAt(i);

                        if (c1 != c2) {
                            return strengthMap.get(c1) - strengthMap.get(c2);
                        }
                    }
                    return 0;
                })
                .toList();

        long sum = 0;
        for (int i = 0; i < cardHands.size(); i++) {
            CardHand cardHand = cardHands.get(i);
            sum += (i + 1) * cardHand.bid();
        }

        return sum;
    }

    private static HashMap<Character, Integer> getCardCount(String cards) {
        HashMap<Character, Integer> cardCount = new HashMap<>();

        for (char c : cards.toCharArray()) {
            cardCount.compute(c, (k, v) -> v == null ? 1 : v + 1);
        }

        return cardCount;
    }

    private static CardScore getScoreWithoutJoker(HashMap<Character, Integer> cardCount) {
        if (cardCount.size() == 1) {
            return CardScore.FIVE_OF_A_KIND;
        }

        if (cardCount.size() == 2) {
            if (cardCount.containsValue(4)) {
                return CardScore.FOUR_OF_A_KIND;
            }

            return CardScore.FULL_HOUSE;
        }

        if (cardCount.size() == 3) {
            if (cardCount.containsValue(3)) {
                return CardScore.THREE_OF_A_KIND;
            }

            return CardScore.TWO_PAIR;
        }

        if (cardCount.size() == 4) {
            return CardScore.ONE_PAIR;
        }

        return CardScore.HIGH_CARD;
    }

    private static CardScore getScoreWithJoker(HashMap<Character, Integer> cardCount) {
        Integer n = cardCount.remove('J');
        if (n != null) {
            if (n >= 4 || cardCount.size() == 1) return CardScore.FIVE_OF_A_KIND;

            return getScoreWithJoker(cardCount, n);
        }

        return getScoreWithoutJoker(cardCount);
    }

    private static CardScore getScoreWithJoker(HashMap<Character, Integer> cardCount, int n) {
        switch (n) {
            case 1: {
                if (cardCount.size() == 2) {
                    if (cardCount.containsValue(3)) {
                        return CardScore.FOUR_OF_A_KIND;
                    }

                    return CardScore.FULL_HOUSE;
                }

                if (cardCount.size() == 3) {
                    return CardScore.THREE_OF_A_KIND;
                }
                return CardScore.ONE_PAIR;
            }
            case 2: {
                if (cardCount.size() == 2) {
                    return CardScore.FOUR_OF_A_KIND;
                }
                return CardScore.THREE_OF_A_KIND;
            }
            default: {
                return CardScore.FOUR_OF_A_KIND;
            }
        }
    }
}
