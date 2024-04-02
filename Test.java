import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        FamilyMart fm = new FamilyMart();
        fm.initialOfSell(0);
        for (int i = 0; i < 10; i++) {
            System.out.println(fm.sell[i].getDiscount());
        }

    }
}
