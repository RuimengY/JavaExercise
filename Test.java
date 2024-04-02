import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //从命令行读取参数作为文件名
        // String fileName = args[0] + "_pur.txt";
        FamilyMart fm = new FamilyMart();
        fm.play();
    }
}
