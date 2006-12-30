
require 'java'

include_class "java.util.ArrayList"
include_class "jfun.parsec.Parsers"
include_class "net.intensicode.idea.syntax.SimpleToken"
include_class "net.intensicode.idea.util.MutableCharSequence"

class LexerAdapter

    def initialize( aParsecLexer )
        @lexer = aParsecLexer
        @cache = []
        @charSequence = MutableCharSequence.new
    end

    # From SimpleLexer

    def start( aCharBuffer, aStartOffset, aEndOffset )
        @buffer = aCharBuffer
        @startOffset = aStartOffset
        @endOffset = aEndOffset

        @cache.clear
    end

    def tokenize()
        tokens = ArrayList.new
        offset = 0
        while true
            token = findToken( offset )
            break unless token
            tokens.add token
            offset = token.end
        end
        return tokens
    end

    def findToken( aStartOffset )
        if ( aStartOffset < @startOffset || aStartOffset >= @endOffset )
            raise "Bad start offset: #{aStartOffset}"
        end

        if ( @cache.empty? )
            @charSequence.reset( @buffer, @startOffset, @endOffset )

            tokens = Parsers.runParser( @charSequence, @lexer, nil )
            return nil if ( tokens == nil || tokens.length == 0 )

            tokens.each do |token|
                simpleToken = makeSimpleToken( @startOffset, token )
                @cache << simpleToken
            end
        end

        @cache.each do |token|
            return token if ( aStartOffset >= token.start && aStartOffset < token.end )
            return token if ( aStartOffset < token.end )
        end

        return nil
    end

    def makeSimpleToken( aStartOffset, aToken )
        newToken = SimpleToken.new
        newToken.start = aStartOffset + aToken.getIndex
        newToken.end = newToken.start + aToken.getLength
        newToken.id = aToken.getToken.getType
        return newToken
    end

end
