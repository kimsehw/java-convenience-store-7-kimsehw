package store.model.domain.input;

import store.constant.ConstantBox;
import store.exception.ExceptionType;
import store.exception.InputException;

public class CustomerRespond {

    private String customerRespond;

    public CustomerRespond(String inputCustomerRespond) {
        validate(inputCustomerRespond);
        this.customerRespond = inputCustomerRespond;
    }

    private void validate(String inputCustomerRespond) {
        validateEmptyOf(inputCustomerRespond);
        validateRespond(inputCustomerRespond);
    }

    private void validateEmptyOf(String inputCustomerRespond) {
        boolean isEmpty = inputCustomerRespond == null || inputCustomerRespond.isEmpty();
        if (isEmpty) {
            throw new InputException(ExceptionType.INVALID_PRODUCTS_INPUT);
        }
    }

    private void validateRespond(String inputCustomerRespond) {
        boolean isValidRespondInput = inputCustomerRespond.matches("^[YN]$");
        if (!isValidRespondInput) {
            throw new InputException(ExceptionType.INVALID_RESPOND_INPUT);
        }
    }

    public boolean doesCustomerAgree() {
        return customerRespond.equals(ConstantBox.CUSTOMER_RESPOND_Y);
    }
}
