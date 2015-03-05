package com.pactera.recipeFinder.parser;

import java.io.File;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.pactera.recipeFinder.model.Ingredient;
import com.pactera.recipeFinder.parser.IngredientParser;

public class IngredientParserTest {

	@Test
	public void testProduceIngredientsWithIncorrectFormat() {

		Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(new File(getClass().getResource("/ingredientsWithIncorrectFormat.csv").getFile()));
		Assert.assertNotNull(ingredients);
		Assert.assertEquals(2, ingredients.size());
	}

	@Test
	public void testProduceIngredientsWithInvalidFile() {

		Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(new File("Invalid.csv"));
		Assert.assertNotNull(ingredients);
		Assert.assertEquals(0, ingredients.size());
	}

	@Test
	public void testProduceIngredients() {

		Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(new File(getClass().getResource("/ingredients.csv").getFile()));
		Assert.assertNotNull(ingredients);
		Assert.assertEquals(5, ingredients.size());
	}

	@Test
	public void testProduceIngredientsWithPartialItem() {

		Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(new File(getClass().getResource("/ingredientsWithLessItems.csv").getFile()));
		Assert.assertNotNull(ingredients);
		Assert.assertEquals(3, ingredients.size());
	}

	@Test
	public void testProduceIngredientsWithExpiredItem() {

		Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(new File(getClass().getResource("/ingredientsWithExpiredItem.csv").getFile()));
		Assert.assertNotNull(ingredients);
		Assert.assertEquals(4, ingredients.size());
	}
}
