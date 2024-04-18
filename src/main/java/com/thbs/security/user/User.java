package com.thbs.security.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.thbs.security.token.Token;

// Lombok annotations for automatically generating getters, setters, constructors, and builder pattern methods.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indicates that this class is an entity that is mapped to a database table.
@Table(name = "user") // Specifies the name of the database table to map this entity.
public class User implements UserDetails {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
  private String password;

    private boolean isemailverified;

    @NotNull
    @Column(unique = true)
    private Integer employeeId;

    @NotBlank(message = "Business unit is required")
    private String businessUnit;
  // Enumerated annotation to specify how the Role enum is persisted in the database.
  @Enumerated(EnumType.STRING)
  private Role role;

  public boolean isEmailVerified() {
    return isemailverified;
}

public void setEmailVerified(boolean emailVerified) {
    isemailverified = emailVerified;
}

  
  public User(String firstname, String lastname, String email, String password, Integer employeeId, String businessUnit,
      Role role,Boolean isemailverified) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.employeeId = employeeId;
    this.businessUnit = businessUnit;
    this.role = role;
    this.isemailverified=isemailverified;
  }

  // OneToMany annotation to specify a one-to-many relationship with the Token entity.
  @OneToMany(mappedBy = "user") // "user" is the field in the Token entity that maps to this User entity.
  private List<Token> tokens;

  // Method to retrieve authorities (permissions) associated with the user's role.
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities(); // Returns authorities (permissions) based on the user's role.
  }

  // Method to retrieve the user's password.
  @Override
  public String getPassword() {
    return password;
  }

  // Method to retrieve the user's username (which is their email).
  @Override
  public String getUsername() {
    return email;
  }

  // Methods indicating the status of the user's account. In this case, they are always true.
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
