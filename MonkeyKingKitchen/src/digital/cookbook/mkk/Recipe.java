package digital.cookbook.mkk;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

/**
 * The entity class of Recipe
 * 
 * @author Jisheng, Zifeng Zhang, Zhibin Xin
 * @version 1.0 26/5/2018
 * @version 2.0 27/5/2018
 */

public class Recipe implements Serializable {
	
	// Recipe attributes
	private int recipeId;
	private int uid;
	private int preparationTime;
	private int cookingTime;
	private int servings;
	private double rate;
	private String type;
	private String name;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<String> preparationSetps;
	
	private DBProcessor dbProcessor = new DBProcessor();
	
	/**
	 * Constructor
	 * @param name
	 * @param type
	 * @param servings
	 */
	public Recipe(String name, String type, int servings) {
		this.name = name;
		this.type = type;
		this.servings = servings;
		ingredients = new ArrayList<>();
		preparationSetps = new ArrayList<>();
		this.recipeId = dbProcessor.getTheMaxID()+1;
	}
	
	/**
	 * Add ingredients to the recipe
	 * insert the item into DB
	 * @param ingredient
	 */
	public void addIngredient(Ingredient ingredient) {
		ingredient.setRecipeId(recipeId);
		this.ingredients.add(ingredient);
	}
	
	public void deleteIngredient(Ingredient ingredient) {
//		ingredient.setRecipeId(0);
		this.ingredients.remove(ingredient);
	}
	/**
	 * Add steps of preparing the meal
	 * @param preparationSetp
	 */
	public void addPreparationStep(String preparationSetp) {
		this.preparationSetps.add(preparationSetp);
	}
	
	/**
	 * Override toString()
	 */
	public String toString() {
		String recipeInfo = "";
		recipeInfo = name + "\r\n";
		for(Ingredient ingredient : ingredients) {
			recipeInfo += (ingredient.getName() + "  " + ingredient.getAmount() + "  " + ingredient.getUnit()+"\n");
		}
		recipeInfo += ("Cooking time: " + this.cookingTime + "minutes\r\n");
		recipeInfo += ("Preparation time: " + this.preparationTime + "minutes\r\n");
		recipeInfo += ("Servings: " + this.servings + "\r\n");
		recipeInfo += ("Average rate: " + this.rate + "\r\n");
		recipeInfo += "Steps: \r\n";
		
		for (String step : preparationSetps) {
			recipeInfo += (step + "\r\n");
		}
		return recipeInfo;
	}

	// getters and setters
	public int getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(int rid) {
		this.recipeId = rid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getPreparationTime() {
		return preparationTime;
	}
	
	public void setPreparationTime(int preparationTime) {
		this.preparationTime =preparationTime;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public ArrayList<String> getPreparationSetps() {
		return preparationSetps;
	}

}
