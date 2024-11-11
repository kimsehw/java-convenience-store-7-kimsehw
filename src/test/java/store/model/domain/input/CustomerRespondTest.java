package store.model.domain.input;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.exception.InputException;

class CustomerRespondTest {
    @DisplayName("예외 형식 테스트")
    @ParameterizedTest
    @MethodSource("generateRespondException")
    void getRequestProducts(String customerRespond) {
        assertThatThrownBy(() -> new CustomerRespond(customerRespond))
                .isInstanceOf(InputException.class);
    }

    static Stream<Arguments> generateRespondException() {
        return Stream.of(
                Arguments.of(" [사이다-2]"),
                Arguments.of("YY"),
                Arguments.of("ss"),
                Arguments.of("NN"),
                Arguments.of(" N")
        );
    }
}