import java.util.Date;

class Account{

    private int id;
    private double balance;
    private double annualInterestRate;
    private Date dateCreated;

    //without arg constructor
    Account(){
        dateCreated=new Date();
    }

    //with arg constructor
    Account(int id,double balance){
        this();
        this.setId(id);
        this.setBalance(balance);
    }

    //id getter and setter
    public int getId(){
        return id;
    }
    private void setId(int id){
        this.id=id;
    }

    //balance getter and setter
    public double getBalance(){
        return balance;
    }

    private void setBalance(double balance){
        this.balance=balance;
    }

    //annualInterestRate getter and setter
    public double getAnnualInterestRate(){
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate){
        this.annualInterestRate=annualInterestRate;
    }


    public String getDateCreated(){
        return dateCreated.toString();
    }


    public void withdraw(double amount){
        if(amount>this.getBalance()){
            System.out.println("Insuffucient balance ");
            System.out.println("Maximum ammount possible to be withdrawn : "+this.getBalance());
            return;
        }
        this.balance-=amount;
    }

    public void deposit(double amount){
        this.balance+=amount;
    }

    public double getMonthlyInterest(){
        return balance*((annualInterestRate/100)/12);
    }

    public String toString(){
        return " Account id : "+id+"\n Balance : "+this.getBalance()+"\n Annual Interest Rate : "+this.getAnnualInterestRate()+"\n Date created : "+this.getDateCreated();
    }

}