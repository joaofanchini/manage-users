package com.training.webcrud.data.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "TB_USER")
public class User {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Id
    @Column(name = "USERNAME", length = 20)
    private String username;
    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @PrePersist
    @PreUpdate
    private void preInsertOrUpdate(){
        if(this.password != null)
            this.password = passwordEncoder.encode(this.password);
    }
}
