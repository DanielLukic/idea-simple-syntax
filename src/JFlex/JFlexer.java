package JFlex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * TODO: Describe this!
 */
public final class JFlexer
{
    public JFlexer()
    {
        this( new NicerEmitter() );
    }

    public JFlexer( final EmitterAPI aEmitterAPI )
    {
        myEmitterAPI = aEmitterAPI;

        Options.char_at = true;
        Options.no_backup = true;
        Options.gen_method = Options.TABLE;
    }

    public final JFlexer setSkeleton( final String aSkeleton ) throws IOException
    {
        Skeleton.readSkel( new BufferedReader( new StringReader( aSkeleton ) ) );
        return this;
    }

    public final String generate( final Reader aInputReader )
    {
        Out.resetCounters();

        final LexScan scanner = new LexScan( aInputReader );
        final LexParse parser = new LexParse( scanner );

        scanner.packed = true;
        try
        {
            final NFA nfa = ( NFA ) parser.parse().value;
            Out.checkErrors();

            final DFA dfa = nfa.getDFA();
            dfa.checkActions( scanner, parser );
            dfa.minimize();

            return myEmitterAPI.emit( parser, dfa );
        }
        catch ( ScannerException e )
        {
            Out.error( e.file, e.message, e.line, e.column );
            throw new GeneratorException();
        }
        catch ( MacroException e )
        {
            Out.error( e.getMessage() );
            throw new GeneratorException();
        }
        catch ( IOException e )
        {
            Out.error( ErrorMessages.IO_ERROR, e.toString() );
            throw new GeneratorException();
        }
        catch ( OutOfMemoryError e )
        {
            Out.error( ErrorMessages.OUT_OF_MEMORY );
            throw new GeneratorException();
        }
        catch ( GeneratorException e )
        {
            throw new GeneratorException();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new GeneratorException();
        }
    }

    private final EmitterAPI myEmitterAPI;
}
