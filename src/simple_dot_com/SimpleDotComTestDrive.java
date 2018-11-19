package simple_dot_com;

public class SimpleDotComTestDrive {
    public static void main(String[] args)
    {
        SimpleDotCom dot = new SimpleDotCom();

        int[] position = {1, 2, 3};

        dot.SetLocation(position);

        System.out.println(dot.CheckYourSelf("2").equals("Попал")? "Тест1 -> Пройден" : "Тест1 -> Не пройден");
        System.out.println(dot.CheckYourSelf("0").equals("Мимо")? "Тест2 -> Пройден" : "Тест2 -> Не пройден");
        System.out.println(dot.CheckYourSelf("4").equals("Мимо")? "Тест3 -> Пройден" : "Тест3 -> Не пройден");
        System.out.println(dot.CheckYourSelf("1").equals("Попал")? "Тест4 -> Пройден" : "Тест4 -> Не пройден");
        System.out.println(dot.CheckYourSelf("3").equals("Потопил")? "Тест5 -> Пройден" : "Тест5 -> Не пройден");
    }
}
