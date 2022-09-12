public class Test extends Student{
    int mathsMarks;
    int kannadaMarks;
    int englishMarks;

    public Test(String name, int rollNumber, int mathsMarks, int kannadaMarks, int englishMarks) {
        super(name, rollNumber);
        this.mathsMarks = mathsMarks;
        this.kannadaMarks = kannadaMarks;
        this.englishMarks = englishMarks;
    }

    public void show_marks(){
        System.out.println("\t\tMarks\n" +
                "\tMaths Marks : "+mathsMarks+"\n" +
                "\tKannada Marks : "+kannadaMarks+"\n" +
                "\tEnglish Marks : "+englishMarks);
    }
}
