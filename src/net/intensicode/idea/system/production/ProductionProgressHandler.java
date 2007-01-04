package net.intensicode.idea.system.production;

import net.intensicode.idea.system.ProgressHandle;
import net.intensicode.idea.system.ProgressHandler;

/**
 * TODO: Describe this!
 */
final class ProductionProgressHandler implements ProgressHandler
{
    public final ProgressHandle startLexerScript()
    {
        return new ProductionProgressHandle();
    }
}
