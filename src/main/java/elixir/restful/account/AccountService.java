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

  public Account getAccountByName(String name) {
    Optional<Account> opt = accountRepository.findAccountByName(name);
    if (opt.isEmpty()) {
      throw new IllegalStateException("Account with name "+name+" doesn't exist.");
    }

    return opt.get();
  }

  public Account getAccountById(Long id) {
    Optional<Account> opt = accountRepository.findById(id);
    if (opt.isEmpty()) {
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
      throw new IllegalStateException("Account with name " + account.getName() + " taken.");
    }
    
    Account naccount = accountRepository.save(account);
    return  naccount;
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
      refAccount.setKey   (account.getKey());
    } 

    Account naccount = accountRepository.save(refAccount);
    System.out.println("@naccount "+naccount);

    return naccount;
  }

}
