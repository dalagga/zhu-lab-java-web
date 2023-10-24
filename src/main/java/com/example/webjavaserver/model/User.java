package com.example.webjavaserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Objects;

@Builder
@Data // getters, setters, hashcode, equals, toString
@NoArgsConstructor // empty constructor
@AllArgsConstructor // all fields constructor
public class User {
    public Integer id;
    public String name;
    public Integer age;
    public String city;
    public String email;
    public List<String> favWaifu;
}