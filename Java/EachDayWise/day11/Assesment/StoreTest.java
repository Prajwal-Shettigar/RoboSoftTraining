import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

//testing if the method can return customers based on their id

    //checking for id present in the list
    @Test
    public void testGetCutomerForPositive(){
        //create an arraylist of customers

        ArrayList<Customer> customers = new ArrayList<>();

        Customer newCustomer = new Customer(101,"prajwal","udupi");
        customers.add(newCustomer);

        assertEquals(newCustomer,Store.getCustomer(101,customers));

    }


    //checking for id not present in the list
    @Test
    public void testGetCutomerForNegetive(){
        //create an arraylist of customers

        ArrayList<Customer> customers = new ArrayList<>();

        Customer newCustomer = new Customer(101,"prajwal","udupi");
        customers.add(newCustomer);

        assertNull(Store.getCustomer(102,customers));

    }


}