package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.enums.Type;
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


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class TacoController {

    private final TacoService tacoService;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Type[] types = Type.values();
        for (Type type : types){
            Iterable<Ingredient> ingredientsByType = tacoService.filterIngredientsByType(type);
            model.addAttribute(type.toString().toLowerCase(), ingredientsByType);
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showForm() {
        return "design";
    }

    @PostMapping
    public String createTaco(@Valid @ModelAttribute Taco taco, Errors errors,
                             @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()){
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }
}
