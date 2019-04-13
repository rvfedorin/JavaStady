package MyWork.Tools;

import java.util.HashMap;
import java.util.Map;

public class ConvertRussian {
    private static Map<String, String> base = new HashMap<String, String>() {{
        put("\\\\u0401", "Ё");
        put("\\\\u0419", "Й");
        put("\\\\u0426", "Ц");
        put("\\\\u0423", "У");
        put("\\\\u041A", "К");
        put("\\\\u0415", "Е");
        put("\\\\u041D", "Н");
        put("\\\\u0413", "Г");
        put("\\\\u0428", "Ш");
        put("\\\\u0429", "Щ");
        put("\\\\u0417", "З");
        put("\\\\u0425", "Х");
        put("\\\\u042A", "Ъ");
        put("\\\\u0424", "Ф");
        put("\\\\u042B", "Ы");
        put("\\\\u0412", "В");
        put("\\\\u0410", "А");
        put("\\\\u041F", "П");
        put("\\\\u0420", "Р");
        put("\\\\u041E", "О");
        put("\\\\u041B", "Л");
        put("\\\\u0414", "Д");
        put("\\\\u0416", "Ж");
        put("\\\\u042D", "Э");
        put("\\\\u042F", "Я");
        put("\\\\u0427", "Ч");
        put("\\\\u0421", "С");
        put("\\\\u041C", "М");
        put("\\\\u0418", "И");
        put("\\\\u0422", "Т");
        put("\\\\u042C", "Ь");
        put("\\\\u0411", "Б");
        put("\\\\u042E", "Ю");
        put("\\\\u0451", "ё");
        put("\\\\u0439", "й");
        put("\\\\u0446", "ц");
        put("\\\\u0443", "у");
        put("\\\\u043A", "к");
        put("\\\\u0435", "е");
        put("\\\\u043D", "н");
        put("\\\\u0433", "г");
        put("\\\\u0448", "ш");
        put("\\\\u0449", "щ");
        put("\\\\u0437", "з");
        put("\\\\u0445", "х");
        put("\\\\u044A", "ъ");
        put("\\\\u0444", "ф");
        put("\\\\u044B", "ы");
        put("\\\\u0432", "в");
        put("\\\\u0430", "а");
        put("\\\\u043F", "п");
        put("\\\\u0440", "р");
        put("\\\\u043E", "о");
        put("\\\\u043B", "л");
        put("\\\\u0434", "д");
        put("\\\\u0436", "ж");
        put("\\\\u044D", "э");
        put("\\\\u044F", "я");
        put("\\\\u0447", "ч");
        put("\\\\u0441", "с");
        put("\\\\u043C", "м");
        put("\\\\u0438", "и");
        put("\\\\u0442", "т");
        put("\\\\u044C", "ь");
        put("\\\\u0431", "б");
        put("\\\\u044E", "ю");
    }};


    public static String convertToRus(String str) {
        for(String m: base.keySet()) {
            str = str.replaceAll(m.toLowerCase(), base.get(m));
        }

        return str;
    }
}
