package store.model.domain.product;

import store.model.domain.PurchaseResponseCode;

public interface Product {
    boolean isProductOf(String productName);

    int getQuantity();

    PurchaseResponseCode isPurchasable(int requestQuantity);

    void reduce(int requestQuantity);
}
