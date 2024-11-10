package store.model.domain.product;

import store.model.domain.PurchaseResponse;
import store.model.domain.SalesData;

public interface Product {
    boolean isProductOf(String productName);

    int getQuantity();

    PurchaseResponse isPurchasable(int requestQuantity);

    void reduce(int requestQuantity);

    SalesData getSalesData(int promotionCount, int restCount);

    String getProductData();
}
