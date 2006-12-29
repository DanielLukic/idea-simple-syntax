package net.intensicode.idea.core;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.system.SystemContext;

import java.util.HashMap;

/**
 * Adapter between IDEA Language objects and our LanguageConfiguration. Takes care of the IDEA OpenAPI quirks:
 * <ul>
 * <li>Circumvent the global Language registry by creating unique subclasses for each LanguageConfiguration.</li>
 * <li>Create and manage unique IElementType/GenericElementType instances (defined tokens).</li>
 * <li>Manage ConfigurableTextAttributes to make TextAttributesKey instances appear dynamic (color settings).</li>
 * </ul>
 */
public final class ConfigurableAttributes
{
    public ConfigurableAttributes( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        reset( aSystemContext, aConfiguration );
    }

    public final TextAttributesKey getTextAttributesKey( final String aTokenID )
    {
        final String keyID = makeKeyID( aTokenID );
        if ( myTextAttributesKeys.containsKey( keyID ) == false )
        {
            final TextAttributes attributes = createTextAttributes( keyID, aTokenID );
            final TextAttributesKey attributesKey = mySystemContext.createTextAttributesKey( keyID, attributes );
            myTextAttributesKeys.put( keyID, attributesKey );
        }
        return myTextAttributesKeys.get( keyID );
    }

    public final TextAttributesKey[] getTokenHighlights( final IElementType tokenType )
    {
        final TextAttributesKey[] keys = myTokenHighlights.get( tokenType );
        if ( keys == null ) return NO_TEXT_ATTRIBUTES;
        return keys;
    }

    // Implementation

    private final void reset( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        mySystemContext = aSystemContext;
        myConfiguration = aConfiguration;

        myTokenHighlights.clear();
        myTextAttributesKeys.clear();

        final LanguageConfiguration languageConfiguration = myConfiguration.getLanguageConfiguration();

        for ( final String token : aConfiguration.getKnownTokenIDs() )
        {
            final IElementType elementType = languageConfiguration.getToken( token );
            final TextAttributesKey attributesKey = getTextAttributesKey( token );
            myTokenHighlights.put( elementType, new TextAttributesKey[]{ attributesKey } );
        }
    }

    private final TextAttributes createTextAttributes( final String aKeyID, final String aTokenID )
    {
        // Note: IDEA does a lot of stupid private/internal registry handling.
        // In case of the TextAttributes this means that for a given TextAttributesKey
        // with a certain ID, the defaults can be set only once. Which in general is
        // ok, i guess, but complicates things for us here. We have to reuse the old
        // defaults object and change its settings instead. Because we cant set a new
        // defaults object into a TextAttrKey object and we cant create a new TextAttrKey
        // for an already used ID. But the latter is exactly what happens upon reloading
        // the SimpleSyntax configuration!

        if ( myTextAttributes.containsKey( aKeyID ) == false )
        {
            myTextAttributes.put( aKeyID, new ConfigurableTextAttributes() );
        }

        final ConfigurableTextAttributes attributes = myTextAttributes.get( aKeyID );
        attributes.reset();

        final String spec = myConfiguration.getTokenAttributes( aTokenID );
        if ( spec != null && spec.length() > 0 ) attributes.setFrom( spec );

        return attributes;
    }

    private final String makeKeyID( final String aTokenID )
    {
        final StringBuilder id = new StringBuilder();
        id.append( myConfiguration.getName().toUpperCase() );
        id.append( '_' );
        id.append( aTokenID );
        return id.toString().replaceAll( "[^\\w]", "_" );
    }



    private SystemContext mySystemContext;

    private InstanceConfiguration myConfiguration;


    private final HashMap<String, TextAttributesKey> myTextAttributesKeys = new HashMap<String, TextAttributesKey>();

    private final HashMap<IElementType, TextAttributesKey[]> myTokenHighlights = new HashMap<IElementType, TextAttributesKey[]>();

    private final HashMap<String, ConfigurableTextAttributes> myTextAttributes = new HashMap<String, ConfigurableTextAttributes>();


    private static final TextAttributesKey[] NO_TEXT_ATTRIBUTES = new TextAttributesKey[0];
}
