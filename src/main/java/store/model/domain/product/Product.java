package store.model.domain.product;

import store.dto.PurchaseResponse;
import store.dto.SalesData;

public interface Product {
    boolean isProductOf(String productName);

    int getQuantity();

    PurchaseResponse isPurchasable(int requestQuantity);

    void reduce(int requestQuantity);

    SalesData getSalesData(int promotionCount, int restCount);

    String getProductData();
}
