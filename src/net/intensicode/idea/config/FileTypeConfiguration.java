package net.intensicode.idea.config;

import com.intellij.openapi.fileTypes.FileNameMatcher;

import javax.swing.*;
import java.util.List;



/**
 * TODO: Describe this!
 */
public interface FileTypeConfiguration
{
    Icon getIcon();

    String getDefaultExtension();

    List<FileNameMatcher> getExtensions();
}
