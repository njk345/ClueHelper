import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by njk on 7/9/16.
 */
public class ScoreCard {
    /**
     * A hash map from strings holding card names to hash maps from strings holding player names to booleans
     * indicating whether I know that player has that card.
     */
    private HashMap<String, HashMap<String, Boolean>> vals;

    /**
     * A string indicating the player whose scorecard this is.
     */
    private String name;

    public ScoreCard(String[] players, ArrayList<String> hand, String name) {
        this.vals = new HashMap<>();
        this.name = name;
        for (String p : players) {
            vals.put(p, new HashMap<>());
            for (String c : Runner.PERSON_NAMES) {
                vals.get(p).put(c, null);
            }
            for (String c : Runner.WEAPON_NAMES) {
                vals.get(p).put(c, null);
            }
            for (String c : Runner.ROOM_NAMES) {
                vals.get(p).put(c, null);
            }
        }
        for (String c : hand) {
            vals.get(c).put(name, true);
        }
    }

    public void noteRumor(Rumor r) {
        for (String p : r.getNonDisprovals()) {
            vals.get(r.getPerson()).replace(p, false);
            vals.get(r.getWeapon()).replace(p, false);
            vals.get(r.getRoom()).replace(p, false);
        }
        if (r.getDisproval() != null) {
            /* If someone disproved */
            vals.get(r.getDisproval()[1]).put(r.getDisproval()[0], true);
        }
    }

    @Override
    public String toString() {
        String rv = "               " + Runner.yourName + " | ";
        for (String p : Runner.otherNames) {
            rv += p + " | ";
        }
        rv += "\n";

        rv += "People:\n";
        for (String p : Runner.PERSON_NAMES) {
            rv += p + "  ";
            rv += displayBool(vals.get(p).get(Runner.yourName));
            for (String s : Runner.otherNames) {

            }
        }

        rv += "Weapons:\n";
        for (String w : Runner.WEAPON_NAMES) {

        }

        rv += "Rooms:\n";
        for (String r : Runner.ROOM_NAMES) {

        }

        return "";
    }

    private String displayBool(Boolean b) {
        if (b == null) {
            return "?";
        } else if (b) {
            return "Y";
        } else { // if b == false
            return "N";
        }
    }
}
