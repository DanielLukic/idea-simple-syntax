package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.LanguageConfiguration;

import java.util.HashMap;



/**
 * TODO: Describe this!
 */
public final class FakeConfigurableLanguage implements LanguageConfiguration
{
    public IElementType getToken( Object aTokenId )
    {
        if ( myElementTypes.containsKey( aTokenId ) == false )
        {
            myElementTypes.put( aTokenId, new FakeElementType( aTokenId, Language.ANY ) );
        }
        return myElementTypes.get( aTokenId );
    }

    public SyntaxHighlighter getSyntaxHighlighter()
    {
        return null;
    }

    public Language getLanguage()
    {
        return null;
    }

    private final HashMap<Object, IElementType> myElementTypes = new HashMap<Object, IElementType>();
}
