public class Something implements Add,Add2{
    @Override
    public void add(int a, int b) {

    }

    @Override
    public void defaultMethod(int a, int b) {
        Add.super.defaultMethod(a, b);
    }
}
