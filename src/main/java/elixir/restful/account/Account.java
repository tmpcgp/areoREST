package elixir.restful.account;

import jakarta.persistence.*;
import elixir.restful.account.*;
import java.util.*;
import lombok.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor// <--- THIS is it
public class Account {

  @Id
  @SequenceGenerator(
    name = "account_sequence",
    sequenceName = "account_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "account_sequence"
  )
  @Column(
    name = "id",
    updatable = false
  )
  private Long id;

  private String name;
  private String spec;
  private String key;
  @OneToMany private List<Config> configs;
}
