package models;

import java.io.Serializable;

public class Reservation implements Serializable {
    private int id;
    private int clientId;
    private String clientName;
    private String flightId;
    private int seatsReserved;

    public Reservation(int id, int clientId, String clientName, String flightId, int seatsReserved) {
        this.id = id;
        this.clientId  = clientId;
        this.clientName = clientName;
        this.flightId = flightId;
        this.seatsReserved = seatsReserved;
    }

    // Getters et setters
    public int getId(){
        return this.id;
    }
    public String getClientName() {
        return this.clientName;
    }

    public int getClientId() {
        return this.clientId;
    }

    public int getSeatsReserved() {
        return this.seatsReserved;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public int setSeatsReserved(int newNumber){
        this.seatsReserved = newNumber;
        return this.seatsReserved;
    }
    @Override
    public String toString() {
        return id + ";" + clientId + ";" + clientName + ";" + flightId + ";" + seatsReserved;
    }

    public static Reservation fromString(String line) {
        String[] parts = line.split(";");
        return new Reservation(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], Integer.parseInt(parts[4]));
    }
}
