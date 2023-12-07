package day7;

class CardHand {
    String cards;
    long bid;
    CardScore score;

    CardHand(String cards, long bid, CardScore score) {
        this.cards = cards;
        this.bid = bid;
        this.score = score;
    }
}