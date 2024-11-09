package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionTest {

    @DisplayName("프로모션 유효 기간 검사 테스트")
    @ParameterizedTest
    @MethodSource("generateDateCase")
    void isOnPromotion(Promotion promotion, boolean expected) {
        assertThat(promotion.isOnPromotion()).isEqualTo(expected);
    }

    static Stream<Arguments> generateDateCase() {
        return Stream.of(
                Arguments.of(new Promotion(2, 1, "2024-01-01", "2024-12-31"), true),
                Arguments.of(new Promotion(2, 1, "2023-01-01", "2023-12-31"), false)
        );
    }

    @DisplayName("프로모션 적용 구매 응답 테스트.")
    @ParameterizedTest
    @CsvSource({"2,1,10,7,PROMOTION_PARTIAL_AVAILABLE,2,4",
            "2,1,5,7,FREE_PRODUCT_REMIND,1,2",
            "2,1,4,7,PROMOTION_PARTIAL_AVAILABLE,1,1",
            "2,1,6,7,PURCHASE_SUCCESS,2,0",
            "2,1,2,2,PROMOTION_PARTIAL_AVAILABLE,0,2",
            "1,1,1,1,PROMOTION_PARTIAL_AVAILABLE,0,1",
            "1,1,2,1,PROMOTION_PARTIAL_AVAILABLE,0,2",
            "1,1,3,3,PROMOTION_PARTIAL_AVAILABLE,1,1",
            "1,1,3,4,FREE_PRODUCT_REMIND,1,1",
            "1,1,1,4,FREE_PRODUCT_REMIND,0,1",
            "1,1,2,4,PURCHASE_SUCCESS,1,0",
            "3,1,5,3,PROMOTION_PARTIAL_AVAILABLE,0,5",
            "3,1,3,4,FREE_PRODUCT_REMIND,0,3"})
    void discountTest(int buy, int get, int requestQuantity, int stockQuantity, PurchaseResponseCode expectedCode,
                      int expectedPromotionCount, int expectedRestCount) {
        Promotion testPromotion = new Promotion(buy, get, null, null);
        assertThat(testPromotion.discount(requestQuantity, stockQuantity))
                .extracting("purchaseResponseCode", "promotionCount", "restCount")
                .containsExactly(expectedCode, expectedPromotionCount, expectedRestCount);
    }
}