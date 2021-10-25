package Lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Codification {
    private final List<String> tokens;

    public List<String> getTokens() {
        return tokens;
    }

    public List<String> getSeparators() {
        return separators;
    }

    public List<String> getReservedWords() {
        return reservedWords;
    }

    private final List<String> separators;
    private final List<String> reservedWords;

    public HashMap<String, Integer> getCodes() {
        return codes;
    }

    private HashMap<String,Integer> codes;
    public Codification(List<String> tokens, List<String> separators, List<String> reservedWords){
        this.tokens = tokens;
        this.separators = separators;
        this.reservedWords = reservedWords;
        initialiseCodes();
    }

    private void initialiseCodes(){
        this.codes = new HashMap<>();

        codes.put("identifier",0);
        codes.put("constant",1);
        int i = 2;
        for(String separator: separators){
            codes.put(separator,i);
            i++;
        }
        for (String operator: tokens){
            codes.put(operator,i);
            i++;
        }
        for (String reservedWord: reservedWords){
            codes.put(reservedWord, i);
            i++;
        }
    }

}
