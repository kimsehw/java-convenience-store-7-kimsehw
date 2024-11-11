package store.model.domain.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import store.constant.ConstantBox;
import store.exception.ExceptionType;
import store.exception.InputException;

public class ProductsInput {

    private String productsInput;

    public ProductsInput(String productsInput) {
        validate(productsInput);
        this.productsInput = productsInput;
    }

    private void validate(String productsInput) {
        validateEmptyOf(productsInput);
        validateEachProduct(productsInput);
    }

    private void validateEmptyOf(String productsInput) {
        boolean isEmpty = productsInput == null || productsInput.isEmpty();
        if (isEmpty) {
            throw new InputException(ExceptionType.INVALID_PRODUCTS_INPUT);
        }
    }

    private void validateEachProduct(String productsInput) {
        List<String> products = List.of(productsInput.split(ConstantBox.SEPARATOR));
        for (String product : products) {
            String withOutStartEndFormat = validateStartEndFormat(product);
            List<String> nameAndQuantity = validateSeparate(withOutStartEndFormat);
            validateNameAndQuantity(nameAndQuantity);
        }
    }

    private String validateStartEndFormat(String product) {
        if (!product.startsWith("[") || !product.endsWith("]")) {
            throw new InputException(ExceptionType.INVALID_PRODUCTS_INPUT);
        }
        return product.substring(1, product.length() - 1);
    }

    private List<String> validateSeparate(String withOutStartEndFormat) {
        List<String> nameAndQuantity = List.of(withOutStartEndFormat.split(ConstantBox.INPUT_SEPARATOR));
        if (nameAndQuantity.size() != 2) {
            throw new InputException(ExceptionType.INVALID_PRODUCTS_INPUT);
        }
        return nameAndQuantity;
    }

    private void validateNameAndQuantity(List<String> nameAndQuantity) {
        validateName(nameAndQuantity);
        validateQuantity(nameAndQuantity);
    }

    private static void validateQuantity(List<String> nameAndQuantity) {
        String quantityInput = nameAndQuantity.getLast();
        try {
            Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            throw new InputException(ExceptionType.INVALID_PRODUCTS_INPUT);
        }
    }

    private void validateName(List<String> nameAndQuantity) {
        String name = nameAndQuantity.getFirst();
        if (name.isEmpty()) {
            throw new InputException(ExceptionType.INVALID_PRODUCTS_INPUT);
        }
    }

    public List<String> getRequestProducts() {
        List<String> products = List.of(productsInput.split(ConstantBox.SEPARATOR));
        List<String> requestProducts = eraseStartEndFormat(products);
        return Collections.unmodifiableList(requestProducts);
    }

    private List<String> eraseStartEndFormat(List<String> products) {
        List<String> requestProducts = new ArrayList<>();
        for (String product : products) {
            String withOutStartEndFormat = validateStartEndFormat(product);
            requestProducts.add(withOutStartEndFormat);
        }
        return requestProducts;
    }
}
