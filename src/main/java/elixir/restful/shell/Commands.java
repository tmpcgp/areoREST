package elixir.restful.shell;

import org.springframework.shell.standard.*;

@ShellComponent
public class Commands {

  @ShellMethod(key="hello-world")
  public String helloWorld(
    @ShellOption(value={"--arg"}, defaultValue = "spring", help="Prints ``Helloworld`` With Specified Argument To The CLI.") String arg
  ) {
    return "Hello world " + arg;
  }

}
