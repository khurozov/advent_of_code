package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {
        HashMap<Character, Integer> strength = new HashMap<>();
        strength.put('A', 13);
        strength.put('K', 12);
        strength.put('Q', 11);
        strength.put('J', 10);
        strength.put('T',  9);
        strength.put('9',  8);
        strength.put('8',  7);
        strength.put('7',  6);
        strength.put('6',  5);
        strength.put('5',  4);
        strength.put('4',  3);
        strength.put('3',  2);
        strength.put('2',  1);

        List<CardHand> cardHands = Files.readAllLines(Path.of("src", "day7", "input"))
                .stream()
                .map(input -> {
                    String[] s = input.split(" ");

                    return new CardHand(s[0], Long.parseLong(s[1]), Util.getScoreWithoutJoker(Util.getCardCount(s[0])));
                })
                .sorted(Util.getComparator(strength))
                .toList();

        long sum = 0;
        for (int i = 0; i < cardHands.size(); i++) {
            CardHand cardHand = cardHands.get(i);
            sum += (i+1) * cardHand.bid;
        }
        System.out.println(sum);
    }
}
