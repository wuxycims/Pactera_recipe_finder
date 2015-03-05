package com.pactera.recipeFinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class RecipeFinderTest {

	private RecipeFinder recipeFinder;
	
	@Before 
	public void initialize() {
		 recipeFinder = new RecipeFinder();
	 }

	@Test
	public void testWithFiles() {
		String result = recipeFinder.findRecipe("/ingredients2.csv", getClass().getResource("/recipe.json").getFile());
		Assert.assertEquals("Order Takeout", result);
	}

	@Test
	public void testWithInvalidRecipeFile() {
		String result = recipeFinder.findRecipe(getClass().getResource("/ingredients.csv").getFile(), "/recipe.json");
		Assert.assertEquals("Order Takeout", result);
		
	}

	@Test
	public void testWithInvalidIngredientsFile() {
		String result = recipeFinder.findRecipe(getClass().getResource("/ingredients.csv").getFile(), getClass().getResource("/recipe.json").getFile());
		Assert.assertEquals("salad sandwich", result);
	}

	@Test
	public void testWithNoneRecipeAndingredients() {
		String result = recipeFinder.findRecipe("", "");
		Assert.assertEquals("Order Takeout", result);
	}

	@Test
	public void testWithExpiredItem() {

		String ingredients = "bread,10,slices,25/12/2010\n"
				+ "cheese,10,slices,25/12/2015\n"
				+ "butter,250,grams,25/12/2015\n"
				+ "peanut butter,250,grams,2/12/2015\n"
				+ "mixed salad,150,grams,20/12/2015\n";

		List<String> ingredientList = getIngredientList(ingredients);

		String recipe = "["
				+ "{"
				+ "\"name\": \"grilled cheese on toast\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}"
				+ "]"
				+ "}"
				+ ","
				+ "{"
				+ "\"name\": \"salad sandwich\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"}"
				+ "]"
				+ "}"
				+ "]";

		String result = recipeFinder.findRecipe(ingredientList, recipe);
		Assert.assertEquals("Order Takeout", result);
	}


	@Test
	public void testWithGoodIngredients() {
		String ingredients = "bread,10,slices,25/12/2015\n"
				+ "cheese,10,slices,25/12/2015\n"
				+ "butter,250,grams,25/12/2015\n"
				+ "peanut butter,250,grams,2/12/2015\n"
				+ "mixed salad,150,grams,20/12/2015\n";

		String recipe = "["
				+ "{"
				+ "\"name\": \"grilled cheese on toast\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}"
				+ "]"
				+ "}"
				+ ","
				+ "{"
				+ "\"name\": \"salad sandwich\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"}"
				+ "]"
				+ "}"
				+ "]";

		List<String> ingredientList = getIngredientList(ingredients);

		String result = recipeFinder.findRecipe(ingredientList, recipe);
		Assert.assertEquals("salad sandwich", result);
	}		

	@Test
	public void testWithTwoDateIngredients() {
		String ingredients = "bread,10,slices,25/12/2015\n"
				+ "cheese,10,slices,25/12/2015\n"
				+ "butter,250,grams,25/12/2015\n"
				+ "peanut butter,250,grams,2/12/2015\n"
				+ "mixed salad,50,grams,20/12/2015\n"
				+ "mixed salad,70,grams,25/12/2015\n";

		String recipe = "["
				+ "{"
				+ "\"name\": \"grilled cheese on toast\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}"
				+ "]"
				+ "}"
				+ ","
				+ "{"
				+ "\"name\": \"salad sandwich\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"}"
				+ "]"
				+ "}"
				+ "]";

		List<String> ingredientList = getIngredientList(ingredients);

		String result = recipeFinder.findRecipe(ingredientList, recipe);
		Assert.assertEquals("salad sandwich", result);
	}		
	
	@Test
	public void testWithMissingIngredient() {
		String ingredients = "bread,10,slices,25/12/2015\n"
				+ "cheese,10,slices,25/12/2015\n"
				+ "butter,250,grams,25/12/2015\n"
				+ "peanut butter,250,grams,2/12/2015\n"
				+ "mixed salad,150,grams,20/12/2015\n";
		
		List<String> ingredientList = getIngredientList(ingredients);
		
		String recipe = "["
				+ "{"
				+ "\"name\": \"grilled cheese on toast\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}"
				+ "]"
				+ "}"
				+ ","
				+ "{"
				+ "\"name\": \"salad sandwich\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"},"
				+ "{ \"item\":\"sugar\", \"amount\":\"200\", \"unit\":\"grams\"}"
				+ "]"
				+ "}"
				+ "]";
		

		String result = recipeFinder.findRecipe(ingredientList, recipe);
		Assert.assertEquals("grilled cheese on toast", result);
	}

	@Test
	public void testWithMissingIngredients() {
		String ingredients = "bread,10,slices,25/12/2015\n"
				+ "cheese,10,slices,25/12/2015\n"
				+ "butter,250,grams,25/12/2015\n"
				+ "peanut butter,250,grams,2/12/2015\n"
				+ "mixed salad,150,grams,20/12/2015\n";

		String recipe = "["
				+ "{"
				+ "\"name\": \"grilled cheese on toast\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"salt\", \"amount\":\"200\", \"unit\":\"grams\"}"
				+ "]"
				+ "}"
				+ ","
				+ "{"
				+ "\"name\": \"salad sandwich\","
				+ "\"ingredients\": ["
				+ "{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"},"
				+ "{ \"item\":\"sugar\", \"amount\":\"200\", \"unit\":\"grams\"}"
				+ "]"
				+ "}"
				+ "]";

		List<String> ingredientList = getIngredientList(ingredients);
		String result = recipeFinder.findRecipe(ingredientList, recipe);
		Assert.assertEquals("Order Takeout", result);

	}
	
	/**
	 * Replace with current date
	 * @param ingredients
	 * @return
	 */
	private String replaceDate(String ingredients) {
		final Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String day = dateFormat.format(calendar.getTime());

		ingredients = ingredients.replace("20/12/2015", day);

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		day = dateFormat.format(calendar.getTime());
		ingredients = ingredients.replace("25/12/2015", day);
		return ingredients;
	}

	/**
	 * Convert lines of string to a list of strings
	 * @param ingredients - lines of string
	 * @return a list of strings
	 */
	private List<String> getIngredientList(String ingredients) {
		
		ingredients = replaceDate(ingredients);

		List<String> ingredientList = new ArrayList<String>();

		for (String ingredient : ingredients.split("\n")) {
			ingredientList.add(ingredient);
		}
		
		return ingredientList;
	}
}
