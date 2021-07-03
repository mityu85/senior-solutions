package org.training360.musicstore;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MusicStoreService {

    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();

    private List<Instrument> instruments = Collections.synchronizedList(new ArrayList<>());

    public MusicStoreService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<InstrumentDTO> listInstrument(Optional<String> brand, Optional<Integer> price) {
        List<Instrument> filtered = instruments.stream()
                .filter(e -> brand.isEmpty() || price.isEmpty() || e.getBrand().equals(brand.get()) || e.getPrice() == price.get())
                .collect(Collectors.toList());
        return filtered.stream().map(e -> modelMapper.map(e, InstrumentDTO.class)).collect(Collectors.toList());
    }

    public InstrumentDTO createInstrument(CreateInstrumentCommand command) {
        Instrument instrument = new Instrument(idGenerator.incrementAndGet(),
                command.getBrand(),
                command.getType(),
                command.getPrice(),
                LocalDate.now());
        instruments.add(instrument);
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteAllInstrument() {
        idGenerator = new AtomicLong();
        instruments.clear();
    }

    public InstrumentDTO findInstrumentById(long id) {
        return modelMapper.map(instruments.stream()
                .filter(e -> e.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Instrument is not found: " + id)), InstrumentDTO.class);
    }

    public InstrumentDTO updateInstrument(long id, UpdatePriceCommand command) {
        Instrument instrument = instruments.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrument is not found: " + id));
        if (instrument.getPrice() != command.getPrice()) {
            instrument.setPrice(command.getPrice());
        }
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteInstrument(long id) {
        Instrument instrument = instruments.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrument is not found: " + id));
        instruments.remove(instrument);
    }
}
