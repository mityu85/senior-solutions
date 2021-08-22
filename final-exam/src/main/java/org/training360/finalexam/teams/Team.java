package org.training360.finalexam.teams;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.training360.finalexam.players.Player;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    public Team(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name")
    private String name;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }
}
