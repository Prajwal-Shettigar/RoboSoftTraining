import java.util.ArrayList;
import java.util.List;

public class Hospital {


    private List<Department> departments;

    private List<Room> rooms;

    private List<Patient> registeredPatients;

    private List<Doctor> doctors;


    //constructor
    public Hospital() {
        this.departments = new ArrayList<>();
        this.rooms =new ArrayList<>();
        registeredPatients = new ArrayList<>();
        doctors = new ArrayList<>();
    }

    //check if the patient is already registered
    public Patient isPatientAlreadyRegistered(int referenceId) {
        for (Patient patient : registeredPatients) {
            if (referenceId == patient.getReferenceId())
                return patient;
        }

        return null;
    }


    //check if the department exists if yes return it
    public Department isDepartmentPresent(String departmentName) {
        for (Department department : departments) {
            if (department.getName().equalsIgnoreCase(departmentName))

                return department;

        }
        return null;
    }

    //get details of rooms that are vacant
    public List<Room> getRoomsThatAreVacant(){
        List<Room> vacantRooms = new ArrayList<>();

        for(Room room : rooms){
            if(!room.isOccupied()){
                vacantRooms.add(room);
            }
        }

        return vacantRooms;
    }



    //print the rooms that are vacant
    public void printVacantRooms(){
        System.out.println("Rooms available in hospital are : ");
        for(Room room : getRoomsThatAreVacant()){
            System.out.println(room);
        }
    }

    //transfer the patient
    public boolean transferThePatient(Doctor from,Doctor to,Patient patient){
        if(to.areMaxPatientsAllocated())
            return false;
        if(!from.getPatients().remove(patient))
            return false;

        to.getPatients().add(patient);
    return true;

    }


    //check by id if room available
    public Room getVacantRoomById(int roomId){
        for(Room room:getRoomsThatAreVacant()){
            if(room.getRoomId()==roomId)
                return room;
        }
        return null;
    }


    //find doctor by id
    public Doctor getDoctorById(int docId){
        return doctors.stream().filter(doctor -> doctor.getDoctorId()==docId).findAny().orElse(null);
    }


    //getters
    public List<Department> getDepartments() {
        return departments;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Patient> getRegisteredPatients() {
        return registeredPatients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
}