import java.util.Scanner;

/** Class represents an individual dock located within a SeaPort.
 * Contains a getter and setter for a ship assigned to the Dock object.*/

public class Dock extends Thing {
    private Ship ship;

    public Dock(Scanner scanner){
        super(scanner);
    }

    /** Set Ship to dock*/
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /** returns Ship currently docked*/
    public Ship getShip() {
        return ship;
    }

    /** Formats the search result display for Dock objects */
    public String formatPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getIndex());
        stringBuilder.append("\nName:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getName());
        stringBuilder.append("\nCurrent Ship Docked:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getShip());
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }
}
