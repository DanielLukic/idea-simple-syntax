
import jfun.parsec.pattern.Patterns;
import jfun.parsec.*;
import jfun.parsec.tokens.Tokenizers;

class GroovyLexer
    {
    def GroovyLexer()
        {
        use( org.codehaus.parsec.groovy.ParserCategory )
            {
            def s_line_comment = Scanners.javaLineComment();
            def s_block_comment = Scanners.isBlockComment("/*","*/");
            def s_whitespace = Scanners.isWhitespaces();
            def s_delim = [s_line_comment, s_block_comment, s_whitespace].first().many();

            def ops = Terms.getOperatorsInstance("+","-","*","/", "(", ")");

            def l_number = Lexers.decimal();
            def l_tok = ops.getLexer() | l_number;

            myLexer = Scanners.lexeme(s_delim, l_tok).followedBy(Parsers.eof());
            }
        }

    def parse( something )
        {
        return Parsers.runParser( something, myLexer, "(string)" );
        }

    private myLexer;
    }

return new GroovyLexer();
