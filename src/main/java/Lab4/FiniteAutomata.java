package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {

    private List<String> setOfStates;
    private List<String> alphabet;
    private List<Transition> transitionsList;
    private List<String> finalStates;
    private String initialState;
    private String fileName;

    public FiniteAutomata(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.setOfStates = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitionsList = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.initialState = "";
        readFiniteAutomata();
    }

    public boolean isDFA() {
        for (Transition transition : transitionsList) {
            if (transition.getEndState().size() > 1) {
                return false;
            }
        }
        return true;
    }

    private String nextState(String startState, String value) {
        for (Transition transition: transitionsList) {
            if (transition.getStartState().equals(startState) && transition.getValue().equals(value))
                if (transition.getEndState().size() == 1)
                    return transition.getEndState().get(0);
        }
        return "No State Found";
    }

    public boolean isAcceptedSequence(String seq) {
        String currentState = this.initialState;
        String[] sequence = seq.split("");
        for (String character : sequence) {
            String nextState = nextState(currentState, character);

            System.out.println(currentState + " " + character + " " + nextState);

            // Case: no state
            if (nextState.equals("No State Found")) return false;

            currentState = nextState;
        }
        // Case: final state
        return this.finalStates.contains(currentState);
    }

    public void readFiniteAutomata() throws FileNotFoundException {
        File file = new File(this.fileName);
        Scanner scanner = new Scanner(file);

        // Set of states
        String setOfStatesText = scanner.nextLine(); // read the line that separates
        String setOfStates = scanner.nextLine();
        this.setOfStates = Arrays.asList(setOfStates.split(","));

        // Alphabet
        String alphabetText = scanner.nextLine(); // read the line that separates
        String alphabet = scanner.nextLine();
        this.alphabet = Arrays.asList(alphabet.split(","));

        // Transitions
        String transitionsText = scanner.nextLine(); // read the line that separates
        String transition = "";

        //  As long as we have transitions, we read them
        while (true) {
            transition = scanner.nextLine();
            if (transition.equals("FINAL STATES")) { // check if there are no more transition
                break;
            }

            List<String> transitions = Arrays.asList(transition.split(","));
            Transition model = new Transition();
            model.setStartState(transitions.get(0));
            model.setValue(transitions.get(1));
            List<String> endStates = new ArrayList<String>();
            for (int i = 2; i < transitions.size(); i++) {
                endStates.add(transitions.get(i));
            }
            model.setEndState(endStates);

            this.transitionsList.add(model);
        }

        // Final states
        String finalStates = scanner.nextLine();
        this.finalStates = Arrays.asList(finalStates.split(","));

        // Initial state
        String initialState = scanner.nextLine();
        this.initialState = scanner.nextLine();

        scanner.close();
    }

    public List<String> getSetOfStates() {
        return setOfStates;
    }
}

