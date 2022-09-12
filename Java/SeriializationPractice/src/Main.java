import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
//        ArrayList<NotSerializable> arrayList = new ArrayList<>();
//
//        arrayList.add(new NotSerializable(1,2));
//        arrayList.add(new NotSerializable(3,4));
//
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("somfile.txt"));
//
//        objectOutputStream.writeObject(arrayList);
//
//        objectOutputStream.close();

        objectOutputStream.write(20);
//        objectOutputStream.write(30);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("somfile.txt"));
        System.out.println(objectInputStream.readInt());
//        System.out.println(objectInputStream.readInt());


    }
}
