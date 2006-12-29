
class RubyNamesValidator

    def initialize
        @keywords = IO.readlines( 'Ruby/Keywords.txt' ).map { |k| k.chop }
    end

    def isIdentifier( name, project )
        md = /\b[a-z]\w*\b/.match( name )
        ( md != nil ) && ( md[ 0 ] == name )
    end

    def isKeyword( name, project )
        return true if @keywords.include?( name )
        return false
    end

end

return RubyNamesValidator.new
