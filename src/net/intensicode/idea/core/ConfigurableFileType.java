package net.intensicode.idea.core;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import net.intensicode.idea.config.InstanceConfiguration;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public final class ConfigurableFileType implements FileType
{
    public ConfigurableFileType( final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        myLanguage = aLanguage;
        myConfiguration = aConfiguration;
    }

    // From FileType

    @NotNull
    public final Language getLanguage()
    {
        return myLanguage;
    }

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

    @NotNull
    public final SyntaxHighlighter getHighlighter( final @Nullable Project project )
    {
        return myLanguage.getSyntaxHighlighter( project );
    }

    @Nullable
    public final StructureViewBuilder getStructureViewBuilder( final @NotNull VirtualFile file, final @NotNull Project project )
    {
        final PsiFile psiFile = PsiManager.getInstance( project ).findFile( file );
        return psiFile == null ? null : myLanguage.getStructureViewBuilder( psiFile );
    }

    public final String getCharset( final VirtualFile file )
    {
        return null;
    }

    public final boolean isBinary()
    {
        return false;
    }

    public final boolean isReadOnly()
    {
        return false;
    }



    private final Language myLanguage;

    private final InstanceConfiguration myConfiguration;
}
