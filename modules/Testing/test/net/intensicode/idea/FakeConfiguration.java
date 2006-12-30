package net.intensicode.idea;

import net.intensicode.idea.config.*;
import net.intensicode.idea.core.ConfigurableAttributes;
import net.intensicode.idea.core.NullLanguageConfiguration;
import net.intensicode.idea.syntax.SimpleLexer;
import net.intensicode.idea.system.ScriptSupport;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
public class FakeConfiguration implements InstanceConfiguration
{
    public final ArrayList<String> recognized_tokens = new ArrayList<String>();

    public FakeConfiguration()
    {
        recognized_tokens.add( "FAKE_TEST" );
    }

    public BracesConfiguration getBracesConfiguration()
    {
        return new FakeBracesConfiguration();
    }

    public CommentConfiguration getCommentConfiguration()
    {
        return new FakeCommentConfiguration();
    }

    public String getDescription()
    {
        throw new RuntimeException( "NYI" );
    }

    public String getExampleCode()
    {
        throw new RuntimeException( "NYI" );
    }

    public FileTypeConfiguration getFileTypeConfiguration()
    {
        throw new RuntimeException( "NYI" );
    }

    public final NamesValidatorConfiguration getNamesValidatorConfiguration()
    {
        throw new RuntimeException( "NYI" );
    }

    public Icon getIcon()
    {
        throw new RuntimeException( "NYI" );
    }

    public String getName()
    {
        return "Fake";
    }

    public List<String> getKnownTokenIDs()
    {
        return recognized_tokens;
    }

    public final boolean isVisibleToken( final String aID )
    {
        return true;
    }

    public String getTokenAttributes( String aTokenID )
    {
        throw new RuntimeException( "NYI" );
    }

    public String getTokenDescription( String aTokenID )
    {
        throw new RuntimeException( "NYI" );
    }

    public final ScriptSupport getScriptSupport()
    {
        throw new RuntimeException( "NYI" );
    }

    public ConfigurableAttributes getAttributes()
    {
        throw new RuntimeException( "NYI" );
    }

    public LanguageConfiguration getLanguageConfiguration()
    {
        return NullLanguageConfiguration.INSTANCE;
    }

    public SimpleLexer getLexer()
    {
        throw new RuntimeException( "NYI" );
    }
}
