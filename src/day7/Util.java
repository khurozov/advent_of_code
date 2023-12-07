package day7;

import java.util.Comparator;
import java.util.HashMap;

class Util {

    public static HashMap<Character, Integer> getCardCount(String cards) {
        HashMap<Character, Integer> cardCount = new HashMap<>();

        for (char c : cards.toCharArray()) {
            cardCount.compute(c, (k,v) -> v == null ? 1 : v+1);
        }

        return cardCount;
    }

    static CardScore getScoreWithoutJoker(HashMap<Character, Integer> cardCount) {
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

    static CardScore getScoreWithJoker(HashMap<Character, Integer> cardCount) {
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

    static Comparator<CardHand> getComparator(HashMap<Character, Integer> strength) {
        return (ch1, ch2) -> {
            if (ch1.score != ch2.score) {
                return ch1.score.ordinal() - ch2.score.ordinal();
            }

            for (int i = 0; i < 5; i++) {
                char c1 = ch1.cards.charAt(i);
                char c2 = ch2.cards.charAt(i);

                if (c1 != c2) {
                    return strength.get(c1) - strength.get(c2);
                }
            }
            return 0;
        };
    }
}
