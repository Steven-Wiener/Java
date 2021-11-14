package P4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

////////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  CompanyHierarchyMain.java
//File:             CompanyHierarchy.java
//Semester:         CS367 Spring 2014
//
//Author:           Steven Wiener
//Email:            SWiener@wisc.edu
//CS Login:         wiener
//Lecturer's Name:  Jim Skrentny
//
//PAIR PROGRAMMERS COMPLETE THIS SECTION
//Pair Partner:     Andrew Minneci
//Email:            minneci@wisc.edu
//CS Login:         minneci
//Lecturer's Name:  Jim Skrentny
//
//STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
//Credits:          N/A
////////////////////////////80 columns wide ///////////////////////////////////

/**
 * CompanyHierarchy class stores methods that change the set-up of the given
 * tree node, by either adding, removing, switching, or finding a given node.
 *
 * @author Steven Wiener and Andrew Minneci
 */
public class CompanyHierarchy{
	//An empty TreeNode
	TreeNode tree;
	int numEmployees;
	
	
	/**
	 *  CompanyHierarchy is the contructor that creates a null TreeNode. 
	 *
	 * <p>Params: none
	 * 
	 * <p>Returns: none
	 */
	public CompanyHierarchy(){
		tree = null;
	}

	/**
	 *  getCEO finds and returns the head node of the tree, if the tree is not 
	 *  	null.
	 *
	 * <p>Params: none
	 * 
	 * <p>Returns: The head node of the tree
	 */
	public String getCEO(){
		if(tree.equals(null)) return null;
		return(tree.getEmployee().getName());
	}

	/**
	 *  getNumEmployees counts the total number of nodes in the company tree 
	 *
	 * <p>Params: none
	 * 
	 * <p>Returns: The number of employees
	 */
	public int getNumEmployees() {
		return numEmployees;
	}

	/**
	 *  getMaxLevels finds and returns the biggest depth of the tree, 
	 *
	 * <p>Params: none
	 * 
	 * <p>Returns: The highest depth of the tree
	 */
	public int getMaxLevels() { 
		return height(tree);
	}

	/**
	 *  If possible, getEmployee takes the given id and name and searches the 
	 *  	Tree to see if there is a match
	 *
	 * <p>Params: int id, where it is the given id of the "old" employee 
	 * <p>Params: String name, of the "old" employee
	 * 
	 * <p>Returns: An Employee that was requested
	 */
	public Employee getEmployee(int id, String name) 
			throws CompanyHierarchyException {
		return getEmploy(tree, id, name);
	}

	/**
	 *  If possible, addEmployee adds the given employee, and sets the 
	 *  	supervisor of said employee to the given  supervisor, if the 
	 *  	given supervisor is valid
	 *
	 * <p>Params: int supervisorId, where it is the given id of the supervisor 
	 * <p>Params: String supervisorName, name of the supervisor
	 * <p>Params: Employee employee, the employee that is wanting to be added
	 * 
	 * <p>Returns: A boolean if the employee was successfully added
	 */
	public boolean addEmployee(Employee employee, int supervisorId, 
			String supervisorName) throws CompanyHierarchyException {
		if(tree == null && supervisorName == null) {
			tree = new TreeNode(employee, null);
			numEmployees++;
			return true;
		}

		try	{
			if (!contain(tree,supervisorId, supervisorName)) throw new CompanyHierarchyException("Cannot add employee as supervisor was not found!");
		}
		catch(CompanyHierarchyException e) {
			if (e.getMessage() != "Cannot add employee as supervisor was not found!") throw new CompanyHierarchyException("Incorrect supervisor name for id!");
			throw new CompanyHierarchyException("Cannot add employee as supervisor was not found!");
		}

		try	{
			if (contain(tree,employee.getId(), employee.getName())) return false;
		}
		catch(CompanyHierarchyException e) {
			throw new CompanyHierarchyException("Id already used!");
		}

		TreeNode Sup = getTreeNode(tree, supervisorId, supervisorName);
		TreeNode temp = new TreeNode(employee, Sup);
		Sup.addWorker(temp);
		numEmployees++;
		return true;
	}

	/**public boolean contains(int id, String name) throws 
	CompanyHierarchyException{
		if (getNumEmployees() == 0) return false;
		if (getNumEmployees() == 1 && name.equals(getCEO()) && id == 1)	
			return true;

		Iterator<TreeNode> itr = tree.getWorkers().iterator();

		while (itr.hasNext()){
			Employee person = itr.next().getEmployee();
			if (person.getId() == id && person.getName() == name) return true;
			if (person.getId() == id)	throw new CompanyHierarchyException(
					"Incorrect employee name for id!");
		}
		return false;
	}
	*/

