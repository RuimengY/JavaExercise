import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入大于等于0的数字作为要查询的开业天数，以空格间隔：");
        String input = scanner.nextLine();
        String[] numbers = input.split(" ");
        int[] array = new int[numbers.length];

        try {
            for (int i = 0; i < numbers.length; i++) {
                array[i] = Integer.parseInt(numbers[i]);
                if (array[i] < 0) {
                    throw new IllegalArgumentException("输入的数字必须是非负数");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("输入的数字格式错误，请重新输入。");
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        FamilyMart fm = new FamilyMart();
        //从命令行读取参数作为文件名
        // String fileName = args[0] + "_pur.txt";
        for (int i = 0; i < array.length; i++) {

            fm.setDay(array[i]);
            fm.play();
        }
    }
}
