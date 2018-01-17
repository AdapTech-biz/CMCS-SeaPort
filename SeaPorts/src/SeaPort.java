import java.util.ArrayList;
import java.util.Scanner;

public class SeaPort extends Thing {
    private ArrayList<Dock> docks;
    private ArrayList<Ship> que;    //ships waiting to dock
    private ArrayList<Ship> ships;  //all ships @ port
    private ArrayList<Person> persons;  //people w/ skill @ port


    public SeaPort(Scanner scanner){
        super(scanner);
        ships = new ArrayList<>();
        que = new ArrayList<>();
        persons = new ArrayList<>();
        docks = new ArrayList<>();
//        System.out.println("Creating new port..");
    }

    public ArrayList<Ship> getShips() {
        return new ArrayList<>(ships);
    }

    public void addShip(Ship ship){
        ships.add(ship);
    }

    public void addToQue(Ship ship){
        que.add(ship);
    }

    public Dock findDockById (int id){
        for (Dock dock : docks){
            if(dock.getIndex() == id)
                return dock;
        }
        return null;
    }

    public void addDock(Dock dock){
        this.docks.add(dock);
    }

    public void addPerson(Person person){
        this.persons.add(person);
    }

    public ArrayList<Person> getPersons() {
        return new ArrayList<>(persons);
    }

    public ArrayList<Dock> getDocks() {
        return new ArrayList<>(docks);
    }

    @Override
    public String toString() {
        String st = "\n\nSeaPort: " + super.toString();
        for (Dock md: docks) st += "\n" + md;
        st += "\n\n --- List of all ships in que:";
        for (Ship ship: que ) st += "\n > " + ship;
        st += "\n\n --- List of all ships:";
        for (Ship ship: ships) st += "\n > " + ship;
        st += "\n\n --- List of all persons:";
        for (Person person: persons) st += "\n > " + person;
        return st;
    }
}
