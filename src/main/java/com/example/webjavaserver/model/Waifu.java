package com.example.webjavaserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data // getters, setters, hashcode, equals, toString
@NoArgsConstructor // empty constructor
@AllArgsConstructor // all fields constructor
@Entity // JPA
@Table(name = "waifu") // JPA
public class Waifu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User ofUser;
}