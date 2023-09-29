package com.example.ordermanager.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
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

    @Column(name = "created_at")//, columnDefinition = "TIME")
    private LocalTime createdAt;

    @Transient
    private Integer existsForMinutes;

    @Column(name = "`is_payed`")
    private Boolean isPayed;

    public Order(Set<OrderItem> productItems) {
        this.productItems = productItems;
        isPayed = false;
        createdAt = LocalTime.now();
    }

    public Integer getExistsForMinutes() {
        return Math.toIntExact(ChronoUnit.MINUTES.between(createdAt, LocalTime.now()));
    }
}
