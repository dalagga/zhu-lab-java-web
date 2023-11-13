package com.example.webjavaserver.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data // getters, setters, hashcode, equals, toString
@NoArgsConstructor // empty constructor
@AllArgsConstructor // all fields constructor
@Entity // JPA
@Table(name = "users") // JPA
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String city;
    private String email;
    @OneToMany(mappedBy = "ofUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waifu> favWaifu;
}