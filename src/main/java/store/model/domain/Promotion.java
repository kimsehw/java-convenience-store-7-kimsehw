package store.model.domain;

public class Promotion {

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
}
