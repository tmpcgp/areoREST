package elixir.restful.account;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString// <--- THIS is it
public class AuthStatus {
  private Boolean auth;
} 
