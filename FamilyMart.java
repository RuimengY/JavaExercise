import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FamilyMart {
    public static void main(String[] args) throws IOException {
        //确定购买数量
        int count = 0;
        Goods[] purchase = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\0_pur.txt"));
            while ((reader.readLine()) != null) {
                count++;
            }
            purchase = new Goods[count-1];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

            BufferedReader reader2 = new BufferedReader(new FileReader("test_cases\\0_pur.txt"));
            //创建一个数组保存商品中的每个对象
            String line;
            for (int i = 0; i < count; i++) {
                line = reader2.readLine();
                if(i==0)continue;
                purchase[i-1] = Initial(line);
            }

    }
    //初始化购买列中的每一个元素
    public static Goods Initial(String string){
        String[] parts = string.split("\t");
        //ZQAgY	1.3	7	2022/04/26
        String name = parts[0];
        float price = Float.parseFloat(parts[1]);
        int life = Integer.parseInt(parts[2]);
        String productDate = parts[3];
        Goods goods = new Goods(name,price,life,productDate);
        return goods;
    }
}
