package part1;

public class GalToLit {

    public static void main(String[] args) {
        GalToLit converter = new GalToLit();
        double gal = 10;
        double lit = converter.ConvertGalToLit(gal);

        System.out.println(lit);
    }

    private double ConvertGalToLit (double aGal){
        return aGal * 3.7854;
    }
}
