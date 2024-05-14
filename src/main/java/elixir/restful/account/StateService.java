package elixir.restful.account;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.*;
import elixir.restful.account.*;
import java.util.*;

@Service // same as Component (@Service is only for sementic stuff.)
public class StateService {

  private final AccountRepository accountRepository;
  private final ConfigRepository configRepository;
  private final StateRepository stateRepository;
  private final IntentRepository intentRepository;
  private final ChoiceService    choiceService;

  @Autowired
  public StateService(AccountRepository a, ConfigRepository c, StateRepository stateRepository, IntentRepository intentRepository, ChoiceService choiceService) {
    this.accountRepository = a;
    this.configRepository  = c;
    this.stateRepository   = stateRepository;
    this.choiceService     = choiceService;
    this.intentRepository  = intentRepository;
  }

  public State addNewState(Long config_id, State state) {
    Optional<Config> opt  = configRepository.findById(config_id);
    Optional<State>  opt2 = stateRepository.findByName(state.getName());
    
    if (!opt.isPresent()) {
      throw new IllegalStateException("Config with id "+config_id+" doesn't exist.");
    }
    if (opt.isPresent()) {
      throw new IllegalStateException("State with name "+state.getName()+" already exist.");
    }

    state.setConfig(opt.get());
    return stateRepository.save(state);
  }

  public List<State> getStates(Long config_id) {
    return stateRepository.findAll();
  }

  public State getState(Long state_id) {
    Optional<State> opt = stateRepository.findById(state_id);
    if (!opt.isPresent()) {
      throw new IllegalStateException("State by id : "+state_id+" is already taken.");
    }
    
    return opt.get();
  }

  public State updateState(State state, Long state_id) {
    State rstate = stateRepository.getReferenceById(state_id);

    if (state.getName() != null) {
      rstate.setName(state.getName());
    }
    if (state.getOnIntent() != null) {
      rstate.setOnIntent(state.getOnIntent());
    }
    if (state.getChoices() != null) {
      for (Choice c : state.getChoices()) {
        choiceService.updateChoice(c, c.getId());
      }
    }

    return stateRepository.save(rstate);
   }
}
