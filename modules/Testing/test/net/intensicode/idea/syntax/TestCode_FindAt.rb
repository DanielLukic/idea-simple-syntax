
def find_at( input, start_offset )
    return start_offset, start_offset + 3 if input[ start_offset, 3 ] == 'def'
    return start_offset, start_offset + 3 if input[ start_offset, 3 ] == 'end'
    return nil
end
