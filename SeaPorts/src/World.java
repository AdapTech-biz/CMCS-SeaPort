import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** Parses the data file selected from the SeaPortProgram.java class, to display to the user on the GUI interface.
 * As the data file is parsed the class creates the appropriate objects encompassed within the SeaPort world.
 * During the creation of each object it is assigned to the proper parent object by use of the parent index value
 * included in the data line. Class contains search functionality to fetch a results list of the user’s
 * search operation. */

public class World extends Thing {
    private ArrayList<SeaPort> ports;   //list of all SeaPorts in world
    private PortTime time;

    public World(String name, File file) {
        super(name, 0, 0);
        ports = new ArrayList<>();
        readFile(file);
    }

    /** Finds Ship by index number*/
    private Ship getShipByIndex (int x) {
        for (SeaPort port: ports)
            for (Ship ship: port.getShips())
                if (ship.getIndex() == x)
                    return ship;
        return null;
    } // end getDockByIndex

    /** Finds Dock by index number*/
    private Dock getDockByIndex (int dockIndex){
        for(SeaPort port : ports){
            Dock dock = port.findDockById(dockIndex);
           if(dock != null)
               return dock;
        }
        return null;
    }

    /** Finds a SeaPort by index number*/
    private SeaPort getSeaPortByIndex(int index){
        for (SeaPort port : ports){
            if (port.getIndex() == index)
                return port;
        }
        return null;
    }

    /** Assigns a Ship object to a Dock object */
    private void assignShip (Ship ship) {
        Dock dock = getDockByIndex (ship.getParent());
        SeaPort port = getSeaPortByIndex(ship.getParent());
        if (dock == null) { //parent index of ship is not Dock
            if (port != null){  //parent index is sea port
                port.addShip(ship); //add ship to sea port
                port.addToQue(ship);    //place in que -- no dock
            }
            return;
        }
        if (port == null) { //parent index is not sea port
            if (dock.getShip() == null){    //checks if dock is not currently used
                dock.setShip(ship); //adds ship to dock
                return;
            }
//            System.out.println(dock.toString() + " is full"); //dubugging
        }
    } // end method assignShip

    public int getNumPorts(){
        return ports.size();
    }

    /** Reads the file selected from the GUI. File object is passed to Scanner object for parsing
     * As each file line is parsed it is passed to the parseData method for the creation of world objects*/
    private void readFile(File file){
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

    /** Method arg is the current String line of the parsed data file. Once the String is received
     * it is passed into a Scanner object to continue the parsing process. The first word of the
     * data file is used to determine what type of class object needs to be created and added to the world.
     * As each object is created the appropriate setter method is called to link the object to its
     * parent object. */
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
                assignShip(passengerShip);  //place ship in dock or que
                break;
            case "CSHIP":
                CargoShip cargoShip = new CargoShip(data);
                assignShip(cargoShip);  //place ship in dock or que
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

    /** Scans all the possible subject objects in the world to collect a complete search list containing
     * only those type of objects. The search list is stored by use of an ArrayList. A for loop is
     * utilized to scan through all the sea ports established within the application world*/
    public ArrayList<? extends Thing> search(String subject, String attribute, String keyword){
        ArrayList<? extends Thing> searchPool = new ArrayList<>();  //complete list of all search subjects
        //***************Temp Storage*********************//
        ArrayList<PassengerShip> passengerShips = new ArrayList<>();
        ArrayList<CargoShip> cargoShips = new ArrayList<>();
        ArrayList<Dock> docks = new ArrayList<>();
        ArrayList<Job> jobs = new ArrayList<>();
        ArrayList<Person> personList = new ArrayList<>();
        //************************************************//
//        System.out.println("Attribute is " + attribute);
//        System.out.println("Key word is " + keyword);

        for (SeaPort port : ports){
            switch (subject.toUpperCase()){
                case "PERSON":
                    personList.addAll(port.getPersons());
                    searchPool = personList;
                    break;
                case "PASSENGER SHIP":  //only look at pships
                    ArrayList<Ship> shipsInPort = port.getShips();

                    for (Ship ship : shipsInPort){
                        if (ship.getClass() == PassengerShip.class){
                            passengerShips.add((PassengerShip) ship);   //only adding passenger ships from ship list
                        }
                    }
                    searchPool = passengerShips;
                    break;
                case "CARGO SHIP":
                    ArrayList<Ship> cShipsInPort = port.getShips();
                    for (Ship ship : cShipsInPort){
                        if (ship.getClass() == CargoShip.class){
                            cargoShips.add((CargoShip) ship);   //only adding cargo ships from ship list
                        }
                    }
                    searchPool = cargoShips;
                    break;
                case "DOCK":
                    ArrayList<Dock> dockList = port.getDocks();
                    docks.addAll(dockList);
                    searchPool = docks;
                    break;
                case "JOB":
                    ArrayList<Ship> ships = port.getShips();    //ships @ current port
                    for (Ship ship : ships){
                        jobs.addAll(ship.getJobs());    //adds all jobs from current ship
                    }
                    searchPool = jobs;
                    break;
            }

        }
        return scanSearchList(searchPool, attribute, keyword);
    }

    /** Uses the ArrayList of Thing objects collected from the search method. The arrayList
     * is a collection of all possible search subjects within the application world. Using the attribute
     * arg of the method the search subjects are processed to find matches based on the search keyword */
    private ArrayList<? extends Thing> scanSearchList(ArrayList<? extends Thing> searchList, String attribute, String keyword){
        ArrayList<Thing> searchResult = new ArrayList<>();  //list to hold all matching search results
//        System.out.println(searchList.size());
        for (Thing thing : searchList){
//            System.out.println("+ "+ thing);
            switch (attribute.toUpperCase()){
                case "NAME":
                    if (thing.getName().equalsIgnoreCase(keyword)){
                        searchResult.add(thing);    //match found
                    }
                    break;
                case "SKILL":   //only people have skills
                    if (thing.getClass() == Person.class){  //check if This is Person
                        if (((Person)thing).getSkill().equalsIgnoreCase(keyword)) {
                            searchResult.add(thing);    //adds person to result list if match
                        }
                    }else if (thing.getClass() == Job.class){   //Check if Thing is Job class
                        if (((Job)thing).searchRequirements(keyword)){
                            searchResult.add(thing);    //adds job to results list if match
                        }
                    }
                    break;
                case "INDEX":
                    try {
                        if (thing.getIndex() == Integer.parseInt(keyword)){
                            searchResult.add(thing);    //numbers match
                        }
                    }catch (NumberFormatException e){
                        searchResult.add(null); //error handling Non-Number entered into index search
                    }
                    break;
            }
        }
//        System.out.println(searchResult.size());
        return searchResult;
    }

    /** String print method to display the parsed file information in application GUI
     * Method uses a for loop to call the toString method of each sea port object created from file*/
    public String printOut(){
        System.out.println("Num: " + ports.size());
        StringBuilder completeDisplay= new StringBuilder();
        for (SeaPort port : ports){
            completeDisplay.append(port.toString());
            completeDisplay.append("\n------------------------------");
        }
        return completeDisplay.toString();
    }
}
