package com.example.ordermanager.model.user;

import com.example.ordermanager.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(appliesTo = "`admin`")
@Entity
public class Admin extends User {

    public Admin(String login,
                 String password,
                 String fullName,
                 String email,
                 Boolean isExpired,
                 Boolean isLocked,
                 Boolean isCredentialsExpired,
                 Boolean isEnabled) {
        super(login, password, fullName, email, Role.ADMIN, isExpired, isLocked, isCredentialsExpired, isEnabled);
    }
}
