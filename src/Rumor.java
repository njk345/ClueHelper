import java.util.ArrayList;

/**
 * Created by njk on 7/9/16.
 */
public class Rumor {
    private String person;
    private String weapon;
    private String room;
    private ArrayList<String> nonDisprovals;
    private String[] disproval;

    public Rumor(String person, String weapon, String room, ArrayList<String> nonDisprovals, String[] disproval) {
        this.person = person;
        this.weapon = weapon;
        this.room = room;
        this.nonDisprovals = nonDisprovals;
        this.disproval = disproval;
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

    public ArrayList<String> getNonDisprovals() {
        return nonDisprovals;
    }

    public String[] getDisproval() {
        return disproval;
    }
}
