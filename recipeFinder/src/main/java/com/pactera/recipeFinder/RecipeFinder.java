package com.pactera.recipeFinder;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.pactera.recipeFinder.model.Ingredient;
import com.pactera.recipeFinder.model.Recipe;
import com.pactera.recipeFinder.parser.IngredientParser;
import com.pactera.recipeFinder.parser.RecipeParser;

/**
 * @author stanley wu
 *
 *  Given a list of items in the fridge (presented as a csv list), and a collection of recipes (a collection of JSON
 *  formatted recipes), produce a recommendation for what to cook tonight.
 */
public class RecipeFinder {

	/** today time stamp in milliseconds */
	private final long today;

	/** Result if no recipe is found */
	private final static String NO_RECIPE_FOUND = "Order Takeout";

	/**
	 * Construct Define today time stamp in milliseconds.
	 */
	public RecipeFinder() {
		final Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		today = calendar.getTimeInMillis();
	}

	/**
	 * Find a recipe for given ingredientItems and given recipe text
	 * @param ingredientItems A given ingredientItems
	 * @param recipeText A given recipe text
	 * @return Name of recipe if found, otherwise return "Order Takeout"
	 */
	public String findRecipe(List<String> ingredientItems, String recipeText) {
		if (ingredientItems == null || ingredientItems.size() == 0 || recipeText == null || recipeText.length() == 0) {
			return NO_RECIPE_FOUND;
		}
		final Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(ingredientItems);
		final List<Recipe> recipes = RecipeParser.produceRecipes(recipeText);
		return findRecipe(ingredients, recipes);

	}

	/**
	 * Find a recipe for given fridge Csv file and given recipe file
	 * @param fridgeCsvFile A given ingredientItems
	 * @param recipeFile A given recipe file
	 * @return Name of recipe if found, otherwise return "Order Takeout"
	 */
	public String findRecipe(String fridgeCsvFile, String recipeFile) {
		if (fridgeCsvFile == null || fridgeCsvFile.length() == 0 || recipeFile == null || recipeFile.length() == 0) {
			return NO_RECIPE_FOUND;
		}
		final Map<String, Ingredient> ingredients = IngredientParser.produceIngredients(new File(fridgeCsvFile));
		final List<Recipe> recipes = RecipeParser.produceRecipes(new File(recipeFile));
		return findRecipe(ingredients, recipes);
	}

	/**
	 * Find a recipe for a given ingredients and a given list of recipes
	 * @param ingredients A given ingredients
	 * @param recipes A given list of recipes
	 * @return Name of recipe if found, otherwise return "Order Takeout"
	 */
	private String findRecipe(Map<String, Ingredient> ingredients, List<Recipe> recipes) {
		if (ingredients == null || ingredients.size() == 0
				|| recipes == null || recipes.size() == 0) {
			return NO_RECIPE_FOUND;
		}

		MatchResult result = null;
		for (Recipe recipe : recipes) {
			result = match(ingredients, recipe, result);
		}

		// If no recipe found
		if (result == null || result.getRecipe() == null) {
			return NO_RECIPE_FOUND;
		}

		return result.getRecipe().getName();
	}

	/**
	 * Get Match result for a given ingredients and a given recipe
	 * @param ingredients A given ingredients
	 * @param recipes A given list of recipes
	 * @param currentResult Current result
	 * @return Best match result
	 */
	private MatchResult match(Map<String, Ingredient> ingredients, Recipe recipe, MatchResult currentResult) {
		if (recipe == null || ingredients == null) {
			return currentResult;
		}

		final MatchResult newResult = match(ingredients, recipe);
		return compareResult(currentResult, newResult);
	}

	/**
	 * Compare current result to new result.
	 * @param currentResult Current result
	 * @param newResult New result
	 * @return The recipe with the closest use-by items
	 */
	private MatchResult compareResult(MatchResult currentResult, MatchResult newResult) {
		if (newResult == null || newResult.getExpiredTimes() == null
				|| newResult.getExpiredTimes().size() == 0) {
			return currentResult;
		}

		if (currentResult == null) {
			return newResult;
		}

		List<Long> currentExpiredTimes = currentResult.getExpiredTimes();
		List<Long> newExpiredTimes = newResult.getExpiredTimes();
		int size = (currentExpiredTimes.size() < newExpiredTimes.size()) ? currentExpiredTimes.size() : newExpiredTimes.size();
		for (int i = 0; i < size; i++) {
			if (currentExpiredTimes.get(i) < newExpiredTimes.get(i)) {
				return currentResult;
			}
			else if (currentExpiredTimes.get(i) > newExpiredTimes.get(i)) {
				return newResult;
			}
		}

		return (currentExpiredTimes.size() > newExpiredTimes.size()) ? currentResult : newResult;
	}

	/**
	 * Match recipe with current ingredients.
	 * @param ingredients Current ingredients
	 * @param recipe A given recipe
	 * @return The result with recipe and expired times of each ingredient if found, otherwise result null
	 */
	private MatchResult match(Map<String, Ingredient> ingredients, Recipe recipe) {
		if (recipe == null || ingredients == null) {
			return null;
		}

		final MatchResult result = new MatchResult(recipe);

		for (Ingredient recipeIngredient : recipe.getIngredients()) {
			Ingredient ingredient = ingredients.get(recipeIngredient.getItem());
			if (ingredient == null || ingredient.getAmount() < recipeIngredient.getAmount() || ingredient.getUnit() != recipeIngredient.getUnit()) {
				return null;
			}

			result.addExpiredTime(ingredient.getUseBy().getTime() - today);
		}

		result.sortExpiredTimes();

		return result;
	}

	/**
	 * Main method. *
	 * Valid given fridge Csv file name and given recipe file name are required as the arguments.
	 *
	 * @param args Argument
	 */
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			System.out.println("Invalid argument: fridge Csv file name and given recipe file name are required as the arguments");
			return;
		}

		String result = new RecipeFinder().findRecipe(args[0], args[1]);
		System.out.println("\n\nRecipe for tonight: " + result);
	}
}
