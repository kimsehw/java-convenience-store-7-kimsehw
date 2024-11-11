package store.view.inputview;

import camp.nextstep.edu.missionutils.Console;
import store.model.domain.PurchaseResponse;

public class InputView {

    public void showRequestMessageOf(InputViewType inputViewType) {
        System.out.println(inputViewType.getRequestMessage());
    }

    public void showRequestMessageOf(InputViewType inputViewType, PurchaseResponse purchaseResponse) {
        String requestMessage = messageFormat(inputViewType, purchaseResponse);
        System.out.println(requestMessage);
    }

    private String messageFormat(InputViewType inputViewType, PurchaseResponse purchaseResponse) {
        String message = inputViewType.getRequestMessage();
        boolean isFreeProductRemindType = (inputViewType.equals(InputViewType.FREE_PRODUCT_REMIND));
        if (isFreeProductRemindType) {
            return formattingFreeProductRemindMessage(message, purchaseResponse);
        }
        return formattingPartialUnavailableMessage(message, purchaseResponse);
    }

    private String formattingFreeProductRemindMessage(String message, PurchaseResponse purchaseResponse) {
        return String.format(message, purchaseResponse.getName());
    }

    private String formattingPartialUnavailableMessage(String message, PurchaseResponse purchaseResponse) {
        return String.format(message, purchaseResponse.getName(), purchaseResponse.getRestCount());
    }

    public String getInput() {
        return Console.readLine();
    }
}
