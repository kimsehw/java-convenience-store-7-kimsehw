package store.model.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Promotion {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final int NO_PARTIAL = 0;

    private final int buy;
    private final int get;
    private final List<String> promotionInformation;

    public Promotion(List<String> promotionInformation) {
        this.promotionInformation = promotionInformation;
        this.buy = Integer.parseInt(promotionInformation.get(1));
        this.get = Integer.parseInt(promotionInformation.get(2));
    }

    public boolean isOnPromotion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String startDateInformation = promotionInformation.get(3);
        LocalDateTime startDate = LocalDate.parse(startDateInformation, formatter).atStartOfDay();
        String endDateInformation = promotionInformation.get(4);
        LocalDateTime endDate = LocalDate.parse(endDateInformation, formatter).atStartOfDay();
        LocalDateTime today = DateTimes.now();
        return (today.isAfter(startDate) && today.isBefore(endDate));
    }

    public PurchaseResponse discount(int requestQuantity, int stockQuantity) {
        int promotionCount = requestQuantity / (buy + get);
        int restCount = requestQuantity % (buy + get);
        return getResponse(requestQuantity, stockQuantity, promotionCount, restCount);
    }

    private PurchaseResponse getResponse(int requestQuantity, int stockQuantity, int promotionCount, int restCount) {
        if (isInsufficientStock(requestQuantity, stockQuantity)) {
            return responseInsufficientStock(requestQuantity, stockQuantity);
        }
        if (isSuccessPromotionPurchase(restCount)) {
            return responseSuccessPromotionPurchase(promotionCount, restCount);
        }
        if (isNeedFreeRemind(restCount)) {
            return responseInsufficientFreeStockOrFreeRemind(requestQuantity, stockQuantity, promotionCount, restCount);
        }
        return responsePartialAvailable(promotionCount, restCount);
    }

    private PurchaseResponse responsePartialAvailable(int promotionCount, int restCount) {
        return new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, promotionCount, restCount);
    }

    private boolean isInsufficientStock(int requestQuantity, int stockQuantity) {
        return requestQuantity > stockQuantity;
    }

    private PurchaseResponse responseInsufficientStock(int requestQuantity, int stockQuantity) {
        int promotionCount = stockQuantity / (buy + get);
        int restCount = stockQuantity % (buy + get) + (requestQuantity - stockQuantity);
        return new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, promotionCount, restCount);
    }

    private boolean isSuccessPromotionPurchase(int restCount) {
        return restCount == NO_PARTIAL;
    }

    private PurchaseResponse responseSuccessPromotionPurchase(int promotionCount, int restCount) {
        return new PurchaseResponse(PurchaseResponseCode.PURCHASE_SUCCESS, promotionCount, restCount);
    }

    private boolean isNeedFreeRemind(int restCount) {
        return restCount == buy;
    }

    private boolean isInsufficientFreeStock(int requestQuantity, int stockQuantity) {
        return requestQuantity == stockQuantity;
    }

    private PurchaseResponse responseInsufficientFreeStockOrFreeRemind(int requestQuantity, int stockQuantity,
                                                                       int promotionCount, int restCount) {
        if (isInsufficientFreeStock(requestQuantity, stockQuantity)) {
            return responseInsufficientFreeStock(promotionCount, restCount);
        }
        return responseFreeRemind(promotionCount, restCount);
    }

    private PurchaseResponse responseInsufficientFreeStock(int promotionCount, int restCount) {
        return new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, promotionCount, restCount);
    }

    private PurchaseResponse responseFreeRemind(int promotionCount, int restCount) {
        return new PurchaseResponse(PurchaseResponseCode.FREE_PRODUCT_REMIND, promotionCount, restCount);
    }
}