	/**
	 *  If possible, removeEmployee removes the given employee, and sets the 
	 *  	workers of said employee to the given employee's supervisor, and 
	 *  	vise versa.
	 *
	 * <p>Params: int id, where it is the given id of the "old" employee 
	 * <p>Params: String name, of the "old" employee
	 * 
	 * <p>Returns: A boolean if the employee was successfully removed
	 */
	public boolean removeEmployee(int id, String name) throws 
	CompanyHierarchyException {
		if(!contain(tree,id, name)) throw new CompanyHierarchyException(
				"Employee not found!");
		TreeNode emp = this.getTreeNode(tree, id, name);
		if(emp.getEmployee().getName().equals(this.getCEO()))
			throw new CompanyHierarchyException(
					"Cannot remove CEO of the company!");
		else{
			TreeNode Soup = emp.getSupervisor();
			List<TreeNode> worker = emp.getWorkers();
			Iterator<TreeNode> itr = worker.iterator();
			while(itr.hasNext()){
				TreeNode workers =itr.next();
				Soup.addWorker(workers);
				workers.updateSupervisor(Soup);
			}
			return true;
		}
	} 

	/**
	 *  If possible, replaceEmployee replaces the validated current employee
	 *  	with the new one
	 *
	 * <p>Params: int id, where it is the given id of the "old" employee 
	 * <p>Params: String name, of the "old" employee
	 * <p>Params: Employee newEmployee, the file for the new Employee
	 * 
	 * <p>Returns: A boolean if the employee was successfully replaced
	 */
	public boolean replaceEmployee(int id, String name, Employee newEmployee) 
			throws CompanyHierarchyException {
		if (!contain(tree,id, name)) throw new CompanyHierarchyException(
				"Employee not found!");
		try	{
			if(contain(tree,newEmployee.getId(), newEmployee.getName()))
				throw new CompanyHierarchyException(
						"Replacing employee already exists on the Company Tree!");
		}
		catch(CompanyHierarchyException e)	{
			throw new CompanyHierarchyException("Id already used");
		}

		if (contain(tree,id, name) && !getEmployee(id, name).getTitle().equals(
				newEmployee.getTitle()))
			throw new CompanyHierarchyException(
					"Replacement title does not match existing title!");

		if (contain(tree,id, name) && getEmployee(id, name).getTitle().equals(
				newEmployee.getTitle()))	{
			TreeNode oldEmp = getTreeNode(tree, id, name);
			oldEmp.updateEmployee(newEmployee);
			return true;
		}
		return false;
	}

	/**
	 *  getEmployeeWithTitle returns the employees with the given title
	 *
	 * <p>Params: String Title, the name of the title to be looked up
	 * 
	 * <p>Returns: A List of all the employees with the given title
	 */
	public List<Employee> getEmployeeWithTitle(String title) {
		ArrayList<Employee> total = new ArrayList<Employee>();
		return getEmployeeTitle(tree, title, total);
	}

	/**
	 *  If possible, getEmployeeInJoiningDateRange returns a list of employees
	 *  	whose start date is within the given time range
	 *
	 * <p>Params: String startDate, the MM/dd/yyyy of the start time 
	 * <p>Params: String endDate, the MM/dd/yyyy of the end time
	 * 
	 * <p>Returns: A list of all the employees in the given date range
	 */
	public List<Employee> getEmployeeInJoiningDateRange(String startDate, 
			String endDate) throws CompanyHierarchyException {
		try{
			List<Employee> dates = new ArrayList<Employee>();
			SimpleDateFormat idea = new SimpleDateFormat("MM/dd/yyyy");
			Date start = idea.parse(startDate);
			Date end = idea.parse(endDate);
			
			dates = getEmployeeInJoiningDateRange(start, end, tree, dates,
					idea);
			if(dates.size() == 0) throw new CompanyHierarchyException(
					"Employee not found!");
			return dates;
		}
		catch(ParseException e){
			throw new CompanyHierarchyException("Date parsing failed!");
		}
	}

