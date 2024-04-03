import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FamilyMart {
    //用list的好处在于，第二天不会完全删掉第一天的内容
    private List<Goods> purchase = new ArrayList<>();
    //sell用数组的好处在于，每天都可以删除重来
    private Goods[] sell = null;
    private float turnover = 0;
    private int day;

    public void setDay(int day) {
        this.day = day;
    }

    public void play() throws IOException {
        initialOfPurchase();
        initialOfSell();
        LastDate();
        judgeFirst();
        morning();
        night();
        System.out.printf(day+" day : turnover:%.2f\n", turnover);
    }

    public void initialOfPurchase() {
        //ArrayList加入数据可长可短
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\" + day + "_pur.txt"));
            String line;
            for (int j = 0; (line = reader.readLine()) != null; j++) {
                if (j == 0) continue;
                //在不止是第一次的基础上添加
                purchase.add(InitialPurchase(line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialOfSell() throws IOException {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test_cases\\" + day + "_sel.txt"));
            while ((reader.readLine()) != null) {
                count++;
            }
            sell = new Goods[count - 1];
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader2 = new BufferedReader(new FileReader("test_cases\\" + day + "_sel.txt"));
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
        for (int i = 0; i < purchase.size(); i++) {
            String date = purchase.get(i).getProductDate();
            int life = purchase.get(i).getLife();
            //2022/04/26
            String format = "yyyy/MM/dd";
            //生产日期+保质期-1=需要扔的晚上
            // 将生产日期字符串解析为LocalDate对象
            LocalDate productDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            // 计算过期日期
            purchase.get(i).setLastDay(productDate.plusDays(life - 1));
        }
    }
    //一开始没有注意到的点：如果购进的食物过期了，则不能卖出去
    public void judgeFirst(){
        for (int i = 0; i < purchase.size(); i++) {
            if(purchase.get(i).getLastDay().isBefore(timeNow())){
                purchase.remove(i);
            }
        }
    }

    public void morning() {
        //建立一个如果名字相同要比较时间的索引数组
        int[] index = new int[purchase.size()];
        //先找到和要卖的相同名字的买的东西
        //进行索引比较，确定出卖哪一个
        //营业额增加，这个索引对应的东西删除掉
        for (int i = 0; i < sell.length; i++) {
            int count = 0;
            for (int j = 0; j < purchase.size(); j++) {
                if (sell[i].getName().equals(purchase.get(j).getName())) {
                    index[count] = j;
                    count++;
                }
            }
            if (count != 0) {
                int numberOfPurchase = compareDate(index, count);
                turnover += purchase.get(numberOfPurchase).getPrice() * sell[i].getDiscount();
                purchase.remove(numberOfPurchase);
            }
        }
    }

    public int compareDate(int[] date, int count) {
        //将lastDay最小的挑出来(没过期且最容易过期)
        int index = date[0];
        for (int i = 1; i < count; i++) {
            if (purchase.get(date[i]).getLastDay().isBefore(purchase.get(index).getLastDay()))
                index = date[i];
        }
        return index;
    }
    //晚上的时候处理要删除的元素
    public void night(){
        for (int i = 0; i < purchase.size(); i++) {
            //比较lastDay和开店时间（5月2日）到现在的时间，如果等于则删除
           if(purchase.get(i).getLastDay().equals(timeNow()))
               purchase.remove(i);
        }
    }
    public LocalDate timeNow(){
        String dateString = "2022-05-02";
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串解析为LocalDate对象
        LocalDate date = LocalDate.parse(dateString, formatter);
        // 添加指定天数
        LocalDate newDate = date.plusDays(day);
        return newDate;
    }
}
