package store.model.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.domain.Promotion;
import store.model.domain.PurchaseResponseCode;

class PromotionProductTest {

    private static final int TEST_QUANTITY = 7;
    private static final String TEST_NAME = "콜라";
    private static final int TEST_PRICE = 1000;

    private PromotionProduct promotionProduct;

    @DisplayName("프로모션 상품 이름 확인 테스트")
    @ParameterizedTest
    @CsvSource({"콜라,true", "사이다,false"})
    void isProductOfTest(String productName, boolean expected) {
        promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_QUANTITY, null);
        assertThat(promotionProduct.isProductOf(productName)).isEqualTo(expected);
    }

    @DisplayName("구매 가능 여부 확인 테스트")
    @ParameterizedTest
    @MethodSource("generatePurchaseCase")
    void isPurchasable(String startDate, String endDate, int requestQuantity, PurchaseResponseCode expectedCode) {
        Promotion promotion = new Promotion(List.of("2+1", "2", "1", startDate, endDate));
        promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_QUANTITY, promotion);
        assertThat(promotionProduct.isPurchasable(requestQuantity))
                .extracting("purchaseResponseCode")
                .isEqualTo(expectedCode);
    }

    static Stream<Arguments> generatePurchaseCase() {
        return Stream.of(
                Arguments.of("2024-01-01", "2024-12-31", 3, PurchaseResponseCode.PURCHASE_SUCCESS),
                Arguments.of("2023-01-01", "2023-12-31", 3, PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE),
                Arguments.of("2024-01-01", "2024-12-31", 2, PurchaseResponseCode.FREE_PRODUCT_REMIND)
        );
    }

    @DisplayName("상품 데이터 생성 테스트")
    @ParameterizedTest
    @ValueSource(strings = "콜라,1000,7,탄산2+1")
    void getProductDataTest(String data) {
        List<String> information = List.of(data.split(","));
        Promotion promotion = new Promotion(List.of(information.getLast(), "2", "1", "null", "null"));
        promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_QUANTITY, promotion);
        assertThat(promotionProduct.getProductData()).isEqualTo(data);
    }
}