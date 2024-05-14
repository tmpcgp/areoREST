package elixir.restful.account;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import elixir.restful.account.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor// <--- THIS is it
public class Intent {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  @Column(
    updatable = false
  )
  private Long id;
  private String name;

  @ElementCollection // 1
  private List<String> trainingSentences;
  @ManyToOne private Config config;
}
