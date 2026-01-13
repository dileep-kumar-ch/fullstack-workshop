package challenges;

import java.util.List;
import java.util.OptionalDouble;

public class StreamProcessor {
	public static OptionalDouble averageOfEvens(List<Integer> numbers) {
		return numbers.stream().filter(n -> n % 2 == 0) // keep even numbers
				.mapToInt(Integer::intValue).average(); // returns OptionalDouble
	}

	// Test
	public static void main(String[] args) {
		System.out.println(averageOfEvens(List.of(1, 2, 3, 4, 5, 6)));
		System.out.println(averageOfEvens(List.of(1, 3, 5)));
	}

}
