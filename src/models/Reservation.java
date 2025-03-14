package src.models;

public class Reservation {
    private int id;
    private int flightId;
    private int passengerId;
    private String seatNumber;

    public Reservation(int id, int flightId, int passengerId, String seatNumber) {
        this.id = id;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
    }

    public int getId() {
        return id;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}
