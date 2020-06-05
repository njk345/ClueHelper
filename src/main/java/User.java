/**
 * Created by njk on 7/1/16.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class User {
  private ScoreCard scoreCard;
  private String name;

  public User(String name) {
    this.name = name;
  }

  public void addCard(String toWhom, String card) {
    scoreCard.addCard(toWhom, card);
  }

  public void addCards(String toWhom, Collection<String> cards) {
    scoreCard.addCards(toWhom, cards);
  }

  public void denyCard(String fromWhom, String card) {
    scoreCard.denyCard(fromWhom, card);
  }

  public void denyCards(String fromWhom, Collection<String> cards) {
    scoreCard.denyCards(fromWhom, cards);
  }

  public void buildScoreCard() {
    this.scoreCard = new ScoreCard();
  }

  public ScoreCard getScoreCard() {
    return scoreCard;
  }

  public String getName() {
    return name;
  }

  public void noteRumor(Rumor r) {
    /* Note the rumor and only show the revealed card to the user if they asked or answered the rumor */
    boolean sawDisproval = Runner.clean(name).equals(r.getAsker()) ||
        Runner.clean(name).equals(r.getDisproval()[0]);
    scoreCard.noteRumor(r, sawDisproval);
  }

  public void printScoreCard() {
    System.out.println(scoreCard);
  }

  public void printScoreCardToFile() {
    try {
      File scorecardsDir = new File("scorecards");
      if (!scorecardsDir.isDirectory()) {
        /* scorecards dir doesnt exist yet, so create it */
        scorecardsDir.mkdir();
      }
      BufferedWriter bw = new BufferedWriter(new FileWriter(new File("scorecards/" + name + "ScoreCard.txt")));
      bw.write(scoreCard.toString());
      bw.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }
}
