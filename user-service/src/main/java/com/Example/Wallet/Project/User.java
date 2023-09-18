package com.Example.Wallet.Project;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Table(name = "users")
public class User implements Serializable, UserDetails {
//for redis server implememts serializable

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;
    private String name ;

    @Column(unique=true)
    private String phoneNumber ;

    @Column(unique=true )
    private String email ;
    private String password ;
    private String dob ;
    private String country ;

    @NotBlank
    @Column(unique = true)
    private String identifierValue ;


    @NotNull
    @Enumerated
    private UserIdentifier userIdentifier;


    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;

    private String authorities;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String [] authorities = this.authorities.split(UserConstants.AUTHORITIES_DELIMETER);
       return Arrays.stream(authorities)
                .map(x->(new SimpleGrantedAuthority(x)))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

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
