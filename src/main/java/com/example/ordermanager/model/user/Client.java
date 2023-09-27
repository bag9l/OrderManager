package com.example.ordermanager.model.user;

import com.example.ordermanager.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(appliesTo = "`client`")
@Entity
public class Client extends User {

    public Client(String login,
                  String password,
                  String fullName,
                  String email,
                  Boolean isExpired,
                  Boolean isLocked,
                  Boolean isCredentialsExpired,
                  Boolean isEnabled) {
        super(login, password, fullName, email, Role.CLIENT, isExpired, isLocked, isCredentialsExpired, isEnabled);
    }


}
