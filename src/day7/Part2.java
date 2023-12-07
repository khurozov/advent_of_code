package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {
        HashMap<Character, Integer> strength = new HashMap<>();
        strength.put('A', 13);
        strength.put('K', 12);
        strength.put('Q', 11);
        strength.put('T', 10);
        strength.put('9',  9);
        strength.put('8',  8);
        strength.put('7',  7);
        strength.put('6',  6);
        strength.put('5',  5);
        strength.put('4',  4);
        strength.put('3',  3);
        strength.put('2',  2);
        strength.put('J',  1);

        List<CardHand> cardHands = Files.readAllLines(Path.of("src", "day7", "input"))
                .stream()
                .map(input -> {
                    String[] s = input.split(" ");

                    return new CardHand(s[0], Long.parseLong(s[1]), Util.getScoreWithJoker(Util.getCardCount(s[0])));
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
