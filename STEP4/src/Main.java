import ASTStatements.Statement;
import Context.COMPILATION_CONTEXT;
import Context.RUNTIME_CONTEXT;
import Helper.CParserException;
import Parser.RDParser;
import Visitor.TreeEvaluatorVisitor;

import java.util.ArrayList;

public class Main {
    static void TestFirstScript() throws Exception, CParserException {
        String a = "PRINTLINE 2*10;" + "\r\n" + "PRINTLINE 10;\r\n PRINT 2*10;\r\n";
        TreeEvaluatorVisitor visitor = new TreeEvaluatorVisitor();
        RUNTIME_CONTEXT context = null;
        COMPILATION_CONTEXT ccontext = null;
        RDParser p = new RDParser(a);
        ArrayList arr = p.parse(visitor, ccontext);
        for (Object obj : arr)
        {
            Statement s = (Statement) obj;
            s.execute(visitor, context);
        }
    }
    /// <summary>
    ///
    /// </summary>
    static void TestSecondScript() throws Exception, CParserException {
       // String programs2 = "NUMERIC a; a = 2*3+5* 30 + -(4*5+3); PRINTLINE a;  // Dump a" ;
        String programs2 = "NUMERIC a; Numeric b; a = 2; b=3; PRINTLINE a*b;  // Dump a" ;
        RDParser pars = new RDParser(programs2);
        TreeEvaluatorVisitor visitor = new TreeEvaluatorVisitor();
        COMPILATION_CONTEXT ctx = new COMPILATION_CONTEXT();
        ArrayList stmts = pars.parse(visitor, ctx);
        RUNTIME_CONTEXT f = new RUNTIME_CONTEXT();
        for (Object obj : stmts ) {
            Statement s = (Statement) obj;
            s.execute(visitor, f);
        }

    }

    public static void main(String[] args)  {
            // TestFirstScript();
            try {
                TestSecondScript();
                // System.out.println;
            } catch (Exception | CParserException e) {
                System.out.println(e);
            }
    }
}