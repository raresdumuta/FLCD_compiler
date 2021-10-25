package Lab3;

import Lab2.DataSet.SymbolTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MyScanner {
    private final String programFileName;
    private final List<String> tokens = Arrays.asList("+",":", "-","*", "/","=", "<", ">", "%");
    private final List<String> separators = Arrays.asList("[", "]", "(", ")", "{", "}", ":", ";", " ", "\n", "\t",",");
    private final List<String> reservedWords = Arrays.asList("int", "string", "bool", "char", "return", "if", "else", "while", "do", "read", "write", "START", "END", "and", "or");
    private Map<Integer, Integer> PIF = new HashMap<>();
    private final int capacity = 83;
    private final SymbolTable symbolTable = new SymbolTable(capacity);
    private int currentLine;
    private final Codification codification;


    public MyScanner(String programFileName) {
        this.programFileName = programFileName;
        codification = new Codification(tokens, separators, reservedWords);
        currentLine = 0;
    }


    public List<String> tokenizeLine(String line) {
        List<String> tokens = new ArrayList<>();

        int i = 0;
        while (i < line.length()) {
            if (line.charAt(i) == '"') {
                StringBuilder stringToken = new StringBuilder("\"");
                i++;
                while (i < line.length() && line.charAt(i) != '"') {
                    stringToken.append(line.charAt(i));
                    i++;
                }
                stringToken.append("\"");
                i++;

                tokens.add(stringToken.toString());
            } else if (line.charAt(i) == '\'') {
                StringBuilder charToken = new StringBuilder("'");
                i++;
                while (i < line.length() && line.charAt(i) != '\'') {
                    charToken.append(line.charAt(i));
                    i++;
                }
                charToken.append("'");
                i++;

                tokens.add(charToken.toString());
            } else if (this.isSeparator(String.valueOf(line.charAt(i)))) {
                if (!(line.charAt(i) == ' ') && !(line.charAt(i) == '\t') && !(line.charAt(i) == '\n')) {
                    tokens.add(String.valueOf(line.charAt(i)));
                }
                i++;
            } else if (this.isOperator(String.valueOf(line.charAt(i)))) {
                if (this.isOperator(String.valueOf(line.charAt(i + 1)))) {
                    tokens.add(line.charAt(i) + String.valueOf(line.charAt(i + 1)));
                    i = i + 2;
                } else {
                    tokens.add(String.valueOf(line.charAt(i)));
                    i++;
                }
            } else {
                // other tokens like reserved, constants and identifiers
                StringBuilder token = new StringBuilder(String.valueOf(line.charAt(i)));
                i++;
                while (i < line.length() && !this.isSeparator(String.valueOf(line.charAt(i)))
                        && !this.isOperator(String.valueOf(line.charAt(i)))) {
                    token.append(line.charAt(i));
                    i++;
                }
                tokens.add(token.toString());
            }
        }

        return tokens;
    }

    public void scanFile() {
        try {
            File inputFile = new File(programFileName);
            Scanner programReader = new Scanner(inputFile);


            // read line
            while (programReader.hasNextLine()) {
                //read characters from line
                String line = programReader.nextLine();
                List<String> tokenizeLine = tokenizeLine(line);
                String lastToken = "";
                for (int i = 0; i < tokenizeLine.size(); i++) {
                    String token = String.valueOf(tokenizeLine.get(i));
                    if (this.isIdentifier(token)) {
                        int code = this.codification.getCodes().get("identifier");
                        this.symbolTable.addInSymbolTable(token);
                        int positionInSt = this.symbolTable.search(token);
                        this.PIF.put(code, positionInSt);
                    } else if (this.isConstant(token)) {
                        int code = this.codification.getCodes().get("constant");
                        this.PIF.put(code, -1);
                    } else if ((token.equals("-") || token.equals("+")) && (this.isNumber(tokenizeLine.get(i + 1))) &&
                            (lastToken.equals("=") || lastToken.equals("("))) {
                        token += tokenizeLine.get(i + 1);
                        if (!token.equals("-0")) {
                            int code = this.codification.getCodes().get("constant"); // 1
                            this.PIF.put(code, -1);
                        } else {
                            System.out.println("Error at line " + currentLine + " invalid token " + token);
                        }
                    } else if (this.isOperator(token) || this.isSeparator(token) || this.isReservedWord(token)) {
                        int code = this.codification.getCodes().get(token);
                        this.PIF.put(code, -1);
                    } else {
                        System.out.println("Error at line " + currentLine + " invalid token " + token);
                    }
                }
                currentLine++;

            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean isSeparator(String token) {
        return this.codification.getSeparators().contains(token);
    }

    public boolean isOperator(String token) {
        return this.codification.getTokens().contains(token);
    }

    public boolean isReservedWord(String token) {
        return this.codification.getReservedWords().contains(token);
    }

    public boolean isIdentifier(String token) {
        String pattern = "^[a-zA-Z]([a-zA-Z0-9_]*$)";
        return token.matches(pattern);
    }

    public boolean isConstant(String token) {
        String number = "^([1-9][0-9]*)|0$";
        String string = "^\"[a-zA-Z0-9_.:;,?!*' ]*\"$";
        String character = "^\'[a-zA-Z0-9_.:;,?!*\" ]\'$";
        return token.matches(number) || token.matches(string) || token.matches(character);
    }

    public boolean isNumber(String token) {
        String number = "^([1-9][0-9]*)|0$";
        return token.matches(number);
    }

    public void printProgramInternalForm() {
        for (Map.Entry<Integer, Integer> entry : PIF.entrySet()) {
            System.out.println("Token " + entry.getKey() + " at position in PIF: " + entry.getValue());
        }
    }

    public void printSymbolTable() {
        System.out.println(symbolTable);
    }

}
