import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class PaymentFactory {

    public void getPaymentMode(Customer customer,double amount , int mode){
        Scanner stringScanner = new Scanner(System.in);
        Scanner otherScanner = new Scanner(System.in);

        //ask for the apyment based on sutomer choice
        switch(mode){

            //for cash payment
            case 1:
                System.out.println("Enter the amount : ");
                new Cash((float)amount,otherScanner.nextFloat()).returnChange();
                break;

                // for check payment
            case 2:
                System.out.println("Enter the name of account holder");
                String name = stringScanner.nextLine();
                System.out.println("Enter the bank account number on check book..");
                new Check((float)amount,name,stringScanner.nextLine()).authorize();
                break;

                //for adding to customer store credit
            case 3:
                new Credit((float)amount, customer.getName()).authorize(customer);
                break;

                
            default:
                System.out.println("Your order has been discarded ");
                System.exit(0);



        }
    }
}
