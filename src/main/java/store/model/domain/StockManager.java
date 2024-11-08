package store.model.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StockManager {

    private static final String SEPARATOR = ",";

    private final List<Product> stock = new ArrayList<>();

    public void readProductsFrom(String productsFilePath) throws IOException {
        List<String> products = Files.readAllLines(Path.of(productsFilePath));
        products.removeFirst(); // 첫 라인(헤더) 제거
        organizeStock(products);
    }

    private void organizeStock(List<String> products) {
        for (String productInformation : products) {
            List<String> productData = List.of(productInformation.split(SEPARATOR));
            String name = productData.get(0);
            int price = Integer.parseInt(productData.get(1));
            String promotion = productData.get(3);
            int quantity = Integer.parseInt(productData.get(2));
            stock.add(new Product(name, price, quantity, promotion));
        }
    }
}
