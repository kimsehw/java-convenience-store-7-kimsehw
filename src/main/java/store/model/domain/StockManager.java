package store.model.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.exception.ExceptionType;
import store.exception.InputException;
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
        String name = productInformation.getFirst();
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
        String name = promotionInformation.getFirst();
        promotions.put(name, new Promotion(promotionInformation));
    }

    public PurchaseResponse getPurchaseResponseFrom(String requestProductName, int requestQuantity) {
        Products products = stock.get(requestProductName);
        checkNotExistProductException(products);
        PurchaseResponse purchaseResponse = products.checkQuantity(requestQuantity);
        checkOverStockAmountException(purchaseResponse);
        purchaseResponse.setName(requestProductName);
        return purchaseResponse;
    }

    private void checkNotExistProductException(Products products) {
        boolean isNotExistProduct = (products == null);
        if (isNotExistProduct) {
            throw new InputException(ExceptionType.NOT_EXIST_PRODUCT);
        }
    }

    private static void checkOverStockAmountException(PurchaseResponse purchaseResponse) {
        boolean isOverStock = purchaseResponse.getPurchaseResponseCode().equals(PurchaseResponseCode.OUT_OF_STOCK);
        if (isOverStock) {
            throw new InputException(ExceptionType.OVER_STOCK_AMOUNT);
        }
    }

    public Map<String, Products> getStock() {
        return this.stock;
    }
}
