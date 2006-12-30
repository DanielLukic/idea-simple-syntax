
println this.class.classLoader;
println this.class.classLoader.getURLs();

println context;
println context.currentDir;

try
{
    println this.class.classLoader.loadClass( "sub.OtherBaseClass" );
}
catch ( Throwable t )
{
    println( t )
}

class MyFilePathTest4 extends sub.OtherBaseClass implements java.io.Serializable
{
}

return new MyFilePathTest4().getClass().getName();
