/**
 * Created by njk on 7/1/16.
 */

import java.util.List;

public class User {
    private ScoreCard scoreCard;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public void addCards(String toWhom, List<String> cards) {
        scoreCard.addCards(toWhom, cards);
    }

    public void denyCards(String fromWhom, List<String> cards) {
        scoreCard.denyCards(fromWhom, cards);
    }

    public void buildScoreCard() {
        this.scoreCard = new ScoreCard();
    }

    public String getName() {
        return name;
    }

    public void noteRumor(Rumor r) {
        scoreCard.noteRumor(r);
    }

    public void printScoreCard() {
        System.out.println(scoreCard);
    }
}
