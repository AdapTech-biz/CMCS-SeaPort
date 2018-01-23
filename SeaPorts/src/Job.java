import java.util.ArrayList;
import java.util.Scanner;

/**Each Job contains a list of required skills and a total time
 * duration for the Job’s completion. Class extends Thing.java.*/
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
    }

    /** Scans the job requirements to see if a requested skill is present in list*/
    public boolean searchRequirements(String skill){
        skill = skill.trim().toLowerCase();
        if (requirements.size() == 0 && skill.isEmpty() || requirements.size() == 0 && skill.equalsIgnoreCase("none"))
            return true;
        return requirements.contains(skill);
    }

    /** Formats the search result display for Job objects */
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
        if (requirements.size() == 0)
            stringBuilder.append("\n\tNo Specific Skill Requirements!");
        for (String requirement : requirements){
            stringBuilder.append("\n\t");
            stringBuilder.append(requirement);
        }
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }
}
