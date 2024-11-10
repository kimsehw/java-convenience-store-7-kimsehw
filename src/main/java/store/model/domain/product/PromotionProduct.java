package store.model.domain.product;

import store.model.domain.Promotion;
import store.model.domain.PurchaseResponse;
import store.model.domain.PurchaseResponseCode;

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
    public PurchaseResponse isPurchasable(int requestQuantity) {
        if (promotion.isOnPromotion()) {
            return promotion.discount(requestQuantity, quantity);
        }
        return new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, 0, requestQuantity);
    }

    @Override
    public void reduce(int requestQuantity) {
        quantity -= requestQuantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

}
