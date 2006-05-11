
# block comments are full line comments..
# how funny is that? :)
class Toast

   attr_reader :w00t Asdfasdf # simple line comment

   def cheese( args )
       @mogg = args
       schlupp1 = 'rupp'
       schnuff2 = "hussle #{hummel}"
       that = 4.2
       thuz = 0xABcd
   end

   DONT_CHANGE = ILikeYouJustTheWayYouAre.new
   DONT_CHANGE=ILikeYouJustTheWayYouAre.new

   def huzzle( thiz, that )
       @@schmogg = @mogg + thiz * that
       %Q!rumsel #{pumsel} testerle!
       %Q{rumsel \#\{pumsel\} testerle}
   end

    def other
        DONT_CHANGE
    end

end
