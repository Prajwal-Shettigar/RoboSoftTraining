public class Main {

    public static void main(String[] args) {

        //student reference to result can only access those members common in student and result and that which
        //are present in student
        Student student = new Result("prajwal",29,70,69,69);
        student.show();

        //test reference to result can access common and test data members
        Test test = new Result("prajwal",29,70,69,69);
        test.show();
        test.show_marks();
        
        Result result = new Result("prajwal",29,70,69,69);
        result.show();
        result.show_sportswt();

        Sports sports = new Result("prajwal",29,70,69,69);
        sports.show_sportswt();
    }
}
