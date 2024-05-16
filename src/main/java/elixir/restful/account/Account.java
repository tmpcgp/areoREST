package elixir.restful.account;

import jakarta.persistence.*;
import elixir.restful.account.*;
import java.util.*;
import lombok.*;

@Entity(name="Account")
@Table(
  name = "account"
  /*
  uniqueConstraints = {
    @UniqueConstraint(name = "student_email_unique", columnNames = "email")
  }
  */
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString// <--- THIS is it
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
  /*
  @Column(
    updatable = false,
    name = "id"
  )
  */
  private Long id;

  /*
  @Column(
    name = "name",
    nullable = false,
    columnDefinition = "TEXT"
  )
  */
  private String name;
  /*
  @Column(
    name = "spec",
    nullable = false,
    columnDefinition = "TEXT"
  )
  */
  private String spec;
  /*
  @Column(
    name = "key",
    nullable = false,
    columnDefinition = "TEXT"
  )
  */
  private String key;

  @OneToMany
  /*
  @OneToMany(
    mappedBy = "account",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  */
  private List<Config> configs;

}