	/**
	 *  getSuperVisorChain finds all of the supervisors to the 
	 *  	given Employee
	 *
	 * <p>Params: int id, where it is the given id of the employee 
	 * <p>Params: String name, name of the employee
	 * 
	 * <p>Returns: A list of the Supervisor Chain, in reverse order
	 */
	public List<Employee> getSupervisorChain(int id, String name) throws
	CompanyHierarchyException {
		if (!contain(tree,id, name)) throw new CompanyHierarchyException(
				"Employee not found!");
		if (getTreeNode(tree, id, name).getSupervisor() == null) throw new CompanyHierarchyException(
				"No Supervisor Chain found for that employee!");
		
		List<Employee> chain = new ArrayList<Employee>();
		
		TreeNode sup = getTreeNode(tree, id, name).getSupervisor();
		Employee supervisor = sup.getEmployee();
		
		while (sup != null && sup.getEmployee() != null)	{
			supervisor = sup.getEmployee();
			chain.add(supervisor);
			sup = getTreeNode(tree, sup.getEmployee().getId(), sup.getEmployee().getName()).getSupervisor();
		}
		
		return chain;	
	}

	/**
	 *  getCoWorkers finds all of the other employees under the same supervisor
	 *
	 * <p>Params: int id, where it is the given id of the employee 
	 * <p>Params: String name, of the employee
	 * 
	 * <p>Returns: A List of the other workers to the supervisor
	 */
	public List<Employee> getCoWorkers(int id, String name) throws
	CompanyHierarchyException{
		if(!contain(tree,id, name)) throw new CompanyHierarchyException(
				"Employee not found!");

		TreeNode emp = this.getTreeNode(tree, id, name);
		TreeNode soup = emp.getSupervisor();
		List<TreeNode> worker = soup.getWorkers();
		worker.remove(emp);
		List<Employee> coworker = new ArrayList<Employee>();
		while(worker.size() >0){
			coworker.add(worker.remove(0).getEmployee());
		}
		if (coworker.isEmpty()) throw new CompanyHierarchyException(
				"The employee has no coworkers.");
		return coworker;
	}

	/**
	 *  A helper method to the getMaxLevels
	 *
	 * <p>Params: the Company tree, as a TreeNode
	 * 
	 * <p>Returns: An integer representing the height of the tree
	 */
	private int height(TreeNode n){
		if(n.equals(null)) return 0;
		if(n.getWorkers().isEmpty()) return 1;
		int maxHeight = 0;
		Iterator <TreeNode> itr=n.getWorkers().iterator();
		while(itr.hasNext()){
			int childHgt=height(itr.next());
			if(childHgt>maxHeight) maxHeight=childHgt;
		}
		return 1 + maxHeight;
	}

	/**
	 *  If possible, getEmployeeTitle returns a list of employees
	 *  	with the specified title
	 *
	 * <p>Params: TreeNode n, the given tree that can be searched for the title
	 * <p>Params: String title, the title of interest to be found
	 * <p>Params: ArrayList<Employee> curr, the current employee to find 
	 * 		it's title
	 * 
	 * <p>Returns: A List of all the employees with the given title
	 */
	private List<Employee> getEmployeeTitle(TreeNode n, String title, 
			ArrayList<Employee> curr){
		Iterator <TreeNode> itr=n.getWorkers().iterator();
		while(itr.hasNext()){
			Employee person = itr.next().getEmployee(); 
			String name = person.getTitle();
			if(name.equals(title)) curr.add(person);
			getEmployeeTitle(getTreeNode(tree, person.getId(), person.getName()), title, curr);
		}
		return curr;
	}

	/**
	 *  getEmploy searches through the given tree, and if possible finds the 
	 *  	employee with the given id and name. it throws an error if the id
	 *  	and name don't match
	 *
	 * <p>Params: TreeNode n, the given tree to be continued to be traversed
	 * <p>Params: int id, where it is the given id of the employee 
	 * <p>Params: String name, the name of the employee
	 * 
	 * <p>Returns: A boolean if the employee was successfully replaced
	 */
	private Employee getEmploy(TreeNode n, int id, String name) throws 
	CompanyHierarchyException{
		Iterator <TreeNode> itr=tree.getWorkers().iterator();
		while(itr.hasNext()){
			Employee person = itr.next().getEmployee(); 
			String namen = person.getName();
			int ID = person.getId();
			if(ID == id && !namen.equals(name)) 
				throw new CompanyHierarchyException(
						"Incorrect employee name for id!");
			if(!namen.equals(name) && ID == id) return(person);
			Employee newEmployee = getEmploy(getTreeNode(tree, person.getId(), person.getName()), id, name);
			if (!newEmployee.equals(null)) return newEmployee;
		}
		return null;
	}
	
