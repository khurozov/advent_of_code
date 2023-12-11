package day11;

record Galaxy(int i, int j) {
    long distance(Galaxy g, boolean[] hasGRow, boolean[] hasGCol, int expand) {
        long d = 0;
        int max = Math.max(this.i, g.i);
        int min = Math.min(this.i, g.i);
        for (int x = min; x < max; x++) {
            d += hasGRow[x] ? 1 : expand;
        }
        max = Math.max(this.j, g.j);
        min = Math.min(this.j, g.j);
        for (int x = min; x < max; x++) {
            d += hasGCol[x] ? 1 : expand;
        }
        return d;
    }
}
