
KEYWORDS = [ 'def', 'end', 'class', 'module', 'require', 'load', 'while', 'do', 'rescue', 'throw', 'begin' ]
VALID_BOUNDARY = / |\n|\r|\t|[^a-zA-Z0-9_]/

def find_at( input, start_offset )
    KEYWORDS.each do |k|
        next unless input[ start_offset, k.length ] == k
        next unless valid_boundary( input, start_offset - 1 )
        next unless valid_boundary( input, start_offset + k.length )
        return start_offset, start_offset + k.length
    end
    return nil
end

def valid_boundary( input, index )
    return true if ( index < 0 || index >= input.length )
    return VALID_BOUNDARY.match( input[ index, 1 ] )
end
