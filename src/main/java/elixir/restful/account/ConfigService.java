package elixir.restful.account;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.*;
import elixir.restful.account.*;
import java.util.*;

@Service // same as Component (@Service is only for sementic stuff.)
public class ConfigService {

  private final AccountRepository accountRepository;
  private final StateService stateService;
  private final IntentService intentService;
  private final ConfigRepository configRepository;

  @Autowired
  public ConfigService(AccountRepository a, ConfigRepository c, StateService stateService, IntentService intentService) {
    this.accountRepository = a;
    this.configRepository  = c;
    this.intentService     = intentService;
    this.stateService      = stateService;
  }

  // @Incomplete to test...
  public Config addNewConfig(Long account_id, Config config) {
    Optional<Account> opt_acc = accountRepository.findById(account_id);
    Optional<Config> opt      = configRepository.findConfigByName(config.getName());

    if (!opt_acc.isPresent()) {
      throw new IllegalStateException("Account with id "+account_id+" doesn't exist.");
    }
    if (opt.isPresent()) {
      throw new IllegalStateException("Config with name "+config.getName()+" doesn't exist.");
    }

    config.setAccount(opt_acc.get());
    return configRepository.save(config);
  }

  public List<Config> getConfigs(Long account_id) {
    List<Config> configs = configRepository.findByAccountId(account_id);
    return configs;
  }

  public Config getConfig(Long config_id) {
    Optional<Config> opt = configRepository.findById(config_id);
    if (!opt.isPresent()) {
      throw new IllegalStateException("Config by id : " + config_id + " is already taken.");
    }

    return opt.get();
  }

  public Config updateConfig(Config config, Long config_id) {
    Config rconfig = configRepository.getReferenceById(config_id);
    if (config.getName() != null) {
      rconfig.setName(config.getName());
    }
    if (config.getStates() != null) {
      for (State s : config.getStates()) {
        stateService.updateState(s, s.getId());
      }
    }
    if (config.getIntents() != null) {
      for (Intent i : config.getIntents()) {
        intentService.updateIntent(i, i.getId());
      }
    }

    return configRepository.save(rconfig);
  }

  public void deleteConfig(Long config_id) {
    configRepository.deleteById(config_id);
  }
}
