package Lab3;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting program 1 \n");
        MyScanner scannerP1 = new MyScanner("src/main/resources/Problems/p1");
        scannerP1.scanFile();
        System.out.println("PIF P1");
        scannerP1.printProgramInternalForm();
        System.out.println("Symbol Table P1");
        scannerP1.printSymbolTable();
        System.out.println();

        System.out.println("Starting program 2 \n");
        MyScanner scannerP2 = new MyScanner("src/main/resources/Problems/p2");
        scannerP2.scanFile();
        System.out.println("PIF P2");
        scannerP2.printProgramInternalForm();
        System.out.println("Symbol Table P2");
        scannerP2.printSymbolTable();
        System.out.println();


        System.out.println("Starting program 3 \n");
        MyScanner scannerP3 = new MyScanner("src/main/resources/Problems/p3");
        scannerP3.scanFile();
        System.out.println("PIF P3");
        scannerP3.printProgramInternalForm();
        System.out.println("Symbol Table P3");
        scannerP3.printSymbolTable();
        System.out.println();


        System.out.println("Starting program 1 with error \n");
        MyScanner scannerP1err = new MyScanner("src/main/resources/Problems/p1err");
        scannerP1err.scanFile();
        System.out.println("PIF P1err");
        scannerP1err.printProgramInternalForm();
        System.out.println("Symbol Table P1err");
        scannerP1err.printSymbolTable();
        System.out.println();

    }
}
