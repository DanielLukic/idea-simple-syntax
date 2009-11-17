
package flux;

import JFlex.GroovyEmitter
import JFlex.JFlexer


class FluxBuilder
    {
    FluxBuilder( aBaseFolder )
        {
        myBaseFolder = aBaseFolder
        }

    def using( aSyntax )
        {
        mySyntax = aSyntax
        return this
        }

    def create( aClassName )
        {
        def header = new File( myBaseFolder, "FluxSyntax.header" ).getText()
        def skeleton = new File( myBaseFolder, "FluxSyntax.skeleton" ).getText()

        def syntax = merge( header, mySyntax ).replacing( "CLASSNAME": aClassName ).create()
        File.createTempFile( "FluxBuilder-", ".flex" ).write( syntax )

        def flexer = new JFlexer( new GroovyEmitter() ).setSkeleton( skeleton )
        def code = flexer.generate( new StringReader( syntax ) )
        File.createTempFile( "FluxBuilder-", ".groovy" ).write( code )

        return code + "\nreturn " + aClassName + ".class\n"
        }

    private merge( aHeader, aSyntax )
        {
        def syntax = aHeader + aSyntax;
        return new TagReplacer( syntax )
        }

    private def mySyntax
    private def myBaseFolder
    }
