package org.mintyn.inventory.repository;

import org.mintyn.inventory.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Long> {
}
