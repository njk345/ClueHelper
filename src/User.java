/**
 * Created by njk on 7/1/16.
 */
import java.util.ArrayList;

public class User {
    private ScoreCard scoreCard;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public void addCards(String toWhom, ArrayList<String> cards) {
        scoreCard.addCards(toWhom, cards);
    }

    public void buildScoreCard() {
        this.scoreCard = new ScoreCard(name);
    }

    public void noteRumor(Rumor r) {
        scoreCard.noteRumor(r);
    }

    public void printScoreCard() {
        System.out.println(scoreCard);
    }
}
