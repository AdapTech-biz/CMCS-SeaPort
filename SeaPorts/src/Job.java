import java.util.ArrayList;
import java.util.Scanner;

public class Job extends Thing {
    private double duration;
    private ArrayList<String> requirements; //should be some of the skill of the person


    public Job(Scanner scanner){
        super(scanner);
        if (scanner.hasNextDouble())
            this.duration = scanner.nextDouble();
        requirements = new ArrayList<>();
        while (scanner.hasNext()){
            this.requirements.add(scanner.next());
        }
//        System.out.println("Job Reqs..." + requirements);
    }

    public String formatPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getIndex());
        stringBuilder.append("\nName:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getName());
        stringBuilder.append("\nJob Duration:");
        stringBuilder.append("\n\t");
        stringBuilder.append(this.duration);
        stringBuilder.append("\nJob Requirements:");
        for (String requirement : requirements){
            stringBuilder.append("\n\t");
            stringBuilder.append(requirement);
        }
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }
}
