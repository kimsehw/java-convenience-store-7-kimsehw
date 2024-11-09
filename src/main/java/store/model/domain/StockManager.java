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
import store.model.domain.product.Products;

public class StockManager {

    public static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    public static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    public static final String SEPARATOR = ",";

    private Map<String, Products> stock;
    private Map<String, Promotion> promotions;

    public StockManager() throws IOException {
        readPromotionsFrom();
        readProductsFrom();
    }

    public void readProductsFrom() throws IOException {
        List<String> productsData = Files.readAllLines(Path.of(PRODUCTS_FILE_PATH));
        productsData.removeFirst(); // 첫 라인(헤더) 제거
        this.stock = organizeStock(productsData);
    }

    private Map<String, Products> organizeStock(List<String> productsData) {
        Map<String, Products> stock = new HashMap<>();
        productsData.forEach(productData -> putStock(stock, productData));
        return Collections.unmodifiableMap(stock);
    }

    private void putStock(Map<String, Products> stock, String productData) {
        List<String> productInformation = List.of(productData.split(SEPARATOR));
        Product product = ProductFactory.createProductFrom(productInformation, promotions);
        String name = productInformation.get(0);
        addProduct(stock, name, product);
    }

    private void addProduct(Map<String, Products> stock, String name, Product product) {
        if (!stock.containsKey(name)) {
            stock.put(name, new Products(product));
        }
        if (stock.containsKey(name)) {
            stock.get(name).add(product);
        }
    }

    private void readPromotionsFrom() throws IOException {
        List<String> promotionsData = Files.readAllLines(Path.of(PROMOTIONS_FILE_PATH));
        promotionsData.removeFirst(); // 첫 라인(헤더) 제거
        promotions = organizePromotions(promotionsData);
    }

    private Map<String, Promotion> organizePromotions(List<String> promotionsData) {
        Map<String, Promotion> promotions = new HashMap<>();
        promotionsData.forEach(promotionData -> putPromotion(promotions, promotionData));
        return Collections.unmodifiableMap(promotions);
    }

    private void putPromotion(Map<String, Promotion> promotions, String promotionData) {
        List<String> promotionInformation = List.of(promotionData.split(SEPARATOR));
        String name = promotionInformation.get(0);
        int buy = Integer.parseInt(promotionInformation.get(1));
        int get = Integer.parseInt(promotionInformation.get(2));
        String startDate = promotionInformation.get(3);
        String endDate = promotionInformation.get(4);
        promotions.put(name, new Promotion(buy, get, startDate, endDate));
    }

    public PurchaseResponse getPurchaseResponseFrom(String requestProductName, int requestQuantity) {
        Products products = stock.get(requestProductName);
        if (products == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        PurchaseResponse purchaseResponse = products.checkQuantity(requestQuantity);
        if (purchaseResponse.getPurchaseResponseCode().equals(PurchaseResponseCode.OUT_OF_STOCK)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        return purchaseResponse;
    }
}
