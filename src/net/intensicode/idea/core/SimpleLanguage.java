package net.intensicode.idea.core;

import com.intellij.lang.Commenter;
import com.intellij.lang.Language;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.syntax.SyntaxHighlighterAdapter;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.DynamicClassHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;



/**
 * Adapter between IDEA Language objects and our LanguageConfiguration. Takes care of the IDEA OpenAPI quirks:
 * <ul>
 * <li>Circumvent the global Language registry by creating unique subclasses for each LanguageConfiguration.</li>
 * <li>Create and manage unique IElementType/GenericElementType instances (defined tokens).</li>
 * <li>Manage ConfigurableTextAttributes to make TextAttributesKey instances appear dynamic (color settings).</li>
 * </ul>
 */
public class SimpleLanguage extends Language implements LanguageConfiguration
{
    public static final SimpleLanguage getOrCreate( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        final String name = aConfiguration.getName();
        for ( final Language language : Language.getRegisteredLanguages() )
        {
            if ( language.getID().equals( name ) == false )
            {
                continue;
            }

            if ( language instanceof SimpleLanguage == false )
            {
                throw new RuntimeException( "Incompatible language registered for " + name );
            }

            final SimpleLanguage oldLanguage = ( SimpleLanguage ) language;
            oldLanguage.reset( aConfiguration );
            return oldLanguage;
        }

        final String className = "SimpleLanguage" + name;
        final Class clazz = SimpleLanguage.class;
        return ( SimpleLanguage ) DynamicClassHelper.newInstance( className, clazz, aConfiguration );
    }

    // From LanguageConfiguration

    public final Language getLanguage()
    {
        return this;
    }

    public synchronized final IElementType getToken( final Object aTokenId )
    {
        if ( myTokens.containsKey( aTokenId ) == false )
        {
            myTokens.put( aTokenId, new GenericElementType( aTokenId, this ) );
        }
        return myTokens.get( aTokenId );
    }

    public synchronized final SyntaxHighlighter getSyntaxHighlighter()
    {
        if ( mySyntaxHighlighter == null )
        {
            mySyntaxHighlighter = new SyntaxHighlighterAdapter( myConfiguration );
        }
        return mySyntaxHighlighter;
    }

    // From Language

    @NotNull
    public final SyntaxHighlighter getSyntaxHighlighter( final Project project, final VirtualFile virtualFile )
    {
        return getSyntaxHighlighter();
    }

    @Nullable
    public synchronized final PairedBraceMatcher getPairedBraceMatcher()
    {
        if ( myBraceMatcher == null )
        {
            myBraceMatcher = new ConfigurableBraceMatcher( this, myConfiguration.getBracesConfiguration() );
        }
        return myBraceMatcher;
    }

    @Nullable
    public synchronized final Commenter getCommenter()
    {
        if ( myCommenter == null )
        {
            myCommenter = new ConfigurableCommenter( myConfiguration.getCommentConfiguration() );
        }
        return myCommenter;
    }

    // Protected Interface

    public /*protected*/ SimpleLanguage( final InstanceConfiguration aConfiguration )
    {
        super( aConfiguration.getName() );
        reset( aConfiguration );
    }

    // Implementation

    private final void reset( final InstanceConfiguration aConfiguration )
    {
        myConfiguration = aConfiguration;

        myCommenter = null;
        myBraceMatcher = null;
        mySyntaxHighlighter = null;
    }



    private Commenter myCommenter;

    private PairedBraceMatcher myBraceMatcher;

    private SyntaxHighlighter mySyntaxHighlighter;

    private InstanceConfiguration myConfiguration;


    private final HashMap<Object, IElementType> myTokens = new HashMap<Object, IElementType>();
}
