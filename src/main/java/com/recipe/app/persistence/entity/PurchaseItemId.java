package com.recipe.app.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemId implements Serializable {
    private Integer idPurchase;
    private Integer idItem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseItemId that)) return false;
        return Objects.equals(idPurchase, that.idPurchase) && Objects.equals(idItem, that.idItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPurchase, idItem);
    }
}
