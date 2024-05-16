package elixir.restful.account;

import elixir.restful.account.*;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity(name="Entity")
@Table(name="entity")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString// <--- THIS is it
public class Config {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  /*
  @Column(
    name = "config_id",
    updatable = false
  )
  */
  private Long id;
  /*
  @Column(
    name = "config_name",
    nullable = false,
    columnDefinition = "TEXT"
  )
  */
  private String name;

  @ManyToOne 
  /*
  @JoinColumn(
    name = "account_id",
    nullable = false,
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      name = "account_config_fk" // fk for Foreign key
    )
  )
  */
  private Account account;


  @OneToMany(cascade = CascadeType.ALL)
  /*
  @OneToMany(
    mappedBy = "config",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  */
  private List<State>  states;

  @OneToMany(cascade = CascadeType.ALL)
  /*
  @OneToMany(
    mappedBy = "config",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  */
  private List<Intent> intents;

}
