
find_at( input, offset ) {
    if ( input[ offset..offset+2 ] == "def" ) {
        return [ offset, offset + 3 ]
    }
    return null
}
