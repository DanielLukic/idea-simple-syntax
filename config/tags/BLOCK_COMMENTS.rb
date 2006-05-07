
BLOCK_COMMENT = Regexp.new( "([ \t]*#[^\n\r]*$)", Regexp::MULTILINE )
VALID_LINE_STARTS = "\n\r"
VALID_PREFIX_CHARS = " \t"

def find_at( input, start_offset )
  from = input.index BLOCK_COMMENT, start_offset
  return nil unless from && from == start_offset

  if from > 0
    idx = from - 1
    while idx >= 0
      code = input[ idx ]
      break if VALID_LINE_STARTS.index( code )
      return nil unless VALID_PREFIX_CHARS.index( code )
      idx -= 1
    end
  end

  md = Regexp.last_match
  return md.begin( 1 ), md.end( 1 )
end
