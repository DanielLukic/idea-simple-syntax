
package flux;

class TagReplacer
    {
    TagReplacer( aTemplate )
        {
        myTemplate = aTemplate
        }

    def replacing( aVariablesHash )
        {
        for ( pair in aVariablesHash )
            {
            def tag = "<<<" + pair.key + ">>>"
            myTemplate = myTemplate.replaceAll( tag, pair.value )
            }

        return this
        }

    def create()
        {
        return myTemplate
        }

    private def myTemplate
    }
