package store.model.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionProductTest {

    private static final int TEST_QUANTITY = 6;
    private static final String TEST_NAME = "콜라";
    private static final int TEST_PRICE = 1000;

    private PromotionProduct normalProduct;

    @DisplayName("프로모션 상품 이름 확인 테스트")
    @ParameterizedTest
    @CsvSource({"콜라,true", "사이다,false"})
    void isProductOfTest(String productName, boolean expected) {
        normalProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_QUANTITY, null);
        assertThat(normalProduct.isProductOf(productName)).isEqualTo(expected);
    }

    @Test
    void isPurchasable() {
    }
}