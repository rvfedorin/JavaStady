package part13

import java.io.*
import java.util.ArrayList
import java.util.Objects

internal class tetetet : Serializable {
    companion object {


        @JvmStatic
        fun main(args: Array<String>) {
            //        ArrayList<Object> obj = new List<Object>();

            try {
                val fw = FileWriter("test.txt")
                fw.write("Моя первая строка Котлин!!!")
                fw.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

        } // public static void main
    }
}

