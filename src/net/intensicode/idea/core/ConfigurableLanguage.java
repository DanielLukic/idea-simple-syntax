package net.intensicode.idea.core;

import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.*;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.lang.surroundWith.SurroundDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.config.NamesValidatorConfiguration;
import net.intensicode.idea.scripting.DynamicClassFactory;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;



/**
 * Adapter between IDEA Language objects and our LanguageConfiguration. Takes care of the IDEA OpenAPI quirks:
 * <ul>
 * <li>Circumvent the global Language registry by creating unique subclasses for each LanguageConfiguration.</li>
 * <li>Create and manage unique IElementType/GenericElementType instances (defined tokens).</li>
 * </ul>
 */
public /*dynamic*/ class ConfigurableLanguage extends Language
{
    public static final ConfigurableLanguage getOrCreate( final InstanceConfiguration aConfiguration )
    {
        final String name = aConfiguration.getName();
        for ( final Language language : Language.getRegisteredLanguages() )
        {
            if ( language.getID().equals( name ) == false )
            {
                continue;
            }

            if ( language instanceof ConfigurableLanguage == false )
            {
                throw new RuntimeException( "Incompatible language registered for " + name );
            }

            final ConfigurableLanguage oldLanguage = ( ConfigurableLanguage ) language;
            oldLanguage.reset( aConfiguration );
            return oldLanguage;
        }

        final String className = "ConfigurableLanguage" + name;
        final Class clazz = ConfigurableLanguage.class;
        return ( ConfigurableLanguage ) DynamicClassFactory.newInstance( className, clazz, aConfiguration );
    }

    @NotNull
    public String getID()
    {
        LOG.info( "getID" );
        return super.getID();
    }

    public String[] getMimeTypes()
    {
        LOG.info( "getMimeTypes" );
        return super.getMimeTypes();
    }

    @NotNull
    public final TokenSet getReadableTextContainerElements()
    {
        LOG.info( "getReadableTextContainerElements" );
        if ( myWordCompletionTokenSet == null )
        {
            final ArrayList<IElementType> tokenTypes = new ArrayList<IElementType>();
            for ( final String tokenID : myConfiguration.getKnownTokenIDs() )
            {
                tokenTypes.add( myLanguageConfiguration.getToken( tokenID ) );
            }

            final IElementType[] types = ( IElementType[] ) tokenTypes.toArray();
            myWordCompletionTokenSet = TokenSet.create( types );
        }
        return myWordCompletionTokenSet;
    }

    @NotNull
    public final SyntaxHighlighter getSyntaxHighlighter( final Project project, final VirtualFile virtualFile )
    {
        LOG.info( "getSyntaxHighlighter" );
        return myLanguageConfiguration.getSyntaxHighlighter();
    }

    @NotNull
    public final NamesValidator getNamesValidator()
    {
        LOG.info( "getNamesValidator" );
        if ( myNamesValidator == null )
        {
            final NamesValidatorConfiguration configuration = myConfiguration.getNamesValidatorConfiguration();

            if ( configuration.hasExternalImplementation() )
            {
                LOG.info( "Creating external NamesValidator" );
                myNamesValidator = configuration.createExternalImplementation();
            }
            else
            {
                LOG.info( "Creating configurable NamesValidator" );
                myNamesValidator = new ConfigurableNamesValidator( configuration );
            }
        }
        return myNamesValidator;
    }

    @Nullable
    public final PairedBraceMatcher getPairedBraceMatcher()
    {
        if ( myBraceMatcher == null )
        {
            myBraceMatcher = new ConfigurableBraceMatcher( myConfiguration );
        }
        return myBraceMatcher;
    }

    @Nullable
    public final Commenter getCommenter()
    {
        LOG.info( "getCommenter" );
        if ( myCommenter == null )
        {
            myCommenter = new ConfigurableCommenter( myConfiguration.getCommentConfiguration() );
        }
        return myCommenter;
    }

    // Protected Interface

    public /*protected*/ ConfigurableLanguage( final InstanceConfiguration aConfiguration )
    {
        super( aConfiguration.getName() );
        reset( aConfiguration );

        LOG.info( "ConfigurableLanguage INIT DONE" );
    }

    // Implementation

    private final void reset( final InstanceConfiguration aConfiguration )
    {
        LOG.info( "reset" );

        myConfiguration = aConfiguration;
        myLanguageConfiguration = myConfiguration.getLanguageConfiguration();

        myCommenter = null;
        myNamesValidator = null;
        myWordCompletionTokenSet = null;
        myBraceMatcher = null;
    }



    private Commenter myCommenter;

    private NamesValidator myNamesValidator;

    private TokenSet myWordCompletionTokenSet;

    private PairedBraceMatcher myBraceMatcher;

    private InstanceConfiguration myConfiguration;

    private LanguageConfiguration myLanguageConfiguration;

    private static final Logger LOG = LoggerFactory.getLogger();
}
