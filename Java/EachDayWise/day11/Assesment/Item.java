public class Item {

    private String nameOfItem;
    private double ShippingWeight;
    private String description;
    private double rateOfTaxOnItem;
    private double price;

    private int itemsInStock;

    public Item(String nameOfItem, double shippingWeight, String description, double rateOfTaxOnItem, double price, int itemsInStock) {
        this.nameOfItem = nameOfItem;
        ShippingWeight = shippingWeight;
        this.description = description;
        this.rateOfTaxOnItem = rateOfTaxOnItem;
        this.price = price;
        this.itemsInStock = itemsInStock;
    }

    public String getNameOfItem() {
        return nameOfItem;
    }

    public int getItemsInStock() {
        return itemsInStock;
    }

    public double getShippingWeight() {
        return ShippingWeight;
    }

    public String getDescription() {
        return description;
    }

    public double getRateOfTaxOnItem() {
        return rateOfTaxOnItem;
    }

    public double getPrice() {
        return price;
    }

    public void setItemsInStock(int itemsInStock) {
        this.itemsInStock = itemsInStock;
    }
}
