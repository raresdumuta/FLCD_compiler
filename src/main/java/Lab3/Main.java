package Lab3;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting program 1 \n");
        MyScanner scannerP1 = new MyScanner("src/main/resources/Problems/p1");
        scannerP1.scanFile();
        System.out.println();

        System.out.println("Starting program 2 \n");
        MyScanner scannerP2 = new MyScanner("src/main/resources/Problems/p2");
        scannerP2.scanFile();
        System.out.println();


        System.out.println("Starting program 3 \n");
        MyScanner scannerP3 = new MyScanner("src/main/resources/Problems/p3");
        scannerP3.scanFile();
        System.out.println();


        System.out.println("Starting program 1 with error \n");
        MyScanner scannerP1err = new MyScanner("src/main/resources/Problems/p1err");
        scannerP1err.scanFile();
        System.out.println();

    }
}
