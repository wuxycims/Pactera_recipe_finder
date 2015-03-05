package com.pactera.recipeFinder.model;

import java.util.Date;

public class Ingredient {
	/** the name of the ingredient */
	private final String item;

	/** the amount of the ingredient */
	private final int amount;

	/** the unit of measure, values */
	private final Unit unit;

	/** the use by date of the ingredient */
	private final Date useBy;

	/**
	 * Constructor of an ingredient
	 *
	 * @param item The name of the ingredient
	 * @param amount The amount of the ingredient
	 * @param unit The unit of the ingredient
	 * @param useBy The use by date of the ingredient
	 */
	public Ingredient(String item, int amount, Unit unit, Date useBy) {
		this.item = item;
		this.amount = amount;
		this.unit = unit;
		this.useBy = useBy;
	}

	/**
	 * get the name of the ingredient
	 *
	 * @return The name of the ingredient
	 */
	public String getItem() {
		return item;
	}

	/**
	 * Get the amount of the ingredient
	 *
	 * @return The amount of the ingredient
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Get the unit of the ingredient
	 *
	 * @return The unit of the ingredient
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Get the use by date of the ingredient
	 *
	 * @return The use by date of the ingredient
	 */
	public Date getUseBy() {
		return useBy;
	}
}
