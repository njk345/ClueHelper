import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
    static final int MIN_PLAYERS = 2, MAX_PLAYERS = 6;
    static final String[] PERSON_NAMES = {"Colonel Mustard", "Miss Scarlet", "Professor Plum", "Mr. Green",
            "Mrs. White", "Mrs. Peacock", "Mr. Plum"};
    static final String[] WEAPON_NAMES = {"Candlestick", "Knife", "Lead pipe", "Revolver", "Rope", "Wrench"};
    static final String[] ROOM_NAMES = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room",
            "Library", "Lounge", "Hall", "Study"};
    static final String[] CONSOLE_OPTIONS = {"Register Rumor", "Print My Score Card", "Print An Opponent's Score Card",
            "Calculate Accusation Probability", "Suggest Useful Rumor", "Print Score Card to File", "End Game & Print Score Card to File"};
    static final Scanner SCAN = new Scanner(System.in);

    static String p1Name;
    static ArrayList<String> otherNames;

    public static void main(String[] args) {
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

        System.out.print("Enter Your Name: ");
        p1Name = SCAN.nextLine().replaceAll("\\s", "");
        if (p1Name.isEmpty()) {
            p1Name = "You";
        }

        User p1 = new User(p1Name);

        otherNames = new ArrayList<>();
        for (int i = 0; i < numPlayers - 1; i++) {
            System.out.print("Enter Player " + (i + 2) + "'s Name: ");
            String name = SCAN.nextLine().replaceAll("\\s", "").toLowerCase();
            if (name.isEmpty()) {
                name = "Player " + (i + 2);
            }
            otherNames.add(name);
        }

        User[] opponents = new User[otherNames.size()];

        for (int i = 0; i < opponents.length; i++) {
            opponents[i] = new User(otherNames.get(i));
        }

        p1.buildScoreCard();
        for (User p : opponents) {
            p.buildScoreCard();
        }

        gap();

        int[] personIndices = null;
        while (personIndices == null) {
            System.out.println("Select Which Person Cards You Have (Enter Numbers Comma Separated): ");
            printArray(PERSON_NAMES);
            personIndices = scanIntSelections(PERSON_NAMES.length);
        }
        ArrayList<>

        gap();

        int[] weaponIndices = null;
        while (weaponIndices == null) {
            System.out.println("Select Which Weapon Cards You Have (Enter Numbers Comma Separated): ");
            printArray(WEAPON_NAMES);
            weaponIndices = scanIntSelections(WEAPON_NAMES.length);
        }
        for (int i : weaponIndices) {
            p1.addCard(WEAPON_NAMES[i - 1]);
        }

        gap();

        int[] roomIndices = null;
        while (roomIndices == null) {
            System.out.println("Select Which Room Cards You Have (Enter Numbers Comma Separated): ");
            printArray(ROOM_NAMES);
            roomIndices = scanIntSelections(ROOM_NAMES.length);
        }
        for (int i : roomIndices) {
            p1.addCard(ROOM_NAMES[i - 1]);
        }



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
                        String person = null;
                        while (person == null) {
                            System.out.print("Enter rumored person: ");
                            person = SCAN.nextLine();
                            if (!Arrays.asList(PERSON_NAMES).contains(person)) {
                                person = null;
                            }
                        }
                        gap();

                        String weapon = null;
                        while (weapon == null) {
                            System.out.print("Enter rumored weapon: ");
                            weapon = SCAN.nextLine();
                            if (!Arrays.asList(WEAPON_NAMES).contains(weapon)) {
                                weapon = null;
                            }
                        }
                        gap();

                        String room = null;
                        while (room == null) {
                            System.out.print("Enter rumored room: ");
                            room = SCAN.nextLine();
                            if (!Arrays.asList(ROOM_NAMES).contains(room)) {
                                room = null;
                            }
                        }
                        gap();

                        String[] ndpNames = null;
                        while (ndpNames == null) {
                            System.out.print("Who could not disprove rumor (comma-sep, if any)? ");
                            ndpNames = scanNames();
                        }

                        System.out.print("Did someone disprove the rumor (y/n)? ");
                        boolean d = SCAN.nextLine().replaceAll("\\s", "").toLowerCase().equals("y");

                        String[] disproval = null;
                        if (d) {
                            disproval = new String[2];
                            String dpName = null;
                            while (dpName == null) {
                                System.out.print("Who disproved the rumor? ");
                                dpName = SCAN.nextLine().replaceAll("\\s", "").toLowerCase();
                                if (!otherNames.contains(dpName)) {
                                    dpName = null;
                                }
                            }
                            String dpCard = null;
                            while (dpCard == null) {
                                System.out.print("Enter name of card to disprove: ");
                                dpCard = SCAN.nextLine().replaceAll("\\s", "");
                                if (!Arrays.asList(PERSON_NAMES).contains(dpCard) && !Arrays.asList(WEAPON_NAMES).contains(dpCard)
                                        && !Arrays.asList(ROOM_NAMES).contains(dpCard)) {
                                    dpCard = null;
                                }
                            }
                            disproval[0] = dpName;
                            disproval[1] = dpCard;
                        }
                        Rumor rumor = new Rumor(person, weapon, room, ndpNames, disproval);
                        p1.noteRumor(rumor);
                        for (User p : opponents) {
                            p.noteRumor(rumor);
                        }
                        break;
                    case 2: //Check My Score Card
                        p1.printScoreCard();
                        break;
                    case 3: //Check My Idea of An Opponent's Score Card
                        break;
                    case 4: //Eval Accusation Probability
                        break;
                    case 5: //Suggest Useful Rumor
                        break;
                    case 6: //Output Score Card
                        break;
                    case 7: //End Game and Output Score Card
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

    private static int scanInt() throws InputMismatchException {
        int i = SCAN.nextInt();
        return i;
    }

    private static String[] scanNames() {
        String nameStr = SCAN.nextLine().replaceAll("\\s", "");
        String[] names = nameStr.split(",");
        System.out.println(otherNames);
        System.out.println(Arrays.toString(names));
        for (String n : names) {
            if (!otherNames.contains(n.toLowerCase())) {
                System.out.println("Invalid name!");
                return null;
            }
        }
        return names;
    }

    private static int[] scanIntSelections(int numChoices) {
        System.out.print("Selections: ");
        String selections = SCAN.nextLine().replaceAll("\\s", ""); // remove whitespace
        String[] sSplit = selections.split(","); // separate numbers between commas
        int[] indices = new int[sSplit.length];
        for (int i = 0; i < sSplit.length; i++) {
            if (!sSplit[i].matches("^[0-9]+$")) { // invalid if choice contains non-numbers
                System.out.println("\nInvalid Input!\n");
                return null;
            }
            int index = Integer.parseInt(sSplit[i]);
            if (index < 1 || index > numChoices) { // invalid if choice is not in [1, numChoices]
                System.out.println("\nInvalid Input!\n");
                return null;
            }
            indices[i] = index;
        }
        return indices;
    }
}
