import java.util.ArrayList;
import java.util.Scanner;

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

    public int getParent(){
        return super.getParent();
    }

    public int getIndex(){
       return super.getIndex();
    }

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
        for (Job job : jobs){
            builder.append("\n\t");
            builder.append(job.toString());
        }

        builder.append(super.formatPrint());
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
