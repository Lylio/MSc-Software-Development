import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Lyle Christine
 * Student number 0912407c
 */

public class WordProcessor {

    private static <E> String displaySet(Set<E> inputSet){

        StringBuilder builder = new StringBuilder();
        int lineElements = 0;

        for(E element : inputSet) {
            if(lineElements < 4) {
                builder.append(element.toString() + ", ");
                lineElements++;
            } else {
                builder.append(element.toString() + ", ");
                builder.append("\n");
                lineElements = 0;
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {

        Scanner in = null;
        Set<String> wordSet = new TreeSet<>();
        Set<CountedElement<String>> countedWordSet = new TreeSet<>();

        for(String file : args) {
            try {
                in = new Scanner(new File(file));
                while(in.hasNext()) {
                    String w = in.next();
                    if(!wordSet.contains(w)) {
                        wordSet.add(w);
                        countedWordSet.add(new CountedElement<>(w,1));
                    } else {
                        for(CountedElement<String> element : countedWordSet) {
                            if(element.getElement().equals(w)) {
                                int currentCount = element.getCount();
                                element.setCount(currentCount + 1);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println(displaySet(countedWordSet));
    }
}
