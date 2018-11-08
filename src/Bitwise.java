public class Bitwise {
    static public void main(String[] args)
    {
        int flags = 53;

        System.out.println(((flags&1) > 0)? "Включено": "Выключено");
        System.out.println(((flags&2) > 0)? "Включено": "Выключено");
        System.out.println(((flags&4) > 0)? "Включено": "Выключено");
        System.out.println(((flags&8) > 0)? "Включено": "Выключено");
        System.out.println(((flags&16) > 0)? "Включено": "Выключено");
        System.out.println(((flags&32) > 0)? "Включено": "Выключено");
        System.out.println(((flags&64) > 0)? "Включено": "Выключено");
        System.out.println(((flags&128) > 0)? "Включено": "Выключено");

        System.out.println("Битовые значения флагов: " + Integer.toBinaryString(flags));
    }
}
