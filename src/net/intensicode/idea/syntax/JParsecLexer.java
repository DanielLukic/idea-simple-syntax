package net.intensicode.idea.syntax;

import com.intellij.util.text.CharArrayCharSequence;
import jfun.parsec.Parser;
import jfun.parsec.Parsers;
import jfun.parsec.Tok;
import jfun.parsec.tokens.TypedToken;
import net.intensicode.idea.core.SimpleLexer;
import net.intensicode.idea.core.SimpleToken;

/**
 * TODO: Describe this!
 */
public final class JParsecLexer implements SimpleLexer
{
    public JParsecLexer( final Parser<Tok[]> aLexer )
    {
        if ( aLexer == null ) throw new NullPointerException();
        myLexer = aLexer;
    }

    // From SimpleLexer

    public final SimpleToken findToken( final char[] aBuffer, final int aStartOffset, final int aEndOffset )
    {
        final CharArrayCharSequence sequence = new CharArrayCharSequence( aBuffer, aStartOffset, aEndOffset );

        final Tok[] tokens = Parsers.runParser( sequence, myLexer, "(editor buffer)" );
        if ( tokens == null || tokens.length == 0 ) return null;

        final Tok firstToken = tokens[ 0 ];
        final TypedToken token = ( TypedToken ) firstToken.getToken();

        myToken.id = token.getType();
        myToken.start = aStartOffset + firstToken.getIndex();
        myToken.end = myToken.start + firstToken.getLength();

        return myToken;
    }



    private final Parser<Tok[]> myLexer;

    private final SimpleToken myToken = new SimpleToken();
}
