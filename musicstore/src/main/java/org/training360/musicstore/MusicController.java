package org.training360.musicstore;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instruments")
public class MusicController {

    private MusicStoreService musicStoreService;

    public MusicController(MusicStoreService musicStoreService) {
        this.musicStoreService = musicStoreService;
    }

    @GetMapping
    public List<InstrumentDTO> listInstrument(@RequestParam Optional<String> brand, @RequestParam Optional<Integer> price) {
        return musicStoreService.listInstrument(brand, price);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstrumentDTO createInstrument(@Valid @RequestBody CreateInstrumentCommand command) {
        return musicStoreService.createInstrument(command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllInstrument() {
        musicStoreService.deleteAllInstrument();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable("id") long id) {
        musicStoreService.deleteInstrument(id);
    }

    @GetMapping("/{id}")
    public InstrumentDTO findInstrumentById(@PathVariable("id") long id) {
        return musicStoreService.findInstrumentById(id);
    }

    @PutMapping("/{id}")
    public InstrumentDTO updateInstrument(@PathVariable("id") long id, @Valid @RequestBody UpdatePriceCommand command) {
        return musicStoreService.updateInstrument(id, command);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException e) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("instruments/not-found"))
                        .withTitle("Not found")
                        .withStatus(Status.NOT_FOUND)
                        .withDetail(e.getMessage())
                        .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Problem> handleValidException(MethodArgumentNotValidException exception) {
//        List<Violation> violations =
//                exception.getBindingResult().getFieldErrors().stream()
//                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
//                .collect(Collectors.toList());
//
//        Problem problem =
//                Problem.builder()
//                        .withType(URI.create("employees/not-valid"))
//                .withTitle("Validation error")
//                .withStatus(Status.BAD_REQUEST)
//                .withDetail(exception.getMessage())
//                .with("violations", violations)
//                .build();
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//                .body(problem);
//    }
}
