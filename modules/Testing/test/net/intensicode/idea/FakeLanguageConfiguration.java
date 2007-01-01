package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.LanguageConfiguration;

/**
 * TODO: Describe this!
 */
public final class FakeLanguageConfiguration implements LanguageConfiguration
{
    public Language getLanguage()
    {
        throw new RuntimeException( "NYI" );
    }

    public SyntaxHighlighter getSyntaxHighlighter()
    {
        throw new RuntimeException( "NYI" );
    }

    public IElementType getToken( Object aTokenId )
    {
        return new FakeElementType( aTokenId, null );
    }
}
