import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        ArrayList<Integer> blah = new ArrayList<>();
        blah.add(2);
        blah.add(3);
        test.removeAll(blah);
        System.out.println(test);
    }
}
