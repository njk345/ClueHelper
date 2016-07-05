/**
 * Created by njk on 7/1/16.
 */
import java.util.*;
public class User {
    private ArrayList<Card> hand;
    private String name;
    public User(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }
    public void addCard(Card c) {
        this.hand.add(c);
    }
    public boolean hasCard(String cardName) {
        for (Card c : hand) {
            if (c.getName().toLowerCase().equals(cardName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public String getName() {
        return this.name;
    }
    public ArrayList<Card> getHand() {
        return this.hand;
    }
}
