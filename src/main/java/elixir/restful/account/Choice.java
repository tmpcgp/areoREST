package elixir.restful.account;

import jakarta.persistence.*;
import elixir.restful.account.*;
import lombok.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString// <--- THIS is it
public class Choice {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  @Column(
    updatable = false
  )
  private Long id;

  private String name;
  @OneToOne(cascade=CascadeType.ALL) private State state;
  @OneToOne(cascade=CascadeType.ALL) private State redirectValue;
}
