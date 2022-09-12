public class UserM {

    public static void main(String[] args) {

        User user1 = new User.UserBuilder("Lokesh", "Gupta")
                .age(30)
                .phone("1234567")
                .address("Fake address 1234")
                .build();

        System.out.println(user1);

        User user2 = new User.UserBuilder("Jack", "Reacher")
                .age(40)
                .phone("5655")
                .build();

        System.out.println(user2);

        User user3 = new User.UserBuilder("Super", "Man")
                .build();

        System.out.println(user3);
    }


}


