package Parser;

import AST.*;
import AST.Binary.BinaryDiv;
import AST.Binary.BinaryMinus;
import AST.Binary.BinaryMul;
import AST.Binary.BinaryPlus;
import AST.Constant.BooleanConstant;
import AST.Constant.NumericConstant;
import AST.Constant.StringLiteral;
import AST.Unary.UnaryMinus;
import AST.Unary.UnaryPlus;
import ASTStatements.*;
import Context.COMPILATION_CONTEXT;
import Helper.CParserException;
import Helper.CSyntaxErrorLog;
import Lexer.Lexer;
import Lexer.TOKEN;
import Lexer.SymbolInfo;
import Lexer.TYPE_INFO;
import Visitor.IExpressionVisitor;

import java.util.ArrayList;

public class RDParser extends Lexer {

    public RDParser(String str)
    {
        super(str);

    }

    protected TOKEN getNextToken() throws Exception {
        lastToken = currentToken;
        currentToken = getToken();
        return currentToken;
    }

    /// <summary>
    ///
    /// </summary>
    /// <returns></returns>
    public Expression callExpr( COMPILATION_CONTEXT context) throws Exception {
        currentToken = getToken();
        return expression(context);
    }

    public ArrayList parse(IExpressionVisitor visitor, COMPILATION_CONTEXT context) throws Exception, CParserException {
        getNextToken();  // Get the Next Token
        //
        // Parse all the statements
        //
        return statementList(visitor, context);
    }

    private ArrayList statementList(IExpressionVisitor visitor, COMPILATION_CONTEXT context) throws Exception, CParserException {
        ArrayList arr = new ArrayList();
        while (currentToken != TOKEN.TOK_NULL)
        {
            Statement temp = statement(visitor, context);
            if (temp != null)
            {
                arr.add(temp);
            }
        }
        return arr;
    }

    private Statement statement(IExpressionVisitor visitor, COMPILATION_CONTEXT context) throws Exception, CParserException {
        Statement retval = null;
        switch (currentToken)
        {
            case TOK_VAR_STRING:
            case TOK_VAR_NUMBER:
            case TOK_VAR_BOOL:
                retval = ParseVariableDeclStatement(context);
                getNext();
                return retval;
            case TOK_PRINT:
                retval = parsePrintStatement(context);
                getNextToken();
                break;
            case TOK_PRINTLN:
                retval = parsePrintLNStatement(context);
                getNextToken();
                break;
            case TOK_UNQUOTED_STRING:
                retval = ParseAssignmentStatement(visitor, context);
                getNext();
                return retval;
            default:
                throw new Exception("Invalid statement");
        }
        return retval;
    }

    private Statement parsePrintStatement(COMPILATION_CONTEXT context) throws Exception {
        getNextToken();
        Expression a = expression(context);

        if (currentToken != TOKEN.TOK_SEMI)
        {
            throw new Exception("; is expected");
        }
        return new PrintStatement(a);
    }

    private Statement parsePrintLNStatement(COMPILATION_CONTEXT context) throws Exception {
        getNextToken();
        Expression exp = expression(context);

        if (currentToken != TOKEN.TOK_SEMI)
        {
            throw new Exception("; is expected");
        }
        return new PrintLineStatement(exp);
    }

    public Statement ParseVariableDeclStatement(COMPILATION_CONTEXT ctx) throws CParserException {

        //--- Save the Data type
        TOKEN tok = currentToken;
        getNext();

        if (currentToken == TOKEN.TOK_UNQUOTED_STRING)
        {
            SymbolInfo symb = new SymbolInfo();
            symb.symbolName = super.lastStr;
            symb.type = (tok == TOKEN.TOK_VAR_BOOL) ?
                    TYPE_INFO.TYPE_BOOL: (tok == TOKEN.TOK_VAR_NUMBER) ?
                    TYPE_INFO.TYPE_NUMERIC : TYPE_INFO.TYPE_STRING;
            //---------- Skip to Expect the SemiColon

            getNext();



            if (currentToken == TOKEN.TOK_SEMI)
            {
                // ----------- Add to the Symbol Table
                // for type analysis
                ctx.getTable().add(symb);

                // --------- return the Object of type
                // --------- VariableDeclStatement
                // This will just store the Variable name
                // to be looked up in the above table
                return new VariableDeclStatement(symb);
            }
            else
            {
                CSyntaxErrorLog.addLine("; expected");
                CSyntaxErrorLog.addLine(getCurrentLine(saveIndex()));
                throw new CParserException(-100, ", or ; expected", saveIndex());
            }
        }
        else
        {

            CSyntaxErrorLog.addLine("invalid variable declaration");
            CSyntaxErrorLog.addLine(getCurrentLine(saveIndex()));
            throw new CParserException(-100, ", or ; expected", saveIndex());
        }


    }

