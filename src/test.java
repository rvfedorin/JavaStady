import java.io.*;

class test implements Serializable {
    private int width;
    private int height;

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public static void main(String[] args) {
        test box = new test();
        box.setHeight(20);
        box.setWidth(40);

        try {
            FileOutputStream fd = new FileOutputStream("foo.ser");
            ObjectOutputStream os = new ObjectOutputStream(fd);
            os.writeObject(box);
            os.close();
        } catch ( Exception ex) {
            ex.printStackTrace();
        }


    } // public static void main
}

