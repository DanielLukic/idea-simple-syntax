package net.intensicode.idea.config.loaded;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.core.ConfigurableLanguage;
import net.intensicode.idea.core.GenericElementType;
import net.intensicode.idea.syntax.SyntaxHighlighterAdapter;
import net.intensicode.idea.system.SystemContext;

import java.util.HashMap;

/**
 * TODO: Describe this!
 */
final class LoadedLanguageConfiguration implements LanguageConfiguration
{
    public LoadedLanguageConfiguration( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        mySystemContext = aSystemContext;
        myConfiguration = aConfiguration;
    }

    // From LanguageConfiguration

    public final Language getLanguage()
    {
        if ( myLanguage == null )
        {
            myLanguage = ConfigurableLanguage.getOrCreate( myConfiguration );
        }
        return myLanguage;
    }

    public final IElementType getToken( final Object aTokenId )
    {
        if ( myTokens.containsKey( aTokenId ) == false )
        {
            myTokens.put( aTokenId, new GenericElementType( aTokenId, getLanguage() ) );
        }
        return myTokens.get( aTokenId );
    }

    public final SyntaxHighlighter getSyntaxHighlighter()
    {
        if ( mySyntaxHighlighter == null )
        {
            mySyntaxHighlighter = new SyntaxHighlighterAdapter( myConfiguration );
        }
        return mySyntaxHighlighter;
    }



    private ConfigurableLanguage myLanguage;

    private SyntaxHighlighter mySyntaxHighlighter;

    private final SystemContext mySystemContext;

    private final InstanceConfiguration myConfiguration;

    private final HashMap<Object, IElementType> myTokens = new HashMap<Object, IElementType>();
}
