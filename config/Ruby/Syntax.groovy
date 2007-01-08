
package Ruby

def syntax = new File( configFolder, 'Ruby/Syntax.flux' ).text
def code = new flux.FluxBuilder( configFolder ).using( syntax ).create( "RubySyntaxFlexer" )
def clazz = evaluate( code )
def instance = clazz.newInstance( configuration )
return new com.intellij.lexer.FlexAdapter( instance )
