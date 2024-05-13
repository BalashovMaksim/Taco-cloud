package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.TacoReadDto;
import com.balashovmaksim.taco.tacocloud.mapper.TacoMapper;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.repository.IngredientRepository;
import com.balashovmaksim.taco.tacocloud.repository.TacoRepository;
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

    public Iterable<Ingredient> filterIngredientsByType(Ingredient.Type type) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Iterable<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
}
