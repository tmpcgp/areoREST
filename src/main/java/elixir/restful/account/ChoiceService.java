package elixir.restful.account;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.*;
import elixir.restful.account.*;
import java.util.*;

@Service // same as Component (@Service is only for sementic stuff.)
public class ChoiceService {

  private final AccountRepository accountRepository;
  private final ConfigRepository  configRepository;
  private final StateRepository   stateRepository;
  private final ChoiceRepository  choiceRepository;

  @Autowired
  public ChoiceService(AccountRepository a, ConfigRepository c, StateRepository stateRepository, ChoiceRepository choiceRepository) {
    this.accountRepository = a;
    this.configRepository  = c;
    this.stateRepository   = stateRepository;
    this.choiceRepository  = choiceRepository;
  }

  public Choice addNewChoice(Long state_id, Choice choice) {
    Optional<State> opt   = stateRepository.findById(state_id);
    Optional<Choice> opt2 = choiceRepository.findByName(choice.getName());
    
    if (!opt.isPresent()) {
      throw new IllegalStateException("State with id "+state_id+" doesn't exist.");
    }
    if (opt2.isPresent()) {
      throw new IllegalStateException("Choice with name "+choice.getName()+" already exist.");
    }

    choice.setState(opt.get());
    return choiceRepository.save(choice);
  }

  public List<Choice> getChoices(Long state_id) {
    List<Choice> choices = choiceRepository.findByStateId(state_id);
    return choices;
  }

  public Choice getChoice(Long choice_id) {
    Optional<Choice> opt = choiceRepository.findById(choice_id);
    if (!opt.isPresent()) {
      throw new IllegalStateException("Choice by id : "+choice_id+" is already taken.");
    }
    
    return opt.get();
  }

  public Choice updateChoice(Choice choice, Long choice_id) {
    Choice rchoice = choiceRepository.getReferenceById(choice_id);

    if (choice.getName() != null) {
      rchoice.setName(choice.getName());
    }
    if (choice.getRedirectValue() != null) {
      String redirect_value_name = choice.getRedirectValue().getName();
      Optional<State> opt        = stateRepository.findByName(redirect_value_name);
      if (!opt.isPresent()) {
        throw new IllegalStateException("redirectValue by name : "+redirect_value_name+" is non-existant.");
      }

      rchoice.setRedirectValue(stateRepository.save(opt.get()));
    }
    if (choice.getState() != null) {
      choice.setState(choice.getState());

      String name_state   = choice.getState().getName(); 
      Optional<State> opt = stateRepository.findByName(name_state);
      if (!opt.isPresent()) {
        throw new IllegalStateException("State by name : "+name_state+" is non-existant.");
      }

      rchoice.setState(stateRepository.save(opt.get()));
    }

    return choiceRepository.save(rchoice);
  }

  public void deleteChoice(Long choice_id) {
    choiceRepository.deleteById(choice_id);
  }

  /*
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
    if (config.getIntents() != null) {
      rconfig.setIntents(config.getIntents());
    }
    if (config.getStates() != null) {
      rconfig.setStates(config.getStates());
    }

    return configRepository.save(rconfig);
  }

  public void deleteConfig(Long config_id) {
    configRepository.deleteById(config_id);
  }
  */
}
