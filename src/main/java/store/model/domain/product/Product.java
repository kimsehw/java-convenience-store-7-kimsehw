package store.model.domain.product;

public interface Product {
    boolean isProductOf(String productName);

    boolean isPurchasable(int requestQuantity);
}
