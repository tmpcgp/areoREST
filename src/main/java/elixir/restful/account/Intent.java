package elixir.restful.account;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import elixir.restful.account.*;

@Entity(name="Intent")
@Table(name="intent")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString// <--- THIS is it
public class Intent {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  /*
  @Column(
    name = "intent_id",
    updatable = false
  )
  */
  private Long id;

  /*
  @Column(
    name = "intent_name",
    nullable = false,
    columnDefinition = "TEXT"
  )
  */
  private String name;

  //@ElementCollection // 1
  private List<String> trainingSentences;

  @ManyToOne
  //@ManyToOne 
  /*
  @JoinColumn(
    name = "config_id",
    nullable = false,
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      name = "config_intent_fk" // fk for Foreign key
    )
  )
  */
  private Config config;

  /*
  @OneToOne(cascade = CascadeType.ALL)
  @OneToOne(
    mappedBy = "state",
    orphanRemoval = true,
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
  )
  */
  /*
  @JoinColumn(
    name = "intent_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      name = "intent_fk"
    )
  )
  */

  @OneToOne(cascade = CascadeType.ALL)
  private State state;
}
