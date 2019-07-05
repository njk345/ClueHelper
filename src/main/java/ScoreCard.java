import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    public ScoreCard() {
        this.vals = new HashMap<>();
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

    public void addCards(String toWhom, List<String> cards) {
        for (String c : cards) {
            vals.get(c).replace(toWhom, true);
        }
    }

    public void denyCards(String fromWhom, List<String> cards) {
        for (String c : cards) {
            vals.get(c).replace(fromWhom, false);
        }
    }

    public void noteRumor(Rumor r) {
        System.out.println(Arrays.toString(r.getNonDisprovals()));
        for (String p : r.getNonDisprovals()) { // note all non-disproving players to not have any of the rumor cards
            denyCards(p, (ArrayList<String>) Arrays.asList(r.getPerson(), r.getWeapon(), r.getRoom()));
        }
        if (r.getDisproval() != null) {
            /* If someone disproved, note them to have their disproving card */
            vals.get(r.getDisproval()[1]).put(r.getDisproval()[0], true);
        }
    }

    @Override
    public String toString() {
        ArrayList<String> headerRow = (ArrayList<String>) Runner.otherNames.clone();
        headerRow.add(0, Runner.p1Name);
        headerRow.add(0, "Cards");

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow(headerRow);
        at.addRule();

        for (int p = 0; p < Runner.PERSON_NAMES.length; p++) {
            ArrayList<String> row = new ArrayList<>();
            row.add(Runner.PERSON_NAMES[p]);
            row.add(displayBool(vals.get(Runner.PERSON_NAMES[p]).get(Runner.p1Name)));
            for (String t : Runner.otherNames) {
                row.add(displayBool(vals.get(Runner.PERSON_NAMES[p]).get(t)));
            }
            at.addRow(row);
            at.addRule();
        }
        for (int w = 0; w < Runner.WEAPON_NAMES.length; w++) {
            ArrayList<String> row = new ArrayList<>();
            row.add(Runner.WEAPON_NAMES[w]);
            row.add(displayBool(vals.get(Runner.WEAPON_NAMES[w]).get(Runner.p1Name)));
            for (String t : Runner.otherNames) {
                row.add(displayBool(vals.get(Runner.WEAPON_NAMES[w]).get(t)));
            }
            at.addRow(row);
            at.addRule();
        }
        for (int r = 0; r < Runner.ROOM_NAMES.length; r++) {
            ArrayList<String> row = new ArrayList<>();
            row.add(Runner.ROOM_NAMES[r]);
            row.add(displayBool(vals.get(Runner.ROOM_NAMES[r]).get(Runner.p1Name)));
            for (String t : Runner.otherNames) {
                row.add(displayBool(vals.get(Runner.ROOM_NAMES[r]).get(t)));
            }
            at.addRow(row);
            at.addRule();
        }
        return at.render();
    }

    private String displayBool(Boolean b) {
        if (b == null) {
            return "?";
        } else if (b) {
            return "YES";
        } else { // if b == false
            return "NO";
        }
    }
}
