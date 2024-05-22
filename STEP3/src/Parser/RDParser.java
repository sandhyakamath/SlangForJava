package Parser;

import AST.*;
import ASTStatements.PrintLineStatement;
import ASTStatements.PrintStatement;
import ASTStatements.Statement;
import Lexer.Lexer;
import Lexer.TOKEN;

import java.util.ArrayList;

public class RDParser extends Lexer {

    TOKEN currentToken;
    TOKEN lastToken;

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
    public Expression callExpr() throws Exception {
        currentToken = getToken();
        return expression();
    }

    public ArrayList parse() throws Exception {
        getNextToken();  // Get the Next Token
        //
        // Parse all the statements
        //
        return statementList();
    }

    private ArrayList statementList() throws Exception {
        ArrayList arr = new ArrayList();
        while (currentToken != TOKEN.TOK_NULL)
        {
            Statement temp = statement();
            if (temp != null)
            {
                arr.add(temp);
            }
        }
        return arr;
    }

    private Statement statement() throws Exception {
        Statement retval = null;
        switch (currentToken)
        {
            case TOK_PRINT:
                retval = parsePrintStatement();
                getNextToken();
                break;
            case TOK_PRINTLN:
                retval = parsePrintLNStatement();
                getNextToken();
                break;
            default:
                throw new Exception("Invalid statement");
        }
        return retval;
    }

    private Statement parsePrintStatement() throws Exception {
        getNextToken();
        Expression a = expression();

        if (currentToken != TOKEN.TOK_SEMI)
        {
            throw new Exception("; is expected");
        }
        return new PrintStatement(a);
    }

    private Statement parsePrintLNStatement() throws Exception {
        getNextToken();
        Expression exp = expression();

        if (currentToken != TOKEN.TOK_SEMI)
        {
            throw new Exception("; is expected");
        }
        return new PrintLineStatement(exp);
    }

    /// <summary>
    ///
    /// </summary>
    /// <returns></returns>
    public Expression expression() throws Exception {
        TOKEN l_token;
        Expression retValue = term();
        while (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB)
        {
            l_token = currentToken;
            currentToken = getToken();
            Expression e1 = expression();
            retValue = new BinaryExpression(retValue, e1,
                    l_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);

        }

        return retValue;

    }
    /// <summary>
    ///
    /// </summary>
    public Expression term() throws Exception {
        TOKEN l_token;
        Expression retValue = factor();

        while (currentToken == TOKEN.TOK_MUL || currentToken == TOKEN.TOK_DIV)
        {
            l_token = currentToken;
            currentToken = getToken();


            Expression e1 = term();
            retValue = new BinaryExpression(retValue, e1,
                    l_token == TOKEN.TOK_MUL ? OPERATOR.MUL : OPERATOR.DIV);

        }

        return retValue;
    }

    /// <summary>
    ///
    /// </summary>
    public Expression factor() throws Exception {
        TOKEN l_token;
        Expression retValue = null;
        if (currentToken == TOKEN.TOK_DOUBLE)
        {

            retValue = new NumericConstant(getNumber());
            currentToken = getToken();

        }
        else if (currentToken == TOKEN.TOK_OPAREN)
        {

            currentToken = getToken();

            retValue = expression();  // Recurse

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
            retValue = factor();

            retValue = new UnaryExpression(retValue,
                    l_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
        }
        else
        {

            System.out.println("Illegal Token");
            throw new Exception();
        }


        return retValue;

    }
}
