package net.intensicode.idea.core;

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;
import net.intensicode.idea.config.NamesValidatorConfiguration;

/**
 * TODO: Describe this!
 */
public final class ConfigurableNamesValidator implements NamesValidator
{
    public ConfigurableNamesValidator( final NamesValidatorConfiguration aConfiguration )
    {
        myConfiguration = aConfiguration;
    }

    // From NamesValidator

    public final boolean isIdentifier( final String name, final Project project )
    {
        return myConfiguration.getIdentifierRegex().matcher( name ).matches();
    }

    public final boolean isKeyword( final String name, final Project project )
    {
        return myConfiguration.getKeywordList().contains( name );
    }



    private final NamesValidatorConfiguration myConfiguration;
}
