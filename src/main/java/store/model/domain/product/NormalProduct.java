package store.model.domain.product;

public class NormalProduct implements Product {

    private final String name;
    private final int price;
    private int quantity;

    public NormalProduct(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean isProductOf(String productName) {
        return name.equals(productName);
    }

    @Override
    public boolean isPurchasable(int requestQuantity) {
        if (quantity < requestQuantity) {
            return false;
        }
        quantity -= requestQuantity;
        return true;
    }
}
