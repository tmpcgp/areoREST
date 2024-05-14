package elixir.restful.account;
import elixir.restful.account.*;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor// <--- THIS is it
public class State {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  @Column(
    updatable = false
  )
  private Long id;

  private String name;
  @ElementCollection private List<String> answers;
  @OneToMany private List<Choice> choices;
  @ManyToOne private Config config;
  @OneToOne Intent onIntent;
}
