package org.training360.finalexam.players;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {

    private ModelMapper modelMapper;
    private PlayerRepository playerRepository;

    public List<PlayerDTO> getPlayers() {
        return playerRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PlayerDTO.class))
                .collect(Collectors.toList());
    }

    public PlayerDTO addNewPlayer(CreatePlayerCommand command) {
        return modelMapper.map(playerRepository.save(new Player(
                command.getName(),
                command.getDateOfBirth(),
                command.getPosition()
        )), PlayerDTO.class);
    }

    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }
}
