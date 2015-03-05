package com.pactera.recipeFinder.parser;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pactera.recipeFinder.model.Recipe;
import com.pactera.recipeFinder.model.Unit;
import com.pactera.recipeFinder.parser.RecipeParser;

public class RecipeParserTest {
	@Test
	public void testProduceRecipesWithInvalidAmount() {

		List<Recipe> recipes = RecipeParser.produceRecipes(new File(getClass().getResource("/recipeInvalidAmount.json").getFile()));
		Assert.assertNotNull(recipes);
		Assert.assertEquals(2, recipes.size());
		Assert.assertEquals("salad sandwich", recipes.get(1).getName());
		Assert.assertEquals(1, recipes.get(1).getIngredients().size());
	}

	@Test
	public void testProduceRecipes() {

		List<Recipe> recipes = RecipeParser.produceRecipes(new File(getClass().getResource("/recipe.json").getFile()));
		Assert.assertNotNull(recipes);
		Assert.assertEquals(2, recipes.size());
		Assert.assertEquals("salad sandwich", recipes.get(1).getName());
		Assert.assertEquals(2, recipes.get(1).getIngredients().size());
		Assert.assertEquals(100, recipes.get(1).getIngredients().get(1).getAmount());
		Assert.assertEquals("bread", recipes.get(1).getIngredients().get(0).getItem());
		Assert.assertEquals(Unit.grams, recipes.get(1).getIngredients().get(1).getUnit());
		Assert.assertNull(recipes.get(1).getIngredients().get(1).getUseBy());
	}
}
