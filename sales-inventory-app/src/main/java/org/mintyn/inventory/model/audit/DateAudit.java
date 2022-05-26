package org.mintyn.inventory.model.audit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
//
//
//    @Column(name = "created_at")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    @CreationTimestamp
//    private LocalDate createdAt;
//
//    @Column(name = "last_modify_date")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    @UpdateTimestamp
//    private LocalDate updatedAt;
}
