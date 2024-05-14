package elixir.restful.account;

import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.*;
import java.util.*;

import elixir.restful.account.*;

@Service // same as Component (@Service is only for sementic stuff.)
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account getAccountByKey(String key) {
    Optional<Account> opt = accountRepository.findAccountByKey(key);
    if (!opt.isPresent()) {
      throw new IllegalStateException("Account with key " + key + " doesn't exist.");
    }

    return opt.get();
  }

  public Account getAccountById(Long id) {
    Optional<Account> opt = accountRepository.findById(id);
    if (!opt.isPresent()) {
      throw new IllegalStateException("Account with id " + id + " doesn't exist.");
    }
    return opt.get();
  }

  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  public void deleteAccountById(Long id) {
    accountRepository.deleteById(id);
  }

  // @Incomplete to test it out.
  // use python ?
  public Account addNewAccount(Account account) {
    Optional<Account> opt = accountRepository.findAccountByName(account.getName());
    if (opt.isPresent()) {
      throw new IllegalStateException("account name taken.");
    }
    
    return accountRepository.save(account);
  }

  public Account updateAccount(Account account, Long id) {
    Account refAccount = accountRepository.getReferenceById(id); // already throws exception
    
    // check if the account has the following
    if (account.getName() != null) {
      refAccount.setName   (account.getName());
    } 
    if (account.getSpec() != null) {
      refAccount.setSpec   (account.getSpec());
    }
    if (account.getKey() != null) {
      refAccount.setKey    (account.getKey ());
    } 

    return accountRepository.save(refAccount);
  }

}
