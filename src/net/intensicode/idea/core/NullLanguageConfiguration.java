package net.intensicode.idea.core;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.LanguageConfiguration;

/**
 * TODO: Describe this!
 */
final class NullLanguageConfiguration implements LanguageConfiguration
{
    static final NullLanguageConfiguration INSTANCE = new NullLanguageConfiguration();

    public final Language getLanguage()
    {
        return NullLanguage.INSTANCE;
    }

    public final SyntaxHighlighter getSyntaxHighlighter()
    {
        return NullSyntaxHighlighter.INSTANCE;
    }

    public final IElementType getToken( final Object aTokenId )
    {
        return null;
    }

    private NullLanguageConfiguration()
    {
    }
}
