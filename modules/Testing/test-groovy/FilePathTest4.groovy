
try
{
    this.class.classLoader.loadClass( "sub.OtherBaseClass" );
}
catch ( Throwable t )
{
    throw new RuntimeException( t )
}

class MyFilePathTest4 extends sub.OtherBaseClass implements java.io.Serializable
{
}

return new MyFilePathTest4().getClass().getName();
