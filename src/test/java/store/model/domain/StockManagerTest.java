package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.exception.ExceptionType;

class StockManagerTest {

    public static final int HEADER_LINE_COUNT = 1;

    private StockManager stockManager;

    @BeforeEach
    void setUp() throws IOException {
        stockManager = new StockManager();
    }

    @DisplayName("상품 목록 불러오기 테스트")
    @Test
    void readProductsTest() throws IOException {
        List<String> productsData = Files.readAllLines(Path.of(StockManager.PRODUCTS_FILE_PATH));
        Set<String> names = new HashSet<>();
        productsData.forEach(productData -> names.add(productData.split(StockManager.SEPARATOR)[0]));
        assertThat(stockManager).extracting("stock").asInstanceOf(MAP)
                .hasSize(names.size() - HEADER_LINE_COUNT);
    }

    @DisplayName("행사 목록 불러오기 테스트")
    @Test
    void readPromotionsTest() throws IOException {
        long productsCount = Files.lines(Path.of(StockManager.PROMOTIONS_FILE_PATH)).count();
        assertThat(stockManager).extracting("promotions").asInstanceOf(MAP)
                .hasSize((int) productsCount - HEADER_LINE_COUNT);
    }

    @DisplayName("구매 상품 구매 응답 반환 기능 테스트")
    @ParameterizedTest
    @CsvSource("콜라,12,PROMOTION_PARTIAL_UNAVAILABLE,3,3")
    void getPurchaseResponseFromTest(String requestProductName, int requestQuantity, PurchaseResponseCode expectedCode,
                                     int expectedPromotionCount, int expectedRestCount) {
        assertThat(stockManager.getPurchaseResponseFrom(requestProductName, requestQuantity))
                .extracting("name", "purchaseResponseCode", "promotionCount", "restCount")
                .containsExactly(requestProductName, expectedCode, expectedPromotionCount, expectedRestCount);
    }

    @DisplayName("재고 수량 초과 예외 테스트")
    @ParameterizedTest
    @CsvSource("콜라,100")
    void overStockAmountTest(String requestProductName, int requestQuantity) {
        assertThatThrownBy(() -> stockManager.getPurchaseResponseFrom(requestProductName, requestQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionType.OVER_STOCK_AMOUNT.getMessage());
    }

    @DisplayName("존재하지 않는 상품 입력 테스트")
    @ParameterizedTest
    @CsvSource("없는거,100")
    void noneProductTest(String requestProductName, int requestQuantity) {
        assertThatThrownBy(() -> stockManager.getPurchaseResponseFrom(requestProductName, requestQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionType.NOT_EXIST_PRODUCT.getMessage());
    }
}