package com.recipe.app.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchases")
@Getter
@Setter
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_purchase", nullable = false)
    private Integer idPurchase;
    
    @Column(name = "user_id", nullable = false, length = 15)
    private String idUser;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "NUMERIC(6,2)")
    private Double total;

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private String method;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
    private List<PurchaseItemEntity> items; 
}
