package store.model.domain.product;

import store.model.domain.Promotion;

public class PromotionProduct implements Product {

    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public PromotionProduct(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public boolean isProductOf(String productName) {
        return name.equals(productName);
    }

    @Override
    public boolean isPurchasable(int requestQuantity) {
        return false;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

}
