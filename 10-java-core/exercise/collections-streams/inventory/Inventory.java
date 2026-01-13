package collections_streams.inventory;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory {

    private List<Product> products = new ArrayList<>();
    private Set<String> categories = new HashSet<>();
    private Map<String, Product> productMap = new HashMap<>();
    private Queue<Product> lowStockQueue = new LinkedList<>();

    // Add product
    public void addProduct(Product product) {
        products.add(product);
        categories.add(product.getCategory());
        productMap.put(product.getId(), product);

        if (product.getQuantity() < 10) {
            lowStockQueue.add(product);
        }
    }

    // Update product
    public void updateProduct(Product product) {
        deleteProduct(product.getId());
        addProduct(product);
    }

    // Delete product
    public void deleteProduct(String id) {
        Product p = productMap.remove(id);
        if (p != null) {
            products.remove(p);
            lowStockQueue.remove(p);
        }
    }

    // Find by id
    public Product getById(String id) {
        return productMap.get(id);
    }

    // Find by category
    public List<Product> getByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Sorted by price
    public List<Product> getAllSortedByPrice() {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    // Low stock alerts
    public Queue<Product> getLowStockAlerts() {
        return new LinkedList<>(lowStockQueue);
    }
}
