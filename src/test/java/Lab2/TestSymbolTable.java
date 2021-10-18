package Lab2;

import Lab2.DataSet.SymbolTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSymbolTable {

    @Test
    public void testAddInSymbolTable(){
        SymbolTable symbolTable = new SymbolTable(17);
        assertTrue(symbolTable.addInSymbolTable("a"));
        assertFalse(symbolTable.addInSymbolTable("a"));
        assertTrue(symbolTable.addInSymbolTable("ab"));
        assertTrue(symbolTable.addInSymbolTable("Cd"));
        assertTrue(symbolTable.addInSymbolTable("dc"));
        assertTrue(symbolTable.addInSymbolTable("ba"));
    }

    @Test
    public void testSearch(){
        SymbolTable symbolTable = new SymbolTable(17);
        symbolTable.addInSymbolTable("a");
        symbolTable.addInSymbolTable("a");
        symbolTable.addInSymbolTable("ab");
        symbolTable.addInSymbolTable("Cd");
        symbolTable.addInSymbolTable("dc");
        symbolTable.addInSymbolTable("ba");
        //167 % 17 = 14
        assertEquals(14,symbolTable.search("Cd"));
        // 97 % 17 = 12
        assertEquals(12,symbolTable.search("a"));
        // 195 % 27 = 8
        assertEquals(8,symbolTable.search("ab"));
        assertEquals(9,symbolTable.search("ba"));
        // not in ST
        assertEquals(-1,symbolTable.search("notInST"));

    }

}
