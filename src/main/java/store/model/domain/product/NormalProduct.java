package store.model.domain.product;

import store.constant.ConstantBox;
import store.dto.PurchaseResponse;
import store.dto.SalesData;
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
    public SalesData getSalesData(int promotionCount, int restCount) {
        reduce(restCount);
        return new SalesData(name, restCount, price, promotionCount, restCount);
    }

    @Override
    public String getProductData() {
        return String.join(ConstantBox.SEPARATOR, name, Integer.toString(price), Integer.toString(quantity));
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
