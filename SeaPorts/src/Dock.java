import java.util.Scanner;

public class Dock extends Thing {
    private Ship ship;

    public Dock(Scanner scanner){
        super(scanner);
    }

    public int getIndex(){
       return super.getIndex();
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

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
