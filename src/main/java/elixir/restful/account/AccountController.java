package elixir.restful.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import io.github.cdimascio.dotenv.Dotenv;

import elixir.restful.account.AccountService;
import elixir.restful.account.*;

import java.util.*;

@RestController
@CrossOrigin
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
  public ResponseEntity<Account> getAccount(@PathVariable("account_id") Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(accountService.getAccountById(account_id), HttpStatus.FOUND);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // get all the accounts
  @GetMapping("/")
  public ResponseEntity<List<Account>> getAllAccounts(@RequestParam("api_key") String key){
    if (is_token(key)) {
      return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.FOUND);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // @Incomplete(need to return http status object)
  @PostMapping("/auth")
  public ResponseEntity<Boolean> getAuthStatus(@RequestBody Account account, @RequestParam("api_key") String api_key) {
    if (is_token(api_key)) {
      Account faccount = accountService.getAccountByName(account.getName());

      if(account.getKey().equals(faccount.getKey())) {
        return new ResponseEntity<>(new Boolean(true), HttpStatus.ACCEPTED);
      }
      else {
        return new ResponseEntity<>(new Boolean(false), HttpStatus.UNAUTHORIZED);
      }
    } else {
      return new ResponseEntity<>(new Boolean(false), HttpStatus.UNAUTHORIZED);
    }
  }

  // create new account
  @PostMapping("/")
  public ResponseEntity<Account> registerAccount(@RequestBody Account naccount, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(accountService.addNewAccount(naccount), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // update account
  @PutMapping("/{account_id}")
  public ResponseEntity<Account> updateAccount(@RequestBody Account naccount, @PathVariable("account_id") Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(accountService.updateAccount(naccount, account_id), HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // delete account
  @DeleteMapping("/{account_id}")
  public ResponseEntity<String> deleteAccount(@PathVariable("account_id") Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      accountService.deleteAccountById(account_id);
      return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>("API_KEY is wrong.", HttpStatus.UNAUTHORIZED);
    }
  }

  // create config
  @PostMapping("/{account_id}/config")
  public ResponseEntity<Config> registerConfig(@RequestBody Config config, @PathVariable("account_id") Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(configService.addNewConfig(account_id, config), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // get all config, specific to some account
  @GetMapping("/{account_id}/config")
  public ResponseEntity<List<Config>> getAllConfigs(@PathVariable("account_id") Long account_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(configService.getConfigs(account_id), HttpStatus.FOUND);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // get specific config
  @GetMapping("/config/{config_id}")
  public ResponseEntity<Config> getConfig(@PathVariable("account_id") Long config_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(configService.getConfig(config_id), HttpStatus.FOUND);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // udpate specific config
  @PutMapping("/config/{config_id}")
  public ResponseEntity<Config> updateConfig(@RequestBody Config config, @PathVariable("config_id") Long config_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      return new ResponseEntity<>(configService.updateConfig(config, config_id), HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  // delete specific config
  @DeleteMapping("/config/{config_id}")
  public ResponseEntity<String> deleteConfig(@PathVariable("config_id") Long config_id, @RequestParam("api_key") String key) {
    if (is_token(key)) {
      configService.deleteConfig(config_id);
      return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>("API_KEY is wrong.", HttpStatus.UNAUTHORIZED);
    }
  }
}
