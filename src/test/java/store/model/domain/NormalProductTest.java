package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.domain.product.NormalProduct;


class NormalProductTest {

    private static final int TEST_QUANTITY = 6;
    private static final String TEST_NAME = "콜라";
    private static final int TEST_PRICE = 1000;

    private final NormalProduct normalProduct = new NormalProduct(TEST_NAME, TEST_PRICE, TEST_QUANTITY);

    @DisplayName("재고 이름 확인 테스트")
    @ParameterizedTest
    @CsvSource({"콜라,true", "사이다,false"})
    void isProductOfTest(String productName, boolean expected) {
        assertThat(normalProduct.isProductOf(productName)).isEqualTo(expected);
    }

    @DisplayName("구매 가능 여부 확인 테스트")
    @ParameterizedTest
    @CsvSource({"6,PURCHASE_SUCCESS", "7,OUT_OF_STOCK"})
    void isPurchasableTest(int requestQuantity, PurchaseResponseCode expected) {
        assertThat(normalProduct.isPurchasable(requestQuantity)).isEqualTo(expected);
    }

    @DisplayName("재고 차감 테스트")
    @Test
    void isPurchasableTest() {
        int requestQuantity = 3;
        normalProduct.reduce(requestQuantity);
        int expected = TEST_QUANTITY - requestQuantity;
        assertThat(normalProduct.getQuantity()).isEqualTo(expected);
    }
}