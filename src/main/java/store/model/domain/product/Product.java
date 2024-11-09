package store.model.domain.product;

import store.model.domain.PurchaseResponse;

public interface Product {
    boolean isProductOf(String productName);

    int getQuantity();

    PurchaseResponse isPurchasable(int requestQuantity);

    void reduce(int requestQuantity);
}
