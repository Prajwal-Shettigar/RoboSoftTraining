import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner strngScanner = new Scanner(System.in);

    private static Scanner otherScanner = new Scanner(System.in);


    public static void main(String[] args) {

        //create a hospital
        Hospital hospital  = new Hospital("my hospital");

        //add dcotors into hospital
        addDoctors(hospital);

        //add helpdesk facility to hospital
        hospital.setHelpDesk(new HelpDesk());


        //main user prompt
        mainPrompt(hospital);
    }



    //main prompt for all type of users
    public static void mainPrompt(Hospital hospital){

        while(true) {
            System.out.println("Enter the type of user : \n 1. For Doctor \n 2. For Patient  \n 3. Other \n 4. To Exit");
            switch (otherScanner.nextInt()) {
                case 1 -> doctorPrompt(hospital);

                case 2 -> patientPrompt(hospital);

                case 3->otherUserPrompt(hospital);

                case 4 -> {
                    return;
                }

                default -> System.out.println("Enter a valid choice..");
            }
        }
    }


    //main prompt for patients
    public static void patientPrompt(Hospital hospital){

        while (true) {
            System.out.println("Enter the type of patient \n 1. For In Patient \n 2. For Out Patient \n 3.To Exit");
            switch (otherScanner.nextInt()) {
                case 1 -> inPatientPrompt(hospital);

                case 2 -> outPatientPrompt(hospital);

                case 3 -> {
                    return;
                }

                default -> System.out.println("Enter a valid choice");
            }
        }

    }




    public static void doctorPrompt(Hospital hospital){
        //TODO:doctorPrompt
    }




    public static void outPatientPrompt(Hospital hospital){
        //TODO:outpatientPrompt
    }


    //in patient prompt
    public static void inPatientPrompt(Hospital hospital) {
        while (true) {
            System.out.println("Enter \n 1. For Registration \n 2. For Booking An Appointment \n 3. To get Medical Records \n 4. To get All Doctor Details \n 5. To Go Back");
            switch (otherScanner.nextInt()) {
                case 1 -> inPatientRegistartionPrompt(hospital);

                case 2 -> appointmentPrompt(hospital);

                case 3 -> getMedRecordsPrompt(hospital);

                case 4->hospital.getHelpDesk().getAllDoctorsWIthDepartment();

                case 5->{
                    return;
                }

                default -> System.out.println("Enter a valid choice");
            }
        }
    }

    public static void otherUserPrompt(Hospital hospital){
        //TODO:otheruser
    }


    //appointment booking prompt
    public static void appointmentPrompt(Hospital hospital){

        System.out.println("Enter the patient id : ");
        int patientId = otherScanner.nextInt();

        System.out.println("Enter the name of the doctor : ");
        String docName = strngScanner.nextLine();

        System.out.println("Enter the department : ");
        String department = strngScanner.nextLine();

        System.out.println(hospital.getHelpDesk().bookAnAppointMent(patientId,docName,department));
    }

    //registration prompt
    public static void inPatientRegistartionPrompt(Hospital hospital){

        System.out.println("Enter the name");
        String name = strngScanner.nextLine();

        System.out.println("Enter the age");
        int age = otherScanner.nextInt();

        System.out.println("Enter the gender");
        String gender = strngScanner.nextLine();

        System.out.println("Enter the telephone number");

        Patient patient = hospital.getHelpDesk().registerAnInPatient(name,age,gender,otherScanner.nextLong());

        System.out.println("Patient Registered Successfully.. & your patient id is "+patient.getPatientId());

    }


    //get patient records by id
    public static void getMedRecordsPrompt(Hospital hospital){
            System.out.println("Enter the patient id : ");
            int patientId = otherScanner.nextInt();

            List<Record> medRecords = hospital.getHelpDesk().getMedicalRecordsById(patientId);

            if(medRecords==null || medRecords.size()<1){
                System.out.println("Currently no medical records available for this id");
                return;
            }

            for (Record record:medRecords){
                System.out.println(record);
            }
    }












    //utility methods
    public static void addDoctors(Hospital hospital){
        int doctorCount = 0;
        for(int i =1;i<6;i++){
            for(int j=1;j<3;j++){
                doctorCount++;
                hospital.addDoctor("doctor"+doctorCount,"department"+i,"roomNo"+doctorCount);
            }
        }
    }


}