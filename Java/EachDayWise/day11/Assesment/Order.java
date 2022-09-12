import java.util.*;

public class Order {

   private Date timeOFOrderPlacement;
   private String statusOfOrder;
   private Map<Item,Integer> itemsOrdered;

   private double totalTax , totalWeight , totalWithoutTax , finalAmount;


    public Order(Date timeOFOrderPlacement, String statusOfOrder, Map<Item,Integer> itemsOrdered ) {
        this.timeOFOrderPlacement = timeOFOrderPlacement;
        this.statusOfOrder = statusOfOrder;
        this.itemsOrdered = itemsOrdered;
    }

    public Date getTimeOFOrderPlacement() {
        return timeOFOrderPlacement;
    }


    public Map<Item, Integer> getItemsOrdered() {
        return itemsOrdered;
    }

    public String getStatusOfOrder() {
        return statusOfOrder;
    }


    //return total amount without the taxes
    public double calcSubTotal(){
        totalWithoutTax =0;
        for(Map.Entry<Item,Integer> item:itemsOrdered.entrySet()){
            totalWithoutTax+=item.getKey().getPrice()*item.getValue();
        }
        return totalWithoutTax;
    }

    //return the tax amount to be paid
    public double calcTax(){
        totalTax=0;
        for(Map.Entry<Item,Integer> item:itemsOrdered.entrySet()){
            totalTax+=item.getKey().getPrice()*item.getValue()*item.getKey().getRateOfTaxOnItem();
        }
       return totalTax;
    }


    //total weigght of all items ordered
    public double totalWeight(){
        totalWeight = 0;
        for(Map.Entry<Item,Integer> item:itemsOrdered.entrySet()){
            totalWeight+=item.getKey().getShippingWeight()*item.getValue();
        }
        return totalWeight;
    }

    public void setStatusOfOrder(String statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }


    //returns the final amount
    public double calcTotal(){
        return calcSubTotal()+calcTax();
    }


}
