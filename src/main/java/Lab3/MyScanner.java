package Lab3;

import Lab2.DataSet.SymbolTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MyScanner {
    private String programFileName;
    private final List<String> tokens = new ArrayList<>();
    private final List<String> separators = new ArrayList<>();
    private Map<String, Integer> PIF = new HashMap<>();
    private Map<Integer, String> ST = new HashMap<>();
    private final int capacity = 83;
    private SymbolTable symbolTable = new SymbolTable(capacity);

    public MyScanner(String programFileName) {
        this.programFileName = programFileName;
        readTokens();
        readSeparators();
    }

    private void readTokens(){
        try {
        File tokenFile = new File("resources/ScannerUtils/token");
        Scanner tokenReader = new Scanner(tokenFile);

        while (tokenReader.hasNextLine()){
            String tokenLine = tokenReader.nextLine();
            tokens.add(tokenLine);
        }
        tokenReader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readSeparators(){
        try {
            File separatorFile = new File("resources/ScannerUtils/separators");
            Scanner separatorReader = new Scanner(separatorFile);

            while (separatorReader.hasNextLine()){
                String tokenLine = separatorReader.nextLine();
                separators.add(tokenLine);
            }
            separatorReader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void tokenize(){
    }
}
