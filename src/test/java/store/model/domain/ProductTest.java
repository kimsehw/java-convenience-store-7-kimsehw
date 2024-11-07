package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class ProductTest {

    private static final int TEST_QUANTITY = 6;
    private static final String TEST_NAME = "콜라";
    private static final int TEST_PRICE = 1000;
    private static final String TEST_PROMOTION = "null";

    private final Product product = new Product(TEST_NAME, TEST_PRICE, TEST_QUANTITY, TEST_PROMOTION);

    @DisplayName("재고 이름 확인 테스트")
    @ParameterizedTest
    @CsvSource({"콜라,true", "사이다,false"})
    void isProductOfTest(String productName, boolean expected) {
        assertThat(product.isProductOf(productName)).isEqualTo(expected);
    }
    
    @DisplayName("구매 가능 여부 확인 테스트")
    @ParameterizedTest
    @CsvSource({"6,true", "7,false"})
    void isPurchasableTest(int requestQuantity, boolean expected) {
        assertThat(product.isPurchasable(requestQuantity)).isEqualTo(expected);
    }

    @DisplayName("재고 차감 테스트")
    @Test
    void isPurchasableTest() {
        product.isPurchasable(TEST_QUANTITY);
        assertThat(product.isPurchasable(1)).isFalse();
    }
}