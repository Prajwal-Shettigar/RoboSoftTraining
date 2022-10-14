import java.util.ArrayList;
import java.util.List;

public class BookShop implements Cloneable{

    private String name;
    private List<Book> books = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }


    //add data
    public void loadData(int count){
        for(int i=1;i<=count;i++){
            getBooks().add(new Book(i,"Book "+i));
        }
    }


    public BookShop clone(String name){

        //create a new object
        BookShop bookShop = new BookShop();

        //add the name
        bookShop.setName(name);

        //add data of the current object to new object
        books.forEach((book)->bookShop.getBooks().add(book));

        return bookShop;
    }


    @Override
    public String toString() {
        return "BookShop{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
