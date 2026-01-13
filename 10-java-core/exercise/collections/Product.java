import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stockQuantity;
    private LocalDate addedDate;
    private Double rating; // nullable

    public Product(String id, String name, String category,
                   double price, int stockQuantity,
                   LocalDate addedDate, Double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.addedDate = addedDate;
        this.rating = rating;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public LocalDate getAddedDate() { return addedDate; }
    public Double getRating() { return rating; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " | " + category + " | $" + price;
    }
}
