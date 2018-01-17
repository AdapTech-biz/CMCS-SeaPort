import java.util.Scanner;

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
        stringBuilder.append("\nSea Port:");
        stringBuilder.append("\n\t");
        //add sea port in World class;
        stringBuilder.append(super.formatPrint());
        return stringBuilder.toString();
    }
}
