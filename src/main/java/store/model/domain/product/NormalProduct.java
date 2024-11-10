package store.model.domain.product;

import store.model.domain.PurchaseResponse;
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
    public PurchaseResponse isPurchasable(int requestQuantity) {
        if (quantity < requestQuantity) {
            return new PurchaseResponse(PurchaseResponseCode.OUT_OF_STOCK, 0, requestQuantity);
        }
        return new PurchaseResponse(PurchaseResponseCode.PURCHASE_SUCCESS, 0, requestQuantity);
    }

    @Override
    public void reduce(int requestQuantity) {
        quantity -= requestQuantity;
    }

    @Override
    public String getProductData() {
        return String.join(",", name, Integer.toString(price), Integer.toString(quantity));
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
