import java.util.*;

public class Store {

    public static Scanner stringScanner;
    public static Scanner otherScanner;

    public static ArrayList<Customer> customers;

    public static ArrayList<Item> itemsInShop;

    public static void main(String[] args) {
        stringScanner = new Scanner(System.in);
        otherScanner = new Scanner(System.in);

        customers = new ArrayList<>();
        itemsInShop = new ArrayList<>();
        addCustomers();
        addItems();


        while(true){
            System.out.println("Do you want to place an order(yes/no)");
            if(stringScanner.nextLine().equalsIgnoreCase("no")){
                break;
            }
            placeAnOrder();
        }




    }


    //adds customer onto the list taking customer details from the user
    public static void addCustomers(){
        System.out.println("Enter the number of customers : ");
        int numberOfCutomers = otherScanner.nextInt();
        for(int i = 0;i<numberOfCutomers;i++){
            System.out.println("Enter customer id");
            int id = otherScanner.nextInt();

            System.out.println("Enter customer name");
            String name= stringScanner.nextLine();

            System.out.println("Enter customer address ");
            customers.add(new Customer(id,name,stringScanner.nextLine()));

        }
    }



    //user prompt for placing order
    public static void placeAnOrder(){
        System.out.println("Enter the customer id from which you want to place an order ");
        int id = otherScanner.nextInt();

        Customer customer = getCustomer(id,customers);
        if(customer==null){
            System.exit(0);
        }
        Map<Item,Integer> cart = new HashMap<>();

        while(true) {
            System.out.println("Do you want to buy  items yes/no");
            if (stringScanner.nextLine().equalsIgnoreCase("no")) {
                break;
            }
            System.out.println("Enter the name of the item you want to buy");
            String nameOfItem = stringScanner.nextLine();
            System.out.println("Enter the quantity of the item you want to buy");
            int quantity = otherScanner.nextInt();
            addToCart(cart,nameOfItem,quantity);
        }

        if(cart.size()<=0){
            return;
        }

        Order order = new Order(new Date(),"Processing",cart);

        System.out.println("Your order is under processing..");

        System.out.println(makePayment(customer,order));





    }


    //make payment through check,cash or credit
    public static String makePayment(Customer customer,Order order){
        System.out.println("your order details are ");
        System.out.println("Date of order placement: "+order.getTimeOFOrderPlacement());
        System.out.println("Order status : "+order.getStatusOfOrder());
        System.out.println("Items in the cart : "+order.getItemsOrdered());
        System.out.println("Gross weight : "+order.totalWeight());
        System.out.println("Total tax amount : "+order.calcTax());
        System.out.println("Total without taxes : "+order.calcSubTotal());
        System.out.println(">>>>>>>>>>>>>>>Final amount : "+order.calcTotal());

        System.out.println("Choose the mode of payment : \n1 for Cash \n2 for Check \n3 for Credit \n4 to cancel");
        new PaymentFactory().getPaymentMode(customer,order.calcTotal(),otherScanner.nextInt());

        order.setStatusOfOrder("Completed");
        customer.placeOrder(order);
        return "Your order has been completed";


    }


    // add items onto the cart
    public static void addToCart(Map<Item,Integer> cart,String nameOfItem , int quantity){
        if(quantity<=0){
            return;
        }
        Item currentItem = null;
        for(Item item:itemsInShop){
            if(item.getNameOfItem().equalsIgnoreCase(nameOfItem)){
                currentItem = item;
                break;

            }
        }

        if(currentItem==null){
            System.out.println("Invalid item..");
            return;
        }

        if(quantity>currentItem.getItemsInStock()){
            try{
                throw new OutOfStockException("Items are out of stock");
            }catch(OutOfStockException outOfStockException){
                System.out.println(outOfStockException.getMessage());
                return;
            }
        }

        currentItem.setItemsInStock(currentItem.getItemsInStock()-quantity);
        cart.put(currentItem,quantity);



    }


    //get customer using their id
    public static Customer getCustomer(int id,ArrayList<Customer> customers){
        for(Customer customer: customers){
            if(customer.getId()==id){
                return customer;
            }
        }
        System.out.println("customer with id "+id+" doesnt exists ");
        //System.exit(0);
        return null;
    }



    //add items onto the shop
    public static void addItems(){
        itemsInShop.add(new Item("Salt",1,"some description",0.2,20,100));
        itemsInShop.add(new Item("Sugar",1," some description",0.1,34,100));
        itemsInShop.add(new Item("Tea powder",1,"some description",0.3,50,30));

    }


}
