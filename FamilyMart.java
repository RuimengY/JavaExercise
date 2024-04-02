import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FamilyMart {
    private Goods[] purchase = null;
    private Goods[] sell = null;
    private float turnover = 0;
    private int day;

    public void play() throws IOException {
        initialOfPurchase(0);
        initialOfSell(0);
        LastDate();
        morning();
        System.out.printf("0 day : turnover:%.2f\n",turnover);
    }
    public void initialOfPurchase(int i) throws IOException {
        int count = 0;
        //统计录入的大小作为商品的数组的大小
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\" + i + "_pur.txt"));
            while ((reader.readLine()) != null) {
                count++;
            }
            purchase = new Goods[count - 1];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader2 = new BufferedReader(new FileReader("test_cases\\" + i + "_pur.txt"));
        String line;
        for (int j = 0; j < count; j++) {
            line = reader2.readLine();
            if (j == 0) continue;
            purchase[j - 1] = InitialPurchase(line);
        }
    }

    public void initialOfSell(int i) throws IOException {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\" + i + "_sel.txt"));
            while ((reader.readLine()) != null) {
                count++;
            }
            sell = new Goods[count - 1];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader2 = new BufferedReader(new FileReader("test_cases\\" + i + "_sel.txt"));
        //创建一个数组保存商品中的每个对象
        String line;
        for (int j = 0; j < count; j++) {
            line = reader2.readLine();
            if (j == 0) continue;
            sell[j - 1] = InitialSeller(line);
        }
    }

    //初始化购买列中的每一个元素
    public Goods InitialPurchase(String string) {
        String[] parts = string.split("\t");
        //ZQAgY	1.3	7	2022/04/26
        String name = parts[0];
        float price = Float.parseFloat(parts[1]);
        int life = Integer.parseInt(parts[2]);
        String productDate = parts[3];
        Goods goods = new Goods(name, price, life, productDate);
        return goods;
    }

    public Goods InitialSeller(String string) {

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
        Goods goods = new Goods(name, discount);
        return goods;
    }

    //关于时间的处理（时间的格式化转数字，判断能不能卖）

    public void LastDate() {
        for (int i = 0; i < purchase.length; i++) {
            String date = purchase[i].getProductDate();
            int life = purchase[i].getLife();
            //2022/04/26
            String format = "yyyy/MM/dd";
            //生产日期+保质期-1=需要扔的晚上
            //2022年5月2日与需要扔的晚上的差，如果最后时限小于5月2日(说明一开始就过期）舍去（如果等于说明白天可以先用)。
            //每run一天就是把5月2号的日期加1，需要扔的晚上等于run的天数
            // 将生产日期字符串解析为LocalDate对象
            LocalDate productDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            // 计算过期日期
            purchase[i].setLastDay(productDate.plusDays(life - 1));

        }
    }

    public void morning() {
        //建立一个如果名字相同要比较时间的索引数组
        int[] index = new int[purchase.length];
        //先找到和要卖的相同名字的买的东西
        //进行索引比较，确定出卖哪一个
        //营业额增加，这个索引对应的东西转成null
        for (int i = 0; i < sell.length; i++) {
            int count = 0;
            for (int j = 0; j < purchase.length; j++) {
                if (purchase[j] != null) {
                    if (sell[i].getName().equals(purchase[j].getName())) {
                        index[count] = j;
                        count++;
                    }
                }
            }
            if (count!=0) {
                int numberOfPurchase = compareDate(index, count);
                turnover += purchase[numberOfPurchase].getPrice() * sell[i].getDiscount();
                purchase[numberOfPurchase] = null;
            }
        }
    }

    public int compareDate(int[] date, int count) {
        //将lastDay最小的挑出来(没过期且最容易过期)
        int index = date[0];
        for (int i = 1; i < count; i++) {
            if (purchase[date[i]].getLastDay().isBefore(purchase[index].getLastDay()))
                index = date[i];
        }
        return index;
    }
}
