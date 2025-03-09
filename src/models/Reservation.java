package src.models;

public class Reservation {
    private int id;
    private int flightId;
    private int passengerId;

    public Reservation(int id, int flightId, int passengerId) {
        this.id = id;
        this.flightId = flightId;
        this.passengerId = passengerId;
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
}
