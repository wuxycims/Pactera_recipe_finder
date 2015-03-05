package com.pactera.recipeFinder.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.pactera.recipeFinder.model.Ingredient;
import com.pactera.recipeFinder.model.Recipe;
import com.pactera.recipeFinder.model.Unit;

/**
 *
 * @author stanleyw
 *
 * Parse the recipe with the input of file or string
 */
public class RecipeParser {

	private static final Logger Logger = LoggerFactory.getLogger(RecipeParser.class);

	/**
	 * Produce a list of recipes from a file
	 * @param fileName A given file name
	 * @return A list of recipes
	 */
	public static List<Recipe> produceRecipes(File recipeFile)
	{
		try {
			return produceRecipes(new String(Files.readAllBytes(recipeFile.toPath())));
		}
		catch (IOException e) {
			Logger.error(e.getMessage());
		}

		return null;
	}

	/**
	 * Produce a list of recipes from a recipe text with json format
	 * @param recipe A recipe text with json format
	 * @return A list of recipes
	 */
	public static List<Recipe> produceRecipes(String recipe)
	{
		final List<Recipe> recipes = new ArrayList<Recipe>();

		try {
			JsonParser jsonParser = new JsonFactory().createParser(recipe);

			jsonParser.nextToken();
			while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
				jsonParser.nextToken();
				String recipeName = null;
				final List<Ingredient> ingredients = new ArrayList<Ingredient>();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

					final String fieldName = jsonParser.getCurrentName();
					if (fieldName == "name") {
						// jsonParser.nextToken();
						recipeName = jsonParser.getText();
					}
					else if (fieldName == "ingredients") {
						parseIngredients(jsonParser, ingredients);
					}
				}

				if (recipeName != null) {
					recipes.add(new Recipe(recipeName, ingredients));
				}
			}
		}
		catch (IllegalArgumentException e) {
			Logger.error(e.getMessage());
		}
		catch (JsonParseException e) {
			Logger.error(e.getMessage());
		}
		catch (IOException e) {
			Logger.error(e.getMessage());
		}
		return recipes;
	}

	/**
	 * Parse the ingredient
	 * @param jsonParser jsonParser for parse json
	 * @param ingredients Ingredients container to be added.
	 */
	private static void parseIngredients(JsonParser jsonParser, final List<Ingredient> ingredients) throws IOException, JsonParseException {
		jsonParser.nextToken();
		while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
			String item = null;
			int amount = 0;
			Unit unit = null;
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String namefield = jsonParser.getCurrentName();
				jsonParser.nextToken();
				switch (namefield) {
					case "item":
						item = jsonParser.getValueAsString();
						break;
					case "amount":
						amount = jsonParser.getValueAsInt();
						break;
					case "unit":
						unit = Unit.valueOf(jsonParser.getValueAsString());
						break;
				}
			}

			if (amount > 0 && unit != null) {
				ingredients.add(new Ingredient(item, amount, unit, null));
			}
		}
	}
}