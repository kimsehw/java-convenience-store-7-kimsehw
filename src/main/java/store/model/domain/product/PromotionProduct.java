package store.model.domain.product;

import store.constant.ConstantBox;
import store.dto.PurchaseResponse;
import store.dto.SalesData;
import store.model.domain.Promotion;
import store.model.domain.PurchaseResponseCode;

public class PromotionProduct implements Product {

    public static final int NO_PROMOTION = 0;
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
        return new PurchaseResponse(PurchaseResponseCode.UNAVAILABLE_PROMOTION_DATE,
                requestQuantity, 0);
    }

    public void reduce(int requestQuantity) {
        quantity -= requestQuantity;
    }

    @Override
    public SalesData getSalesData(int promotionCount, int restCount) {
        int promotionQuantity = promotionCount * promotion.getAppliedQuantity();
        int reduceQuantity = promotionQuantity + restCount;
        if (quantity < reduceQuantity) {
            restCount = reduceQuantity - quantity;
            quantity = Integer.parseInt(ConstantBox.NO_QUANTITY);
        }
        if (quantity >= reduceQuantity) {
            reduce(reduceQuantity);
        }
        return new SalesData(name, reduceQuantity, price, promotionCount, restCount);
    }

    public SalesData getSalesDataInIsNotOnPromotionDate(int promotionCount, int restCount) {
        int promotionQuantity = promotionCount;
        int reduceQuantity = promotionQuantity + restCount;
        if (quantity < reduceQuantity) {
            restCount = reduceQuantity - quantity;
            quantity = Integer.parseInt(ConstantBox.NO_QUANTITY);
        }
        if (quantity >= reduceQuantity) {
            reduce(reduceQuantity);
        }
        return new SalesData(name, reduceQuantity, price, NO_PROMOTION, restCount);
    }

    @Override
    public String getProductData() {
        String promotionName = promotion.getPromotionName();
        return String.join(ConstantBox.SEPARATOR, name, Integer.toString(price), Integer.toString(quantity),
                promotionName);
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
