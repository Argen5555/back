package com.example.carservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    private String description;
    @Column(name = "order_time")
    private LocalDateTime orderTime;
    @OneToMany(mappedBy = "order")
    private List<ServiceModel> services;
    @ManyToMany
    @JoinTable(name = "orders_goods",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id"))
    private List<Goods> goods;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal price;
    @Column(name = "completion_time")
    private LocalDateTime completionTime;

    public enum OrderStatus {
        ACCEPTED("Accepted"),
        IN_PROCESS("In process"),
        COMPLETED_SUCCESSFULLY("Completed successfully"),
        COMPLETED_UNSUCCESSFULLY("Completed unsuccessfully"),
        PAID("Paid");
        private String value;

        OrderStatus(String value) {
            this.value = value;
        }
    }
}
