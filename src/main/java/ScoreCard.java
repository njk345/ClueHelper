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

  /**
   * A constructor for a scorecard. It initializes the vals map and adds in the names of all cards,
   * linked to a map with all usernames, linked to null values.
   */
  public ScoreCard() {
    this.vals = new HashMap<>();
    /* Make the keys for vals the no-whitespace, lower-case card names that are the keys in pSet, wSet, rSet */
    for (String p : Runner.pSet.keySet()) {
      vals.put(p, new HashMap<>());
      vals.get(p).put(Runner.p1Name, null);
      for (String n : Runner.otherNames) {
        vals.get(p).put(n, null);
      }
    }
    for (String p : Runner.wSet.keySet()) {
      vals.put(p, new HashMap<>());
      vals.get(p).put(Runner.p1Name, null);
      for (String n : Runner.otherNames) {
        vals.get(p).put(n, null);
      }
    }
    for (String p : Runner.rSet.keySet()) {
      vals.put(p, new HashMap<>());
      vals.get(p).put(Runner.p1Name, null);
      for (String n : Runner.otherNames) {
        vals.get(p).put(n, null);
      }
    }
  }

  /**
   * A method to assign a list of cards to a user.
   * @param toWhom A username
   * @param cards A list of string card names
   */
  public void addCards(String toWhom, List<String> cards) {
    for (String c : cards) {
      vals.get(c).replace(toWhom, true);
    }
  }

  /**
   * A method to confirm a user does not have a list of cards.
   * @param fromWhom A username
   * @param cards A list of string card names
   */
  public void denyCards(String fromWhom, List<String> cards) {
    for (String c : cards) {
      vals.get(c).replace(fromWhom, false);
    }
  }

  /**
   * Notes the contents of a rumor. Makes all players who couldn't disprove not have the cards and
   * assigns the disproval card, if there is one, to the disprover.
   * @param r A rumor
   */
  public void noteRumor(Rumor r) {
    for (String p : r.getNonDisprovals()) { // note all non-disproving players to not have any of the rumor cards
      denyCards(p, Arrays.asList(r.getPerson(), r.getWeapon(), r.getRoom()));
    }
    if (r.getDisproval() != null && r.getDisproval()[1] != null) {
      /* If someone disproved, note them to have their disproving card */
      vals.get(r.getDisproval()[1]).put(r.getDisproval()[0], true);
    }
  }

  /**
   * Prints the score card's representation.
   * @return
   */
  @Override
  public String toString() {
    ArrayList<String> headerRow = new ArrayList<>();
    headerRow.add("Cards");
    for (String n : Runner.allNames) {
      headerRow.add(cap(n));
    }

    AsciiTable at = new AsciiTable();
    at.addRule();
    at.addRow(headerRow);
    at.addRule();

    for (String p : Runner.pSet.keySet()) {
      ArrayList<String> row = new ArrayList<>();
      row.add(Runner.PERSON_NAMES[Runner.pSet.get(p)]); // add the nice-looking version from PERSON_NAMES
      row.add(displayBool(vals.get(p).get(Runner.p1Name)));
      for (String t : Runner.otherNames) {
        row.add(displayBool(vals.get(p).get(t)));
      }
      at.addRow(row);
      at.addRule();
    }
    at.addRule();
    for (String w : Runner.wSet.keySet()) {
      ArrayList<String> row = new ArrayList<>();
      row.add(Runner.WEAPON_NAMES[Runner.wSet.get(w)]); // add the nice-looking version from WEAPON_NAMES
      row.add(displayBool(vals.get(w).get(Runner.p1Name)));
      for (String t : Runner.otherNames) {
        row.add(displayBool(vals.get(w).get(t)));
      }
      at.addRow(row);
      at.addRule();
    }
    at.addRule();
    for (String r : Runner.rSet.keySet()) {
      ArrayList<String> row = new ArrayList<>();
      row.add(Runner.ROOM_NAMES[Runner.rSet.get(r)]); // add the nice-looking version from ROOM_NAMES
      row.add(displayBool(vals.get(r).get(Runner.p1Name)));
      for (String t : Runner.otherNames) {
        row.add(displayBool(vals.get(r).get(t)));
      }
      at.addRow(row);
      at.addRule();
    }
    return at.render();
  }

  /**
   * Capitalizes the first letter in a string.
   * @param s A string
   * @return s, with the first letter capitalized
   */
  private String cap(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  /**
   * Given a boolean object, returns "?" if it's null, "YES" if true, "NO" if false.
   * @param b A Boolean
   * @return b's string form
   */
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
