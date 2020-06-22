import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Runner {
  static final int MIN_PLAYERS = 2, MAX_PLAYERS = 6;
  static final String[] PERSON_NAMES = {"Colonel Mustard", "Miss Scarlet", "Professor Plum",
      "Mr. Green", "Mrs. White", "Mrs. Peacock"};
  static final String[] WEAPON_NAMES = {"Candlestick", "Knife", "Lead pipe", "Revolver", "Rope",
      "Wrench"};
  static final String[] ROOM_NAMES = {"Kitchen", "Ballroom", "Conservatory", "Dining Room",
      "Billiard Room", "Library", "Lounge", "Hall", "Study"};
  static final String[] CONSOLE_OPTIONS = {"Register Rumor", "Register Player Loss",
      "Print My Score Card", "Print An Opponent's Score Card", "Calculate Accusation Probability",
      "Suggest Useful Rumor", "End Game", "End Game & Print My Score Card to File"};
  static final Scanner SCAN = new Scanner(System.in);
  static Map<String, Integer> pSet;
  static Map<String, Integer> wSet;
  static Map<String, Integer> rSet;
  private static Map<String, CardType> cardTypes;
  static String p1Name;
  static List<String> otherNames;
  static List<String> allNames;
  static User p1;
  static User[] opponents;
  static User[] allPlayers; // array with p1 at index 0 and opponents after
  private static Probability prob;

  public static void main(String[] args) {
    /* ------------ VARIABLE SETUP -------------- */
    /* Populate pSet, wSet, rSet with pairings between no-whitespace, lower-case versions of
    PERSON_NAMES, etc., strings and their integer indices in each String[] -->
    e.g. (colonelmustard, 0), (knife, 1), (mr.green, 3) */
    pSet = new HashMap<>();
    for (int i = 0; i < PERSON_NAMES.length; i++) {
      pSet.put(clean(PERSON_NAMES[i]), i);
    }
    wSet = new HashMap<>();
    for (int i = 0; i < WEAPON_NAMES.length; i++) {
      wSet.put(clean(WEAPON_NAMES[i]), i);
    }
    rSet = new HashMap<>();
    for (int i = 0; i < ROOM_NAMES.length; i++) {
      rSet.put(clean(ROOM_NAMES[i]), i);
    }

    cardTypes = new HashMap<>();
    /* Associate names of cards with their CardType */
    for (String person: pSet.keySet()) {
      cardTypes.put(person, CardType.PERSON);
    }
    for (String weapon: wSet.keySet()) {
      cardTypes.put(weapon, CardType.WEAPON);
    }
    for (String room: rSet.keySet()) {
      cardTypes.put(room, CardType.ROOM);
    }

    /* ------------ REPL SETUP --------------- */

    System.out.println("Welcome to ClueHelper!\n");
    System.out.print("How many players are present (2-6)? ");
    int numPlayers = -1;
    while (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
      try {
        numPlayers = scanInt();
        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
          System.out.print("Invalid number of players: ");
          SCAN.nextLine();
        }
      } catch (InputMismatchException ime) {
        numPlayers = -1;
        System.out.print("Please enter a number: ");
        SCAN.nextLine();
      }
    }
    SCAN.nextLine();

    while (p1Name == null) {
      System.out.print("Enter Your Name: ");
      p1Name = scanStr();
      if (p1Name.isEmpty()) {
        p1Name = null;
      }
    }

    p1 = new User(p1Name);

    otherNames = new ArrayList<>();
    for (int i = 0; i < numPlayers - 1; i++) {
      System.out.print("Enter Player " + (i + 2) + "'s Name: ");
      String name = scanStr();
      if (name.isEmpty()) {
        name = "Player " + (i + 2);
      }
      otherNames.add(name);
    }

    allNames = new ArrayList<>();
    allNames.add(p1Name);
    for (String n : otherNames) {
      allNames.add(n);
    }

    opponents = new User[otherNames.size()];

    for (int i = 0; i < opponents.length; i++) {
      opponents[i] = new User(otherNames.get(i));
    }

    allPlayers = new User[opponents.length + 1];
    allPlayers[0] = p1;
    for (int i = 1; i < allPlayers.length; i++) {
      allPlayers[i] = opponents[i - 1];
    }

    for (User p : allPlayers) {
      p.buildScoreCard();
    }

    gap();

    List<Integer> personIndices = null;
    while (personIndices == null) {
      System.out.println("Select Which Person Cards You Have (Enter Numbers Comma Separated): ");
      printArray(PERSON_NAMES);
      personIndices = scanIntSelections(PERSON_NAMES.length);
    }
    /* Maps indices selected to card names, and makes a list of player card names selected */
    List<String> pCards = personIndices.stream().map(i -> clean(PERSON_NAMES[i]))
        .collect(Collectors.toList());
    /* Assign chosen cards to p1 */
    p1.addCards(p1Name, pCards);

    List<Integer> nonPIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
    nonPIndices.removeAll(personIndices);

    List<String> nonPCards = nonPIndices.stream().map(i -> clean(PERSON_NAMES[i]))
        .collect(Collectors.toList());
    /* Deny p1 the cards they did not assign their self */
    p1.denyCards(p1Name, nonPCards);

    gap();

    /* Repeat the process above for weapon cards and room cards */

    List<Integer> weaponIndices = null;
    while (weaponIndices == null) {
      System.out.println("Select Which Weapon Cards You Have (Enter Numbers Comma Separated): ");
      printArray(WEAPON_NAMES);
      weaponIndices = scanIntSelections(WEAPON_NAMES.length);
    }
    List<String> wCards = weaponIndices.stream().map(i -> clean(WEAPON_NAMES[i]))
        .collect(Collectors.toList());
    p1.addCards(p1Name, wCards);

    List<Integer> nonWIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
    nonWIndices.removeAll(weaponIndices);

    List<String> nonWCards = nonWIndices.stream().map(i -> clean(WEAPON_NAMES[i]))
        .collect(Collectors.toList());
    p1.denyCards(p1Name, nonWCards);

    gap();

    List<Integer> roomIndices = null;
    while (roomIndices == null) {
      System.out.println("Select Which Room Cards You Have (Enter Numbers Comma Separated): ");
      printArray(ROOM_NAMES);
      roomIndices = scanIntSelections(ROOM_NAMES.length);
    }
    List<String> rCards = roomIndices.stream().map(i -> clean(ROOM_NAMES[i]))
        .collect(Collectors.toList());
    p1.addCards(p1Name, rCards);

    List<Integer> nonRIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    nonRIndices.removeAll(roomIndices);

    List<String> nonRCards = nonRIndices.stream().map(i -> clean(ROOM_NAMES[i]))
        .collect(Collectors.toList());
    p1.denyCards(p1Name, nonRCards);

    gap();

    prob = new Probability(p1.getScoreCard());

    /* ----------- GAME REPL ------------- */

    boolean gameOver = false;
    while (!gameOver) {
      printConsoleOptions();
      try {
        int choice = scanInt();
        gap();
        SCAN.nextLine();
        switch (choice) {
          case 1: // Register Rumor
            String asker = null;
            while (asker == null) {
              System.out.print("Who started the rumor? ");
              asker = scanStr();
              if (!allNames.contains(asker)) {
                asker = null;
              }
            }

            String person = null;
            while (person == null) {
              System.out.print("Enter rumored person: ");
              person = scanStr();
              if (!pSet.containsKey(person)) {
                person = null;
              }
            }

            String weapon = null;
            while (weapon == null) {
              System.out.print("Enter rumored weapon: ");
              weapon = scanStr();
              if (!wSet.containsKey(weapon)) {
                weapon = null;
              }
            }

            String room = null;
            while (room == null) {
              System.out.print("Enter rumored room: ");
              room = scanStr();
              if (!rSet.containsKey(room)) {
                room = null;
              }
            }

            String[] ndpNames = null;
            while (ndpNames == null) {
              System.out.print("Who couldn't disprove the rumor (comma-separated, if anyone)? ");
              ndpNames = scanNames();
            }

            String[] disproval = null;
            if (ndpNames.length < allPlayers.length - 1) {
              disproval = new String[2];
              String dpName = null;
              while (dpName == null) {
                System.out.print("Who disproved the rumor? ");
                dpName = scanStr();
                if (!allNames.contains(dpName)) {
                  dpName = null;
                }
              }
              if (dpName.equals(asker)) {
                System.out.println("Asker cannot disprove their own rumor");
                break;
              }
              String dpCard = null;
              if (asker.equals(p1Name) || dpName.equals(p1Name)) {
                /* If p1 started the rumor or disproved the rumor */
                while (dpCard == null) {
                  System.out.print("Enter name of card to disprove: ");
                  dpCard = scanStr();
                  if (!pSet.containsKey(dpCard) && !wSet.containsKey(dpCard)
                      && !rSet.containsKey(dpCard)) {
                    dpCard = null;
                  }
                }

                /* Error checking */
                if (p1.getScoreCard().hasCard(dpName, dpCard) != null &&
                    !p1.getScoreCard().hasCard(dpName, dpCard)) {
                  System.out.println("Someone else has already been assigned " + dpCard);
                  break;
                }
              }
              disproval[0] = dpName;
              disproval[1] = dpCard;
            }

            Rumor rumor = new Rumor(asker, person, weapon, room, ndpNames, disproval);
            for (User p : allPlayers) {
              p.noteRumor(rumor);
            }
            break;
          case 2: // Register Player Loss
            String ru = null;
            while (ru == null) {
              System.out.print("Which player was eliminated? ");
              ru = scanStr();
              if (!allNames.contains(ru)) {
                ru = null;
              }
            }

            List<Integer> personRevealed = null;
            while (personRevealed == null) {
              System.out.println("Select Which Person Cards They Had (Enter Numbers Comma Separated): ");
              printArray(PERSON_NAMES);
              personRevealed = scanIntSelections(PERSON_NAMES.length);
            }

            Set<String> rpCards = personRevealed.stream().map(i -> clean(PERSON_NAMES[i])).collect(Collectors.toSet());
            boolean problem1 = false;
            for (String rpc: rpCards) {
              if (p1.getScoreCard().hasCard(ru, rpc) != null &&
                  !p1.getScoreCard().hasCard(ru, rpc)) {
                System.out.println("Someone else has already been assigned " + rpc);
                problem1 = true;
                break;
              }
            }
            if (problem1) break;

            gap();

            List<Integer> weaponRevealed = null;
            while (weaponRevealed == null) {
              System.out.println("Select Which Weapon Cards They Had (Enter Numbers Comma Separated): ");
              printArray(WEAPON_NAMES);
              weaponRevealed = scanIntSelections(WEAPON_NAMES.length);
            }

            Set<String> rwCards = weaponRevealed.stream().map(i -> clean(WEAPON_NAMES[i])).collect(Collectors.toSet());
            boolean problem2 = false;
            for (String rwc: rwCards) {
              if (p1.getScoreCard().hasCard(ru, rwc) != null &&
                  !p1.getScoreCard().hasCard(ru, rwc)) {
                System.out.println("Someone else has already been assigned " + rwc);
                problem2 = true;
                break;
              }
            }
            if (problem2) break;

            gap();

            List<Integer> roomRevealed = null;
            while (roomRevealed == null) {
              System.out.println("Select Which Room Cards They Had (Enter Numbers Comma Separated): ");
              printArray(ROOM_NAMES);
              roomRevealed = scanIntSelections(ROOM_NAMES.length);
            }

            Set<String> rrCards = roomRevealed.stream().map(i -> clean(ROOM_NAMES[i])).collect(Collectors.toSet());
            boolean problem3 = false;
            for (String rrc: rrCards) {
              if (p1.getScoreCard().hasCard(ru, rrc) != null &&
                  !p1.getScoreCard().hasCard(ru, rrc)) {
                System.out.println("Someone else has already been assigned " + rrc);
                problem3 = true;
                break;
              }
            }
            if (problem3) break;

            List<Set<String>> revealedCardLists = new ArrayList<>();
            revealedCardLists.add(rpCards);
            revealedCardLists.add(rwCards);
            revealedCardLists.add(rrCards);

            /* ASSIGNING CARDS */

            /* For all users, assign the revealed cards of each type to the player who lost.
            Also, note that no other player has each of the revealed cards.
             */
            for (Set<String> revealedCardList : revealedCardLists) {
              for (User u : allPlayers) {
                u.addCards(ru, revealedCardList);
              }
            }
            /* Deny every card not revealed to the losing player */
            for (int i = 0; i < PERSON_NAMES.length; i++) {
              String pName = clean(PERSON_NAMES[i]);
              if (!rpCards.contains(pName)) {
                for (User u : allPlayers) {
                  u.denyCard(ru, pName);
                }
              }
            }
            for (int i = 0; i < WEAPON_NAMES.length; i++) {
              String wName = clean(WEAPON_NAMES[i]);
              if (!rwCards.contains(wName)) {
                for (User u : allPlayers) {
                  u.denyCard(ru, wName);
                }
              }
            }
            for (int i = 0; i < ROOM_NAMES.length; i++) {
              String rName = clean(ROOM_NAMES[i]);
              if (!rrCards.contains(rName)) {
                for (User u : allPlayers) {
                  u.denyCard(ru, rName);
                }
              }
            }
            break;
          case 3: // Check My Score Card
            p1.printScoreCard();
            break;
          case 4: // Check My Idea of An Opponent's Score Card
            String opName = null;
            while (opName == null) {
              System.out.print("Enter opponent name: ");
              opName = scanStr();
              if (!otherNames.contains(opName)) {
                opName = null;
              }
            }
            getUser(opName).printScoreCard();
            break;
          case 5: // Eval Accusation Probability
            System.out.println(prob.getNumberSolutions());
            break;
          case 6: // Suggest Useful Rumor
            break;
          case 7: // End Game
            gameOver = true;
            break;
          case 8: // End Game and Output Score Card
            gameOver = true;
            p1.printScoreCardToFile();
            break;
          default:
            System.out.println("\nInvalid Choice!");
        }
      } catch (InputMismatchException ime) {
        System.out.println("\nInvalid Input!");
        SCAN.nextLine();
      }
      gap();
    }
  }

  public static CardType getCardType(String card) {
    return cardTypes.get(card);
  }
  private static User getUser(String name) {
    for (User r : opponents) {
      if (r.getName().equals(name)) {
        return r;
      }
    }
    return null;
  }

  private static void printConsoleOptions() {
    System.out.println("What Would You Like To Do (Choose Number)?");
    for (int i = 0; i < CONSOLE_OPTIONS.length; i++) {
      System.out.println("" + (i + 1) + ": " + CONSOLE_OPTIONS[i]);
    }
    System.out.print("Enter Choice: ");
  }

  private static void gap() {
    System.out.println();
  }

  private static void printArray(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.println("" + (i + 1) + ": " + arr[i]);
    }
  }

  private static String scanStr() {
    return clean(SCAN.nextLine());
  }

  public static String clean(String s) {
    return s.replaceAll("\\s", "").toLowerCase();
  }

  private static int scanInt() throws InputMismatchException {
    int i = SCAN.nextInt();
    return i;
  }

  private static String[] scanNames() {
    String nameStr = scanStr();
    if (nameStr.isBlank()) {
      return new String[]{};
    }
    String[] names = nameStr.split(",");
    for (int i = 0; i < names.length; i++) {
      if (!allNames.contains(names[i])) {
        System.out.println("Invalid name!");
        return null;
      }
    }
    return names;
  }

  private static ArrayList<Integer> scanIntSelections(int numChoices) {
    System.out.print("Selections: ");
    String selections = scanStr(); // remove whitespace (and make lower-case)
    String[] sSplit = selections.split(","); // separate numbers between commas
    ArrayList<Integer> indices = new ArrayList<>(sSplit.length);
    for (int i = 0; i < sSplit.length; i++) {
      if (!sSplit[i].matches("^[0-9]+$")) { // invalid if choice contains non-numbers
        System.out.println("\nInvalid Input!\n");
        return null;
      }
      int index = Integer.parseInt(sSplit[i]) - 1;
      if (index < 0 || index > numChoices - 1) { // invalid if choice is not in [0, numChoices - 1]
        System.out.println("\nInvalid Input!\n");
        return null;
      }
      indices.add(index);
    }
    return indices;
  }
}
