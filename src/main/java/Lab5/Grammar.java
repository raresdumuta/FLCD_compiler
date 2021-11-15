package Lab5;

import Lab3.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {
    private String fileName;
    private List<String> terminals;
    private List<String> nonTerminals;
    private Map<List<String>, List<String>> productions;
    private String startingSymbol;

    public Grammar(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
//        this.productions = new<>();
        this.startingSymbol = "";
        readGrammar();
    }

    public void readGrammar() throws FileNotFoundException {
        File file = new File(this.fileName);
        Scanner scanner = new Scanner(file);

        String nonTerminalLabel = scanner.nextLine();
        String setOfNonTerminals = scanner.nextLine();
        this.nonTerminals = Arrays.asList(setOfNonTerminals.split(","));

        String terminalsLabel = scanner.nextLine();
        String setOfTerminals = scanner.nextLine();
        this.terminals = Arrays.asList(setOfTerminals.split(","));

        String productionsLabel = scanner.nextLine();
        String production;

        while (true) {
            production = scanner.nextLine();
            if (production.equals("Starting Symbol")) {
                break;
            }
            List<String> productions = Arrays.asList(production.split("->"));
            List<String> states = Arrays.asList(productions.get(1).split("\\|"));
            AbstractMap.SimpleEntry<String, List<String>> model = new AbstractMap.SimpleEntry<>(productions.get(0), states);
            if (this.productions.containsKey(model.getKey())) {
                this.productions.get(model.getKey()).addAll(model.getValue());
            } else {
                this.productions.put(model.getKey(), model.getValue());
            }
        }

        this.startingSymbol = scanner.nextLine();
        scanner.close();
        System.out.println(validate() ? "Correct" : "Not Correct");
    }

    private boolean validate(){
        if(!nonTerminals.contains(startingSymbol)){
            return false;
        }
        for(var key : this.productions.keySet()){
            if(!nonTerminals.contains(key))
                return false;
            for(var move : this.productions.get(key)){
                for( var chr:  move.toCharArray()){
                    if(!nonTerminals.contains(String.valueOf(chr)) && !terminals.contains(String.valueOf(chr)) && chr != 'E' ){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @Override
    public String toString() {
        return "Grammar" +
                ", terminals=" + terminals +
                ", nonTerminals=" + nonTerminals +
                ", productions=" + productions;
    }

    public List<String> productionForNonTerminal(String nonTerminal) {
        if(this.nonTerminals.contains(nonTerminal)){
            return Collections.singletonList("Not a terminal");
        }
        return this.productions.get(nonTerminal);
    }
}
