package Lab6;

import Lab5.Grammar;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveDescendent {

    Grammar grammar;
    public ConfigurationDTO configurationDTO;

    public RecursiveDescendent(Grammar newGrammar) {
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
        if (grammar.getTerminals().contains(configurationDTO.inputStack.get(0))) {
            String currentElement = configurationDTO.inputStack.get(0);
            configurationDTO.position++;
            configurationDTO.inputStack.remove(currentElement);
            configurationDTO.workingStack.add(currentElement);
        }

    }

    public void momentaryInsuccess() {

    }

    public void back() {

    }

    public void anotherTry() {

    }

    public void success() {

    }

}