	/**
	 *  getSupChain finds and returns all of the supervisors of the employee
	 *
	 * <p>Params: TreeNode n, the tree to search for the given employee/sup
	 * <p>Params: int id, where it is the given id of the employee 
	 * <p>Params: String name, name of the employee
	 * <p>Params: List<Employee> curr, a pointer for the current employee
	 * 
	 * <p>Returns: A list of all the supervisors to the given employee
	 */
	
	/**
	private List<Employee> getSupChain(TreeNode n, int id, String name, 
			List<Employee> curr) throws CompanyHierarchyException{
		Iterator <TreeNode> itr=tree.getWorkers().iterator();
		while(itr.hasNext()){
			TreeNode person = itr.next(); 
			String namen = person.getEmployee().getName();
			int ID = person.getEmployee().getId();
			if(ID == id && !namen.equals(name)) 
				throw new CompanyHierarchyException(
						"Incorrect employee name for id!");
			if(namen.equals(name) && ID == id && !person.getSupervisor().equals(null)){
				curr.add(person.getSupervisor().getEmployee());
				return getSupChain(person.getSupervisor(), id, name, curr);
			}
			return getSupChain(person, id, name, curr);
		}
		return curr;
	}
	*/

	/**
	 *  If possible, contain searches for a match between the given id and name
	 *
	 * <p>Params: TreeNode n, the given tree to search for the given employee
	 * <p>Params: int id, where it is the given id of the employee 
	 * <p>Params: String name, name of the employee
	 * 
	 * <p>Returns: A boolean if the employee was successfully found
	 */
	private boolean contain(TreeNode n, int id, String name) 
			throws CompanyHierarchyException{
		if(n == null) return false;
		if (n.getEmployee().getName().equals(name) && n.getEmployee().getId() == id) return true;
		if (n.getWorkers().equals(null)) return false;
		
		Iterator <TreeNode> itr=n.getWorkers().iterator();

		while(itr.hasNext()){
			Employee person = itr.next().getEmployee(); 
			String namen = person.getName();
			int ID = person.getId();
			if(ID == id && !namen.equals(name)) 
				throw new CompanyHierarchyException("Incorrect employee name for id!");
			if(namen.equals(name) && ID == id) return true;
			if (contain(getTreeNode(tree, person.getId(), person.getName()), id, name)) return true;
		}
		return false;
	}
	 
	/**
	 *  If possible, getTreeNode finds the given id and name, and tries to match
	 *  	it to an employee instance, and returns it
	 *
	 * <p>Params: TreeNode n, the given TreeNode to continue to search for 
	 * 					the employee
	 * <p>Params: int id, where it is the given id of the employee 
	 * <p>Params: String name, the name of the employee
	 * 
	 * <p>Returns: A TreeNode reference of the found employee
	 */
	private TreeNode getTreeNode(TreeNode n, int id, String name){
		if(n.getEmployee().getId() == id && n.getEmployee().getName().equals(name))	return n;
		if(!n.getWorkers().isEmpty()){
			List<TreeNode> worker = n.getWorkers();
			Iterator<TreeNode> itr = worker.iterator();
			while(itr.hasNext()){
				TreeNode next = itr.next();
				TreeNode got = getTreeNode(next, id, name);
				if (got != null) return got;
			}
		}
		return null;
	}
	
	/**
	 *  If possible, getEmployeeInJoiningDateRange finds all the employees that
	 *  	started within the date range, and returns them
	 *
	 * <p>Params: String startDate, the MM/dd/yyyy of the start time 
	 * <p>Params: String endDate, the MM/dd/yyyy of the end time
	 * <p>Params: TreeNode n, the current Node the pointer is set to
	 * <p>Params: List<Employee> date, the current list of employees that are 
	 * 					within the date range
	 * <p>Params: SimpleDateFormat idea, the logical instance of the date range
	 * 
	 * <p>Returns: A List of employees within the given date range
	 */
	private List<Employee> getEmployeeInJoiningDateRange(Date start, Date end,
			TreeNode n, List<Employee> date, SimpleDateFormat idea) 
					throws ParseException{
		Date curr = idea.parse(n.getEmployee().getDateOfJoining());
		if((curr.after(start)&&curr.before(end)) || curr.equals(start) 
				||curr.equals(end)) date.add(n.getEmployee());
		if(!n.getWorkers().isEmpty()){
			List<TreeNode> worker = n.getWorkers();
			Iterator<TreeNode> itr = worker.iterator();
			while (itr.hasNext())	{
				getEmployeeInJoiningDateRange(start, end, itr.next(), date, idea);
			}
		}
		return date;
	}
}