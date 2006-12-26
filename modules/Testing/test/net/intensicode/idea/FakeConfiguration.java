package net.intensicode.idea;

import net.intensicode.idea.config.*;
import net.intensicode.idea.core.SimpleAttributes;
import net.intensicode.idea.core.SimpleLexer;

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
        return null;
    }

    public String getExampleCode()
    {
        return null;
    }

    public FileTypeConfiguration getFileTypeConfiguration()
    {
        return null;
    }

    public Icon getIcon()
    {
        return null;
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
        return null;
    }

    public String getTokenDescription( String aTokenID )
    {
        return null;
    }


    public SimpleAttributes getAttributes()
    {
        return null;
    }

    public LanguageConfiguration getLanguageConfiguration()
    {
        return null;
    }

    public SimpleLexer getLexer()
    {
        return null;
    }
}
