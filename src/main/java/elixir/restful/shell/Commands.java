package elixir.restful.shell;

import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.shell.standard.*;

import java.util.*;
import elixir.restful.account.*;

@ShellComponent
public class Commands {

  private final AccountService accountService;
  private final ConfigService  configService;

  @Autowired
  public Commands(AccountService accountService, ConfigService configService) {
    this.accountService = accountService;
    this.configService  = configService;
  }

  @ShellMethod(key="user", value="Prints User information.")
  public Account printUser(
    @ShellOption(value={"--id"}, help="The User id :: Long.") long id
  ) {
    return accountService.getAccountById(new Long(id));
  }

  @ShellMethod(key="users", value="Prints All The Users.")
  public List<Account> printUsers(
  ) {
    return accountService.getAccounts();
  }

  @ShellMethod(key="config-user", value="Prints Config Of User.")
  public List<Config> printConfigUser(
    @ShellOption(value={"--id"}, help="The User Id :: Long.") long id
  ) {
    return configService.getConfigs(new Long(id));
  }

  @ShellMethod(key="delete-user", value="Deletes User.")
  public String printUsers(
    @ShellOption(value={"--id"}, help="The User Id :: Long.") long id
  ) {
    accountService.deleteAccountById(new Long(id));
    return "Deleted Account With ID ``"+id+"``.";
  }

}
