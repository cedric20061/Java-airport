package src.models;

import java.io.Serializable;

public class Flight implements Serializable {
    private String numeroVol;
    private String destination;
    private String dateDepart; // format YYYY-MM-DD
    private int nombrePlaces;

    public Flight(String numeroVol, String destination, String dateDepart, int nombrePlaces) {
        this.numeroVol = numeroVol;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.nombrePlaces = nombrePlaces;
    }

    // Getters et setters

    @Override
    public String toString() {
        return numeroVol + ";" + destination + ";" + dateDepart + ";" + nombrePlaces;
    }

    public static Flight fromString(String line) {
        String[] parts = line.split(";");
        return new Flight(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
    }
}
