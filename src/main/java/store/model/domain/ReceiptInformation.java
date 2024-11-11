package store.model.domain;

import java.util.List;
import java.util.Map;

public class ReceiptInformation {

    private Map<String, List> salesDatas;
    private List<Integer> amountInformation;

    public ReceiptInformation(Map<String, List> salesDatas, List<Integer> amountInformation) {
        this.salesDatas = salesDatas;
        this.amountInformation = amountInformation;
    }

    public Map<String, List> getSalesDatas() {
        return salesDatas;
    }

    public void setSalesDatas(Map<String, List> salesDatas) {
        this.salesDatas = salesDatas;
    }

    public List<Integer> getAmountInformation() {
        return amountInformation;
    }

    public void setAmountInformation(List<Integer> amountInformation) {
        this.amountInformation = amountInformation;
    }
}
