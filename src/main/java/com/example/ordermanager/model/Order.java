package com.example.ordermanager.model;

import com.example.ordermanager.model.user.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Table(appliesTo = "`order`")
@Entity
public class Order {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @ToString.Exclude
    private Set<OrderItem> productItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    @JsonBackReference
    private Client owner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Transient
    private Integer existsForMinutes;

    @Column(name = "`is_paid`")
    private Boolean isPaid;

    public Order(Set<OrderItem> productItems,
                 Client owner) {
        this.productItems = productItems;
        this.owner = owner;
        isPaid = false;
        createdAt = LocalDateTime.now();
    }

    public Integer getExistsForMinutes() {
        return Math.toIntExact(ChronoUnit.MINUTES.between(createdAt, LocalTime.now()));
    }
}
