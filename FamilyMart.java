import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FamilyMart {
     Goods[] purchase = null;
     Goods[] sell = null;

    public void initialOfPurchase(int i) throws IOException {
        int count = 0;
        //统计录入的大小作为商品的数组的大小
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\"+i+"_pur.txt"));
            while ((reader.readLine()) != null) {
                count++;
            }
            purchase = new Goods[count-1];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader2 = new BufferedReader(new FileReader("test_cases\\"+i+"_pur.txt"));
        //创建一个数组保存商品中的每个对象
        String line;
        for (int j = 0; j < count; j++) {
            line = reader2.readLine();
            if(j==0)continue;
            purchase[j-1] = InitialPurchase(line);
        }
    }
    public void initialOfSell(int i) throws IOException {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\"+i+"_sel.txt"));
            while ((reader.readLine()) != null) {
                count++;
            }
            sell = new Goods[count-1];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader2 = new BufferedReader(new FileReader("test_cases\\"+i+"_sel.txt"));
        //创建一个数组保存商品中的每个对象
        String line;
        for (int j = 0; j < count; j++) {
            line = reader2.readLine();
            if(j==0)continue;
            sell[j-1] = InitialSeller(line);
        }
    }
    //初始化购买列中的每一个元素
    public static Goods InitialPurchase(String string){
        String[] parts = string.split("\t");
        //ZQAgY	1.3	7	2022/04/26
        String name = parts[0];
        float price = Float.parseFloat(parts[1]);
        int life = Integer.parseInt(parts[2]);
        String productDate = parts[3];
        Goods goods = new Goods(name,price,life,productDate);
        return goods;
    }
    public static Goods InitialSeller(String string){

        String[] parts = string.split("\t");
        //mtDdT	0.6
        String name = parts[0];
        //默认折扣值为1.0(没有折扣)
        float discount = 1.0F;
        // 如果有第二部分且可以转换为小数，则将其作为值
            if (parts.length > 1) {
                try {
                    discount = Float.parseFloat(parts[1]);
                } catch (NumberFormatException e) {
                    // 如果无法转换为小数，保持默认值1.0
                }
            }
        Goods goods = new Goods(name,discount);
        return goods;
    }
}
