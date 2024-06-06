package com.balashovmaksim.taco.controllers;

import com.balashovmaksim.taco.enums.Type;
import com.balashovmaksim.taco.model.Bucket;
import com.balashovmaksim.taco.model.Ingredient;
import com.balashovmaksim.taco.model.Taco;
import com.balashovmaksim.taco.model.TacoOrder;
import com.balashovmaksim.taco.service.TacoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("bucket")
@RequiredArgsConstructor
public class TacoController {

    private final TacoService tacoService;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Type[] types = Type.values();
        for (Type type : types) {
            Iterable<Ingredient> ingredientsByType = tacoService.filterIngredientsByType(type);
            model.addAttribute(type.toString().toLowerCase(), ingredientsByType);
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "bucket")
    public Bucket bucket() {
        return new Bucket();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showForm(Model model) {
        addIngredientsToModel(model);
        return "design";
    }

    @PostMapping
    public String createTaco(@Valid @ModelAttribute Taco taco, Errors errors,
                             @ModelAttribute Bucket bucket, Model model) {
        if (errors.hasErrors()) {
            addIngredientsToModel(model);
            return "design";
        }

        bucket.addTaco(taco);
        model.addAttribute("bucket", bucket);
        return "redirect:/bucket";
    }
}
