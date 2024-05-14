package elixir.restful.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import io.github.cdimascio.dotenv.Dotenv;
import elixir.restful.account.AccountService;
import elixir.restful.account.*;

import java.util.*;

@RestController
@RequestMapping(path="api/v1/account")
public class AccountController{

  private static final String token = Dotenv.configure().directory(".").filename("env").load().get("token").trim(); 

  private final AccountService accountService;
  private final ConfigService configService;

  @Autowired
  public AccountController(AccountService accountService, ConfigService configService) {
    this.accountService = accountService;
    this.configService  = configService;
  }

  private static boolean is_token(String key) { return token.equals(key); }
  // get specific account
  @GetMapping("/{account_id}")
  public @ResponseBody Account getAccount(@PathVariable Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return accountService.getAccountById(account_id);
    } else {
      return null;
    }
  }

  // get all the accounts
  @GetMapping("/")
  public List<Account> getAllAccounts(@RequestParam("api_key") String key){
    if (is_token(key)) {
      return accountService.getAccounts();
    } else {
      return null;
    }
  }

  // @Incomplete(need to return http status object)
  @GetMapping("/auth")
  public @ResponseBody Boolean getAuthStatus(@RequestBody String key, @RequestParam("api_key") String api_key) {
    if (is_token(api_key)) {
      Account faccount = accountService.getAccountByKey(key);
      if(key.equals(faccount.getKey())) {
        return new Boolean(true);
      }
      else {
        return new Boolean(false);
      }
    } else {
      return new Boolean(false);
    }
  }

  // create new account
  @PostMapping("/")
  public Account registerAccount(@RequestBody Account naccount, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return accountService.addNewAccount(naccount);
    } else {
      return null;
    }
  }

  // update account
  @PutMapping("/{account_id}")
  public Account updateAccount(@RequestBody Account naccount, @PathVariable Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return accountService.updateAccount(naccount, account_id);
    } else {
      return null;
    }
  }

  // delete account
  @DeleteMapping("/{account_id}")
  public void deleteAccount(@PathVariable Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      accountService.deleteAccountById(account_id);
    } else {
      return;
    }
  }

  // create config
  @PostMapping("/{account_id}/config")
  public Config registerConfig(@RequestBody Config config, @PathVariable Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return configService.addNewConfig(account_id, config);
    } else {
      return null;
    }
  }

  // get all config, specific to some account
  @GetMapping("/{account_id}/config")
  public List<Config> getAllConfigs(@PathVariable Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return configService.getConfigs(account_id);
    } else {
      return null;
    }
  }

  // get specific config
  @GetMapping("/config/{config_id}")
  public Config getConfig(@PathVariable Long config_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return configService.getConfig(config_id);
    } else {
      return null;
    }
  }

  // udpate specific config
  @PutMapping("/config/{config_id}")
  public Config updateConfig(@RequestBody Config config, @PathVariable Long config_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return configService.updateConfig(config, config_id);
    } else {
      return null;
    }
  }

  // delete specific config
  @DeleteMapping("/config/{config_id}")
  public void deleteConfig(@PathVariable Long config_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      configService.deleteConfig(config_id);
    } else {
      return;
    }
  }
}
