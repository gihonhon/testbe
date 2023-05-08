package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "email", columnNames = "email"),
                @UniqueConstraint(name = "telephone", columnNames = "telephone")
        }, schema = "public")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "telephone", columnDefinition = "CHAR(16)")
    private String telephone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date", columnDefinition="DATE")
    private LocalDate birthDate;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "status_active", nullable = false)
    private Boolean statusActive = true;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authProvider;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> rolesUsers = new HashSet<>();

    @OneToMany(mappedBy = "usersTravelerList", cascade = CascadeType.ALL)
    private Set<TravelerList> travelerList;

    @OneToMany(mappedBy = "usersOrder", cascade = CascadeType.ALL)
    private Set<Orders> orders;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notifications;

}
