
=begin
these things here are weird comments..
=end

# full line comments.. i like.. very nice
# how funny is that? :)
class Toast

   attr_reader :w00t # simple line comment

   def cheese( args, more = "THIS" )
       @mogg = args
       schlupp1 = 'rupp'
       schnuff2 = "hussle #{hummel}" + test
       that = 4.2
       thuz = 0xABcd
   end

   DONT_CHANGE = ILikeYouJustTheWayYouAre.new

   def huzzle( thiz, that, test = 10 )
       @@schmogg = @mogg + thiz * that / 10
       @something = AnotherSide.of_mine

       %Q!rumsel #{pumsel} testerle!    # test1
       %Q{rumsel more of this testerle} # test2
       %Q<rumsel #{pumsel} testerle>    # test3
   end

   # A check for regex..
   def get_current_method_name
       if /'(.*)'/.match(caller.first)
           return $1
       end
       nil
   end

end
