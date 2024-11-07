package store.model.domain;

public class Stock {

    private final String name;
    private int quantity;

    public Stock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public boolean isOf(String productName) {
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
