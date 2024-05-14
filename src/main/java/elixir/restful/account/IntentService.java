package elixir.restful.account;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.*;
import elixir.restful.account.*;
import java.util.*;

@Service // same as Component (@Service is only for sementic stuff.)
public class IntentService {

  private final AccountRepository accountRepository;
  private final ConfigRepository  configRepository;
  private final StateRepository   stateRepository;
  private final ChoiceRepository  choiceRepository;
  private final IntentRepository  intentRepository;

  @Autowired
  public IntentService(AccountRepository a, ConfigRepository c, StateRepository stateRepository, ChoiceRepository choiceRepository, IntentRepository intentRepository) {
    this.accountRepository = a;
    this.configRepository  = c;
    this.stateRepository   = stateRepository;
    this.choiceRepository  = choiceRepository;
    this.intentRepository  = intentRepository;
  }

  public Intent addNewIntent(Long config_id, Intent intent) {
    Optional<Config> opt  = configRepository.findById(config_id);
    Optional<Intent> opt2 = intentRepository.findByName(intent.getName());

    if (!opt.isPresent()) {
      throw new IllegalStateException("State with id "+config_id+" doesn't exist.");
    }
    if (opt2.isPresent()) {
      throw new IllegalStateException("Intent with name "+intent.getName()+" already exist.");
    }

    intent.setConfig(opt.get());
    return intentRepository.save(intent);
  }

  public List<Intent> getIntents(Long config_id) {
    List<Intent> intents = intentRepository.findByConfigId(config_id);
    return intents;
  }

  public Intent getIntent(Long intent_id) {
    Optional<Intent> opt = intentRepository.findById(intent_id);
    if (!opt.isPresent()) {
      throw new IllegalStateException("Intent by id : "+intent_id+" is already taken.");
    }
    
    return opt.get();
  }

  public Intent updateIntent(Intent intent, Long intent_id) {
    Intent rintent = intentRepository.getReferenceById(intent_id);

    if (intent.getName() != null) {
      rintent.setName(intent.getName());
    }
    if (intent.getTrainingSentences() != null) {
      // check if that is existant
      // we already do it on the client side.
      rintent.setTrainingSentences(intent.getTrainingSentences());
    }

    return intentRepository.save(rintent);
  }

  public void deleteIntent(Long intent_id) {
    intentRepository.deleteById(intent_id);
  }
}
