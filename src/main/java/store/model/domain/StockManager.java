package store.model.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.model.domain.product.Product;
import store.model.domain.product.ProductFactory;

public class StockManager {

    public static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    public static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    private List<Product> stock;
    private Map<String, Promotion> promotions;

    public StockManager() throws IOException {
        readProductsFrom();
        readPromotionsFrom();
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

    private void readPromotionsFrom() throws IOException {
        List<String> promotionsInformation = Files.readAllLines(Path.of(PROMOTIONS_FILE_PATH));
        promotionsInformation.removeFirst(); // 첫 라인(헤더) 제거
        promotions = organizePromotions(promotionsInformation);
    }

    private Map<String, Promotion> organizePromotions(List<String> promotionsInformation) {
        Map<String, Promotion> promotions = new HashMap<>();
        promotionsInformation.forEach(promotionInformation -> putPromotion(promotions, promotionInformation));
        return Collections.unmodifiableMap(promotions);
    }

    private void putPromotion(Map<String, Promotion> promotions, String promotionInformation) {
        List<String> promotionData = List.of(promotionInformation.split(","));
        String name = promotionData.get(0);
        int buy = Integer.parseInt(promotionData.get(1));
        int get = Integer.parseInt(promotionData.get(2));
        String startDate = promotionData.get(3);
        String endDate = promotionData.get(4);
        promotions.put(name, new Promotion(buy, get, startDate, endDate));
    }
}
