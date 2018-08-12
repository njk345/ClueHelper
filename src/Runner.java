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
    static String yourName;
    static String[] otherNames;

    public static void main(String[] args) {
        System.out.println("Welcome to ClueHelper!");
        System.out.print("How Many Players are Present (2-6)? ");
        int numPlayers = -1;
        while (numPlayers == -1 || numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            try {
                numPlayers = scanInt();
            } catch (InputMismatchException ime) {
                numPlayers = -1;
                System.out.print("Invalid Input - Try Again: ");
                SCAN.nextLine();
            }
            if (numPlayers != -1 && (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS)) {
                System.out.print("Invalid Number of Players — Try Again: ");
                SCAN.nextLine();
            }
        }
        SCAN.nextLine();

        System.out.print("Enter Your Own Name: ");
        yourName = SCAN.nextLine();
        User you = new User(yourName);

        otherNames = new String[numPlayers - 1];
        for (int i = 0; i < otherNames.length; i++) {
            System.out.print("Enter Player " + (i + 2) + "'s Name: ");
            otherNames[i] = SCAN.nextLine();
        }

        User[] opponents = new User[otherNames.length];
        for (int i = 0; i < opponents.length; i++) {
            opponents[i] = new User(otherNames[i]);
        }

        gap();

        System.out.println("Select Which Person Cards You Have (Enter Numbers Comma Separated): ");
        printArray(PERSON_NAMES);
        int[] personIndices = scanIntSelections();
        for (int i : personIndices) {
            you.addCard(PERSON_NAMES[i - 1]);
        }

        gap();

        System.out.println("Select Which Weapon Cards You Have (Enter Numbers Comma Separated): ");
        printArray(WEAPON_NAMES);
        int[] weaponIndices = scanIntSelections();
        for (int i : weaponIndices) {
            you.addCard(WEAPON_NAMES[i - 1]);
        }

        gap();

        System.out.println("Select Which Room Cards You Have (Enter Numbers Comma Separated): ");
        printArray(ROOM_NAMES);
        int[] roomIndices = scanIntSelections();
        for (int i : roomIndices) {
            you.addCard(ROOM_NAMES[i - 1]);
        }

        gap();

        boolean gameOver = false;
        while (!gameOver) {
            printConsoleOptions();
            try {
                int choice = scanInt();
                switch (choice) {
                    case 1: //Register Rumor
                        break;
                    case 2: //Check My Score Card
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

    private static int[] scanIntSelections() {
        System.out.print("Selections: ");
        String selections = SCAN.nextLine();
        String[] sSplit = selections.split(",");
        int[] indices = new int[sSplit.length];
        for (int i = 0; i < sSplit.length; i++) {
            indices[i] = Integer.parseInt(sSplit[i].trim());
        }
        return indices;
    }
}
