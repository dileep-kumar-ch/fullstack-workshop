package challenges;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionAnalyzer {

	// Group words by their length
	public static Map<Integer, List<String>> groupByLength(List<String> words) {
		return words.stream().collect(Collectors.groupingBy(String::length));
	}

	// Count frequency of each character
	public static Map<Character, Long> charFrequency(List<String> words) {
		return words.stream().flatMap(word -> word.chars().mapToObj(c -> (char) c))
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
	}

	// Test
	public static void main(String[] args) {
		System.out.println(groupByLength(Arrays.asList("hi", "bye", "hello", "ok")));
		System.out.println(charFrequency(Arrays.asList("aab", "bc")));
	}

}
