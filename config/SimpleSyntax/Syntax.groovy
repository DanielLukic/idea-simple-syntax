
package SimpleSyntax;

def syntax = new File( configFolder, 'SimpleSyntax/Syntax.flux' ).text
def code = new flux.FluxBuilder( configFolder ).using( syntax ).create( "SimpleSyntaxSyntaxFlexer" )
def clazz = evaluate( code )
def instance = clazz.newInstance( configuration )
return new com.intellij.lexer.FlexAdapter( instance )
