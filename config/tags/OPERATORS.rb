
OPERATORS = [ '<<<', '>>>', '<<', '>>', '+', '-', '/', '*', '=' ]

def find_at( input, start_offset )
    OPERATORS.each do |op|
        from = start_offset
        to = start_offset + op.length
        return from, to if input[ from..to - 1 ] == op
    end
    return nil
end
