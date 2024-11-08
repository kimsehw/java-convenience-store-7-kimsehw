package store.model.domain.product;

import java.util.List;
import java.util.Map;
import store.model.domain.Promotion;

public class ProductFactory {

    private ProductFactory() {
    }

    public static Product createProductFrom(List<String> productData, Map<String, Promotion> promotions) {
        String name = productData.get(0);
        int price = Integer.parseInt(productData.get(1));
        int quantity = Integer.parseInt(productData.get(2));
        String promotion_name = productData.get(3);
        if (promotion_name.equals("null")) {
            return new NormalProduct(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotions.get(promotion_name));
    }
}
