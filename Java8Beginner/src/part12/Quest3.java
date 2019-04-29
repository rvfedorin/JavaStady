package part12;

public class Quest3 {
    public static void main(String[] args) {
        for (Tools s: Tools.values()){
            System.out.println(s + " = " + s.getName());
        }
    }
}

enum Tools {
    SCREWDRIVER("screwdriver"), WRENCH("wrench"), HAMMER("hammer"), PLIERS("pliers");

    String name;

    Tools(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
