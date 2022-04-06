package application;

import java.util.HashMap;
import java.util.Map;

public class Info extends Brain {
	public static String listNames = "";
	public static String itemNames = "";
	// protected static List[] listOfLists = new List[1];
	protected static Map<String, String[]> listOfMaps = new HashMap<>();
	protected static Map<String, String[]> listOfLists = new HashMap<>();

	/*
	 * SUMMARY/SYNOPSIS: purpose of this class is to consolidate the lists and
	 * information gathering at the top of brain.java (all the lists, groups, files,
	 * etc.) Needs to be fluid and easy to add/remove and access any lists made
	 * here. Lists also need to be expandable and as functional as a normal array if
	 * not more. INFO IN THE LISTS: every single list displays info with strings,
	 * some with multi-dimension arrays, others with single ones.
	 * 
	 * 
	 * 
	 * THIS FILE NEEDS TO BE ABLE TO: Every single list and group uses strings (this
	 * should help simplify, good to note) contain single and multi-dimensional
	 * arrays add/remove capabilities for arrays full accessibility for user in each
	 * array (only in package access)
	 * 
	 * Structure of file should be "name of item", content of item
	 * 
	 * is a collection of collection of items, some collected and stored in
	 * different ways, such as using createList method, directly putting the map
	 * into listOfMaps, or using CTier class to create, modify, and delete info in
	 * CTier.
	 * 
	 * Currently can collect lists and maps (not sure of maps), need to create CTier
	 * in this file
	 * 
	 */
	public void createList(String listName) {

		if (!listNames.contains(listName)) {
			String[] s = {};
			listOfLists.put(listName, s);
			// List newList = new List(listName);
			listNames = listNames.concat(listName);
		}
	}

	public void addItem(String listName, String item) {
		String[] strArr = listOfLists.get(listName);
		listOfLists.replace(listName, Brain.appendToArray(strArr, item));

	}

	public void removeItem(String listName, String item) {
		String[] strArr = listOfLists.get(listName);
		listOfLists.replace(listName, Brain.removeLastStrFromArr(strArr, item));

	}

	public void showList(String listName) {
		String[] arr = listOfLists.get(listName);
		String str = "";
		for (int x = 0; x < arr.length; x++) {
			str = str.concat("[" + x + "]" + arr[x] + ", ");
		}
		System.out.println("List '" + listName + "' contains: " +Brain.replaceLast(str, ", ", ""));
	}

	public void showListName() {
		String str = "";
		for (String s : listOfLists.keySet()) {
			str = str.concat(s + ", ");
		}
		System.out.println("List of listNames are: " + Brain.removeTrailingStr(str, ", "));
	}
	
	public List returnList(String str) {
		return new List(str);		
	}

	public static void main(String[] args) {
		Info newList = new Info();
		newList.createList("List1");
		newList.addItem("List1", "item1");
		newList.addItem("List1", "item2");
		newList.addItem("List1", "item3");
		newList.showList("List1");
		newList.showListName();
		newList.removeItem("List1", "item2");
		newList.showList("List1");

		CTier command = new CTier();
		command.addNode("root", "N/A", 0);
		command.addNode("group1", "root", 1);
		command.addNode("group2", "root", 1);
		command.addNode("command1", "group2", 2);
		command.addNode("command2", "group1", 2);
		command.displayNode("command2");
		command.addNode("Phrase 1", "command1", 3);
		command.showTree();
	}
}

class List {
	String listName;
	String[] content;

	List(String listName) {
		this.listName = listName;
	}

	@Override
	public String toString() {
		String s = listName + "'s content is: ";
		for (int x = 0; x < content.length; x++) {
			s = s.concat("[" + x + "]: " + content[x] + ", ");
		}
		return Brain.replaceLast(s, ", ", "");
	}
}

class CTier {
	protected static Node[] tierZeroNodes;
	protected static Node[] tierOneNodes;
	protected static Node[] tierTwoNodes;
	protected static Node[] tierThreeNodes;

	protected void addNode(String name, String parentName, int tier) {
		Node newNode = new Node(name, parentName, tier);
		// newNode.childNodeList = new Node[1];
		/*
		 * when adding a node, want to first check to see if tiers are close to estimate
		 * where parent is, if tier difference is 1, search through width-wise for
		 * parent with same name as parentName, and add it to next null in it's list if
		 * difference is more than 1, try searching for parent.name equals parentName
		 * 
		 */
		if (!containsName(name)) {
			while (true) {
				switch (newNode.tier) {
				case 0:
					tierZeroNodes = extendArray(tierZeroNodes, newNode);
					break;
				case 1:
					tierOneNodes = extendArray(tierOneNodes, newNode);
					break;
				case 2:
					tierTwoNodes = extendArray(tierTwoNodes, newNode);
					break;
				case 3:
					tierThreeNodes = extendArray(tierThreeNodes, newNode);
					break;
				}
				break;

			}

		}

	}

