package net.intensicode.idea.core;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe this!
 */
public final class NullSyntaxHighlighter implements SyntaxHighlighter
{
    public static final NullSyntaxHighlighter INSTANCE = new NullSyntaxHighlighter();

    @NotNull
    public final Lexer getHighlightingLexer()
    {
        return NullLexer.INSTANCE;
    }

    @NotNull
    public final TextAttributesKey[] getTokenHighlights( IElementType tokenType )
    {
        return new TextAttributesKey[0];
    }

    private NullSyntaxHighlighter()
    {
    }
}
