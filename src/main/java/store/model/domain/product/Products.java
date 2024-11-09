package store.model.domain.product;

import java.util.Arrays;
import java.util.List;

public class Products {

    private static final int PROMOTION_INDEX = 0;
    public static final int NORMAL_INDEX = 1;

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
        if (products instanceof NormalProduct) {
            products.set(NORMAL_INDEX, product);
        }
    }
}
