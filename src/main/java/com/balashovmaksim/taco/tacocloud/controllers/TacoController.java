package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.service.TacoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.balashovmaksim.taco.tacocloud.model.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class TacoController {
    private final TacoService tacoService;

    @GetMapping
    public String showDesignForm(Model model) {
        addIngredientsToModel(model);
        model.addAttribute("tacoOrder", new TacoOrder());
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder, Model model) {
        if (errors.hasErrors()) {
            addIngredientsToModel(model);
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

    private void addIngredientsToModel(Model model) {
        Type[] types = Ingredient.Type.values();
        for (Type type : types){
            Iterable<Ingredient> ingredientsByType = tacoService.filterIngredientsByType(type);
            model.addAttribute(type.toString().toLowerCase(), ingredientsByType);
        }
    }
}