    public Statement ParseAssignmentStatement(IExpressionVisitor visitor, COMPILATION_CONTEXT ctx) throws Exception, CParserException {

        //
        // Retrieve the variable and look it up in
        // the symbol table ..if not found throw exception
        //
        String variable = super.lastStr;
        SymbolInfo s = ctx.getTable().get(variable);
        if (s == null)
        {
            CSyntaxErrorLog.addLine("Variable not found  " + lastStr);
            CSyntaxErrorLog.addLine(getCurrentLine(saveIndex()));
            throw new CParserException(-100, "Variable not found", saveIndex());

        }

        //------------ The next token ought to be an assignment
        // expression....

        getNext();

        if (currentToken != TOKEN.TOK_ASSIGN)
        {

            CSyntaxErrorLog.addLine("= expected");
            CSyntaxErrorLog.addLine(getCurrentLine(saveIndex()));
            throw new CParserException(-100, "= expected", saveIndex());

        }

        //-------- Skip the token to start the expression
        // parsing on the RHS
        getNext();
        Expression exp = expression(ctx);

        //------------ Do the type analysis ...

        if (exp.accept(visitor, ctx) != s.type)
        {
            throw new Exception("Type mismatch in assignment");

        }

        // -------------- End of statement ( ; ) is expected
        if (currentToken != TOKEN.TOK_SEMI)
        {
            CSyntaxErrorLog.addLine("; expected");
            CSyntaxErrorLog.addLine(getCurrentLine(saveIndex()));
            throw new CParserException(-100, " ; expected", -1);

        }
        // return an instance of AssignmentStatement node..
        //   s => Symbol info associated with variable
        //   exp => to evaluated and assigned to symbol_info
        return new AssignmentStatement(s, exp);

    }

    /// <summary>
    ///
    /// </summary>
    /// <returns></returns>
    public Expression expression(COMPILATION_CONTEXT context) throws Exception {
        TOKEN l_token;
        Expression retValue = term(context);
        while (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB)
        {
            l_token = currentToken;
            currentToken = getToken();
            Expression e1 = expression(context);
            if (l_token == TOKEN.TOK_PLUS)
                retValue = new BinaryPlus(retValue, e1);
            else
                retValue = new BinaryMinus(retValue, e1);
        }

        return retValue;

    }
    /// <summary>
    ///
    /// </summary>
    public Expression term(COMPILATION_CONTEXT context) throws Exception {
        TOKEN l_token;
        Expression retValue = factor(context);

        while (currentToken == TOKEN.TOK_MUL || currentToken == TOKEN.TOK_DIV)
        {
            l_token = currentToken;
            currentToken = getToken();


            Expression e1 = term(context);
            if (l_token == TOKEN.TOK_MUL)
                retValue = new BinaryMul(retValue, e1);
            else
                retValue = new BinaryDiv(retValue, e1);

        }

        return retValue;
    }

    /// <summary>
    ///
    /// </summary>
    public Expression factor(COMPILATION_CONTEXT context) throws Exception {
        TOKEN l_token;
        Expression retValue = null;
        if (currentToken == TOKEN.TOK_NUMERIC)
        {

            retValue = new NumericConstant(getNumber());
            currentToken = getToken();

        }
        else if (currentToken == TOKEN.TOK_STRING)
        {
            retValue = new StringLiteral(lastStr);
            currentToken = getToken();
        }
        else if (currentToken == TOKEN.TOK_BOOL_FALSE ||
                currentToken == TOKEN.TOK_BOOL_TRUE)
        {
            retValue = new BooleanConstant(
                    currentToken == TOKEN.TOK_BOOL_TRUE ? true : false);
            currentToken = getToken();
        }
        else if (currentToken == TOKEN.TOK_OPAREN)
        {

            currentToken = getToken();

            retValue = expression(context);  // Recurse

            if (currentToken != TOKEN.TOK_CPAREN)
            {
                System.out.println("Missing Closing Parenthesis\n");
                throw new Exception();

            }
            currentToken = getToken();
        }

        else if (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB)
        {
            l_token = currentToken;
            currentToken = getToken();
            retValue = factor(context);

            if (l_token == TOKEN.TOK_PLUS)
                retValue = new UnaryPlus(retValue);
            else
                retValue = new UnaryMinus(retValue);
        } else if (currentToken == TOKEN.TOK_UNQUOTED_STRING) {
            ///
            ///  Variables
            ///
            String str = super.lastStr;
            SymbolInfo info = context.getTable().get(str);

            if (info == null)
                throw new Exception("Undefined symbol");

            getNextToken();
            retValue = new Variable(info);
        }
        else
        {

            System.out.println("Illegal Token");
            throw new Exception();
        }


        return retValue;

    }
}
