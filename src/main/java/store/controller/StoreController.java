package store.controller;

import java.io.IOException;
import java.util.List;
import store.constant.ConstantBox;
import store.exception.InputException;
import store.model.domain.PurchaseResponse;
import store.model.domain.PurchaseResponseCode;
import store.model.domain.ReceiptInformation;
import store.model.domain.StockManager;
import store.model.domain.input.CustomerRespond;
import store.model.domain.input.ProductsInput;
import store.model.domain.product.ProductsDisplayData;
import store.view.inputview.InputView;
import store.view.inputview.InputViewType;
import store.view.outputview.OutputView;
import store.view.outputview.OutputViewFactory;

public class StoreController {

    private static InputView inputView;
    private static OutputView outputView;
    private static StockManager stockManager;

    public StoreController(InputView inputView, StockManager stockManager) {
        this.inputView = inputView;
        this.stockManager = stockManager;
    }

    public void run() throws IOException {
        while (true) {
            displayStore();
            List<String> requestProducts = getRequestProducts();
            handlePurchase(requestProducts);
            ReceiptInformation receiptInformation = getReceiptInformation();
            displayReceipt(receiptInformation);
            CustomerRespond continueShopping = getContinueRespond();
            if (!continueShopping.doesCustomerAgree()) {
                break;
            }
        }
    }

    private CustomerRespond getContinueRespond() {
        inputView.showRequestMessageOf(InputViewType.CONTINUE_SHOPPING);
        return getCustomerRespond();
    }

    private void handlePurchase(List<String> requestProducts) {
        for (String requestProduct : requestProducts) {
            PurchaseResponse purchaseResponse = getPurchaseResponse(requestProduct);
            PurchaseResponseCode purchaseResponseCode = purchaseResponse.getPurchaseResponseCode();
            CustomerRespond customerRespond = announceAndGetRespond(purchaseResponseCode, purchaseResponse);
            stockManager.updateReceipt(customerRespond, purchaseResponse);
        }
    }

    private CustomerRespond announceAndGetRespond(PurchaseResponseCode purchaseResponseCode,
                                                  PurchaseResponse purchaseResponse) {
        if (purchaseResponseCode.equals(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE)) {
            inputView.showRequestMessageOf(InputViewType.PROMOTION_PARTIAL_UNAVAILABLE, purchaseResponse);
            return getCustomerRespond();
        }
        if (purchaseResponseCode.equals(PurchaseResponseCode.FREE_PRODUCT_REMIND)) {
            inputView.showRequestMessageOf(InputViewType.FREE_PRODUCT_REMIND, purchaseResponse);
            return getCustomerRespond();
        }
        return null;
    }

    private PurchaseResponse getPurchaseResponse(String requestProduct) {
        String requestProductName = requestProduct.split(ConstantBox.INPUT_SEPARATOR)[0];
        int requestQuantity = Integer.parseInt(requestProduct.split(ConstantBox.INPUT_SEPARATOR)[1]);
        return stockManager.getPurchaseResponseFrom(requestProductName, requestQuantity);
    }

    private void displayReceipt(ReceiptInformation receiptInformation) {
        outputView = OutputViewFactory.createReceiptDisplayView(receiptInformation);
        outputView.display();
    }

    private ReceiptInformation getReceiptInformation() {
        inputView.showRequestMessageOf(InputViewType.MEMBERSHIP);
        CustomerRespond membershipRespond = getCustomerRespond();
        return stockManager.getReceiptInformation(membershipRespond);
    }

    private List<String> getRequestProducts() {
        inputView.showRequestMessageOf(InputViewType.PRODUCTS_NAME_AMOUNT);
        while (true) {
            try {
                ProductsInput productsInput = new ProductsInput(inputView.getInput());
                List<String> requestProducts = productsInput.getRequestProducts();
                for (String requestProduct : requestProducts) {
                    String requestProductName = requestProduct.split(ConstantBox.INPUT_SEPARATOR)[0];
                    int requestQuantity = Integer.parseInt(requestProduct.split(ConstantBox.INPUT_SEPARATOR)[1]);
                    stockManager.checkNotExistProductException(requestProductName);
                    stockManager.checkOverStockAmountException(requestProductName, requestQuantity);
                }
                return requestProducts;
            } catch (InputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private CustomerRespond getCustomerRespond() {
        CustomerRespond customerRespond;
        while (true) {
            try {
                return new CustomerRespond(inputView.getInput());
            } catch (InputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void displayStore() {
        List<ProductsDisplayData> displayDatas = stockManager.getDisplayDatas();
        outputView = OutputViewFactory.createProductDisplayView(displayDatas);
        outputView.display();
    }
}

