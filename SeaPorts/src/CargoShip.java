import java.util.Scanner;

public class CargoShip extends Ship{
    private double cargoValue;
    private double cargoVolume;
    private double cargoWeight;

    // weight length width draft cargoWeight cargoVolume cargoValue


    public CargoShip (Scanner scanner){
        super(scanner);
        if (scanner.hasNextDouble())
            this.cargoWeight = scanner.nextDouble();
        if (scanner.hasNextDouble())
            this.cargoVolume = scanner.nextDouble();
        if (scanner.hasNextDouble())
            this.cargoValue = scanner.nextDouble();
    }

    public String formatPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getIndex());
        stringBuilder.append("\nName:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getName());
        stringBuilder.append("\nCargo Value:");
        stringBuilder.append("\n\t");
        stringBuilder.append(cargoValue);
        stringBuilder.append("\nCargo Weight:");
        stringBuilder.append("\n\t");
        stringBuilder.append(cargoWeight);
        stringBuilder.append("\nCargo Volume:");
        stringBuilder.append("\n\t");
        stringBuilder.append(cargoVolume);
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        String st = "Cargo ship: " + super.toString();
        if (super.getJobs().size() == 0)
            return st;
        for (Job mj: super.getJobs()) st += "\n - " + mj;
        return st;
    }
}
