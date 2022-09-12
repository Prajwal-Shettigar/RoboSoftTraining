import java.util.Scanner;
import java.util.Random;

class Bank{
    public static Account[] accounts=new Account[5];
    public static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args){

        Random random=new Random();
        Account account1=new Account();

        int idCounter=random.nextInt(1000);

        accounts[0]=account1;
        for(int counter=1;counter<5;counter++){
            accounts[counter]=new Account(++idCounter,1000000*random.nextDouble());
            accounts[counter].setAnnualInterestRate(10*random.nextDouble());

        }
        userPrompt();

    }

    public static void userPrompt(){
        System.out.println("Available account Ids:");
        for(int counter=0;counter<accounts.length;counter++){
            System.out.println(accounts[counter].getId());
        }
        System.out.println("Enter your account ID");
        int accountId=scanner.nextInt();
        Account acc=chooseAccount(accountId);


        while(true){
            System.out.println(" 1. Get balance\n 2. Get Interest Rate\n 3. Get date of creation\n 4. Withdraw\n 5. Deposit\n 6. Get Monthly Interest\n 7. Print Account Details\n 8. Exit\n Enter your choice : ");

            int choice=scanner.nextInt();
            System.out.println("\n==================================\n");
            switch(choice){
                case 1:
                    System.out.println("Your balance is ="+acc.getBalance());
                    break;
                case 2:
                    System.out.println("Your interest rate is ="+acc.getAnnualInterestRate());
                    break;
                case 3:
                    System.out.println("Date of account creation is ="+acc.getDateCreated());
                    break;
                case 4:
                    withdrawPrompt(acc);
                    System.out.println("Balance after withdrawal = "+acc.getBalance());
                    break;
                case 5:
                    depositPrompt(acc);
                    System.out.println("Balance after Deposition = "+acc.getBalance());
                    break;
                case 6:
                    System.out.println("Your monthly interest is : "+acc.getMonthlyInterest());
                    break;
                case 7:
                    System.out.println(acc);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Enter a valid choice");

            }

            System.out.println("\n==========================================\n\n");

        }
    }

    public static Account chooseAccount(int id){
        for(int counter=0;counter<accounts.length;counter++){
            if(accounts[counter].getId()==id){
                return accounts[counter];
            }
        }

        return accounts[0];
    }

    public static void withdrawPrompt(Account acc){
        System.out.println("Enter the amount to be withdrawn :");
        double amount=scanner.nextDouble();
        acc.withdraw(amount);
    }

    public static void depositPrompt(Account acc){
        System.out.println("Enter the amount to be deposited :");
        double amount=scanner.nextDouble();
        acc.deposit(amount);
    }

}