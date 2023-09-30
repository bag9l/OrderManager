package com.example.ordermanager.model.user;

import com.example.ordermanager.model.Order;
import com.example.ordermanager.model.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(appliesTo = "`client`")
@Entity
public class Client extends User {

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    @JsonManagedReference
    private Set<Order> orders;

    public Client(String login,
                  String password,
                  String fullName,
                  String email,
                  Boolean isExpired,
                  Boolean isLocked,
                  Boolean isCredentialsExpired,
                  Boolean isEnabled,
                  Set<Order> orders) {
        super(login, password, fullName, email, Role.CLIENT, isExpired, isLocked, isCredentialsExpired, isEnabled);
        this.orders = orders;
    }


}
