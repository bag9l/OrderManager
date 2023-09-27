package com.example.ordermanager.model.user;

import com.example.ordermanager.model.Role;
import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(appliesTo = "`manager`")
@Entity
public class Manager extends User {

    public Manager(String login,
                   String password,
                   String fullName,
                   String email,
                   Boolean isExpired,
                   Boolean isLocked,
                   Boolean isCredentialsExpired,
                   Boolean isEnabled) {
        super(login, password, fullName, email, Role.MANAGER, isExpired, isLocked, isCredentialsExpired, isEnabled);
    }
}
