import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Runner {
  static final int MIN_PLAYERS = 2, MAX_PLAYERS = 6;
  static final String[] PERSON_NAMES = {"Colonel Mustard", "Miss Scarlet", "Professor Plum", "Mr. Green",
      "Mrs. White", "Mrs. Peacock"};
  static final String[] WEAPON_NAMES = {"Candlestick", "Knife", "Lead pipe", "Revolver", "Rope", "Wrench"};
  static final String[] ROOM_NAMES = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room",
      "Library", "Lounge", "Hall", "Study"};
  static final String[] CONSOLE_OPTIONS = {"Register Rumor", "Register Player Loss", "Print My Score Card", "Print An Opponent's Score Card",
      "Calculate Accusation Probability", "Suggest Useful Rumor", "Print Score Card to File", "End Game & Print Score Card to File"};
  static final Scanner SCAN = new Scanner(System.in);
  static HashMap<String, Integer> pSet;
  static HashMap<String, Integer> wSet;
  static HashMap<String, Integer> rSet;
  static String p1Name;
  static ArrayList<String> otherNames;
  static ArrayList<String> allNames;
  static User p1;
  static User[] opponents;
  static User[] allPlayers; // array with p1 at index 0 and opponents after

  public static void main(String[] args) {
        /* Populate pSet, wSet, rSet with pairings between no-whitespace, lower-case versions of PERSON_NAMES, etc.,
           strings and their integer indices in each String[] --> e.g. (colonelmustard, 0), (knife, 1), (mr.green, 3) */
    pSet = new HashMap<>();
    for (int i = 0; i < PERSON_NAMES.length; i++) {
      pSet.put(PERSON_NAMES[i].replaceAll("\\s", "").toLowerCase(), i);
    }
    wSet = new HashMap<>();
    for (int i = 0; i < WEAPON_NAMES.length; i++) {
      wSet.put(WEAPON_NAMES[i].replaceAll("\\s", "").toLowerCase(), i);
    }
    rSet = new HashMap<>();
    for (int i = 0; i < ROOM_NAMES.length; i++) {
      rSet.put(ROOM_NAMES[i].replaceAll("\\s", "").toLowerCase(), i);
    }

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
    List<String> pCards = personIndices.stream().map(i -> clean(PERSON_NAMES[i])).collect(Collectors.toList());
    p1.addCards(p1Name, pCards);
    for (String n : otherNames) {
      p1.denyCards(n, pCards);
    }

    List<Integer> nonPIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
    nonPIndices.removeAll(personIndices);

    List<String> nonPCards = nonPIndices.stream().map(i -> clean(PERSON_NAMES[i])).collect(Collectors.toList());
    p1.denyCards(p1Name, nonPCards);

    gap();

    List<Integer> weaponIndices = null;
    while (weaponIndices == null) {
      System.out.println("Select Which Weapon Cards You Have (Enter Numbers Comma Separated): ");
      printArray(WEAPON_NAMES);
      weaponIndices = scanIntSelections(WEAPON_NAMES.length);
    }
    List<String> wCards = weaponIndices.stream().map(i -> clean(WEAPON_NAMES[i])).collect(Collectors.toList());
    p1.addCards(p1Name, wCards);
    for (String n : otherNames) {
      p1.denyCards(n, wCards);
    }

    List<Integer> nonWIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
    nonWIndices.removeAll(weaponIndices);

    List<String> nonWCards = nonWIndices.stream().map(i -> clean(WEAPON_NAMES[i])).collect(Collectors.toList());
    p1.denyCards(p1Name, nonWCards);

    gap();

    List<Integer> roomIndices = null;
    while (roomIndices == null) {
      System.out.println("Select Which Room Cards You Have (Enter Numbers Comma Separated): ");
      printArray(ROOM_NAMES);
      roomIndices = scanIntSelections(ROOM_NAMES.length);
    }
    List<String> rCards = roomIndices.stream().map(i -> clean(ROOM_NAMES[i])).collect(Collectors.toList());
    p1.addCards(p1Name, rCards);
    for (String n : otherNames) {
      p1.denyCards(n, rCards);
    }

    List<Integer> nonRIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    nonRIndices.removeAll(roomIndices);

    List<String> nonRCards = nonRIndices.stream().map(i -> clean(ROOM_NAMES[i])).collect(Collectors.toList());
    p1.denyCards(p1Name, nonRCards);

    gap();

    boolean gameOver = false;
    while (!gameOver) {
      printConsoleOptions();
      try {
        int choice = scanInt();
        gap();
        SCAN.nextLine();
        switch (choice) {
          case 1: //Register Rumor
            String asker = null;
            while (asker == null) {
              System.out.print("Who started the rumor? ");
              asker = scanStr();
              if (!allNames.contains(asker)) {
                asker = null;
              }
            }

            gap();

            String person = null;
            while (person == null) {
              System.out.print("Enter rumored person: ");
              person = scanStr();
              if (!pSet.containsKey(person)) {
                person = null;
              }
            }
            gap();

            String weapon = null;
            while (weapon == null) {
              System.out.print("Enter rumored weapon: ");
              weapon = scanStr();
              if (!wSet.containsKey(weapon)) {
                weapon = null;
              }
            }
            gap();

            String room = null;
            while (room == null) {
              System.out.print("Enter rumored room: ");
              room = scanStr();
              if (!rSet.containsKey(room)) {
                room = null;
              }
            }
            gap();

            String[] ndpNames = null;
            while (ndpNames == null) {
              System.out.print("Who couldn't disprove the rumor (comma-separated, if anyone)? ");
              ndpNames = scanNames();
            }

            String[] disproval = null;
            if (ndpNames.length < allPlayers.length - 1) { // if not everyone couldn't disprove, someone must have disproved
              disproval = new String[2];
              String dpName = null;
              while (dpName == null) {
                System.out.print("Who disproved the rumor? ");
                dpName = scanStr();
                if (!allNames.contains(dpName)) {
                  dpName = null;
                }
              }
              String dpCard = null;
              if (asker.equals(p1Name)) {
                while (dpCard == null) {
                  System.out.print("Enter name of card to disprove: ");
                  dpCard = scanStr();
                  if (!pSet.containsKey(dpCard) && !wSet.containsKey(dpCard)
                      && !rSet.containsKey(dpCard)) {
                    dpCard = null;
                  }
                }
              }
              disproval[0] = dpName;
              disproval[1] = dpCard;
            }

            Rumor rumor = new Rumor(person, weapon, room, ndpNames, disproval);
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

            List<String> rpCards = personRevealed.stream().map(i -> clean(PERSON_NAMES[i])).collect(Collectors.toList());
            for (User u : allPlayers) {
              /* Have every player note that ru holds rpCards, and that no other player holds them */
              u.addCards(ru, rpCards);
              for (String n : allNames) {
                if (!n.equals(ru)) {
                  u.denyCards(n, rpCards);
                }
              }
            }

            gap();

            List<Integer> weaponRevealed = null;
            while (weaponRevealed == null) {
              System.out.println("Select Which Weapon Cards They Had (Enter Numbers Comma Separated): ");
              printArray(WEAPON_NAMES);
              weaponRevealed = scanIntSelections(WEAPON_NAMES.length);
            }

            List<String> rwCards = weaponRevealed.stream().map(i -> clean(WEAPON_NAMES[i])).collect(Collectors.toList());
            for (User u : allPlayers) {
              u.addCards(ru, rwCards);
              for (String n : allNames) {
                if (!n.equals(ru)) {
                  u.denyCards(n, rwCards);
                }
              }
            }

            gap();

            List<Integer> roomRevealed = null;
            while (roomRevealed == null) {
              System.out.println("Select Which Room Cards They Had (Enter Numbers Comma Separated): ");
              printArray(ROOM_NAMES);
              roomRevealed = scanIntSelections(ROOM_NAMES.length);
            }

            List<String> rrCards = roomRevealed.stream().map(i -> clean(ROOM_NAMES[i])).collect(Collectors.toList());
            for (User u : allPlayers) {
              u.addCards(ru, rrCards);
              for (String n : allNames) {
                if (!n.equals(ru)) {
                  u.denyCards(n, rrCards);
                }
              }
            }

            break;
          case 3: //Check My Score Card
            p1.printScoreCard();
            break;
          case 4: //Check My Idea of An Opponent's Score Card
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
          case 5: //Eval Accusation Probability
            break;
          case 6: //Suggest Useful Rumor
            break;
          case 7: //Output Score Card to File
            p1.printScoreCardToFile();
            break;
          case 8: //End Game and Output Score Card
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

  private static User getUser(String name) {
    for (User r : opponents) {
      if (r.getName().equals(name)) {
        return r;
      }
    }
    return null;
  }

  private static void printConsoleOptions() {
    System.out.println("What Would You Like To Do?");
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

  private static String clean(String s) {
    return s.replaceAll("\\s", "").toLowerCase();
  }

  private static int scanInt() throws InputMismatchException {
    int i = SCAN.nextInt();
    return i;
  }

  private static String[] scanNames() {
    String nameStr = scanStr();
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
