import java.util.Iterator;

/**
 * @author Lyle Christine
 * Student number 0912407c
 */

public class WordProcessor2 {

    public static void main(String[] args) {

        //Create 2 BST bags
        BSTBag<String> bag = new BSTBag<String>();
        BSTBag<String> bag2 = new BSTBag<>();

        //Add items to bag
        bag.add("Apple");
        bag.add("Orange");
        bag.add("Pear");
        bag.add("Banana");

        //Query if bag is empty
        System.out.println(bag.isEmpty()); //false

        //Query size of bag
        System.out.println(bag.size()); //4

        //Query contains
        System.out.println(bag.contains("Orange")); //true

        //Add items to bag2
        bag2.add("Apple");
        bag2.add("Orange");
        bag2.add("Pear");
        bag2.add("Banana");

        //Test comparison of bags
        System.out.println(bag.equals(bag2)); //true

        //Clear bag2 and query contents
        bag2.clear();
        System.out.println(bag2.contains("Apple")); //false
        System.out.println(bag2.size()); //0

        //Remove item from bag and query size
        bag.remove("Pear");
        System.out.println(bag.size()); //3
        System.out.println(bag.contains("Pear")); //false

        //Create and test iterator
        Iterator iterator = bag.iterator();

        while(iterator.hasNext()) {
            Object obj = iterator.next();
            System.out.print(obj + ", "); //Apple, Banana, Orange,
        }
    }
}
