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
  private final StateRepository stateRepository;
  private final IntentRepository intentRepository;

  @Autowired
  public ConfigService(AccountRepository a,
    ConfigRepository c,
    StateService stateService,
    IntentService intentService,
    StateRepository stateRepository,
    IntentRepository intentRepository
  ) {
    this.accountRepository = a;
    this.configRepository  = c;
    this.intentService     = intentService;
    this.stateService      = stateService;
    this.stateRepository   = stateRepository;
    this.intentRepository  = intentRepository;
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

  @Transactional
  public List<Config> getConfigs(Long account_id) {
    List<Config> configs = configRepository.findByAccountId(account_id);
    return configs;
  }

  @Transactional
  public Config getConfig(Long config_id) {
    Optional<Config> opt = configRepository.findById(config_id);
    if (!opt.isPresent()) {
      throw new IllegalStateException("Config by id : " + config_id + " is already taken.");
    }

    return opt.get();
  }

  @Transactional
  public Config updateConfig(Config config, Long config_id) {
    Config rconfig = configRepository.getReferenceById(config_id);
    if (config.getName() != null) {
      rconfig.setName(config.getName());
    }
    if (config.getStates() != null) {
      rconfig.setStates(config.getStates());
    }
    if (config.getIntents() != null) {
      rconfig.setIntents(config.getIntents());
    }
    if (config.getNote() != null) {
      rconfig.setNote(config.getNote());
    }

    System.out.println("||||||||||||||||||||||||||||||||||||||||");
    System.out.println("@rconfig "+rconfig);
    System.out.println("||||||||||||||||||||||||||||||||||||||||");

    return configRepository.save(rconfig);
  }

  public void deleteConfig(Long config_id) {
    configRepository.deleteById(config_id);
  }
}
