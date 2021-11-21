package Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {
    private String fileName;
    private List<String> terminals;
    private List<String> nonTerminals;
    private Map<List<String>, List<List<String>>> productions;
    private String startingSymbol;

    public Grammar(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.productions = new LinkedHashMap<>(); // linkedHasMap keeps the order of the entries
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
            List<String> allProductions = Arrays.asList(productions.get(0).split(""));
            String[] states = productions.get(1).split("\\|");
            List<List<String>> allStates = new ArrayList<>();
            for (var state : states) {
                allStates.add(Arrays.asList(state.split("")));
            }
            AbstractMap.SimpleEntry<List<String>, List<List<String>>> model = new AbstractMap.SimpleEntry<>(allProductions, allStates);
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

    private boolean validate() {
        if (!nonTerminals.contains(startingSymbol)) {
            return false;
        }
        for (var key : this.productions.keySet()) {
            for (var k : key) {
                if (!nonTerminals.contains(k))
                    return false;
            }
            for (var move : this.productions.get(key)) {
                for (var list : move) { // iterating through each list from the set of productions
                    for (var chr : list.toCharArray()) {
                        if (!nonTerminals.contains(String.valueOf(chr)) && !terminals.contains(String.valueOf(chr)) && chr != 'E') {
                            return false;
                        }
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

    public List<List<String>> productionForNonTerminal(String nonTerminal) {
        if(!this.nonTerminals.contains(nonTerminal)){
            return Collections.singletonList(Collections.singletonList("Not a NonTerminal"));
        }
        for(var prod: productions.entrySet()){
            List<String> listOfProd = prod.getKey();
            if(listOfProd.contains(nonTerminal)){
                return this.productions.get(listOfProd);
            }
        }
        return null;
    }

    public boolean isCFG(){
        Set<List<String>> keys = this.productions.keySet();
        boolean checkStart = false;
        for(List<String> key: keys) {
            if (key.contains(this.startingSymbol)) {
                checkStart = true;
                break;
            }
        }
        if(!checkStart)
            return false;

        for(List<String> key: keys){
            if(key.size() > 1)
                return false;
            if(!this.nonTerminals.contains(key.get(0)))
                return false;

            List<List<String>> rules = this.productions.get(key);
            for(var rule: rules){
                for(var term: rule){
                    if(!this.terminals.contains(term) && !this.nonTerminals.contains(term) && !term.equals("epsilon"))
                        return false;
                }
            }
        }
        return true;
    }
}
