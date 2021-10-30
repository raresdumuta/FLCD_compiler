package Lab2.DataSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    // only 1 symbol table for constants and identifiers
    private final String[] symbolTable;
    private final int capacity;

    public SymbolTable(int capacity){
        this.capacity = capacity;
        symbolTable = new String[capacity];
    }

    private int hashFunction(String keyInSymbolTable){
        int asciiSumChar = 0;
        for(int i = 0 ; i< keyInSymbolTable.length(); i++){
            asciiSumChar += keyInSymbolTable.charAt(i);
        }

        return asciiSumChar % capacity;
    }

    public boolean addInSymbolTable (String keyInSymbolTable){
        // already in ST
        for (String entryInSt: symbolTable){
            if(entryInSt != null && entryInSt.equals(keyInSymbolTable)){
                return false;
            }
        }
        //compute the hash of the key
        int hashValueOfKey = hashFunction(keyInSymbolTable);
        // no collisions
        if(symbolTable[hashValueOfKey] == null){
            symbolTable[hashValueOfKey] = keyInSymbolTable;
            return true;
        }
        //collision with Linear Probing
        int positionInStAtHashPosition = hashValueOfKey;
        while (symbolTable[positionInStAtHashPosition] != null){
            positionInStAtHashPosition++;
        }

        if (symbolTable[positionInStAtHashPosition] == null){
            symbolTable[positionInStAtHashPosition] = keyInSymbolTable;
            return true;
        }
        return false;
    }

    public int search(String keyInSymbolTable){
        int hashValueOfKey = hashFunction(keyInSymbolTable);
        while (symbolTable[hashValueOfKey]!=null){
            if(symbolTable[hashValueOfKey].equalsIgnoreCase(keyInSymbolTable)){
                // we return the position
                return hashValueOfKey;
            }
            hashValueOfKey++;
        }
        System.out.println("Key not Found " + keyInSymbolTable);
        // not found we return -1
        return -1;
    }

    public String toString(){
        Map<String,Integer> SymbolTab = new HashMap<>();
        for(int i = 0; i < symbolTable.length; i++){
            if(symbolTable[i] != null){
                SymbolTab.put(symbolTable[i],i);
            }
        }
        return "Symbol Table = " + SymbolTab;
    }


}
