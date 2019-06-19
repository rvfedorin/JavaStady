package MyWork.Tools;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeedFileParser {

    public static String getParsedString(String toParse) {
        String result;
        String mnemokod = "";
        String speed = "";

        Pattern mnemoPattern = Pattern.compile("(\\w{2,4}-\\w*)");
        Pattern speedPattern = Pattern.compile("(\\d{1,3}\\s*[МмMm]|\\d{3,7}\\s*[КкKk])");
        Matcher mnemokodM = mnemoPattern.matcher(toParse);
        Matcher speedM = speedPattern.matcher(toParse);

        if(mnemokodM.find()) {
            mnemokod = mnemokodM.group(1);
        }
        if(speedM.find()) {
            speed = speedM.group(1);

            speed = speed.replaceAll("\\s*", "");
            speed = speed.replaceAll("[Ммm]", "M");
            speed = speed.replaceAll("[Ккk]", "K");
        }

        result = mnemokod + " " + speed;

        return result;
    } // ** getParsedString(String toParse)

    public static void main(String[] args) {
        String st = "О-Ресурсстекло_пер/Orel-OSK  на 10 Мбит/с";
        // О-АтлантМ/Orel-Atlantm до 100 М
        //  Кр-ИПГончароваЕ_пер /   Kr-IPGridasov  3 М TT  # 452642
        // > О-ИПЖуковВА_пер / Orel-Tehnosfe3 на 70 Мбит/с  (ТТ 448041)
        //> О-Агроком / Orel-Agrokom  на 10 Мбит/с

        getParsedString(st);
    } // ** main()
} // ** class SpeedFileParser


