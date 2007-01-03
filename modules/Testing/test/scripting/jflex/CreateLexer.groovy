
import JFlex.GroovyEmitter
import JFlex.JFlexer

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.intensicode.idea.util.StreamUtils;



def skeleton = retrieve( "idea-skeleton" );
def flexer = new JFlexer( new GroovyEmitter() ).setSkeleton( skeleton );

def input = read( "Python.flex" );
def result = flexer.generate( input );

return evaluate( result )



def retrieve( aResourceName )
{
    return StreamUtils.loadIntoString( stream( aResourceName ) );
}

def Reader read( aResourceName )
{
    return new InputStreamReader( stream( aResourceName ) );
}

def InputStream stream( aResourceName )
{
    return reference.class.getResourceAsStream( aResourceName );
}
