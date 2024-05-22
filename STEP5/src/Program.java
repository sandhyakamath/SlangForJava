import Compilation.TModule;
import Context.RUNTIME_CONTEXT;
import Lexer.SymbolInfo;
import Parser.RDParser;
import Visitor.IExpressionVisitor;
import Visitor.TreeEvaluatorVisitor;

import java.io.BufferedReader;
import java.io.FileReader;

public class Program {
    static void TestFileScript() throws Exception {

        // -------------- Read the contents from the file
        String currentDirectory = System.getProperty("user.dir");
        String file = currentDirectory+"/Data/STEP5/Bool.sl";
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append("\n\r");
        }
        reader.close();

        //---------------- Creates the Parser Object
        // With Program text as argument
        RDParser pars = null;
        pars = new RDParser(content.toString());
        TModule p = null;
        p = pars.doParse();
        IExpressionVisitor visitor = new TreeEvaluatorVisitor();
        if (p == null)
        {
            System.out.println("Parse Process Failed");
            return;
        }
        //
        //  Now that Parse is Successul...
        //  Do a recursive interpretation...!
        //
        RUNTIME_CONTEXT f = new RUNTIME_CONTEXT(p);
        SymbolInfo fp = p.Execute(visitor, f);

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="args"></param>
    public static void main(String args[]) throws Exception {

        TestFileScript();
        //------------- Wait for the Key Press

    }
}