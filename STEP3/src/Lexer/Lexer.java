package Lexer;

public class Lexer {

    String exp; // Expression string
    int index; // index into a character
    int length; // Length of the string
    double number; // Last grabbed number from the stream
    private ValueTable[] val = null;
    private String lastStr;

    // ctor
    public Lexer(String expr)
    {
        exp = expr;
        length = exp.length();
        index = 0;

        val = new ValueTable[2];
        val[0] = new ValueTable(TOKEN.TOK_PRINT, "PRINT");
        val[1] = new ValueTable(TOKEN.TOK_PRINTLN, "PRINTLINE");
    }

    // grab the token from stream
    public TOKEN getToken() throws Exception {
        before:
        while (true) {
            // label
            TOKEN tok = TOKEN.ILLEGAL_TOKEN;

            // Skip the white space
            while (index < length &&
                    (exp.charAt(index) == ' ' || exp.charAt(index) == '\t'))
                index++;

            // End of string ? return NULL
            if (index == length)
                return TOKEN.TOK_NULL;

            switch (exp.charAt(index)) {
                case '\r':
                case '\n':
                    index++;
                    continue before;
                case '+':
                    tok = TOKEN.TOK_PLUS;
                    index++;
                    break;
                case '-':
                    tok = TOKEN.TOK_SUB;
                    index++;
                    break;
                case '/':
                    tok = TOKEN.TOK_DIV;
                    index++;
                    break;
                case '*':
                    tok = TOKEN.TOK_MUL;
                    index++;
                    break;
                case '(':
                    tok = TOKEN.TOK_OPAREN;
                    index++;
                    break;
                case ')':
                    tok = TOKEN.TOK_CPAREN;
                    index++;
                    break;
                case ';':
                    tok = TOKEN.TOK_SEMI;
                    index++;
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    String str = "";
                    while (index < length &&
                            (exp.charAt(index) == '0' ||
                                    exp.charAt(index) == '1' ||
                                    exp.charAt(index) == '2' ||
                                    exp.charAt(index) == '3' ||
                                    exp.charAt(index) == '4' ||
                                    exp.charAt(index) == '5' ||
                                    exp.charAt(index) == '6' ||
                                    exp.charAt(index) == '7' ||
                                    exp.charAt(index) == '8' ||
                                    exp.charAt(index) == '9')) {
                        str += exp.charAt(index);
                        index++;
                    }
                    number = Double.parseDouble(str);
                    tok = TOKEN.TOK_DOUBLE;
                }
                break;
                default: {
                    if (Character.isLetter(exp.charAt(index))) {

                        String temp = String.valueOf((exp.charAt(index)));
                        index++;
                        while (index < length && ((Character.isLetterOrDigit(exp.charAt(index))) ||
                                exp.charAt(index) == '_')) {
                            temp += exp.charAt(index);
                            index++;
                        }

                        temp = temp.toUpperCase();

                        for (int i = 0; i < this.val.length; ++i) {
                            if (val[i].Value.compareTo(temp) == 0)
                                return val[i].tok;

                        }


                        this.lastStr = temp;


                        return TOKEN.TOK_UNQUOTED_STRING;


                    } else {
                        System.out.println("Error");
                        throw new Exception();
                    }

                }
            }
            return tok;
        }

    }
    public double getNumber() { return number; }

}
