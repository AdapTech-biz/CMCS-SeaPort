import java.util.Scanner;

/** Object for individuals located within a SeaPort. Each person extends Thing.java to contain a skill.*/

public class Person extends Thing {
    private String skill;

    // name index parent skill

    public Person(Scanner scanner){
        super(scanner);
        if (scanner.hasNext())
            this.skill = scanner.next();
    }

    public String getSkill(){
        return this.skill;
    }

    /** Formats the search result display for Person objects */
    public String formatPrint(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getIndex());
        stringBuilder.append("\nName:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getName());
        stringBuilder.append("\nSkill:");
        stringBuilder.append("\n\t");
        stringBuilder.append(getSkill());
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }
}
