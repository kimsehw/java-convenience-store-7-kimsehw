package store.model.domain.product;

import java.util.Arrays;
import java.util.List;
import store.constant.ConstantBox;
import store.model.domain.PurchaseResponse;
import store.model.domain.PurchaseResponseCode;

public class Products {

    private static final int PROMOTION_INDEX = 0;
    public static final int NORMAL_INDEX = 1;
    public static final int NO_QUANTITY = 0;

    private int totalQuantity = 0;
    private final List<Product> products = Arrays.asList(null, null);

    public Products(Product product) {
        add(product);
    }

    public void add(Product product) {
        totalQuantity += product.getQuantity();
        set(product);
    }

    private void set(Product product) {
        if (product instanceof PromotionProduct) {
            products.set(PROMOTION_INDEX, product);
        }
        if (product instanceof NormalProduct) {
            products.set(NORMAL_INDEX, product);
        }
    }

    public PurchaseResponse checkQuantity(int requestQuantity) {
        if (totalQuantity < requestQuantity) {
            return new PurchaseResponse(PurchaseResponseCode.OUT_OF_STOCK, 0, requestQuantity);
        }
        if (isTherePromotionProduct()) {
            Product product = products.get(PROMOTION_INDEX);
            return product.isPurchasable(requestQuantity);
        }
        return new PurchaseResponse(PurchaseResponseCode.PURCHASE_SUCCESS, 0, requestQuantity);
    }

    private boolean isTherePromotionProduct() {
        return (products.get(PROMOTION_INDEX) != null);
    }

    private boolean isThereNormalProduct() {
        return (products.get(NORMAL_INDEX) != null);
    }

    public ProductsDisplayData getProductsData() {
        if (isTherePromotionProduct() & isThereNormalProduct()) {
            return getBothProductsDisplayData();
        }
        if (isTherePromotionProduct()) {
            return getPromotionProductDisplayData();
        }
        return getNormalProductsDisplayData();
    }

    private ProductsDisplayData getBothProductsDisplayData() {
        Product product = products.get(PROMOTION_INDEX);
        String promotionProductData = product.getProductData();
        product = products.get(NORMAL_INDEX);
        String normalProductData = product.getProductData();
        return new ProductsDisplayData(promotionProductData, normalProductData);
    }

    private ProductsDisplayData getPromotionProductDisplayData() {
        Product product = products.get(PROMOTION_INDEX);
        String promotionProductData = product.getProductData();
        String normalProductData = convertToNormalProductData(promotionProductData);
        return new ProductsDisplayData(promotionProductData, normalProductData);
    }

    private String convertToNormalProductData(String promotionProductData) {
        List<String> forConvert = List.of(promotionProductData.split(ConstantBox.SEPARATOR));
        return String.join(ConstantBox.SEPARATOR, forConvert.get(0), forConvert.get(1), ConstantBox.NO_QUANTITY);
    }

    private ProductsDisplayData getNormalProductsDisplayData() {
        Product product = products.get(NORMAL_INDEX);
        String normalProductData = product.getProductData();
        return new ProductsDisplayData(null, normalProductData);
    }
}
