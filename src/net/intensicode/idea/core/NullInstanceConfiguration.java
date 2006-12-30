package net.intensicode.idea.core;

import net.intensicode.idea.config.*;
import net.intensicode.idea.syntax.SimpleLexer;
import net.intensicode.idea.system.ScriptSupport;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
final class NullInstanceConfiguration implements InstanceConfiguration
{
    static final NullInstanceConfiguration INSTANCE = new NullInstanceConfiguration();

    public final BracesConfiguration getBracesConfiguration()
    {
        return NullBracesConfiguration.INSTANCE;
    }

    public final CommentConfiguration getCommentConfiguration()
    {
        return NullCommentConfiguration.INSTANCE;
    }

    public final String getDescription()
    {
        return NULL_STRING;
    }

    public final String getExampleCode()
    {
        return NULL_STRING;
    }

    public final FileTypeConfiguration getFileTypeConfiguration()
    {
        return NullFileTypeConfiguration.INSTANCE;
    }

    public final NamesValidatorConfiguration getNamesValidatorConfiguration()
    {
        throw new RuntimeException( "NYI" );
    }

    public final Icon getIcon()
    {
        return null;
    }

    public final String getName()
    {
        return NULL_STRING;
    }

    public final List<String> getKnownTokenIDs()
    {
        return NO_RECOGNIZED_TOKENS;
    }

    public final boolean isVisibleToken( final String aID )
    {
        return true;
    }

    public final String getTokenAttributes( final String aTokenID )
    {
        return EMPTY_STRING;
    }

    public final String getTokenDescription( final String aTokenID )
    {
        return EMPTY_STRING;
    }

    public final ConfigurableAttributes getAttributes()
    {
        throw new RuntimeException( "NYI" );
    }

    public final LanguageConfiguration getLanguageConfiguration()
    {
        return NullLanguageConfiguration.INSTANCE;
    }

    public final ScriptSupport getScriptSupport()
    {
        throw new RuntimeException( "NYI" );
    }

    public final SimpleLexer getLexer()
    {
        throw new RuntimeException( "NYI" );
    }

    private NullInstanceConfiguration()
    {
    }

    private static final String EMPTY_STRING = "";

    private static final String NULL_STRING = "NULL";

    private static final ArrayList<String> NO_RECOGNIZED_TOKENS = new ArrayList<String>();
}
