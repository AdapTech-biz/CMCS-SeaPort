import java.util.ArrayList;
import java.util.Scanner;

/** A parent class for the classes CargoShip and PassengerShip. Class contains the basic
 * information about the ship’s dimensions, port times, and a list of jobs
 * associated with the Ship object.*/

public class Ship extends Thing {
    private PortTime arrivalTime, dockTime;
    private double draft, length, weight, width;
    private ArrayList<Job> jobs;


    public Ship(Scanner scanner){
        super(scanner);
        if(scanner.hasNextDouble())
            this.draft = scanner.nextDouble();
        if (scanner.hasNextDouble())
            this.length = scanner.nextDouble();
        if (scanner.hasNextDouble())
            this.weight = scanner.nextDouble();
        if (scanner.hasNextDouble())
            this.width = scanner.nextDouble();
        jobs = new ArrayList<>();
    }

    public void addJob(Job job){
        this.jobs.add(job);
    }

    public ArrayList<Job> getJobs(){
        return new ArrayList<>(jobs);
    }

    /** Formats the search result display for Ship objects */
    @Override
    public String formatPrint() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nShip Draft:");
        builder.append("\n\t");
        builder.append(this.draft);
        builder.append("\nShip Width:");
        builder.append("\n\t");
        builder.append(this.width);
        builder.append("\nShip Length:");
        builder.append("\n\t");
        builder.append(this.length);
        builder.append("\nShip Weight:");
        builder.append("\n\t");
        builder.append(this.weight);
        builder.append("\nJobs:");
        for (Job job : jobs){   //loops through all jobs to be listed in result display
            builder.append("\n\t");
            builder.append(job.toString());
        }

        builder.append(super.formatPrint());    //adds separator to end of result display
        return builder.toString();
    }

}
