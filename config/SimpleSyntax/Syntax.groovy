
package SimpleSyntax;

import JFlex.GroovyEmitter
import JFlex.JFlexer

import com.intellij.lexer.FlexAdapter;



def skeleton = new File( currentDir, 'SimpleSyntax/Syntax.skeleton' ).getText()
def syntax = new File( currentDir, 'SimpleSyntax/Syntax.flex' ).newReader()

def flexer = new JFlexer( new GroovyEmitter() ).setSkeleton( skeleton );
def lexer = flexer.generate( syntax );

return new FlexAdapter( evaluate( lexer ) )
