package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.TacoCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.TacoReadDto;
import com.balashovmaksim.taco.tacocloud.dto.TacoUpdateDto;
import com.balashovmaksim.taco.tacocloud.enums.Type;
import com.balashovmaksim.taco.tacocloud.mapper.TacoMapper;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.repository.IngredientRepository;
import com.balashovmaksim.taco.tacocloud.repository.TacoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TacoService {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final TacoMapper tacoMapper;

    @Transactional
    public List<TacoReadDto> getAll(){
        List<Taco> tacos = tacoRepository.findAll();
        return tacos.stream()
                .map(tacoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TacoReadDto getTacoById(Long id){
        Taco taco = tacoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Taco not found"));
        return tacoMapper.toDto(taco);
    }

    @Transactional
    public TacoReadDto createTaco(TacoCreateDto tacoCreateDto) {
        Taco taco = tacoMapper.toEntity(tacoCreateDto);
        Taco savedTaco = tacoRepository.save(taco);
        return tacoMapper.toDto(savedTaco);
    }

    @Transactional
    public TacoReadDto updateTaco(Long id, TacoUpdateDto tacoUpdateDto) {
        Taco taco = tacoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Taco not found"));

        tacoMapper.updateEntity(tacoUpdateDto, taco);
        tacoRepository.save(taco);

        return tacoMapper.toDto(taco);
    }

    @Transactional
    public void deleteTacoById(Long id) {
        tacoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Taco not found or deleted"));
        tacoRepository.deleteById(id);
    }

    public Iterable<Ingredient> filterIngredientsByType(Type type) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}
