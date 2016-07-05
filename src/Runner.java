import java.util.*;
public class Runner {
    static final String[] PERSON_NAMES = {"Colonel Mustard", "Miss Scarlet", "Professor Plum", "Mr. Green",
    "Mrs. White", "Mrs. Peacock", "Mr. Plum"};
    static final String[] WEAPON_NAMES = {"Candlestick", "Knife", "Lead pipe", "Revolver", "Rope", "Wrench"};
    static final String[] ROOM_NAMES = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room",
    "Library", "Lounge", "Hall", "Study"};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to ClueHelper!");
        System.out.print("How Many Players are Present? ");
        int numPlayers = scan.nextInt();
        scan.nextLine();

        System.out.print("Enter Your Own Name: ");
        User you = new User(scan.nextLine());

        String[] otherNames = new String[numPlayers - 1];
        for (int i = 0; i < otherNames.length; i++) {
            System.out.print("Enter Player " + (i+2) + "'s Name: ");
            otherNames[i] = scan.nextLine();
        }

        gap();

        System.out.println("Select Which Person Cards You Have (Enter Numbers Comma Separated): ");
        printArray(PERSON_NAMES);
        System.out.print("Selections: ");
        String personSelections = scan.nextLine();
        String[] psSplit = personSelections.split(",");
        int[] personIndices = new int[psSplit.length];
        for (int i = 0; i < psSplit.length; i++) {
            personIndices[i] = Integer.parseInt(psSplit[i].trim()) - 1;
        }
        for (int i : personIndices) {
            you.addCard(new Card(Card.Type.PERSON, PERSON_NAMES[i]));
        }

        gap();

        System.out.println("Select Which Weapon Cards You Have (Enter Numbers Comma Separated): ");
        printArray(WEAPON_NAMES);
        System.out.print("Selections: ");
        String weaponSelections = scan.nextLine();
        String[] wsSplit = weaponSelections.split(",");
        int[] weaponIndices = new int[wsSplit.length];
        for (int i = 0; i < wsSplit.length; i++) {
            weaponIndices[i] = Integer.parseInt(wsSplit[i].trim()) - 1;
        }
        for (int i : weaponIndices) {
            you.addCard(new Card(Card.Type.WEAPON, WEAPON_NAMES[i]));
        }

        gap();

        System.out.println("Select Which Room Cards You Have (Enter Numbers Comma Separated): ");
        printArray(ROOM_NAMES);
        System.out.print("Selections: ");
        String roomSelections = scan.nextLine();
        String[] rsSplit = roomSelections.split(",");
        int[] roomIndices = new int[rsSplit.length];
        for (int i = 0; i < rsSplit.length; i++) {
            roomIndices[i] = Integer.parseInt(rsSplit[i].trim()) - 1;
        }
        for (int i : roomIndices) {
            you.addCard(new Card(Card.Type.ROOM, ROOM_NAMES[i]));
        }

        gap();

        
    }
    public static void gap() {
        System.out.println();
    }
    public static void printArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println("" + (i+1) + ": " + arr[i]);
        }
    }
}
