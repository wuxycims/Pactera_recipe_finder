package com.pactera.recipeFinder.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class RecipeTest {

	@Test
	public void test() {

		final String myRecipe = "My Recipe";
		final List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Recipe recipe = new Recipe(myRecipe, ingredients);

		Assert.assertEquals(myRecipe, recipe.getName());
		Assert.assertEquals(0, recipe.getIngredients().size());

		final String item = "My Ingredient";
		final int amount = 220;
		final Unit unit = Unit.slices;
		final Date useBy = new Date();
		Ingredient ingredient = new Ingredient(item, amount, unit, useBy);

		ingredients.add(ingredient);

		Assert.assertEquals(1, recipe.getIngredients().size());
		Assert.assertEquals(item, recipe.getIngredients().get(0).getItem());
		Assert.assertEquals(amount, recipe.getIngredients().get(0).getAmount());
		Assert.assertEquals(unit, recipe.getIngredients().get(0).getUnit());
		Assert.assertEquals(useBy, recipe.getIngredients().get(0).getUseBy());
	}
}
