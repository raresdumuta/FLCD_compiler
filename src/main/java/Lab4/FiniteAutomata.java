package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomata {

    private List<String> setOfStates;
    private List<String> alphabet;
    private final Map<AbstractMap.SimpleEntry<String, String>, List<String>> transitionsList;
    private List<String> finalStates;
    private String initialState;
    private String fileName;

    public FiniteAutomata(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.setOfStates = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitionsList = new HashMap<>();
        this.finalStates = new ArrayList<>();
        this.initialState = "";
        readFiniteAutomata();
    }

    public boolean isDFA() {
        System.out.println(transitionsList);
        for (var transition : transitionsList.keySet()) {
            if (transitionsList.get(transition).size() > 1) {
                return false;
            }
        }
        return true;
    }

    private String nextState(String startState, String value) {
        for (var transition : transitionsList.keySet()) {
            if (transitionsList.get(transition).equals(startState) && transition.getValue().equals(value))
                if (transitionsList.get(transition).size() == 1)
                    return transitionsList.get(transition).get(0);
        }
        return "No State Found";
    }

    public boolean isAcceptedSequence(String inputSequence) {
        if(!isDFA()){
            return false;
        }

        String currentState = this.initialState;

        for (char character : inputSequence.toCharArray()) {
            var key = new AbstractMap.SimpleEntry<>(currentState,String.valueOf(character));
            if(this.transitionsList.containsKey(key)){
                currentState = this.transitionsList.get(key).get(0);
            } else {
                return false;
            }
        }
        // Case: final state
        return this.finalStates.contains(currentState);
    }

    public void readFiniteAutomata() throws FileNotFoundException {
        File file = new File("src/main/resources/fa.in");
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
            AbstractMap.SimpleEntry<String, String> model = new AbstractMap.SimpleEntry<>(transitions.get(0), transitions.get(1));

            String endStates = transitions.get(2);
            if (transitionsList.containsKey(model)) {
                if (!transitionsList.get(model).contains(endStates)) {
                    transitionsList.get(model).add(endStates);
                }
            } else {
                this.transitionsList.put(model, new ArrayList<>(Collections.singletonList(endStates)));
            }
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

