import java.util.*;
import java.util.function.Function;

public class ProductComparators {

    public static Comparator<Product> byPrice() {
        return Comparator.comparingDouble(Product::getPrice);
    }

    public static Comparator<Product> byPriceDescending() {
        return Comparator.comparingDouble(Product::getPrice).reversed();
    }

    public static Comparator<Product> byName() {
        return Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Product> byCategoryThenName() {
        return Comparator.comparing(Product::getCategory)
                         .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Product> byRatingThenPrice() {
        return Comparator.comparing(
                    Product::getRating,
                    Comparator.nullsLast(Comparator.naturalOrder())
               ).thenComparing(Product::getPrice);
    }

    public static Comparator<Product> byLowStockFirst() {
        return Comparator.comparing(
                    p -> p.getStockQuantity() >= 10
               ).thenComparing(Product::getName);
    }

    public static Comparator<Product> byNewest() {
        return Comparator.comparing(Product::getAddedDate).reversed();
    }

    // Dynamic multi-field sort
    public static Comparator<Product> multiSort(List<String> fields,
                                                 List<Boolean> ascending) {

        Map<String, Function<Product, Comparable>> fieldMap = Map.of(
            "name", Product::getName,
            "category", Product::getCategory,
            "price", p -> p.getPrice(),
            "rating", p -> p.getRating(),
            "stockQuantity", p -> p.getStockQuantity(),
            "addedDate", Product::getAddedDate
        );

        Comparator<Product> comparator = (a, b) -> 0;

        for (int i = 0; i < fields.size(); i++) {
            Function<Product, Comparable> key = fieldMap.get(fields.get(i));
            Comparator<Product> next =
                Comparator.comparing(key, Comparator.nullsLast(Comparator.naturalOrder()));

            if (!ascending.get(i)) {
                next = next.reversed();
            }
            comparator = comparator.thenComparing(next);
        }
        return comparator;
    }
}
