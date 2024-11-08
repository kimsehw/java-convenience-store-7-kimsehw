package store.model.domain.product;

public interface Product {
    boolean isProductOf(String productName);

    int getQuantity();

    boolean isPurchasable(int requestQuantity);
}
