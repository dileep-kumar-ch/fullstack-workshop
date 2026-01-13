package challenges;

import java.util.Objects;

public class Pair<K, V> {

	private final K key;
	private final V value;

	// Constructor
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	// Getters
	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	// Swap method
	public Pair<V, K> swap() {
		return new Pair<>(value, key);
	}

	// Override equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Pair<?, ?> pair = (Pair<?, ?>) obj;
		return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
	}

	// Override hashCode
	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	// Override toString
	@Override
	public String toString() {
		return "Pair(" + key + ", " + value + ")";
	}

	public static void main(String[] args) {
		Pair<String, Integer> pair = new Pair<>("age", 25);

		System.out.println(pair.getKey()); // age
		System.out.println(pair.getValue()); // 25
		System.out.println(pair.swap()); // Pair(25, age)
	}
}