	public Node getNode(String name, int tier) {
		Node newNode;
		Boolean contains = false;
		if (tierZeroNodes != null) {
			for (Node n : tierZeroNodes) {
				if (n.name.equals(name)) {
					contains = true;
					newNode = n;
					return newNode;
				}
			}
		}
		if (tierOneNodes != null && !contains) {
			for (Node n : tierOneNodes) {
				if (n.name.equals(name)) {
					contains = true;
					newNode = n;
					return newNode;
				}
			}
		}
		if (tierTwoNodes != null && !contains) {
			for (Node n : tierTwoNodes) {
				if (n.name.equals(name)) {
					contains = true;
					newNode = n;
					return newNode;
				}
			}
		}
		if (tierThreeNodes != null && !contains) {
			for (Node n : tierThreeNodes) {
				if (n.name.equals(name)) {
					contains = true;
					newNode = n;
					return newNode;
				}
			}
		}
		if (!contains) {
			System.out.println("Cannot get Node " + name + ", does not exist!");
		}
		return null;
	}

	public void displayNode(String name) {
		Boolean contains = false;
		if (tierZeroNodes != null) {
			for (Node n : tierZeroNodes) {
				if (n.name.equals(name)) {
					contains = true;
					System.out.println(n);
					break;
				}
			}
		}
		if (tierOneNodes != null && !contains) {
			for (Node n : tierOneNodes) {
				if (n.name.equals(name)) {
					contains = true;
					System.out.println(n);
					break;
				}
			}
		}
		if (tierTwoNodes != null && !contains) {
			for (Node n : tierTwoNodes) {
				if (n.name.equals(name)) {
					contains = true;
					System.out.println(n);
					break;
				}
			}
		}
		if (tierThreeNodes != null && !contains) {
			for (Node n : tierThreeNodes) {
				if (n.name.equals(name)) {
					contains = true;
					System.out.println(n);
					break;
				}
			}
		}
		if (!contains) {
			System.out.println("Node " + name + " does not exist!");
		}
	}

	public Node[] extendArray(Node[] nodeList, Node node) {
		Node[] newNodeList;
		if (nodeList != null) {
			newNodeList = new Node[nodeList.length + 1];
			for (int x = 0; x < nodeList.length; x++) {
				newNodeList[x] = nodeList[x];
			}
			newNodeList[newNodeList.length - 1] = node;
		} else {
			newNodeList = new Node[1];
			newNodeList[0] = node;
		}
		return newNodeList;
	}

	public void showTree() {
		if (tierZeroNodes != null) {
			System.out.print("Tier 0 nodes:");
			for (Node n : tierZeroNodes) {
				System.out.print(n + ", ");
			}
			System.out.println();
		}
		if (tierOneNodes != null) {
			System.out.print("Tier 1 nodes:");
			for (Node n : tierOneNodes) {
				System.out.print(n + ", ");
			}
			System.out.println();
		}
		if (tierTwoNodes != null) {
			System.out.print("Tier 2 nodes:");
			for (Node n : tierTwoNodes) {
				System.out.print(n + ", ");
			}
			System.out.println();
		}
		if (tierThreeNodes != null) {
			System.out.print("Tier 3 nodes:");
			for (Node n : tierThreeNodes) {
				System.out.print(n + ", ");
			}
			System.out.println();
		}

	}

	public static boolean containsName(String name) {
		Boolean contains = false;
		if (tierZeroNodes != null) {
			for (Node n : tierZeroNodes) {
				if (n.name.equals(name))
					contains = true;
			}
		}
		if (tierOneNodes != null && !contains) {
			for (Node n : tierOneNodes) {
				if (n.name.equals(name))
					contains = true;
			}
		}
		if (tierTwoNodes != null && !contains) {
			for (Node n : tierTwoNodes) {
				if (n.name.equals(name))
					contains = true;
			}
		}
		if (tierThreeNodes != null && !contains) {
			for (Node n : tierThreeNodes) {
				if (n.name.equals(name))
					contains = true;
			}
		}
		return contains;
	}

	class Node {
		String name;
		String parentName;
		int tier;

		// Node node1;
		// Node node2;
		// Node[] childNodeList; // i want to expand on this later for infinite node
		// count

		/*
		 * tier list consists of 4 groups, group 0 is the root node group 1 is the group
		 * nodes group 2 is the command nodes group 3 is the command phrase nodes
		 * 
		 */

		Node(String name, String parentName, int tier) {
			this.name = name;
			this.parentName = parentName;
			this.tier = tier;

		}

		public String toString() {
			return name + " is at tier " + tier + " with parent " + parentName;

		}

	}

}
