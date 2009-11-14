package net.intensicode.idea.core;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.scripting.DynamicClassFactory;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;



/**
 * Adapter between IDEA FileType objects and our InstanceConfiguration/FileTypeConfiguration.
 * Has to be used through the ConfigurableFileTypeBuilder to take care of unregistering previous
 * extensions/filetypes.
 */
public class ConfigurableFileType extends LanguageFileType
{
    public /*protected*/ static final ConfigurableFileType getOrCreate( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        final String name = aConfiguration.getName();
        final FileTypeManager manager = FileTypeManager.getInstance();
        for ( final FileType fileType : manager.getRegisteredFileTypes() )
        {
            if ( fileType.getName().equals( name ) == false ) continue;
            if ( fileType instanceof ConfigurableFileType == false ) continue;

            final ConfigurableFileType oldFileType = ( ConfigurableFileType ) fileType;
            oldFileType.reset( aConfiguration );
            return oldFileType;
        }

        // TODO: Should we have an own registry here? So far IDEA seems to be OK with the current solution..

        final String className = "ConfigurableFileType" + aConfiguration.getName();
        final Class clazz = ConfigurableFileType.class;
        return ( ConfigurableFileType ) DynamicClassFactory.newInstance( className, clazz, aConfiguration, aLanguage );
    }

    // From FileType

    @NotNull
    @NonNls
    public final String getName()
    {
        return myConfiguration.getName();
    }

    @Nullable
    public final Icon getIcon()
    {
        return myConfiguration.getIcon();
    }

    @NotNull
    public final String getDescription()
    {
        return myConfiguration.getDescription();
    }

    @NotNull
    @NonNls
    public final String getDefaultExtension()
    {
        return myConfiguration.getFileTypeConfiguration().getDefaultExtension();
    }

    public final String getCharset( @NotNull final VirtualFile aFile )
    {
        LOG.info( "getCharset " + aFile );
        return null;
    }

    // Protected Interface

    public /*protected*/ ConfigurableFileType( final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        super( aLanguage );
        reset( aConfiguration );
    }

    // Implementation

    private final void reset( final InstanceConfiguration aConfiguration )
    {
        myConfiguration = aConfiguration != null ? aConfiguration : NullInstanceConfiguration.INSTANCE;
    }



    private InstanceConfiguration myConfiguration;

    private static final Logger LOG = LoggerFactory.getLogger();
}
