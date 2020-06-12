public class Accusation {
  private String person, weapon, room;
  public Accusation(String person, String weapon, String room) {
    this.person = person;
    this.weapon = weapon;
    this.room = room;
  }
  public String getPerson() {
    return person;
  }
  public String getWeapon() {
    return weapon;
  }
  public String getRoom() {
    return room;
  }
}
