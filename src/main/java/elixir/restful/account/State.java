package elixir.restful.account;

import elixir.restful.account.*;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity(name="State")
@Table(name="state")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString// <--- THIS is it
public class State {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  /*
  @Column(
    name = "state_id",
    updatable = false
  )
  */
  private Long id;
  /*
  @Column(
    name = "state_name",
    nullable = false,
    columnDefinition = "TEXT"
  )
  */
  private String name;

  @ElementCollection 
  private List<String> answers;

  @ManyToOne 
  /*
  @JoinColumn(
    name = "config_id",
    nullable = false,
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      name = "config_state_fk" // fk for Foreign key
    )
  )
  */
  private Config config;

  @OneToMany(cascade = CascadeType.ALL)
  /*
  @OneToMany(
    mappedBy = "state",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  */
  private List<Choice> choices;

  @OneToOne(cascade = CascadeType.ALL)
  //@OneToOne(cascade = CascadeType.ALL)
  private Intent onIntent;
}
