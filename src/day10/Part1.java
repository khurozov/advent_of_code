package day10;

import java.io.IOException;
import java.util.LinkedList;

public class Part1 {
    public static void main(String[] args) throws IOException {
        Pipe[][] pipes = Util.readInput();
        LinkedList<Coordinate> path = Util.findPath(pipes);
        System.out.println(path.size()/2);
    }
}
