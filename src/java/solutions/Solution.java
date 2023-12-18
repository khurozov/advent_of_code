package solutions;

import java.net.URL;
import java.nio.file.Path;

public abstract class Solution {
    protected Path inputFile() {
        Class<? extends Solution> clazz = this.getClass();
        URL resource = clazz.getResource(clazz.getSimpleName()+".txt");

        assert resource != null;

        return Path.of(resource.getPath());
    }
    
    abstract public void  part1() throws Exception;
    abstract public void  part2() throws Exception;
}
