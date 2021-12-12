package Lab7;

import Lab6.ConfigurationDTO;
import Lab6.RecursiveDescendent;

public class RecursiveDescendentAlgorithm {

    private final RecursiveDescendent recursiveDescendent;

    public RecursiveDescendentAlgorithm(RecursiveDescendent newRecursiveDescendent) {
        this.recursiveDescendent = newRecursiveDescendent;
    }

    public void runParsing() {
        while (!recursiveDescendent.configurationDTO.state.equals(ConfigurationDTO.StateValues.f) &&
                !recursiveDescendent.configurationDTO.state.equals(ConfigurationDTO.StateValues.e)) {
            if (recursiveDescendent.configurationDTO.state.equals(ConfigurationDTO.StateValues.q)) {
                if (recursiveDescendent.configurationDTO.position == recursiveDescendent.sequence.length() + 1 &&
                        recursiveDescendent.configurationDTO.inputStack.isEmpty()) {
                    recursiveDescendent.success(); // make success yayyyy
                } else {
                    String headOfInputStack = "E";
                    if (recursiveDescendent.configurationDTO.inputStack.size() > 0) {
                         headOfInputStack = recursiveDescendent.configurationDTO.inputStack.get(0);
                    }
                    if (recursiveDescendent.grammar.getNonTerminals().contains(headOfInputStack)) {
                        recursiveDescendent.expand(); // if head of input stack is a non-terminal we expand
                    } else {
                        if (recursiveDescendent.grammar.getTerminals().contains(headOfInputStack)) {
                            recursiveDescendent.advance(); // if headOfInputStack is a terminal we advance
                        } else {
                            recursiveDescendent.momentaryInsuccess();
                            // if is not a terminal nor a nonTerminal we do momentaryInsuccess
                        }
                    }
                }
            } else {
                if (recursiveDescendent.configurationDTO.state.equals(ConfigurationDTO.StateValues.b)) {
                    String headOfWorkingStack = recursiveDescendent.configurationDTO.workingStack.
                            get(recursiveDescendent.configurationDTO.workingStack.size() - 1);
                    if (recursiveDescendent.grammar.getTerminals().contains(headOfWorkingStack)) {
                        recursiveDescendent.back(); // if head of working stack is a non-terminal we go back
                    } else {
                        recursiveDescendent.anotherTry(); // if is a terminal or something else we try again
                    }
                }
            }
        }

        if (recursiveDescendent.configurationDTO.state.equals(ConfigurationDTO.StateValues.e)) {
            System.out.println("Error");
        } else {
            System.out.println("Sequence Accepted");
            System.out.println("WorkingStack " + recursiveDescendent.configurationDTO.workingStack);
            System.out.println("InputStack " + recursiveDescendent.configurationDTO.inputStack);
        }
    }
}
