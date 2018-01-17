import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class World extends Thing {
    private ArrayList<SeaPort> ports;
    private PortTime time;

    public World(String name, int index, int parent, File file) {
        super(name, index, parent);
        ports = new ArrayList<>();
        readFile(file);
    }

    private Ship getShipByIndex (int x) {
        for (SeaPort port: ports)
            for (Ship ship: port.getShips())
                if (ship.getIndex() == x)
                    return ship;
        return null;
    } // end getDockByIndex

    private Dock getDockByIndex (int dockIndex){
        for(SeaPort port : ports){
            Dock dock = port.findDockById(dockIndex);
           if(dock != null)
               return dock;
        }
        return null;
    }
    private SeaPort getSeaPortByIndex(int index){
        for (SeaPort port : ports){
            if (port.getIndex() == index)
                return port;
        }
        return null;
    }

    private void assignShip (Ship ship) {
        Dock dock = getDockByIndex (ship.getParent());
        SeaPort port = getSeaPortByIndex(ship.getParent());
        if (dock == null) {
            if (port != null){
                port.addShip(ship);
                port.addToQue(ship);
            }
            return;
        }
        if (port == null) {
            if (dock.getShip() == null){
                dock.setShip(ship);
                return;
            }
            System.out.println(dock.toString() + " is full");
//            port.addShip(ship);
        }
    } // end method assignShip

    public  void readFile(File file){
//        String filename = "./PopulatedData/Test.txt";
        try{
            Scanner scanner = new Scanner(new FileReader(file));
            while (scanner.hasNextLine()){
               String line = scanner.nextLine();
               if (line.startsWith("//") || line.isEmpty()){
                   continue;
               }
                parseData(line);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void parseData(String line){
        Scanner data = new Scanner(line.trim());
        String keyWord = data.next();
        switch (keyWord.toUpperCase()){
            case "PORT":
                SeaPort port = new SeaPort(data);
                ports.add(port);
                break;
            case "DOCK":
                Dock dock = new Dock(data);
                SeaPort seaPort = getSeaPortByIndex(dock.getParent());
                if (seaPort != null)
                    seaPort.addDock(dock);
                // assign to Seaport
                break;
            case "PSHIP":
                PassengerShip passengerShip = new PassengerShip(data);
                assignShip(passengerShip);
                break;
            case "CSHIP":
                CargoShip cargoShip = new CargoShip(data);
                assignShip(cargoShip);
                break;
            case "PERSON":
                Person person = new Person(data);
                seaPort = getSeaPortByIndex(person.getParent());
                if (seaPort != null)
                    seaPort.addPerson(person);
                //associate to Seaport
                break;
            case "JOB":
                Job job = new Job(data);
                Ship ship = getShipByIndex(job.getParent());
                if (ship != null)
                    ship.addJob(job);
                // assign to ship
                break;
        }

    }

    public ArrayList<? extends Thing> search(String subject, String attribute, String keyword){
        ArrayList<? extends Thing> searchList = new ArrayList<>();
        ArrayList<PassengerShip> passengerShips = new ArrayList<>();
        ArrayList<CargoShip> cargoShips = new ArrayList<>();
        ArrayList<Dock> docks = new ArrayList<>();
        ArrayList<Job> jobs = new ArrayList<>();
        ArrayList<Person> personList = new ArrayList<>();
//        System.out.println("Attribute is " + attribute);
//        System.out.println("Key word is " + keyword);
        for (SeaPort port : ports){
            switch (subject.toUpperCase()){
                case "PERSON":
                    for (Person person : port.getPersons()){
//                        System.out.println(person);
                        personList.add(person);
                    }
                    searchList = personList;
                    break;
                case "PASSENGER SHIP":  //only look at pships
                    ArrayList<Ship> shipsInPort = port.getShips();

                    for (Ship ship : shipsInPort){
                        if (ship.getClass() == PassengerShip.class){
                            passengerShips.add((PassengerShip) ship);
                        }
                    }
                    searchList = passengerShips;
                    break;
                case "CARGO SHIP":
                    ArrayList<Ship> cShipsInPort = port.getShips();
                    for (Ship ship : cShipsInPort){
                        if (ship.getClass() == CargoShip.class){
                            cargoShips.add((CargoShip) ship);
                        }
                    }
                    searchList = cargoShips;
                    break;
                case "DOCK":
                    ArrayList<Dock> dockList = port.getDocks();
                    docks.addAll(dockList);
                    searchList = docks;
                    break;
                case "JOB":
                    ArrayList<Ship> ships = port.getShips();    //ships @ current port
                    for (Ship ship : ships){
                        jobs.addAll(ship.getJobs());
                    }
                    searchList = jobs;
                    break;
            }

        }
        return scanSearchList(searchList, attribute, keyword);
    }

    private ArrayList<? extends Thing> scanSearchList(ArrayList<? extends Thing> searchList, String attribute, String keyword){
        ArrayList<Thing> searchResult = new ArrayList<>();
//        System.out.println(searchList.size());
        for (Thing thing : searchList){
//            System.out.println("+ "+ thing);
            switch (attribute.toUpperCase()){
                case "NAME":
                    if (thing.getName().equalsIgnoreCase(keyword)){
                        searchResult.add(thing);
                    }
                    break;
                case "SKILL":   //only people have skills
                    if (thing.getClass() == Person.class){
                        if (((Person)thing).getSkill().equalsIgnoreCase(keyword)) {
                            searchResult.add(thing);
                        }
                    }

                    break;
                case "INDEX":
                    if (thing.getIndex() == Integer.parseInt(keyword)){
                        searchResult.add(thing);
                    }
                    break;
            }
        }
        return searchResult;
    }

    public String printOut(){
        for (SeaPort port : ports){
            return port.toString();
        }
        return null;
    }
}
