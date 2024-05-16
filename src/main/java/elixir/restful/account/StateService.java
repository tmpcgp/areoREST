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
    Optional<State>  opt2 = stateRepository.findStateByName(state.getName());

    System.out.println("@addNewState "+state);

    if (opt.isEmpty()) {
      throw new IllegalStateException("Config with id "+config_id+" doesn't exist.");
    }
    if (opt2.isPresent()) {
      throw new IllegalStateException("State with name "+state.getName()+" already exist.");
    }

    state.setConfig(opt.get());
    State ret = stateRepository.save(state);

    return ret;
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

  // it is possible to update a state when
  // is is not created
  // in that case just create it
  public State updateState(State state, Long state_id) {
    State rstate = stateRepository.getReferenceById(state_id);

    System.out.println("@updateState "+state);

    if (state.getName() != null) {
      rstate.setName(state.getName());
    }
    if (state.getOnIntent() != null) {
      rstate.setOnIntent(state.getOnIntent());
    }
    if (state.getChoices() != null) {
      for (Choice c : state.getChoices()) {
        if (c.getId() != null) { choiceService.updateChoice(c, c.getId()); }
        else                   { choiceService.addNewChoice(state_id,c);  }
      }
    }

    return stateRepository.save(rstate);
   }
}
