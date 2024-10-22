package com.onlinelibrary.entity;

import com.onlinelibrary.entity.base.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 225, nullable = false)
    private String name;

    @NotBlank(message = "Email is required.")
    @Column(length = 225, nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|hotmail\\.com)$",
            message = "Email must be a valid gmail or hotmail domain.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Column(length = 1024, nullable = false)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, one capital letter, one digit, and no special characters.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Loan> loans = new HashSet<>();

    public User(Long userId) {
        super();
    }
}
