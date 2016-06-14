Parsing lots of parameters (eg. in web services) takes a lot of repeated code.
This is a small experiment to try make it better.

Improvements
------------

 * Add a flag to `ParserFactory.addParser` to mark the parameter as optional
   (don't fail - but skip remaining tests - if the parameter is unset)
 * (Maybe?) Get rid of `params` param and custom parser-adders in
   `ParserFactory`: make the caller create its own parsers. `ParserFactory` is
   obviously not a factory in that case
