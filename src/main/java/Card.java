/**
 * Created by njk on 7/1/16.
 */
public class Card {
  private String name;

  public Card(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Card) {
      return ((Card) o).getName().equals(this.getName());
    }
    return false;
  }
}
