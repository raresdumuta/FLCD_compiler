package Lab6;

import Lab5.Grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecursiveDescendent {

    public Grammar grammar;
    public ConfigurationDTO configurationDTO;
    public String sequence;

    public RecursiveDescendent(Grammar newGrammar, String sequence) {
        this.sequence = sequence;
        grammar = newGrammar;
        configurationDTO = new ConfigurationDTO();
        configurationDTO.state = ConfigurationDTO.StateValues.q;
        configurationDTO.position = 1;
        configurationDTO.inputStack = new ArrayList<>(Collections.singletonList(grammar.getStartingSymbol()));
        configurationDTO.workingStack = new ArrayList<>(Collections.singletonList("E"));
    }

    public void expand() {
        if (grammar.getNonTerminals().contains(configurationDTO.inputStack.get(0))) {
            // q,1,E,S -> q, 1, S1, [0,A,0] -> q,2,S10,A
            String currentElement = configurationDTO.inputStack.get(0);
            if (configurationDTO.workingStack.get(0).equals("E")) {
                configurationDTO.workingStack.remove("E");
            }
            configurationDTO.workingStack.add(currentElement + "1");
            configurationDTO.inputStack.remove(currentElement);
            var productions = grammar.getProductions();
            for (var p : productions.entrySet()) {
                if (p.getKey().contains(currentElement)) {
                    configurationDTO.inputStack.addAll(p.getValue().get(0));
                }
            }
        }
    }

    public void advance() {
        String headOfInputStack = configurationDTO.inputStack.get(0);
        if (grammar.getTerminals().contains(headOfInputStack) && configurationDTO.position <= sequence.length()  &&
                headOfInputStack.equals(Character.toString(sequence.charAt(configurationDTO.position - 1)))) {
            configurationDTO.position++;
            configurationDTO.inputStack.remove(headOfInputStack);
            configurationDTO.workingStack.add(headOfInputStack);
        }
        else {
            this.momentaryInsuccess();
        }
    }

    public void momentaryInsuccess() {
        String headOfInputStack = "E";
        if (configurationDTO.inputStack.size() > 0){
            headOfInputStack = configurationDTO.inputStack.get(0);
        }
        else {
            configurationDTO.state = ConfigurationDTO.StateValues.b;
        }
        if(configurationDTO.position > sequence.length() && grammar.getTerminals().contains(headOfInputStack)){
            configurationDTO.state = ConfigurationDTO.StateValues.b;
        } else if (grammar.getTerminals().contains(headOfInputStack) &&
                !headOfInputStack.equals(String.valueOf(sequence.charAt(configurationDTO.position -1)))) {
            configurationDTO.state = ConfigurationDTO.StateValues.b;
        }
    }

    public void back() {
        String headOfWorkingStack = configurationDTO.workingStack.get(configurationDTO.workingStack.size() - 1);
        if (grammar.getTerminals().contains(headOfWorkingStack)) {
            configurationDTO.position--;
            configurationDTO.inputStack.add(headOfWorkingStack);
            configurationDTO.workingStack.remove(headOfWorkingStack);
        }
    }

    public void anotherTry() {
        String headOfWorkingStack = configurationDTO.workingStack.get(configurationDTO.workingStack.size() - 1);
        String stringValueOfHead = String.valueOf(headOfWorkingStack.charAt(0));
        int intValueOfHead = Integer.parseInt(String.valueOf(headOfWorkingStack.charAt(1))) + 1;
        if (grammar.getNonTerminals().contains(stringValueOfHead)) {
            Map<List<String>, List<List<String>>> productions = grammar.getProductions();
            int i = 0;
            for (var p : productions.entrySet()) {
                if (p.getKey().contains(stringValueOfHead)) {
                    if (p.getValue().size() >= intValueOfHead) {
                        configurationDTO.workingStack.remove(configurationDTO.workingStack.size() - 1);
                        configurationDTO.workingStack.add(stringValueOfHead + intValueOfHead);
                        configurationDTO.inputStack.removeAll(p.getValue().get(intValueOfHead - 2));
                        configurationDTO.inputStack.addAll(p.getValue().get(intValueOfHead - 1));
                        configurationDTO.state = ConfigurationDTO.StateValues.q;
                    } else {
                        if (configurationDTO.position == 1 && stringValueOfHead.equals(grammar.getStartingSymbol())) {
                            configurationDTO.state = ConfigurationDTO.StateValues.e;
                        } else {
                            configurationDTO.inputStack.removeAll(p.getValue().get(intValueOfHead - 2));
                            configurationDTO.inputStack.add(stringValueOfHead);
                            configurationDTO.workingStack.remove(headOfWorkingStack);
                        }
                    }
                }
            }
        }
    }

    public void success() {
        configurationDTO.state = ConfigurationDTO.StateValues.f;
    }

}
