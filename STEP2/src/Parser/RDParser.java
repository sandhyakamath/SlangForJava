package Parser;

import AST.*;
import Lexer.Lexer;
import Lexer.TOKEN;

public class RDParser extends Lexer {

    TOKEN currentToken;


    public RDParser(String str)
    {
        super(str);

    }

    /// <summary>
    ///
    /// </summary>
    /// <returns></returns>
    public Expression callExpr() throws Exception {
        currentToken = getToken();
        return expression();
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
