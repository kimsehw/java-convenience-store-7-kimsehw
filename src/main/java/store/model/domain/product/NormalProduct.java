package store.model.domain.product;

import store.model.domain.PurchaseResponseCode;

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
    public PurchaseResponseCode isPurchasable(int requestQuantity) {
        if (quantity < requestQuantity) {
            return PurchaseResponseCode.OUT_OF_STOCK;
        }
        return PurchaseResponseCode.PURCHASE_SUCCESS;
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
