package solutions.y2023;

import extra.Lens;
import solutions.Solution;

import java.io.IOException;
import java.util.*;

public class Day15 extends Solution {
    public static void main(String[] args) throws Exception {
        Day15 day15 = new Day15();
        day15.part1();
        day15.part2();
    }

    @Override
    public void part1() throws Exception {
        Integer sum = input().stream().map(Day15::hash).reduce(0, Integer::sum);
        System.out.println(sum);
    }

    @Override
    public void part2() throws Exception {
        HashMap<Integer, List<Lens>> boxes = new HashMap<>();

        input().forEach(s -> {
            int x = s.indexOf('-');
            if (x > 0) { // remove
                s = s.substring(0, x);
                int h = hash(s);
                List<Lens> list = boxes.get(h);
                if (list != null) {
                    Iterator<Lens> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().getLabel().equals(s)) {
                            iterator.remove();
                        }
                    }
                }
            } else {
                x = s.indexOf('=');
                int focalLength = s.charAt(x + 1) - '0';
                String label = s.substring(0, x);
                boxes.compute(hash(label), (key, oldVal) -> {
                    if (oldVal == null) {
                        oldVal = new LinkedList<>();
                        oldVal.add(new Lens(label, focalLength));
                    } else {
                        Iterator<Lens> iterator = oldVal.iterator();
                        boolean set = false;
                        while (iterator.hasNext()) {
                            Lens lens = iterator.next();
                            if (lens.getLabel().equals(label)) {
                                lens.setFocalLength(focalLength);
                                set = true;
                            }
                        }
                        if (!set) {
                            oldVal.add(new Lens(label, focalLength));
                        }
                    }
                    return oldVal;
                });
            }
        });

        int sum = 0;
        for (Map.Entry<Integer, List<Lens>> box : boxes.entrySet()) {
            int b = box.getKey() + 1;
            List<Lens> lenses = box.getValue();
            for (int i = 0; i < lenses.size(); i++) {
                Lens lens = lenses.get(i);
                sum += b * (i + 1) * lens.getFocalLength();
            }
        }

        System.out.println(sum);
    }

    private List<String> input() throws IOException {
        Scanner in = new Scanner(this.inputFile());
        in.useDelimiter(",");

        List<String> list = new ArrayList<>();

        while (in.hasNext()) {
            list.add(in.next());
        }

        return list;
    }

    private static int hash(String s) {
        int h = 0;
        for (char c : s.toCharArray()) {
            h = (h + c) * 17 % 256;
        }
        return h;
    }
}
