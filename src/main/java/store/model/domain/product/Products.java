package store.model.domain.product;

import java.util.Arrays;
import java.util.List;
import store.constant.ConstantBox;
import store.model.domain.PurchaseResponse;
import store.model.domain.PurchaseResponseCode;
import store.model.domain.Receipt;
import store.model.domain.SalesData;

public class Products {

    private static final int PROMOTION_INDEX = 0;
    public static final int NORMAL_INDEX = 1;
    public static final int NO_QUANTITY = 0;
    public static final int NO_COUNT = 0;

    private int totalQuantity = NO_QUANTITY;
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
            return new PurchaseResponse(PurchaseResponseCode.OUT_OF_STOCK, NO_COUNT, requestQuantity);
        }
        if (isTherePromotionProduct()) {
            Product product = products.get(PROMOTION_INDEX);
            return product.isPurchasable(requestQuantity);
        }
        return new PurchaseResponse(PurchaseResponseCode.PURCHASE_SUCCESS, NO_COUNT, requestQuantity);
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

    public void updateReceipt(Receipt receipt, String customerRespond, PurchaseResponse purchaseResponse) {
        PurchaseResponseCode purchaseResponseCode = purchaseResponse.getPurchaseResponseCode();
        int promotionCount = purchaseResponse.getPromotionCount();
        int restCount = purchaseResponse.getRestCount();
        updateByEachCase(receipt, customerRespond, purchaseResponseCode, restCount, promotionCount);
    }

    private void updateByEachCase(Receipt receipt, String customerRespond,
                                  PurchaseResponseCode purchaseResponseCode, int restCount, int promotionCount) {
        if (purchaseResponseCode.equals(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE)) {
            updateInPartialUnavailableCase(receipt, customerRespond, restCount, promotionCount);
        }
        if (purchaseResponseCode.equals(PurchaseResponseCode.FREE_PRODUCT_REMIND)) {
            updateInFreeProductRemindCase(receipt, customerRespond, restCount, promotionCount);
        }
        if (purchaseResponseCode.equals(PurchaseResponseCode.PURCHASE_SUCCESS)) {
            updateInSuccessCase(receipt, promotionCount, restCount);
        }
    }

    private void updateInSuccessCase(Receipt receipt, int promotionCount, int restCount) {
        SalesData salesData;
        if (promotionCount != NO_COUNT) {
            salesData = getSalesDataFrom(PROMOTION_INDEX, restCount, promotionCount);
            receipt.addSalesData(salesData);
            return;
        }
        salesData = getSalesDataFrom(NORMAL_INDEX, restCount, promotionCount);
        receipt.addSalesData(salesData);
    }

    private void updateInFreeProductRemindCase(Receipt receipt, String customerRespond, int restCount,
                                               int promotionCount) {
        if (customerRespond.equals(ConstantBox.CUSTOMER_RESPOND_Y)) {
            restCount = NO_COUNT;
            promotionCount++;
        }
        SalesData salesData = getSalesDataFrom(PROMOTION_INDEX, restCount, promotionCount);
        Product product;
        product = products.get(NORMAL_INDEX);
        receipt.addSalesData(salesData);
    }

    private void updateInPartialUnavailableCase(Receipt receipt, String customerRespond, int restCount,
                                                int promotionCount) {
        if (customerRespond.equals(ConstantBox.CUSTOMER_RESPOND_N)) {
            restCount = NO_COUNT;
        }
        SalesData salesData = getSalesDataFrom(PROMOTION_INDEX, restCount, promotionCount);
        reduceRestCountNormalProduct(salesData);
        receipt.addSalesData(salesData);
    }

    private void reduceRestCountNormalProduct(SalesData salesData) {
        Product product = products.get(NORMAL_INDEX);
        int restCount = salesData.getRestCount();
        product.reduce(restCount);
    }

    private SalesData getSalesDataFrom(int index, int restCount, int promotionCount) {
        Product product = products.get(index);
        return product.getSalesData(promotionCount, restCount);
    }
}
