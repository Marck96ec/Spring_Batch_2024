import java.util.ArrayList;
import java.util.List;

public class ChemicalMachine {

    List<String> chemicals = new ArrayList<String>();

    public void add(String chemical) {
        chemicals.add(chemical);
    }

    public void applyHeat() {

        if(chemicals.contains("GREEN") && chemicals.contains("YELLOW")) {
            chemicals.clear();
            chemicals.add("BROWN");
        } else if (chemicals.contains("GREEN") && chemicals.contains("CYAN")) {
            chemicals.clear();
            chemicals.add("ORANGE");
        } else if (chemicals.contains("ORANGE")) {
            chemicals.clear();
            chemicals.add("RED");
            chemicals.add("BLUE");
        } else if (chemicals.contains("BROWN")) {
            chemicals.clear();
            chemicals.add("MAGENTA");
        } else if (chemicals.isEmpty()) {
        }else {
            chemicals.clear();
            chemicals.add("UNKNOWN");
        }
    }

    public ArrayList<String> emptyMachine() {
        ArrayList<String> chemicalsAux = new ArrayList<>(this.chemicals);
        this.chemicals.clear();
        return chemicalsAux;
    }

    public static void main(String[] args) {
        ChemicalMachine machine = new ChemicalMachine();

        machine.add("GREEN");
        machine.add("YELLOW");
        machine.applyHeat();
        System.out.println(machine.emptyMachine()); // emptyMachine should return {"BROWN"}

        machine.add("RED");
        machine.add("YELLOW");
        machine.applyHeat();
        System.out.println(machine.emptyMachine()); // emptyMachine should return {"UNKOWN"}
    }
}