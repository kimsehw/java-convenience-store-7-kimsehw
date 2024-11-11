package store;

import java.io.IOException;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) throws IOException {
        StoreController storeController = Config.createStoreController();
        storeController.run();
    }
}
