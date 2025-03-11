package src.services;

import src.models.Flight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightService {
    public static boolean addFlight(String code, String destination, String date, int seats) {
        flights.add(new Flight("1",code, destination, LocalDate.parse(date), seats));
    }

    public static boolean removeFlight(int code) {
        flights.add(new Flight("1",code, destination, LocalDate.parse(date), seats));
    }

    public static List<Flight> getFlights() {
        return flights;
    }

    public static Flight getFlightById(int id){

    }

    public static boolean updateFlight(int flightId, String flightCode, String destination, String date, int seats){

    }
}
