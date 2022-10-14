public class Main {
    public static void main(String[] args) {


        BookShop bookShop1 = new BookShop();
        bookShop1.setName("BookShop1");
        bookShop1.loadData(10);

        BookShop bookShop2 = bookShop1.clone("BookShop2");
        bookShop1.getBooks().remove(2);

        System.out.println(bookShop1);
        System.out.println(bookShop2);

    }
}