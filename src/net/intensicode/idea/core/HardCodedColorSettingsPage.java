package net.intensicode.idea.core;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class HardCodedColorSettingsPage implements ColorSettingsPage
{
    // From ColorSettingsPage

    public final Icon getIcon()
    {
        return null;
    }

    @NotNull
    public final String getDisplayName()
    {
        return "Simple Syntax";
    }

    @NotNull
    public final AttributesDescriptor[] getAttributeDescriptors()
    {
        final ArrayList<AttributesDescriptor> descriptors = createDescriptors();
        return myAttributesDescriptors = descriptors.toArray( new AttributesDescriptor[descriptors.size()] );
    }

    @NotNull
    public final ColorDescriptor[] getColorDescriptors()
    {
        return NO_COLOR_DESCRIPTOR;
    }

    @NotNull
    public final SyntaxHighlighter getHighlighter()
    {
        //if ( mySyntaxHighlighter == null )
        //{
        //    mySyntaxHighlighter = myConfiguration.getLanguageConfiguration().getSyntaxHighlighter();
        //}
        return new PlainSyntaxHighlighter();
    }

    @NotNull
    public final String getDemoText()
    {
        return "TEST THIS TEST";
    }

    public final Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap()
    {
        return null;
    }

    // Implementation

    private ArrayList<AttributesDescriptor> createDescriptors()
    {
        final ArrayList<String> handledIDs = new ArrayList<String>();

        final ArrayList<AttributesDescriptor> descriptors = new ArrayList<AttributesDescriptor>();
        final List<String> ids = new ArrayList<String>();
        ids.add( "one" );
        ids.add( "two" );
        ids.add( "three" );

        for ( final String id : ids )
        {
            if ( handledIDs.contains( id ) ) continue;

            final String description = "description " + id;
            final TextAttributesKey attributesKey = TextAttributesKey.createTextAttributesKey( "key " + id );
            descriptors.add( new AttributesDescriptor( description, attributesKey ) );

            handledIDs.add( id );
        }
        return descriptors;
    }



    private SyntaxHighlighter mySyntaxHighlighter;

    private AttributesDescriptor[] myAttributesDescriptors;

    private static final ColorDescriptor[] NO_COLOR_DESCRIPTOR = new ColorDescriptor[0];

    private static final AttributesDescriptor[] NO_ATTRIBUTES_DESCRIPTORS = new AttributesDescriptor[0];

    private static final Logger LOG = LoggerFactory.getLogger();
}