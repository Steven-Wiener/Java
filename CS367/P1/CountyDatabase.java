package P1;
///////////////////////////////////////////////////////////////////////////////
// Title:            CountyDBMain
// Files:            CountyDBMain.java
// Semester:         CS367 Spring 2014
//
// Author:           Steven Wiener
// Email:            SWiener@wisc.edu
// Pair Partner:     Andrew Minneci
// Email:            minneci@wisc.edu
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
/**
 * CountyDatabase class stores an ArrayList of Counties, which can be 
 * linked to Storms. Various actions may be placed, such as adding or removing
 * a County or Storm, or getting data related to the County or Storm
 * @author Steven Wiener, Andrew Minneci
 */
public class CountyDatabase {
	private ArrayList<County> database;
	public CountyDatabase() {
		database = new ArrayList<County>();
	}

	/**
	 * Add a county with the given name to the end of the database.
	 * If a county with the name is already in the database, just return.
	 * @param (String name) (Name of County to which you want to add)
	 */
	void addCounty(String name) {
		boolean flag = false;
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getName().equals(name))
				flag = true;
		}
		if (!flag)
			database.add(new County(name));
	}
	
	/**
	 * Add the given storm to county in the database. 
	 * If county is not in the database 
	 * throw a java.lang.IllegalArgumentException.
	 * @param (Storm storm) (A storm you want to add)
	 * @param (County county) (The county you want to add it to)
	 */
	void addStorm(Storm storm, String county) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getName().equals(county)) {
				curr.getStorms().add(storm);
				return;
			}	
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Return true iff county is in the database. 
	 * @param (String county) (The county in which you want to see 
	 * if it is in the database.)
	 * @return (true if in Database, false if not)
	 */
	boolean containsCounty(String county) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getName() == county)
				return true;
		}
		return false;
	}
	/**
	 * Return true iff storm with the name storm appears in at least 
	 * one county's list of storms in the database.
	 * @param (String storm) (A storm to see if it is in the database at all)
	 * @return (true if in database, false if not)
	 */
	boolean containsStorm(String storm) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getStorms().contains(storm))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns true iff storm with the name storm is in the list of storms
	 * for county. If county is not in the database, return false.
	 * @param (String Storm) (Name of Storm)
	 * @param (String county) (Name of County)
	 * @return (returns true if given storm is in the given county)
	 */
	boolean hasStorm(String storm, String county) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getName().equals(county) && (curr.getStorms().contains(storm)))
				return true;	
		}
		return false;
	}
	/**
	 * Return the list of storms with damage amount. 
	 * If no storms in the database have damage amount, return null. 
	 *
	 * @param (Integer amount) (Damage amount to want to find in a storm)
	 * @return (A list of storms with given damage amount)
	 */
	List<Storm> getStormsWithDamageAmount(Integer amount) {
		List<Storm> returnValue = new ArrayList<Storm>();
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()){
			County curr = countyItr.next();
			List<Storm> countyStorms = curr.getStorms();
			Iterator<Storm> stormItr = countyStorms.iterator();
			while (stormItr.hasNext()) {
				Storm currStorm=stormItr.next();
				if (currStorm.getDamageAmount().equals(amount))
					returnValue.add(currStorm);
			}	
		}
		if (returnValue.isEmpty())
			return null;
		else
			return returnValue;
	}
	/**
	 * Return a list of storms that have the date. 
	 * If no storms in the database occurred on date, return null. 
	 *
	 * @param (String date) (The date of which you want to know about)
	 * @return (List of all storms with the given date)
	 */
	List<Storm> getStormsWithDate(String date) {
		List<Storm> returnValue = new ArrayList<Storm>();
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			List<Storm> countyStorms = curr.getStorms();
			Iterator<Storm> stormItr = countyStorms.iterator();
			while (stormItr.hasNext()) {
				Storm currStorm = stormItr.next();
				if (currStorm.getDate().equals(date))
					returnValue.add(currStorm);
			}	
		}
		if (returnValue.isEmpty())
			return null;
		else
			return returnValue;
	}
	
	/**
	 * Return the list of storms with name . 
	 * If no storms in the database have name , return null. 
	 * @param (String name) (Name of a Storm)
	 * @return (A list of all the storms with the given name)
	 */
	List<Storm> getStormsWithName(String name) {
		List<Storm> returnValue = new ArrayList<Storm>();
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			List<Storm> countyStorms = curr.getStorms();
			Iterator<Storm> stormItr = countyStorms.iterator();
			while (stormItr.hasNext()) {
				Storm currStorm=stormItr.next();
				if (currStorm.getName().equals(name))
					returnValue.add(currStorm);
			}	
		}
		if (returnValue.isEmpty())
			return null;
		else
			return returnValue;
	}
	
	/**
	 * Return the list of storms for the county. 
	 * If a county is not in the database, return null. 
	 * @param (String county) (Name of county that might have storms)
	 * @return (returns list of storms in county, null if none)
	 */
	List<Storm> getStormsFromCounty(String county) {
		List<Storm> returnValue = new ArrayList<Storm>();
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getName().equals(county)) {
				List<Storm> countyStorms = curr.getStorms();
				Iterator<Storm> stormItr = countyStorms.iterator();
				while (stormItr.hasNext())
					returnValue.add(stormItr.next());
			}

		}
		if (returnValue.isEmpty())
			return null;
		else
			return returnValue;
	}
	/**
	 * Get the percentage of storms in the database 
	 * that have a damage amount of 0. 
	 * @return (a double that has the percent of storms with no damage)
	 */
	double getPercentageOfStormsNoDamage() {
		float totalStorms = 0;
		float numStorms = getStormsWithDamageAmount(0).size();
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			List<Storm> countyStorms = curr.getStorms();
			Iterator<Storm> stormItr = countyStorms.iterator();
			while(stormItr.hasNext()) {
				totalStorms ++;
				stormItr.next();
			}
		}
		return (numStorms / totalStorms) * 100;
	}
	
	/**
	 * Return an Iterator over the County objects in the database.
	 * @return (An iterator over the County)
	 */ 
	Iterator<County> iterator() {
		return database.iterator();
	}
	
	/**
	 *  Print the names, dates, and damage amounts of the three storms
	 *  that have the largest damage amounts, 1 storm per line.
	 */
	void printThreeMostExpensiveStorms() {
		Storm[] topThree = new Storm[3];
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County currCounty = countyItr.next();
			List<Storm> countyStorms = currCounty.getStorms();
			Iterator<Storm> stormItr = countyStorms.iterator();
			while (stormItr.hasNext()) {
				Storm currStorm = stormItr.next();
				if (topThree[0] == null) {
					topThree[0] = currStorm;
				} else if (topThree[1] == null && currStorm.compareTo(topThree[0]) == 1)	{
					topThree[1] = topThree[0];
					topThree[0] = currStorm;
				} else if (topThree[1] == null) {
						topThree[1] = currStorm;
				} else if ((topThree[2] == null && currStorm.compareTo(topThree[0]) == 1) || (currStorm.compareTo(topThree[0]) == 1))	{
					topThree[2] = topThree[1];
					topThree[1] = topThree[0];
					topThree[0] = currStorm;
				} else if ((topThree[2] == null && currStorm.compareTo(topThree[1]) == 1) || (currStorm.compareTo(topThree[1]) == 1))	{
					topThree[2] = topThree[1];
					topThree[1] = currStorm;
				} else if (topThree[2] == null || currStorm.compareTo(topThree[2]) == 1)
					topThree[2] = currStorm;
			}
		}
		for (int i = 0; i < 3; i++)
			System.out.println(topThree[i].getName() + ", " + topThree[i].getDate() + ", " + topThree[i].getDamageAmount());
	}
	
	/**
	 *  Remove county from the database. 
	 *  If county is not in the database, return false; otherwise 
	 *  (i.e., the removal is successful) return true. 
	 * @param (String county) (Name of county to which to attempt to remove)
	 * @return (True if removed, false if not found)
	 */
	boolean removeCounty(String county) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if(curr.getName().equals(county)) {
				database.remove(curr);
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  Remove all storms with the name storm from the database. 
	 *  If there are no storms with the name storm in the database,
	 *  return false; otherwise (i.e., the removal is successful) return true.
	 * @param (String storm) (name of storm to remove from Database)
	 * @return (true if removed, false if not found in Database)
	 */
	boolean removeStormsWithName(String storm) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			if (curr.getStorms().contains(storm)) {
				curr.getStorms().remove(storm);
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * Remove storms with damage amount damage from the database, i.e., 
	 * remove ALL storms that have the damage amount damage from the database. 
	 * If storms with the damage amount damage are not in the database, return 
	 * false; otherwise (i.e., the removal is successful) return true. 
	 * @param (Integer damage) (Amount of Damage from any given storm)
	 * @return (true if removal successful, false if given value not found)
	 */
	boolean removeStormsWithDamageAmount(Integer damage) {
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			County curr = countyItr.next();
			List<Storm> countyStorms = curr.getStorms();
			Iterator<Storm> stormItr = countyStorms.iterator();
			while (stormItr.hasNext()) {
				Storm currStorm=stormItr.next();
				if (currStorm.getDamageAmount().equals(damage)) {					 
					curr.getStorms().remove(currStorm);
					return true;
				}
			}	
		}
		return false;
	}
	
	/**
	 *  	Return the number of counties in this database.
	 *
	 * @return (an integer the size of the database)
	 */
	int size() {
		int counter = 0;		
		Iterator<County> countyItr = database.iterator();
		while (countyItr.hasNext()) {
			counter++;
			countyItr.next();
		}		
		return counter;
	}
}
