package guru.springframework.strap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class Strap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public Strap(CategoryRepository categoryRepository,
            RecipeRepository recipeRepository,
            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        Recipe spicyChickenRecipe = new Recipe("Spicy Grilled Chicken Tacos Recipe", 20, 15);
        final Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        final Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        final Optional<UnitOfMeasure> piece = unitOfMeasureRepository.findByDescription("Piece");
        final Optional<UnitOfMeasure> pound = unitOfMeasureRepository.findByDescription("Pound");

        final Category mexicanCategory = categoryRepository.findByDescription("Mexican").get();
        final Category grillCategory = categoryRepository.findByDescription("Grill").get();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("ancho chili powder", new BigDecimal(2), tablespoon.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("dried oregano", new BigDecimal(1), teaspoon.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("dried cumin", new BigDecimal(1), teaspoon.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("sugar", new BigDecimal(1), teaspoon.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("salt", new BigDecimal(0.5), teaspoon.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("clove garlic, finely chopped", null, piece.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("finely grated orange zest",
                                       new BigDecimal(1),
                                       tablespoon.get(),
                                       spicyChickenRecipe));
        ingredients.add(new Ingredient("fresh-squeezed orange juice",
                                       new BigDecimal(3),
                                       tablespoon.get(),
                                       spicyChickenRecipe));
        ingredients.add(new Ingredient("olive oil", new BigDecimal(2), tablespoon.get(), spicyChickenRecipe));
        ingredients.add(new Ingredient("skinless, boneless chicken thighs",
                                       new BigDecimal(1.25),
                                       pound.get(),
                                       spicyChickenRecipe));


        spicyChickenRecipe.getIngredients().addAll(ingredients);
        spicyChickenRecipe.getCategories().addAll(Arrays.asList(mexicanCategory, grillCategory));
        mexicanCategory.getRecipes().add(spicyChickenRecipe);
        grillCategory.getRecipes().add(spicyChickenRecipe);

        spicyChickenRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                                                 "\n" +
                                                 "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                                                 "\n" +
                                                 "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                                                 "\n" +
                                                 "Spicy Grilled Chicken Tacos\n" +
                                                 "\n" +
                                                 "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                                                 "\n" +
                                                 "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                                                 "\n" +
                                                 "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                                                 "\n" +
                                                 "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Notes spicyNotes = new Notes();
        spicyNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                                          "\n" +
                                          "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                                          "\n" +
                                          "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                                          "\n" +
                                          "\n" +
                                          "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                                          "\n" +
                                          "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
        spicyChickenRecipe.setNotes(spicyNotes);
        spicyNotes.setRecipe(spicyChickenRecipe);

        categoryRepository.saveAll(Arrays.asList(mexicanCategory, grillCategory));
        recipeRepository.save(spicyChickenRecipe);
    }
}
