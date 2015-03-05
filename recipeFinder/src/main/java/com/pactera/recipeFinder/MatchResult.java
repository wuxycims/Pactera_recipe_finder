package com.pactera.recipeFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pactera.recipeFinder.model.Recipe;

/**
 *
 * @author stanley wu
 *
 *  Match result data including recipe and expired times of each ingredient if found,
 */
public class MatchResult {

	/** recipe matched */
	private final Recipe recipe;

	/** expired times in milliseconds of each ingredient */
	private final List<Long> expiredTimes = new ArrayList<Long>();

	/**
	 * Construct
	 * @param recipe The recipe matched
	 */
	public MatchResult(Recipe recipe) {
		this.recipe = recipe;
	}

	/**
	 * Get The recipe matched
	 * @return The recipe matched
	 */
	public Recipe getRecipe() {
		return recipe;
	}

	/**
	 * Get the expired times 
	 * @return The expired times in milliseconds of each ingredient
	 */
	public List<Long> getExpiredTimes() {
		return expiredTimes;
	}

	/**
	 * Add a new expired times
	 * @param expiredTime A new expired times
	 */
	public void addExpiredTime(Long expiredTime) {
		this.expiredTimes.add(expiredTime);
	}

	/**
	 * Sort the expired times
	 */
	public void sortExpiredTimes() {
		Collections.sort(expiredTimes);
	}
}
