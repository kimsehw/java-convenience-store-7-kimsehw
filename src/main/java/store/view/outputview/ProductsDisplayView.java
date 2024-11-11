package store.view.outputview;

import java.util.List;
import store.constant.ConstantBox;
import store.model.domain.product.ProductsDisplayData;

public class ProductsDisplayView implements OutputView {

    private static final String PROMOTION_PRODUCT_DISPLAY_FORMAT = "- %s %,d원 %,d개 %s";
    private static final String NOT_EXIST_PROMOTION_PRODUCT_DISPLAY_FORMAT = "- %s %,d원 재고 없음 %s";
    private static final String NORMAL_PRODUCT_DISPLAY_FORMAT = "- %s %,d원 %,d개";
    private static final String NOT_EXIST_NORMAL_PRODUCT_DISPLAY_FORMAT = "- %s %,d원 재고 없음";
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_PRODUCTS_MESSAGE = "현재 보유하고 있는 상품입니다.";

    private List<ProductsDisplayData> displayDatas;

    public ProductsDisplayView(List<ProductsDisplayData> displayDatas) {
        this.displayDatas = displayDatas;
    }

    @Override
    public void display() {
        System.out.println();
        displayWelcomeMessage();
        for (ProductsDisplayData displayData : displayDatas) {
            displayPromotionProduct(displayData);
            System.out.println(getNormalProductMessage(displayData));
        }
    }

    private void displayWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(CURRENT_PRODUCTS_MESSAGE);
        System.out.println();
    }

    private void displayPromotionProduct(ProductsDisplayData displayData) {
        if (isPromotionProductNotnull(displayData)) {
            System.out.println(getPromotionProductDisplayMessage(displayData));
        }
    }

    private boolean isPromotionProductNotnull(ProductsDisplayData displayData) {
        return displayData.getPromotionProductData() != null;
    }

    private String getPromotionProductDisplayMessage(ProductsDisplayData displayData) {
        List<String> productInformation = getPromotionProductInformation(displayData);
        String name = productInformation.get(0);
        int price = Integer.parseInt(productInformation.get(1));
        int quantity = Integer.parseInt(productInformation.get(2));
        String promotion_name = productInformation.get(3);
        return makeDisplayMessage(name, price, quantity, promotion_name);
    }

    private String getNormalProductMessage(ProductsDisplayData displayData) {
        List<String> productInformation = getNormalProductInformation(displayData);
        String name = productInformation.get(0);
        int price = Integer.parseInt(productInformation.get(1));
        int quantity = Integer.parseInt(productInformation.get(2));
        return makeDisplayMessage(name, price, quantity);
    }

    private String makeDisplayMessage(String name, int price, int quantity, String promotion_name) {
        if (isNotExist(quantity)) {
            return String.format(NOT_EXIST_PROMOTION_PRODUCT_DISPLAY_FORMAT, name, price, promotion_name);
        }
        return String.format(PROMOTION_PRODUCT_DISPLAY_FORMAT, name, price, quantity, promotion_name);
    }

    private String makeDisplayMessage(String name, int price, int quantity) {
        if (isNotExist(quantity)) {
            return String.format(NOT_EXIST_NORMAL_PRODUCT_DISPLAY_FORMAT, name, price);
        }
        return String.format(NORMAL_PRODUCT_DISPLAY_FORMAT, name, price, quantity);
    }

    private boolean isNotExist(int quantity) {
        return quantity == 0;
    }

    private List<String> getPromotionProductInformation(ProductsDisplayData displayData) {
        String promotionProductData = displayData.getPromotionProductData();
        return List.of(promotionProductData.split(ConstantBox.SEPARATOR));
    }

    private List<String> getNormalProductInformation(ProductsDisplayData displayData) {
        String NormalProductData = displayData.getNormalProductData();
        return List.of(NormalProductData.split(ConstantBox.SEPARATOR));
    }
}
