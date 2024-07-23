import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HotelReservationSystem {

    private static Map<Integer, Room> rooms = new HashMap<>();
    private static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize rooms
        rooms.put(1, new Room(1, "Single", 50.0));
        rooms.put(2, new Room(2, "Double", 80.0));
        rooms.put(3, new Room(3, "Suite", 150.0));

        // Main loop
        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Search for rooms");
            System.out.println("2. Make reservation");
            System.out.println("3. View booking details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    searchRooms(scanner);
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewBookingDetails();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Search for available rooms
    private static void searchRooms(Scanner scanner) {
        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();

        System.out.println("\nAvailable rooms:");
        for (Room room : rooms.values()) {
            if (room.isAvailable(checkInDate, checkOutDate)) {
                System.out.println(room);
            }
        }
    }

    // Make reservation
    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Room room = rooms.get(roomNumber);
        if (room == null) {
            System.out.println("Invalid room number.");
            return;
        }

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();

        if (!room.isAvailable(checkInDate, checkOutDate)) {
            System.out.println("Room is not available for the specified dates.");
            return;
        }

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        Reservation reservation = new Reservation(room, checkInDate, checkOutDate, guestName);
        reservations.add(reservation);

        room.addReservation(reservation);

        System.out.println("Reservation successful!");
        System.out.println("Reservation ID: " + reservation.getId());
    }

    // View booking details
    private static void viewBookingDetails() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("\nBooking details:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}

// Room class
class Room {
    private int roomNumber;
    private String type;
    private double pricePerNight;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }

    // Check if room is available for given dates
    public boolean isAvailable(String checkInDate, String checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.overlaps(checkInDate, checkOutDate)) {
                return false;
            }
        }
        return true;
    }

    // Add reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + ", Type: " + type + ", Price per night: $" + pricePerNight;
    }
}

// Reservation class
class Reservation {
    private static int nextId = 1;
    private int id;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private String guestName;

    public Reservation(Room room, String checkInDate, String checkOutDate, String guestName) {
        this.id = nextId++;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestName = guestName;
    }

    // Check if reservation overlaps with given dates
    public boolean overlaps(String checkInDate, String checkOutDate) {
        return (this.checkInDate.compareTo(checkOutDate) <= 0 && this.checkOutDate.compareTo(checkInDate) >= 0);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + id + ", Room: " + room.getRoomNumber() + ", Check-in: " + checkInDate
                + ", Check-out: " + checkOutDate + ", Guest: " + guestName;
    }
}
