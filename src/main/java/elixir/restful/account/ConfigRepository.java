package elixir.restful.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.*;

import elixir.restful.account.*;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Repository // responsible for data.
public interface ConfigRepository extends JpaRepository<Config, Long>{
  List<Config> findByAccountId(Long id);
  Optional<Config> findConfigByName(String name);
  @Transactional void deleteByAccountId(Long id);
}
