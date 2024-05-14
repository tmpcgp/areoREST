package elixir.restful.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.*;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Repository // responsible for data.
public interface AccountRepository extends JpaRepository<Account, Long>{
  Optional<Account> findAccountByName(String name);
  Optional<Account> findAccountByKey(String key);
}
