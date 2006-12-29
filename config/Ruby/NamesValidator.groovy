
class RubyNamesValidator
{
    def RubyNamesValidator( aConfigDir )
    {
        new File( aConfigDir, 'Ruby/Keywords.txt' ).eachLine { line -> myKeywords << line }
    }

    boolean isIdentifier( name, project )
    {
        return name ==~ /\b[a-z]\w*\b/
    }

    boolean isKeyword( name, project )
    {
        return myKeywords.contains( name )
    }

    def myKeywords = []
}

return new RubyNamesValidator( context.currentDir() )
