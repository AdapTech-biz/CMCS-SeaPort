import java.util.Scanner;

public class Thing implements Comparable<Thing> {
    private int index, parent;
    private String name;

    public Thing( String name, int index, int parent) {
        this.index = index;
        this.parent = parent;
        this.name = name;
    }

    public Thing(Scanner scanner) {
//        scanner.useDelimiter("\\S");
        this.name = scanner.next();
        this.index = scanner.nextInt();
        this.parent = scanner.nextInt();
    }

    public int getIndex(){
        return this.index;
    }

    public int getParent(){
        return this.parent;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public int hashCode() {
        System.out.println("hashcode called");
        return this.name.hashCode() + 57;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        System.out.println("obj.getClass() is " + obj.getClass());
        System.out.println("this.getClass() is " + this.getClass());
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }

        String objName = ((Thing) obj).getName();
        return this.name.equals(objName);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String formatPrint(){
        return "\n--------------------------------------------------";
    }

    @Override
    public int compareTo(Thing o) {
        return this.name.compareTo(o.name);
    }
}
