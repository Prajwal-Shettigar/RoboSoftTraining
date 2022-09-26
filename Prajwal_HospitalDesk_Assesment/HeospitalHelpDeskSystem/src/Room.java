public class Room {

    private int roomId;
    private Doctor doctor;
    private boolean occupied;

    private Patient patient;

    private double priceForADay;



    //constructor
    public Room(int roomId, Doctor doctor, boolean occupied, double priceForADay) {
        this.roomId = roomId;
        this.doctor = doctor;
        this.occupied = occupied;
        this.priceForADay = priceForADay;
    }


    //getters and setters
    public int getRoomId() {
        return roomId;
    }

    public Doctor getDoctors() {
        return doctor;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getPriceForADay() {
        return priceForADay;
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", doctor=" + doctor +
                ", occupied=" + occupied +
                ", patient=" + patient +
                ", priceForADay=" + priceForADay +
                '}';
    }
}
