package org.mintyn.order.report.repository;

import org.mintyn.order.report.model.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {

    @Query("select o from OrderReport o where o.orderCreatedDate between ?1 and ?2")
    List<OrderReport> findAllByOrderCreatedDateIsBetween(LocalDateTime min, LocalDateTime max);
}
