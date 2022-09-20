import java.time.LocalDate;
import java.util.Scanner;

public class HelpDeskApp {

    public static Scanner stringScanner;
    public static Scanner otherScanner;

    public static void main(String[] args) {

        stringScanner = new Scanner(System.in);
        otherScanner = new Scanner(System.in);

        Hospital hospital = createAHospital();
        userPrompt(hospital);

    }


    //the main user prompt for all kind of users asks what kind of user u are and based on that assigns roles
    public static void userPrompt(Hospital hospital){

        while(true){
            System.out.println("Enter the type of user \n " +
                    "1 For Doctor \n" +
                    " 2 For Patient \n " +
                    "3 For Admin \n"+
                    "4 to Exit");

            switch (otherScanner.nextInt()){
                case 1:
                    doctorPrompt(hospital);
                    break;
                case 2:
                    patientPrompt(hospital);
                    break;
                case 3:
                    adminPrompt(hospital);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter a valid choice : ");

            }
        }

    }



    //user interface for admin can either fetch records based on id or transfer patient from one doctor to another
    public static  void adminPrompt(Hospital hospital){
        while(true){
            System.out.println("Enter \n" +
                    "1 For Searching patient records \n" +
                    "2 For transferring patient \n" +
                    "3 to go back");


            switch(otherScanner.nextInt()){
                case 1:
                    getPatientById(hospital);
                    break;
                case 2:
                    transferPatient(hospital);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }


    //interface for doctor he can check patients waiting in the department or also visit patient in the room assigned to him
    public static void doctorPrompt(Hospital hospital){

        System.out.println("Enter the Doc ID : ");
        Doctor doctor = hospital.getDoctorById(otherScanner.nextInt());

        if(doctor!=null){
            while (true){
                System.out.println("Enter \n" +
                        "1 to perform check up of patients\n" +
                        "2 to perform visit of admitted patient \n" +
                        "3 to go back");

                switch (otherScanner.nextInt()){
                    case 1:
                        System.out.println("Enter the maximum number of patients you want to check at once..");
                        doctor.checkPatient(otherScanner.nextInt(),stringScanner);
                        break;
                    case 2:
                        doctor.visitPatient();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Enter a valid choice..");
                }
            }
        }

        System.out.println("Doctor by given id not present...");
    }

    //interface for the patient he can wither visit a doctor or get admitted or vacate the room allocated to him
    public static void patientPrompt(Hospital hospital){

        //register a patient if not registered with hospital
        Patient patient = createPatient(hospital);

        if(patient == null){
            return;
        }

        while (true){
            System.out.println("Enter \n" +
                    "1 to visit a doctor \n" +
                    "2 to get admitted \n" +
                    "3 to vacate a room \n"+
                    "4 to go back ");

            switch(otherScanner.nextInt()){
                case 1:
                    visitADoctor(hospital,patient);
                    break;
                case 2:
                    getAdmitted(hospital,patient);
                    break;
                case 3:
                    vacateRoom(patient);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter a valid choice..");
            }
        }
    }



    //get patient based on id and prints the appropriate message
    public static void getPatientById(Hospital hospital){
        System.out.println("Enter the id of the patient : ");
        Patient patient = hospital.isPatientAlreadyRegistered(otherScanner.nextInt());

        if(patient==null) {
            System.out.println("Patient not in our database...");
            return;
        }
        System.out.println("Patient details are : ");
        System.out.println(patient);
    }


    // used for transferring patient from one doctor to another based on id of the doctors and patient
    public static void transferPatient(Hospital hospital){
        System.out.println("Doctors in hospital are : ");
        for(Doctor doctor : hospital.getDoctors()){
            System.out.println(doctor);
        }
        Doctor fromDoctor,toDcotor;
        while(true){
            System.out.println("Enter the transferring doctor  id :");
            fromDoctor = hospital.getDoctorById(otherScanner.nextInt());

            if(fromDoctor!=null){
                break;
            }
        }

        while(true){
            System.out.println("Enter the id of the doctor to be transferred to:");
            toDcotor = hospital.getDoctorById(otherScanner.nextInt());

            if(toDcotor!=null){
                break;
            }
        }
            System.out.println("Enter the id of patient to be transferred");
            Patient patient = fromDoctor.getPatientById(otherScanner.nextInt());

            if(patient==null){
                System.out.println("Patient not present in the doctors appointment list..");
                return;
            }

            hospital.transferThePatient(fromDoctor,toDcotor,patient);
        System.out.println("patient successfully transferred...");
    }








    //used by patient to vacate the room assigned to him
    public static void vacateRoom(Patient patient){

        Room room = patient.getRoom();

        if(room==null){
            System.out.println("No rooms occupied  under this patient");
            return;
        }
        patient.setRoom(null);
        room.setOccupied(false);
        room.setPatient(null);

        System.out.println("Room vacated successfully..");
    }


    //patient visiting a doctor
    public static void visitADoctor(Hospital hospital,Patient patient){

        System.out.println("Enter the name of the department you want to visit : ");
        Department department = hospital.isDepartmentPresent(stringScanner.nextLine());

        if(department== null){
            return;
        }

        System.out.println("Enter the name of the doctor u want to visit : ");
        Doctor doctor = department.doesDoctorExists(stringScanner.nextLine());

        if(doctor.areMaxPatientsAllocated())
            return;


        doctor.getPatients().add(patient);

        System.out.println("Your appointment has been made the doctor will come in a short while please wait till then...");


    }




    //patient getting admitted
    public static void getAdmitted(Hospital hospital,Patient patient){


        System.out.println("Enter the number of days for which u want to get admitted");
        int noOfDays = otherScanner.nextInt();

        hospital.printVacantRooms();

        Room room;
        while(true){
            System.out.println("Enter the room number in which u want to get admitted..");

            room = hospital.getVacantRoomById(otherScanner.nextInt());

            if(room==null){
                System.out.println("Room you chose is currently not available..");
                continue;
            }
            break;
        }
        double amount = payAfterInsurance(room,noOfDays);

        System.out.println("Enter the reason for which u want to get admitted : ");
        String reason = stringScanner.nextLine();
        patient.makePayment(amount,new MedicalRecord(LocalDate.now(),reason,"admit",noOfDays, room.getRoomId(), room.getDoctors().getDoctorId(),room.getDoctors().getName(),amount));

        room.setOccupied(true);
        room.setPatient(patient);
        patient.setRoom(room);

    }

    //payment after applying insurance
    public static double payAfterInsurance(Room room,int noOfDays){
        double amount = (room.getPriceForADay()+room.getDoctors().getDoctorsFee())*noOfDays;
        amount-=amount*Insurance.discount;

        return amount;
    }



    //registering a new patient
    public static Patient createPatient(Hospital hospital){
        System.out.println("If you are a already registered user press y");
        if(stringScanner.nextLine().equalsIgnoreCase("y")){
            System.out.println("Enter the patient id :");
            return hospital.isPatientAlreadyRegistered(otherScanner.nextInt());
        }
        int id;
        while (true){
            System.out.println("Enter a id for patient : ");
             id = otherScanner.nextInt();
            if(hospital.isPatientAlreadyRegistered(id)==null){
                break;
            }
        }
        System.out.println("Enter the name of patient : ");
        String name = stringScanner.nextLine();
        System.out.println("Enter the age of patient : ");
        int age= otherScanner.nextInt();
        System.out.println("Enter the gender of the patient : ");
        String gender = stringScanner.nextLine();
        System.out.println("Enter the telephone number : ");
        long telNumber = otherScanner.nextLong();

        System.out.println("Do you want to create an insurance policy in our hospital ? (enter yes/no)");
        if(!stringScanner.nextLine().equalsIgnoreCase("yes")){
            System.out.println("Sorry you cant register in our hospital wihout using our insurance policy");
            return null;
        }



        Patient patient =  new Patient(id,name,age,gender,telNumber,new Insurance(id,name,age,gender,LocalDate.now().plusYears(2)));
        hospital.getRegisteredPatients().add(patient);

        return patient;
    }


    //creates a hospital
    public static Hospital createAHospital(){

        Hospital hospital = new Hospital();

        createDepartMents(hospital);


        createDoctors(hospital);

        return hospital;
    }



    //creates a list of doctors using user input
    public static void createDoctors(Hospital hospital){

        System.out.println("Enter the number of doctors u want to create : ");
        int noOfDoctrs = otherScanner.nextInt();

        while(noOfDoctrs>0){

            System.out.println("Enter the id of the doctor : ");
            int docId = otherScanner.nextInt();

            if(hospital.getDoctorById(docId)!=null){
                System.out.println("Doctor with id = " + docId + "already exists..");
                continue;
            }



            System.out.println("Enter the name of the doctor : ");
            String docName = stringScanner.nextLine();

            System.out.println("Enter the fee taken by the doctor : ");
            double docFee = otherScanner.nextDouble();

            Doctor doctor = new Doctor(docId,docName,docFee);

            System.out.println("To assign a department press 1 \n To assign a Room press 2");
            int choice = otherScanner.nextInt();

            if(choice==1){
                if (!addDoctorsIntoDepartment(hospital,doctor))
                    continue;

            }else if(choice==2){

                if(!assignARoomToDoctor(hospital,doctor))
                    continue;

            }else {
                System.out.println("Not a valid choice..");
                continue;
            }

            noOfDoctrs--;



        }


    }


    //creates list of department using user input
    public static void createDepartMents(Hospital hospital){


        System.out.println("Enter the number of departments u want to create:");
        int noOfDepartments = otherScanner.nextInt();

        while(noOfDepartments>0){
            System.out.println("enter the name of the department : ");
            String depName = stringScanner.nextLine();

            if(hospital.isDepartmentPresent(depName)!=null){
                System.out.println("department already present..");
                continue;
            }

            hospital.getDepartments().add(new Department(depName));
            noOfDepartments--;
        }

    }



    //adds doctor into the hospital and assigns them a department
    public static boolean addDoctorsIntoDepartment(Hospital hospital,Doctor doctor){
        System.out.println("Enter the name of the department u want to assign : ");
        Department department = hospital.isDepartmentPresent(stringScanner.nextLine());

        if(department==null)
            return false;

        hospital.getDoctors().add(doctor);
        department.getDoctors().add(doctor);

        return  true;
    }


    //creates a room and assigns it to a doctor
    public static boolean assignARoomToDoctor(Hospital hospital,Doctor doctor){
        System.out.println("Enter the room id : ");
        int roomId = otherScanner.nextInt();

        for(Room room:hospital.getRooms()){
            if(room.getRoomId()==roomId){
                System.out.println("Room already assigned to some other doctor..");
                return false;
            }
        }

        System.out.println("Enter the price for the room..");
        Room room = new Room(roomId,doctor,false,otherScanner.nextDouble());

        hospital.getRooms().add(room);
        doctor.setRoom(room);
        hospital.getDoctors().add(doctor);

        return  true;
    }





}
