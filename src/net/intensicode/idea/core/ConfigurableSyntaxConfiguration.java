package net.intensicode.idea.core;

/**
 * TODO: Describe this!
 */
public final class ConfigurableSyntaxConfiguration //implements SyntaxConfiguration, ConfigurationAPI
{
//    public ConfigurableSyntaxConfiguration( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
//    {
//        mySystemContext = aSystemContext;
//        myConfiguration = aConfiguration;
//    }
//
//    // From ConfigurationAPI
//
//    public final void addRecognizedToken( final RecognizedToken aToken )
//    {
//        final String id = aToken.getTokenID();
//        final TextAttributesKey attributesKey = getTextAttributesKey( id );
//
//        if ( isVisible( id ) )
//        {
//            final String description = myConfiguration.getTokenDescription( id );
//            final AttributesDescriptor descriptor = new AttributesDescriptor( description, attributesKey );
//            myAttributesDescriptors.add( descriptor );
//        }
//
//        final IElementType elementType = myLanguageSpecification.getToken( id );
//        myTokenHighlights.put( elementType, new TextAttributesKey[]{attributesKey} );
//
//        myRecognizedTokens.add( aToken );
//    }
//
//    // From SyntaxConfiguration
//
//    public final List<RecognizedToken> getRecognizedTokens()
//    {
//        return myRecognizedTokens;
//    }
//
//    public final TextAttributesKey[] getTokenHighlights( final IElementType tokenType )
//    {
//        final TextAttributesKey[] keys = myTokenHighlights.get( tokenType );
//        if ( keys == null ) return ConfigurableSyntaxConfiguration.NO_TEXT_ATTRIBUTES;
//        return keys;
//    }
//
//    public final List<AttributesDescriptor> getAttributesDescriptors()
//    {
//        return myAttributesDescriptors;
//    }
//
//    // Implementation
//
//    private final TextAttributesKey getTextAttributesKey( final String aTokenID )
//    {
//        if ( myTextAttributesKeys.containsKey( aTokenID ) == false )
//        {
//            final TextAttributes attributes = createTextAttributes( aTokenID );
//            final TextAttributesKey attributesKey = mySystemContext.createTextAttributesKey( aTokenID, attributes );
//            myTextAttributesKeys.put( aTokenID, attributesKey );
//        }
//        return myTextAttributesKeys.get( aTokenID );
//    }
//
//    private final boolean isVisible( final String aTokenID )
//    {
//        if ( aTokenID.equals( "WHITESPACE" ) ) return false;
//        if ( aTokenID.equals( "UNRECOGNIZED" ) ) return false;
//        return true;
//    }
//
//    private final TextAttributes createTextAttributes( final String aTokenID )
//    {
//        final String attributes = myConfiguration.getTokenAttributes( aTokenID );
//        if ( attributes == null || attributes.length() == 0 ) return new TextAttributes();
//        return ConfigurableTextAttributes.newInstance( attributes );
//    }
//
//
//
//    private final SystemContext mySystemContext;
//
//    private final InstanceConfiguration myConfiguration;
//
//    private final ArrayList<RecognizedToken> myRecognizedTokens = new ArrayList<RecognizedToken>();
//
//    private final ArrayList<AttributesDescriptor> myAttributesDescriptors = new ArrayList<AttributesDescriptor>();
//
//    private final HashMap<String, TextAttributesKey> myTextAttributesKeys = new HashMap<String, TextAttributesKey>();
//
//    private final HashMap<IElementType, TextAttributesKey[]> myTokenHighlights = new HashMap<IElementType, TextAttributesKey[]>();
//
//
//    private static final TextAttributesKey[] NO_TEXT_ATTRIBUTES = new TextAttributesKey[0];
}
