
find_in( input, start, end ) {
    index = input.indexOf( "def", start )
    if ( index < start || index + 3 >= end ) return null;
    return [ index, index + 3 ]
}
