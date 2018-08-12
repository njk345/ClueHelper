/**
 * Created by njk on 7/1/16.
 */

import java.util.ArrayList;

public class User {
    private ArrayList<String> hand;
    private ScoreCard scoreCard;
    private String name;

    public User(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(String card) {
        this.hand.add(card);
    }

    public void buildScoreCard() {
        this.scoreCard = new ScoreCard(Runner.otherNames, hand, name);
    }

    public void noteRumor(Rumor r) {
        scoreCard.noteRumor(r);
    }
}
