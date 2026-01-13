package collections_streams.inventory;

import java.util.List;
import java.util.Queue;

public class InventoryTest {

  public static void main(String[] args) {

    Inventory inventory = new Inventory();

    inventory.addProduct(new Product("P001", "Laptop", "Electronics", 999.99, 50));
    inventory.addProduct(new Product("P002", "Mouse", "Electronics", 29.99, 5));
    inventory.addProduct(new Product("P003", "Desk", "Furniture", 299.99, 15));

    // Get by category
    List<Product> electronics = inventory.getByCategory("Electronics");

    // Get sorted by price
    List<Product> sorted = inventory.getAllSortedByPrice();

    // Get low stock alerts
    Queue<Product> lowStock = inventory.getLowStockAlerts();
    while (!lowStock.isEmpty()) {
      Product p = lowStock.poll();
      System.out.println("Low stock: " + p.getName());
    }
  }
}
