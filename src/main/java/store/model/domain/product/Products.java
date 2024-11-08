package store.model.domain.product;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private int totalQuantity;
    private List<Product> products = new ArrayList<>();

    public Products(Product product) {
        this.totalQuantity = product.getQuantity();
        products.add(product);
    }

    public void add(Product product) {
        totalQuantity += product.getQuantity();
        products.add(product);
    }
}
