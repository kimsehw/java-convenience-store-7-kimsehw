package store.model.domain.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.exception.InputException;

class ProductsInputTest {

    @DisplayName("예외 형식 테스트")
    @ParameterizedTest
    @MethodSource("generateProductsInputException")
    void getRequestProducts(String productsInput) {
        assertThatThrownBy(() -> new ProductsInput(productsInput))
                .isInstanceOf(InputException.class);
    }

    static Stream<Arguments> generateProductsInputException() {
        return Stream.of(
                Arguments.of(" [사이다-2]"),
                Arguments.of("[사이다-2"),
                Arguments.of("[사이다2]"),
                Arguments.of("[사이다-2]2[감자칩-1]"),
                Arguments.of("[사이다-2],[감자칩-일]"),
                Arguments.of("[-2],[감자칩-일]"),
                Arguments.of("사이다-2"),
                Arguments.of("")
        );
    }

    @DisplayName("정상 형식 입력 테스트")
    @ParameterizedTest
    @MethodSource("generateValidCase")
    void validateTest(String input, List<String> expectedOutput) {
        ProductsInput productsInput = new ProductsInput(input);
        assertThat(productsInput.getRequestProducts()).isEqualTo(expectedOutput);
    }

    static Stream<Arguments> generateValidCase() {
        return Stream.of(
                Arguments.of("[사이다-2],[감자칩-1]", List.of("사이다-2", "감자칩-1"))
        );
    }
}