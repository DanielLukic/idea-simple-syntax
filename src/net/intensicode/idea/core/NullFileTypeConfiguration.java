package net.intensicode.idea.core;

import com.intellij.openapi.fileTypes.FileNameMatcher;
import net.intensicode.idea.config.FileTypeConfiguration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class NullFileTypeConfiguration implements FileTypeConfiguration
{
    public static final NullFileTypeConfiguration INSTANCE = new NullFileTypeConfiguration();

    public final String getDefaultExtension()
    {
        return NO_EXTENSION;
    }

    public final List<FileNameMatcher> getExtensions()
    {
        return NO_EXTENSIONS;
    }

    public final Icon getIcon()
    {
        return null;
    }

    private NullFileTypeConfiguration()
    {
    }

    private static final String NO_EXTENSION = "";

    private static final ArrayList<FileNameMatcher> NO_EXTENSIONS = new ArrayList<FileNameMatcher>();
}
