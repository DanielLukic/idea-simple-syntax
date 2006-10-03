
class Recognizer implements GroovyRecognizer {

    GroovySegment find_at( CharSequence input, int offset ) {
        if ( input[ offset..offset+2 ] == "def" ) {
            return new GroovySegment( offset, offset + 3 )
        }
        return GroovySegment.NOT_FOUND
    }

    GroovySegment find_in( CharSequence input, int start, int end ) {
        index = input.indexOf( "def" )
        if ( index >= 0 ) {
            return new GroovySegment( index, index + 3 )
        }
        return GroovySegment.NOT_FOUND
    }

    def toast() {
        "queso"
    }

    def test1( one ) {
        "test " + one
    }

    def test2( one, two ) {
        "test " + one + " " + two
    }

}

return new Recognizer()
