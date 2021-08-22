package org.training360.finalexam.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerDTO> getPlayers() {
        return playerService.getPlayers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO addNewPlayer(@Valid @RequestBody CreatePlayerCommand command) {
        return playerService.addNewPlayer(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayerById(@PathVariable("id") Long id) {
        playerService.deletePlayerById(id);
    }
}
