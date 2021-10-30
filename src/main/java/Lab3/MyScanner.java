package Lab3;

import Lab2.DataSet.SymbolTable;

import java.io.*;
import java.util.*;

public class MyScanner {
    private final String programFileName;
    private final List<String> separators = Arrays.asList("[", "]", "(", ")", "{", "}", ";", " ", ":", ",", "\n", "\t");
    private final List<String> operators = Arrays.asList("+", "-", "*", "/", "%", "=",":", "<", "<=", ">=", ">", "!=", "and", "or", "!");
    private final List<String> reservedWords = Arrays.asList("int", "string", "char", "bool", "return", "while", "do",
            "for", "if", "else", "read", "write", "struct", "or", "and", "START", "END", "true", "false");
    private Map<Integer, Integer> PIF = new HashMap<>();
    private final int capacity = 83;
    private final SymbolTable symbolTable = new SymbolTable(capacity);
    private final Codification codification;


    public MyScanner(String programFileName) {
        this.programFileName = programFileName;
        codification = new Codification(operators, separators, reservedWords);
    }


    public void scanFile() {
        try {
            File file = new File(programFileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            String line;

            while((line = br.readLine()) != null) {
                List<String> tokenList = TokenizeLine(line);
                scanLine(i,tokenList);
                i++;
            }
            printSymbolTable();
            printProgramInternalForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printProgramInternalForm() {
        System.out.println("PIF");
        System.out.println(PIF);
    }

    public void printSymbolTable(){
        System.out.println("Symbol Table");
        System.out.println(symbolTable.toString());
    }

    public List<String> TokenizeLine(String line) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i < line.length()) {

            if (line.charAt(i) == '"') {
                // user' declared strings
                StringBuilder stringToken = new StringBuilder("\"");
                i++;
                while (i < line.length() && line.charAt(i) != '"') {
                    stringToken.append(line.charAt(i));
                    i++;
                }
                stringToken.append("\"");
                i++;
                tokens.add(stringToken.toString());
            }
            else if (isSeparator(String.valueOf(line.charAt(i)))) {
                // separators excluding space, tab and new line
                if (!(line.charAt(i) == ' ') && !(line.charAt(i) == '\t') && !(line.charAt(i) == '\n')) {
                    tokens.add(String.valueOf(line.charAt(i)));
                }
                i++;
            }
            else if (isOperator(String.valueOf(line.charAt(i)))) {
                // composed operators and operators
                if(isOperator(String.valueOf(line.charAt(i + 1)))) {
                    tokens.add(line.charAt(i) + String.valueOf(line.charAt(i + 1)));
                    i = i + 2;
                }
                else {
                    tokens.add(String.valueOf(line.charAt(i)));
                    i++;
                }
            }
            else {
                // other tokens like reserved words, constants and identifiers
                StringBuilder token = new StringBuilder(String.valueOf(line.charAt(i)));
                i++;
                while (i < line.length() && !isSeparator(String.valueOf(line.charAt(i))) && !isOperator(String.valueOf(line.charAt(i)))) {
                    token.append(line.charAt(i));
                    i++;
                }
                tokens.add(token.toString());
            }
        }

        return tokens;
    }

    public void scanLine(int line, List<String> tokenizedLine) {
        String lastToken = "";

        for(int i = 0; i < tokenizedLine.size(); ++i) {
            String token = tokenizedLine.get(i);

            if (isConstant(token)){
                int code = codification.getCodes().get("constant"); // 1
                symbolTable.addInSymbolTable(token);
                var position = symbolTable.search(token);
                PIF.put(code, position);
                System.out.println("Token " + token + " on position: " + position + "\n");
            }
            else if ((token.equals("-") || token.equals("+")) && (isNumber(operators.get(i + 1))) &&
                    (lastToken.equals("=") || lastToken.equals("("))) {
                token += tokenizedLine.get(i + 1);
                i++;
                if (!token.equals("-0")){
                    int code = codification.getCodes().get("constant"); // 1
                    symbolTable.addInSymbolTable(token);
                    var position = symbolTable.search(token);
                    PIF.put(code, position);
                    System.out.println("Token " + token + " on position: ");
                }
                else {
                    System.out.println("Error at line " + line + ". Invalid token: " + token);
                }
            }
            else if (isOperator(token) || isSeparator(token) || isReservedWord(token)) {
                int code = codification.getCodes().get(token);
                PIF.put(code, -1);
                System.out.println("Token " + token + " on position: " + -1 + "\n");
            }
            else if (isIdentifier(token)){
                int code = codification.getCodes().get("identifier"); // 0
                symbolTable.addInSymbolTable(token);
                var position = symbolTable.search(token);
                PIF.put(code, position);
                System.out.println("Token " + token + " on position: " + position);
            }
            else {
                System.out.println("Error at line " + line + ". Invalid token: " + token);
            }
            lastToken = token;
        }
    }

    public boolean isSeparator(String token) {
        return this.codification.getSeparators().contains(token);
    }

    public boolean isOperator(String token){
        return this.codification.getTokens().contains(token);
    }

    public boolean isReservedWord(String token){
        return this.codification.getReservedWords().contains(token);
    }

    public boolean isNumber(String token){
        String number = "^([+|-]?[1-9][0-9]*)|0$";
        return token.matches(number);
    }

    public boolean isString(String token){
        String string = "^\"[a-zA-Z0-9_.:;,?!*' ]*\"$";
        return token.matches(string);
    }

    public boolean isCharacter(String token){
        String character = "^\'[a-zA-Z0-9_]\'$";
        return token.matches(character);
    }

    public boolean isConstant(String token){
        return isNumber(token) || isString(token) || isCharacter(token);
    }

    public boolean isIdentifier(String token){
        String pattern = "^[a-zA-Z]([a-zA-Z0-9_]*$)";
        return token.matches(pattern);
    }
}