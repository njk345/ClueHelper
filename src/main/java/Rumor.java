/**
 * Created by njk on 7/9/16.
 */
public class Rumor {
    private String person;
    private String weapon;
    private String room;
    /* array of usernames of players who could not disprove the rumor */
    private String[] nonDisprovals;
    /* disproval[0] is the username of someone who could disprove the rumor and disproval[1] is the card they
       disproved with. If no one could disprove, disproval is null. */
    private String[] disproval;

    public Rumor(String person, String weapon, String room, String[] nonDisprovals, String[] disproval) {
        this.person = person;
        this.weapon = weapon;
        this.room = room;
        this.nonDisprovals = nonDisprovals;
        this.disproval = disproval;
    }

    /* Getters and Setters */
    public String getPerson() {
        return person;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getRoom() {
        return room;
    }

    public String[] getNonDisprovals() {
        return nonDisprovals;
    }

    public String[] getDisproval() {
        return disproval;
    }
}
