package solutions.y2015;

import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.BitSet;
import java.util.HashMap;

public class Day7 extends Solution {
    public static void main(String[] args) throws Exception {
        Day7 day1 = new Day7();
        day1.part1();
        day1.part2();
    }

    @Override
    public void part1() throws Exception {
        solve(false);
    }

    @Override
    public void part2() throws Exception {
        solve(true);
    }

    private void solve(boolean changeB) throws IOException {
        HashMap<String, String> commands = new HashMap<>();
        Files.readAllLines(this.inputFile()).forEach(line -> {
            String[] s = line.split(" -> ");
            commands.put(s[1], s[0]);
        });

        System.out.println(toNum(find("a", commands, new HashMap<>(), changeB)));
    }

    private static BitSet find(String key, HashMap<String, String> commands, HashMap<String, BitSet> mem, boolean changeB) {
        if (changeB && key.equals("b")) {
            return parseIfNum("3176");
        }

        BitSet bs = mem.get(key);

        if (bs == null) {
            bs = calc(key, commands, mem, changeB);
            mem.put(key, bs);
        }

        return bs;
    }

    private static BitSet calc(String key, HashMap<String, String> commands, HashMap<String, BitSet> mem, boolean changeB) {
        BitSet bs = parseIfNum(key);

        if (bs != null) return bs;

        String command = commands.get(key);

        if (command.startsWith("NOT ")) {
            return not(find(command.substring(4), commands, mem, changeB));
        }

        if (command.contains("AND")) {
            String[] s = command.split(" AND ");
            return and(find(s[0], commands, mem, changeB), find(s[1], commands, mem, changeB));
        }

        if (command.contains("OR")) {
            String[] s = command.split(" OR ");
            return or(find(s[0], commands, mem, changeB), find(s[1], commands, mem, changeB));
        }

        if (command.contains("LSHIFT")) {
            String[] s = command.split(" LSHIFT ");
            return shiftLeft(find(s[0], commands, mem, changeB), Integer.parseInt(s[1]));
        }

        if (command.contains("SHIFT")) {
            String[] s = command.split(" RSHIFT ");
            return shiftRight(find(s[0], commands, mem, changeB), Integer.parseInt(s[1]));
        }

        return find(command, commands, mem, changeB);
    }

    private static BitSet not(BitSet bs) {
        BitSet b = new BitSet(16);
        b.or(bs);
        b.flip(0, 16);
        return b;
    }

    private static BitSet and(BitSet bs0, BitSet bs1) {
        BitSet bs = new BitSet(16);
        bs.or(bs0);
        bs.and(bs1);
        return bs;
    }

    private static BitSet or(BitSet bs0, BitSet bs1) {
        BitSet bs = new BitSet(16);
        bs.or(bs0);
        bs.or(bs1);
        return bs;
    }

    public static BitSet shiftLeft(BitSet bits, int n) {
        long[] words = bits.toLongArray();

        for (int i = 0; i < words.length - 1; i++) {
            words[i] >>>= n;
            words[i] |= words[i + 1] << (64 - n);
        }
        if (words.length > 0) {
            words[words.length - 1] >>>= n;
        }

        return BitSet.valueOf(words);
    }

    public static BitSet shiftRight(BitSet bits, int n) {
        long[] words = bits.toLongArray();

        if (words[words.length - 1] >>> (64 - n) > 0) {
            long[] tmp = new long[words.length + 1];
            System.arraycopy(words, 0, tmp, 0, words.length);
            words = tmp;
        }

        for (int i = words.length - 1; i > 0; i--) {
            words[i] <<= n;
            words[i] |= words[i - 1] >>> (64 - n);
        }
        words[0] <<= n;

        return BitSet.valueOf(words);
    }

    private static BitSet parseIfNum(String s) {
        try {
            int n = Integer.parseInt(s);
            int i = 15;
            BitSet bs = new BitSet(16);

            while (n > 0) {
                if ((n & 1) == 1) bs.set(i);
                n = n >>> 1;
                i--;
            }

            return bs;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int toNum(BitSet bs) {
        int n = 0;

        for (int i = 0; i < 16; i++) {
            n <<= 1;
            if (bs.get(i)) {
                n |= 1;
            }
        }

        return n;
    }
}