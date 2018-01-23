import java.util.Scanner;

/** A parent class Ship, CargoShip, PassengerShip, Dock and Person.java.
 * Thing.java contains information on the object’s name, index value
 * and index of the parent object.*/

public class Thing implements Comparable<Thing> {
    private int index, parent;
    private String name;

    public Thing( String name, int index, int parent) {
        this.index = index;
        this.parent = parent;
        this.name = name;
    }

    public Thing(Scanner scanner) {
        this.name = scanner.next();
        this.index = scanner.nextInt();
        this.parent = scanner.nextInt();
    }

    /** returns index*/
    public int getIndex(){
        return this.index;
    }
    /** returns parent's index*/
    public int getParent(){
        return this.parent;
    }
    /** returns name*/
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /** method call to format search results of thing objects -- parent class adds
     * list separator to end of each search result*/
    public String formatPrint(){
        return "\n--------------------------------------------------";
    }

    @Override
    public int compareTo(Thing o) {
        return this.name.compareTo(o.getName());
    }
}
