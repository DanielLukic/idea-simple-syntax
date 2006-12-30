
package jparsec;

import jfun.parsec.Parser
import jfun.parsec.Parsers
import jfun.parsec.Tok
import jfun.parsec.tokens.TypedToken

import net.intensicode.idea.syntax.SimpleLexer
import net.intensicode.idea.syntax.SimpleToken

import net.intensicode.idea.util.MutableCharSequence

import java.util.ArrayList
import java.util.List



class LexerAdapter implements SimpleLexer
{
    LexerAdapter( aParsecLexer )
    {
        myLexer = aParsecLexer
    }

    // From SimpleLexer

    void start( char[] aCharBuffer, int aStartOffset, int aEndOffset )
    {
        myCharBuffer = aCharBuffer
        myStartOffset = aStartOffset
        myEndOffset = aEndOffset

        myCache.clear()
    }

    List tokenize()
    {
        def tokens = new ArrayList()
        storeTokens( tokens, 0 )
        return tokens
    }

    SimpleToken findToken( int aStartOffset )
    {
        if ( aStartOffset < myStartOffset || aStartOffset >= myEndOffset )
        {
            throw new IllegalArgumentException()
        }

        if ( myCache.isEmpty() )
        {
            myCharSequence.reset( myCharBuffer, myStartOffset, myEndOffset )

            def tokens = Parsers.runParser( myCharSequence, myLexer, null )
            if ( tokens == null || tokens.length == 0 ) return null

            for ( idx in 0..tokens.length - 1 )
            {
                def token = tokens[ idx ]
                myCache.add( makeSimpleToken( myStartOffset, token ) )
            }
        }

        for ( idx in 0..myCache.size() - 1 )
        {
            def token = myCache.get( idx )
            if ( aStartOffset >= token.start && aStartOffset < token.end ) return token
            if ( aStartOffset < token.end ) return token
        }

        return null
    }

    // Implementation

    def makeSimpleToken( aStartOffset, aParsecToken )
    {
        def newToken = new SimpleToken()
        newToken.start = aStartOffset + aParsecToken.getIndex()
        newToken.end = newToken.start + aParsecToken.getLength()
        newToken.id = aParsecToken.getToken().getType()
        return newToken
    }

    def storeTokens( aTokens, aStartOffset )
    {
        def token = findToken( aStartOffset )
        if ( token == null ) return

        aTokens.add( token )
        storeTokens( aTokens, token.end )
    }



    def myLexer
    def myCharBuffer
    def myEndOffset
    def myStartOffset

    def myCache = new ArrayList()
    def myCharSequence = new MutableCharSequence()
}
