public class Probability {
  private ScoreCard sc;
  public Probability(ScoreCard sc) {
    this.sc = sc;
  }
  public int getNumberSolutions() {
    return sc.getPersonsLeft().size() * sc.getWeaponsLeft().size() * sc.getRoomsLeft().size();
  }
}
