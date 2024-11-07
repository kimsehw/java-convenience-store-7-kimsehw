package store.model.domain;

public class Product {

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean isProductOf(String productName) {
        if (name.equals(productName)) {
            return true;
        }
        return false;
    }

    public boolean isPurchasable(int requestQuantity) {
        if (quantity < requestQuantity) {
            return false;
        }
        quantity -= requestQuantity;
        return true;
    }
}
