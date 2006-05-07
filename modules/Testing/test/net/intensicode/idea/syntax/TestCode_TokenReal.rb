
def get_delimiter( delimiter )
    delimiter = '}' if delimiter == '{'
    delimiter = ')' if delimiter == '('
    delimiter = ']' if delimiter == '['
    Regexp.escape delimiter
end

def delimiter( input, start_offset, end_offset )
    start_index = input.index /%[qQ]./, start_offset
    return nil unless start_index
    get_delimiter input[ start_index + 2, 1 ]
end

def find_in( input, start_offset, end_offset )
    start_index = input.index /%[qQ]./, start_offset
    return nil unless start_index

    delimiter = get_delimiter( input[ start_index + 2, 1 ] )
    return start_index, start_index + 4 if input[ start_index + 3, 1 ] == delimiter

    pattern = Regexp.new "[^\\\\]#{delimiter}"
    end_index = input.index pattern, start_index + 3
    return nil unless end_index

    return start_index, end_index + 2
end
