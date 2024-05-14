package elixir.restful.account;
import elixir.restful.account.*;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor// <--- THIS is it
public class Config {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  @Column(
    updatable = false
  )
  private Long id;

  private String name;
  @ManyToOne private Account account;
  @OneToMany private List<State> states;
  @OneToMany private List<Intent> intents;
}
