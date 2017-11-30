package com.mazhen.datacollector.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pingan_puhui_user_info",
       uniqueConstraints = @UniqueConstraint(columnNames = { "mobile" }),
       indexes = {
               @Index(name = "pingan_puhui_user_info_idx01", columnList = "modifiedAt")
       })
public class PinganPuhuiUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", columnDefinition = "varchar(20)", nullable = false)
    private String name;

    @Column(name = "mobile", columnDefinition = "varchar(11)", nullable = false)
    private String mobile;

    @Column(name = "birthDate", columnDefinition = "varchar(10)", nullable = false)
    private String birthDate;

    @Column(name = "sex", columnDefinition = "tinyint(1)", nullable = false)
    private Byte sex;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedAt", nullable = false)
    private Date modifiedAt;

    @PrePersist
    void onCreate() {
        Date now = new Date();
        createdAt = now;
        modifiedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        modifiedAt = new Date();
    }
}
