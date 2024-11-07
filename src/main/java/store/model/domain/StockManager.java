package store.model.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StockManager {

    private List<Product> stock = new ArrayList<>();

    public void readProductsFrom(String productsFilePath) throws IOException {
        List<String> products = Files.readAllLines(Path.of(productsFilePath));
        products.removeFirst();
        for (String productInformation : products) {
            List<String> productData = List.of(productInformation.split(","));
            stock.add(new Product(productData.get(0), Integer.parseInt(productData.get(1)),
                    Integer.parseInt(productData.get(2)), productData.get(3)));
        }
    }
}
