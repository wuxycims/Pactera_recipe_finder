package com.pactera.recipeFinder.model;

import java.util.List;

public class Recipe {
	/** The name of the recipe */
	private final String name;

	/** The ingredients of the recipe */
	private final List<Ingredient> ingredients;

	/**
	 * Constructor of a recipe
	 * 
	 * @param name The name of the recipe
	 * @param ingredients The ingredients of the recipe
	 */
	public Recipe(String name, List<Ingredient> ingredients) {
		this.name = name;
		this.ingredients = ingredients;
	}

	/**
	 * get the name of the recipe
	 *
	 * @return The name of the recipe
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the ingredients of the recipe
	 *
	 * @return The ingredients of the recipe
	 */
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
}
