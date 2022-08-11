package ultramodern.activity.dukaplus;

public class TransactionHistoryModel {
    private String code;
    private String item;
    private String quantity;
    private String unit_price;
    private String date;
    private String description;

    public TransactionHistoryModel() {
    }

    public TransactionHistoryModel(String code, String item, String quantity, String unit_price, String date, String description) {
        this.code = code;
        this.item = item;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.date = date;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
