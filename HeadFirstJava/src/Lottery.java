public class Lottery {
    static public void main(String[] args)
    {
        final int NUMS = 50;

        int[] nums = new int[NUMS];

        for (int i = 0; i < NUMS; i++) { nums[i] = i;}

        for (int i = 0; i < NUMS; i++)
        {
            int r = (int) Math.ceil( Math.random() * (NUMS - 1));
            int temp = nums[i];
            nums[i] = nums[r];
            nums[r] = temp;
        }

        for (int i = 0; i < 6; i++)
        {
            System.out.print(Integer.toString(nums[i]) + ' ');
        }

    }
}
