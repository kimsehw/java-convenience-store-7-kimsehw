package store.view.outputview;

import java.util.List;
import store.dto.ReceiptInformation;
import store.model.domain.product.ProductsDisplayData;

public class OutputViewFactory {

    private OutputViewFactory() {
    }

    public static OutputView createProductDisplayView(List<ProductsDisplayData> displayDatas) {
        return new ProductsDisplayView(displayDatas);
    }

    public static OutputView createReceiptDisplayView(ReceiptInformation receiptInformation) {
        return new ReceiptView(receiptInformation);
    }
}
