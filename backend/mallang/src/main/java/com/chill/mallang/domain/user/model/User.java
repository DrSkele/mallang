package com.chill.mallang.domain.user.model;


import com.chill.mallang.domain.faction.model.Faction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String email;
    private String nickname;

    @OneToOne
    @JoinColumn(name = "faction_id")
    private Faction faction_id;
    private String picture;

    // pwd
    // access token

}
