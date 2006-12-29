package net.intensicode.idea.syntax;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.core.ConfigurableAttributes;
import net.intensicode.idea.syntax.SimpleLexer;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Describe this!
 */
public final class SyntaxHighlighterAdapter implements SyntaxHighlighter
{
    public SyntaxHighlighterAdapter( final InstanceConfiguration aConfiguration )
    {
        LOG.info( "Creating adapter instance");
        myConfiguration = aConfiguration;
    }

    // From SyntaxHighlighter

    @NotNull
    public synchronized final Lexer getHighlightingLexer()
    {
        if ( myLexer == null )
        {
            LOG.info( "Creating highlighting lexer");
            final LanguageConfiguration languageConfiguration = myConfiguration.getLanguageConfiguration();
            final SimpleLexer lexer = myConfiguration.getLexer();
            myLexer = new LexerAdapter( languageConfiguration, lexer );
        }
        return myLexer;
    }

    @NotNull
    public synchronized final TextAttributesKey[] getTokenHighlights( final IElementType tokenType )
    {
        if ( myAttributes == null )
        {
            LOG.info( "Creating token highlights");
            myAttributes = myConfiguration.getAttributes();
        }
        return myAttributes.getTokenHighlights( tokenType );
    }



    private Lexer myLexer;

    private ConfigurableAttributes myAttributes;

    private final InstanceConfiguration myConfiguration;

    private static final Logger LOG = LoggerFactory.getLogger();
}
