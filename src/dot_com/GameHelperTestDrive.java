package dot_com;

import java.util.ArrayList;

public class GameHelperTestDrive {

    public static void main(String[] args){
        int num = 0;
        int toClear = 1;
        int allNum = 1000;
        GameHelper helperTest = new GameHelper();
        ArrayList<String> testList;

        for (int i = 0; i < allNum; i++)
        {
            testList = helperTest.placeDotCom(3);
//            System.out.println(testList);

            if (testList.size() == 3){
                num++;
            }
            if (toClear == 3){
                toClear = 0;
                helperTest.clearUsed();
            }
            toClear++;
        }
        System.out.println("Test 1 passed: " + num + " from " + allNum);
    }
}

