package store.model.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import store.model.domain.product.Product;
import store.model.domain.product.ProductFactory;

public class StockManager {

    public static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";

    private List<Product> stock;

    public StockManager() throws IOException {
        readProductsFrom();
    }

    private void readProductsFrom() throws IOException {
        List<String> productsInformation = Files.readAllLines(Path.of(PRODUCTS_FILE_PATH));
        productsInformation.removeFirst(); // 첫 라인(헤더) 제거
        stock = organizeStock(productsInformation);
    }

    private List<Product> organizeStock(List<String> productsInformation) {
        return productsInformation.stream()
                .map(ProductFactory::createProduct)
                .toList();
    }
}
