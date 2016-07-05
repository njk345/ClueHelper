/**
 * Created by njk on 7/1/16.
 */
import java.util.*;
public class Card {
    private String name;
    private Type type;
    public enum Type {
        WEAPON, PERSON, ROOM
    }
    public Card(Type type, String name) {
        this.name = name;
        this.type = type;
    }
    public Type getType() {
        return this.type;
    }
    public String getName() {
        return this.name;
    }
    public String toString() {
        return getName();
    }
    public boolean equals(Object o) {
        if (o instanceof Card) {
            return ((Card)o).getName().equals(this.getName());
        }
        return false;
    }
}
