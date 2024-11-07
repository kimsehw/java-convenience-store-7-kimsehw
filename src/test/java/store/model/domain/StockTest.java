package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class StockTest {

    private final Stock stock = new Stock("콜라", 6);

    @DisplayName("재고 이름 확인 테스트")
    @ParameterizedTest
    @CsvSource({"콜라,true", "사이다,false"})
    void isOfTest(String productName, boolean expected) {
        assertThat(stock.isOf(productName)).isEqualTo(expected);
    }


    @DisplayName("구매 가능 여부 확인 테스트")
    @ParameterizedTest
    @CsvSource({"6,true", "7,false"})
    void isPurchasableTest(int requestQuantity, boolean expected) {
        assertThat(stock.isPurchasable(requestQuantity)).isEqualTo(expected);
    }

    @DisplayName("재고 차감 테스트")
    @Test
    void isPurchasableTest() {
        stock.isPurchasable(6);
        assertThat(stock.isPurchasable(1)).isFalse();
    }
}