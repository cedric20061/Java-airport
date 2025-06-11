package console;

import java.util.Scanner;

import main.AirportHandler;
import models.Flight;
import models.Reservation;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("1. Ajouter un vol");
            System.out.println("2. Réserver une place");
            System.out.println("3. Afficher les vols");

            int choix = sc.nextInt();
            sc.nextLine(); // flush
            switch (choix) {
                case 1:
                    System.out.print("Numéro du vol : ");
                    String num = sc.nextLine();
                    System.out.print("Destination : ");
                    String dest = sc.nextLine();
                    System.out.print("Date départ (YYYY-MM-DD) : ");
                    String date = sc.nextLine();
                    System.out.print("Nombre de places : ");
                    int places = sc.nextInt();

                    Flight flight = new Flight(num, dest, date, places);
                    AirportHandler.addFlight(flight);
                    System.out.println("Vol ajouté !");
                    break;

                case 2:
                    System.out.print("Nom du client : ");
                    String clientName = sc.nextLine();
                    System.out.print("Numéro du vol : ");
                    String flightId = sc.nextLine();
                    System.out.print("Nombre de places réservées : ");
                    int seatsReserved = sc.nextInt();
                    Reservation reservation = new Reservation(clientName, flightId, seatsReserved);
                    AirportHandler.bookFlight(reservation);
                    System.out.println(flightId + " réservé pour " + clientName + " avec " + seatsReserved + " places.");
                    break;
                default:
                    break;
            }
        }
    }
}
