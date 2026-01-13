package challenges;

import java.util.Optional;

public class SafeExecutor {
	@FunctionalInterface
	interface ThrowingSupplier<T> {
		T get() throws Exception;
	}

	public static <T> Optional<T> safeExecute(ThrowingSupplier<T> supplier) {
		try {
			return Optional.ofNullable(supplier.get());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	// Test
	public static void main(String[] args) {
		System.out.println(safeExecute(() -> Integer.parseInt("123")));
		System.out.println(safeExecute(() -> Integer.parseInt("abc")));
	}

}
