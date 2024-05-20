package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        System.out.println("(a==b) = " + (a==b));

        Address copyAddress = new Address("city", "street", "1800");
        Address copyAddress2 = new Address("city", "street", "1800");
        System.out.println("(copyAddress == copyAddress2) = " + (copyAddress == copyAddress2));
        System.out.println("(copyAddress equals copyAddress2) = " + (copyAddress.equals(copyAddress2)));
    }
}
