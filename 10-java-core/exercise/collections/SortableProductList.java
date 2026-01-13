import java.lang.reflect.Field;
import java.util.*;

public class SortableProductList {

    private List<Product> products;

    public SortableProductList(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public SortableProductList sortBy(Comparator<Product> comparator) {
        products.sort(comparator);
        return this;
    }

    public SortableProductList thenSortBy(Comparator<Product> comparator) {
        products.sort(comparator);
        return this;
    }

    // Reflection-based sorting
    public SortableProductList sortByField(String fieldName, boolean ascending) {
        products.sort((p1, p2) -> {
            try {
                Field field = Product.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Comparable v1 = (Comparable) field.get(p1);
                Comparable v2 = (Comparable) field.get(p2);
                int result = Comparator.nullsLast(Comparator.naturalOrder()).compare(v1, v2);
                return ascending ? result : -result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }

    public List<Product> top(int n) {
        return products.subList(0, Math.min(n, products.size()));
    }

    public List<Product> getPage(int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());
        return products.subList(start, end);
    }

    public List<Product> getProducts() {
        return products;
    }
}
