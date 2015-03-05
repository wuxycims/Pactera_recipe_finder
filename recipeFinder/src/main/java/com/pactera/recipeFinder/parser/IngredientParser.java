package com.pactera.recipeFinder.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.pactera.recipeFinder.model.Ingredient;
import com.pactera.recipeFinder.model.Unit;

/**
 *
 * @author stanley wu
 *
 * Parse the ingredient with the input of file or string
 */
public class IngredientParser {

	private static final Logger Logger = LoggerFactory.getLogger(IngredientParser.class);
	private static final String INGREDIENT_ITEM_SEPARATOR = ",";

	/**
	 * Produce a map of ingredients from a file
	 * @param fileName A given file name
	 * @return A map of ingredients
	 */
	public static Map<String, Ingredient> produceIngredients(File ingredientFile)
	{
		List<String> ingredientLines = readFile(ingredientFile);
		return produceIngredients(ingredientLines);
	}

	/**
	 * Get the content of a given file
	 * @param file The given file
	 * @return The content of the given file
	 */
	private static List<String> readFile(File file) {
		final List<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		if (file == null) {
			return lines;
		}

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}
		catch (IOException e) {
			Logger.error(e.getMessage());
		}
		finally {
			try {
				if (reader != null) {
					reader.close();
				}
			}
			catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}

		return lines;
	}

	/**
	 * Produce a map of ingredient from a list of ingredients
	 * @param ingredientLines A list of ingredients
	 * @return A map of ingredients
	 */
	public static Map<String, Ingredient> produceIngredients(List<String> ingredientLines)
	{
		final Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		final Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		final Date today = calendar.getTime();

		CsvMapper mapper = new CsvMapper();
		mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
		for (String ingredientLine : ingredientLines) {
			String[] items = ingredientLine.split(INGREDIENT_ITEM_SEPARATOR);
			Ingredient ingredient = parseIngredient(items, dateFormat);
			addIngredient(ingredient, ingredients, today);
		}

		return ingredients;
	}

	/**
	 * Add an ingredient to the ingredients map. An ingredient will ignore if these condition are matched: * amount less than
	 * zero * unit is null * use by date expired
	 * @param ingredient A given ingredient
	 * @param ingredients Ingredients map to be added into
	 * @param today Day time for today
	 */
	private static void addIngredient(Ingredient ingredient, Map<String, Ingredient> ingredients, Date today) {
		if (ingredient != null && ingredient.getAmount() > 0 && ingredient.getUnit() != null && today.compareTo(ingredient.getUseBy()) <= 0) {
			final Ingredient oldIngredient = ingredients.get(ingredient.getItem());
			if (oldIngredient != null) {
				// use the earliest use by date
				final Date useBy = (oldIngredient.getUseBy().compareTo(ingredient.getUseBy()) < 0) ? oldIngredient.getUseBy() : ingredient.getUseBy();
				// add amount
				final int amount = oldIngredient.getAmount() + ingredient.getAmount();
				ingredient = new Ingredient(oldIngredient.getItem(), amount, oldIngredient.getUnit(), useBy);
			}

			ingredients.put(ingredient.getItem(), ingredient);
		}
	}

	/**
	 * Parse the ingredient
	 * @param items Ingredient fields
	 * @param dateFormat  A given date format
	 * @return An ingredient
	 */
	private static Ingredient parseIngredient(String[] items, SimpleDateFormat dateFormat) {
		Ingredient ingredient = null;
		if (items != null && items.length == 4) {
			try {
				final String item = items[0];
				final int amount = Integer.parseInt(items[1]);
				final Unit unit = Unit.valueOf(items[2]);
				final Date useBy = dateFormat.parse(items[3]);

				ingredient = new Ingredient(item, amount, unit, useBy);
			}
			catch (Exception e) {
				Logger.error(e.getMessage());
			}
		}

		return ingredient;

	}
}