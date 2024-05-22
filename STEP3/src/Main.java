import AST.*;
import ASTStatements.Statement;
import Builder.ExpressionBuilder;
import Parser.RDParser;

import java.util.ArrayList;

public class Main {
    static void TestFirstScript() throws Exception {
        String a = "PRINTLINE 2*10;" + "\r\n" + "PRINTLINE 10;\r\n PRINT 2*10;\r\n";
        RDParser p = new RDParser(a);
        ArrayList arr = p.parse();
        for (Object obj : arr)
        {
            Statement s = (Statement) obj;
            s.execute(null);
        }
    }
    /// <summary>
    ///
    /// </summary>
    static void TestSecondScript() throws Exception {
        String a = "PRINTLINE -2*10;" + "\r\n" + "PRINTLINE -10*-1;\r\n PRINT 2*10;\r\n";
        RDParser p = new RDParser(a);
        ArrayList arr = p.parse();
        for (Object obj : arr) {
            Statement s = (Statement) obj;
            s.execute(null);
        }
    }

    public static void main(String[] args)  {
            // TestFirstScript();
            try {
                TestFirstScript();
                // System.out.println;
            } catch (Exception e) {
                System.out.println(e);
            }
    }
}