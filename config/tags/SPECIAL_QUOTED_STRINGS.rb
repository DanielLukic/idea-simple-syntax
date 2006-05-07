
def get_delimiter( delimiter )
    delimiter = '}' if delimiter == '{'
    delimiter = ')' if delimiter == '('
    delimiter = ']' if delimiter == '['
    delimiter = Regexp.escape delimiter
    regex = "#{delimiter}|\\n|\\r|\z|\Z|$"
end

def find_in( input, start_offset, end_offset )
    start_index = input.index /%[qQ]./, start_offset
    return nil unless start_index

    delimiter = get_delimiter( input[ start_index + 2, 1 ] )
    return start_index, start_index + 4 if input[ start_index + 3, 1 ] == delimiter

    pattern = Regexp.new "(?m)[^\\\\]#{delimiter}"
    end_index = input.index pattern, start_index + 3
    return nil unless end_index

    return start_index, end_index + 2
end
