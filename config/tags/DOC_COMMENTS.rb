
BEGIN_COMMENT = Regexp.new( "^=begin(?:(?:$)|(?:\s+))", Regexp::MULTILINE )
END_COMMENT = Regexp.new( "^=end(?:(?:$)|(?:\s+))", Regexp::MULTILINE )

def find_at( input, start_offset )
  from = input.index BEGIN_COMMENT, start_offset
  return nil unless from && from == start_offset

  to = input.index END_COMMENT, start_offset
  return nil unless to

  return from, Regexp.last_match.end( 0 )
end
