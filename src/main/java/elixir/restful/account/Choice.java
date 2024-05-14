package elixir.restful.account;

import jakarta.persistence.*;
import elixir.restful.account.*;
import lombok.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor// <--- THIS is it
public class Choice {

  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO
  )
  @Column(updatable = false)
  private Long id;

  private String name;
  @OneToOne private State state;
  @OneToOne private State redirectValue;
}
