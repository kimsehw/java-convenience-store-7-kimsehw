package store.config;

import java.io.IOException;
import store.controller.StoreController;
import store.model.domain.StockManager;
import store.view.inputview.InputView;

public class Config {

    private Config() {
    }

    public static StoreController createStoreController() throws IOException {
        return new StoreController(inputView(), stockManager());
    }

    private static InputView inputView() {
        return new InputView();
    }

    private static StockManager stockManager() throws IOException {
        return new StockManager();
    }
}
