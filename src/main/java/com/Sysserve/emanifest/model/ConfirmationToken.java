package com.Sysserve.emanifest.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="confirmationToken")
@Getter
@Setter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String token;
    @CreationTimestamp
    @Column
    private Timestamp timestamp;
    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User user;
    @Transient
    private boolean isExpired;
}
