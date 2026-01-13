package collections_streams;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaRefactor {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("John", "Alex", "Christopher");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // 1. Sort by length (Lambda)
        Collections.sort(names, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // Method reference alternative
        names.sort(Comparator.comparingInt(String::length));

        // 2. Filter even numbers (Stream + Predicate)
        List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        // 3. Print each element (Method reference)
        names.forEach(System.out::println);

        // 4. Create thread (Lambda)
        Thread t = new Thread(() -> System.out.println("Running"));
        t.start();

        // 5. Transform strings to uppercase (Function + Method reference)
        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
