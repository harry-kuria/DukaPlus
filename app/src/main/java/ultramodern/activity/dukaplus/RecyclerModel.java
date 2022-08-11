package ultramodern.activity.dukaplus;

public class RecyclerModel {
    private String code;
    private String item;
    private String quantity;
    private String unit_price;
    private String total_price;

    public RecyclerModel() {
    }

    public RecyclerModel(String code, String item, String quantity, String unit_price, String total_price) {
        this.code = code;
        this.item = item;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.total_price = total_price;
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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
