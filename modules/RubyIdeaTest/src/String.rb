
test = %q!tes! + it # comment
toast = %q{test{my} + stuff # ok!

toasts.each { |t| eat t }

toasts.each do |t|
    eat t
end

=begin
and
=end

class Something

    # Are these allowed everywhere?
    =begin
    and
    =end

end

# comment
