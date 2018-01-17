import java.util.Scanner;

public class PassengerShip extends Ship {
    private int numberOfOccupiedRooms;
    private int numberOfPassengers;
    private int numberOfRooms;

    //weight length width draft numPassengers numRooms numOccupied

    public PassengerShip(Scanner scanner){
        super(scanner);
        if (scanner.hasNextInt())
            this.numberOfPassengers = scanner.nextInt();
        if (scanner.hasNextInt())
            this.numberOfRooms = scanner.nextInt();
        if (scanner.hasNextInt())
            this.numberOfOccupiedRooms = scanner.nextInt();
    }

    public String formatPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getIndex());
        stringBuilder.append("\nName:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getName());
        stringBuilder.append("\nNumber of Passengers:");
        stringBuilder.append("\n\t");
        stringBuilder.append(numberOfPassengers);
        stringBuilder.append("\nNumber of Rooms:");
        stringBuilder.append("\n\t");
        stringBuilder.append(numberOfRooms);
        stringBuilder.append("\nNumber of Rooms Used:");
        stringBuilder.append("\n\t");
        stringBuilder.append(numberOfOccupiedRooms);
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }
    @Override
    public String toString() {
        String st = "Passenger ship: " + super.toString();
        if (super.getJobs().size() == 0)
            return st;
        for (Job mj: super.getJobs()) st += "\n - " + mj;
        return st;
    }
}
