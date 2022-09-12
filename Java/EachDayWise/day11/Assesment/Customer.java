import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Customer {

    private int id;
    private String name;
    private String address;
    private ArrayList<Order> customerOrders;

    private double credit;



    public Customer(int id, String name, String address){
        customerOrders = new ArrayList<>();
        this.id = id;
        this.name=name;
        this.address = address;
        credit =0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Order> getCustomerOrders() {
        return customerOrders;
    }


    //add current order onto customer order history
    public void placeOrder(Order order){
        customerOrders.add(order);
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
