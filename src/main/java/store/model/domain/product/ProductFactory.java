package store.model.domain.product;

import java.util.List;

public class ProductFactory {

    private static final String SEPARATOR = ",";

    private ProductFactory() {
    }

    public static Product createProduct(String productInformation) {
        List<String> productData = List.of(productInformation.split(SEPARATOR));
        String name = productData.get(0);
        int price = Integer.parseInt(productData.get(1));
        int quantity = Integer.parseInt(productData.get(2));
        String promotion = productData.get(3);
        if (promotion.equals("null")) {
            return new NormalProduct(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }
}
