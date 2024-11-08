package store.model.domain.product;

public class PromotionProduct implements Product {

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public PromotionProduct(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public boolean isProductOf(String productName) {
        return false;
    }

    @Override
    public boolean isPurchasable(int requestQuantity) {
        return false;
    }
}
