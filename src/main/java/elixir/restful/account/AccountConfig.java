package elixir.restful.account;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.context.annotation.*;

import com.github.javafaker.Faker;
import elixir.restful.account.*;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;

@Configuration
public class AccountConfig {

  private static Faker faker = new Faker();

  @Bean // just for inserting stuff ?
  CommandLineRunner commandLineRunner(AccountRepository repo) {
    return args -> {

      System.out.println("-------------------------------|||||||||||||||||||||||||||||||||--------------------------------");
      System.out.println("@commandlinerunner");
      System.out.println("-------------------------------|||||||||||||||||||||||||||||||||--------------------------------");

      String name1 = faker.name().name();
      String name2 = faker.name().name();


      // dont need to set the id, since it's sequenced.
      Account someAccount = new Account(
        1L,
        name1,
        faker.job().field(),
        UUID.randomUUID().toString(),
        new ArrayList<Config>()
      );

      Account someOtherAccount = new Account(
        2L,
        name2,
        faker.job().field(),
        UUID.randomUUID().toString(),
        new ArrayList<Config>()
      );

      // java 8
      ArrayList<Account> accs = new ArrayList<>();
      accs.add(someAccount);
      accs.add(someOtherAccount);

      repo.saveAll(accs);
    };
  }
}
