
import JFlex.GroovyEmitter
import JFlex.JFlexer

import com.intellij.lexer.FlexAdapter;



def skeleton = new File( currentDir, 'Python/Syntax.skeleton' ).getText()
def syntax = new File( currentDir, 'Python/Syntax.flex' ).newReader()

def flexer = new JFlexer( new GroovyEmitter() ).setSkeleton( skeleton );
def lexer = flexer.generate( syntax );

return new FlexAdapter( evaluate( lexer ) )
