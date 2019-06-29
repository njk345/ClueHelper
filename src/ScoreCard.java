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

    public ScoreCard(String name) {
        this.vals = new HashMap<>();
        this.name = name;
        for (String p : Runner.PERSON_NAMES) {
            vals.put(p, new HashMap<>());
            vals.get(p).put(Runner.p1Name, null);
            for (String n : Runner.otherNames) {
                vals.get(p).put(n, null);
            }
        }
        for (String p : Runner.WEAPON_NAMES) {
            vals.put(p, new HashMap<>());
            vals.get(p).put(Runner.p1Name, null);
            for (String n : Runner.otherNames) {
                vals.get(p).put(n, null);
            }
        }
        for (String p : Runner.ROOM_NAMES) {
            vals.put(p, new HashMap<>());
            vals.get(p).put(Runner.p1Name, null);
            for (String n : Runner.otherNames) {
                vals.get(p).put(n, null);
            }
        }
    }

    public void addCards(String toWhom, ArrayList<String> cards) {
        for (String c : cards) {
            vals.get(c).replace(toWhom, true);
        }
    }

    public void noteRumor(Rumor r) {
        for (String p : r.getNonDisprovals()) { // note all non-disproving players to not have any of the rumor cards
            vals.get(r.getPerson()).replace(p, false);
            vals.get(r.getWeapon()).replace(p, false);
            vals.get(r.getRoom()).replace(p, false);
        }
        if (r.getDisproval() != null) {
            /* If someone disproved, note them to have their disproving card */
            vals.get(r.getDisproval()[1]).put(r.getDisproval()[0], true);
        }
    }

    @Override
    public String toString() {
        String rv = "               " + Runner.p1Name + " | ";
        for (String p : Runner.otherNames) {
            rv += p + " | ";
        }
        rv += "\n";

        String[][] names = {Runner.PERSON_NAMES, Runner.WEAPON_NAMES, Runner.ROOM_NAMES};
        String[] nms = {"People:\n", "Weapons:\n", "Rooms:\n"};

        for (int i = 0; i < 3; i++) {
            rv += nms[i];
            for (String s : names[i]) {
                rv += s + "  ";
                rv += displayBool(vals.get(s).get(Runner.p1Name)) + " ";
                for (String t : Runner.otherNames) {
                    rv += displayBool(vals.get(t).get(s)) + " ";
                }
                rv += "\n";
            }
        }

        return rv;
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
