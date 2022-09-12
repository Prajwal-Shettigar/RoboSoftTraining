public class Result extends Test implements Sports{

    public Result(String name, int rollNumber, int mathsMarks, int kannadaMarks, int englishMarks) {
        super(name, rollNumber, mathsMarks, kannadaMarks, englishMarks);
    }

    @Override
    public void show_sportswt() {
        System.out.println("Marks attained in sports : "+SPORTSMARKS);
    }


    public void showTotalMarks(){
        System.out.println("Total Marks : "+(mathsMarks+kannadaMarks+englishMarks+SPORTSMARKS));
    }
}
