package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
}