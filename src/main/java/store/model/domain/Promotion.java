package store.model.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Promotion {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final int buy;
    private final int get;
    private final String startDate;
    private final String endDate;

    public Promotion(int buy, int get, String startDate, String endDate) {
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isOnPromotion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime startDate = LocalDate.parse(this.startDate, formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(this.endDate, formatter).atStartOfDay();
        LocalDateTime today = DateTimes.now();
        return (today.isAfter(startDate) && today.isBefore(endDate));
    }

    public int discount(int requestQuantity) {
        int freeAmount = (requestQuantity / buy) * get;
        return requestQuantity + freeAmount;
    }
}
