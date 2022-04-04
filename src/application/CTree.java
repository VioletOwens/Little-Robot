package application;

public class CTree {
	protected Node root;
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
		if(!containsName(name)) {
		if (root == null) {
			root = newNode;
			tierZeroNodes = extendArray(tierZeroNodes, root);
		} else {
			while (true) {
				switch (newNode.tier) {
				case 0:
					tierZeroNodes = extendArray(tierZeroNodes, root);
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
	}
	public void displayNode(String name, int tier) {
		/*
		Boolean found = false;
		if (tier != -1) {
			switch (tier) {

			case 0:
				for (Node n0 : tierZeroNodes) {
					if (n0.name.equals(name)) {
						System.out.println(n0);
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Node " + name + " doesn't exists!");

				break;

			case 1:
				for (Node n1 : tierOneNodes) {
					if (n1.name.equals(name)) {
						System.out.println(n1);
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Node " + name + " doesn't exists!!");
				break;

			case 2:
				for (Node n2 : tierTwoNodes) {
					if (n2.name.equals(name)) {
						System.out.println(n2);
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Node " + name + " doesn't exists!!");
				break;

			case 3:
				for (Node n3 : tierThreeNodes) {
					if (n3.name.equals(name)) {
						System.out.println(n3);
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Node " + name + " doesn't exists!!");
				break;
			}

		}
		*/
		Boolean contains = false;
		if(tierZeroNodes != null) {
		for(Node n:tierZeroNodes) {
			if(n.name.equals(name)) {
				contains = true;
				System.out.println(n);
				break;
			}
		}
		}
		if(tierOneNodes != null&&!contains) {
		for(Node n:tierOneNodes) {
			if(n.name.equals(name)) {
				contains = true;
				System.out.println(n);
				break;
			}
		}
		}
		if(tierTwoNodes != null&&!contains) {
		for(Node n:tierTwoNodes) {
			if(n.name.equals(name)) {
				contains = true;
				System.out.println(n);
				break;
			}
		}
		}
		if(tierThreeNodes != null&&!contains) {
		for(Node n:tierThreeNodes) {
			if(n.name.equals(name)) {
				contains = true;
				System.out.println(n);
				break;
			}
		}
		}
		if(!contains) {
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
	
	public static void showTree() {
		if(tierZeroNodes != null) {
			System.out.print("Tier 0 nodes:");
		for(Node n:tierZeroNodes) {
			System.out.print(n + ", ");
		}
		System.out.println();
		}
		if(tierOneNodes != null) {
			System.out.print("Tier 1 nodes:");
		for(Node n:tierOneNodes) {
			System.out.print(n + ", ");
		}
		System.out.println();
		}
		if(tierTwoNodes != null) {
			System.out.print("Tier 2 nodes:");
		for(Node n:tierTwoNodes) {
			System.out.print(n + ", ");
		}
		System.out.println();
		}
		if(tierThreeNodes != null) {
			System.out.print("Tier 3 nodes:");
		for(Node n:tierThreeNodes) {
			System.out.print(n + ", ");
		}
		System.out.println();
		}
		
	}
	
	public static boolean containsName(String name) {
		Boolean contains = false;
		if(tierZeroNodes != null) {
		for(Node n:tierZeroNodes) {
			if(n.name.equals(name))contains = true;
		}
		}
		if(tierOneNodes != null&&!contains) {
		for(Node n:tierOneNodes) {
			if(n.name.equals(name))contains = true;
		}
		}
		if(tierTwoNodes != null&&!contains) {
		for(Node n:tierTwoNodes) {
			if(n.name.equals(name))contains = true;
		}
		}
		if(tierThreeNodes != null&&!contains) {
		for(Node n:tierThreeNodes) {
			if(n.name.equals(name))contains = true;
		}
		}
		return contains;
	}

	public static void main(String[] args) {
		CTree tree = new CTree();
		tree.addNode("root","N/A", 0);
		tree.addNode("group1","root", 1);
		tree.addNode("group2","root", 1);
		tree.addNode("command1","group2", 2);
		tree.addNode("command2","group1", 2);
		tree.displayNode("command2", 2);
		tree.addNode("Phrase 1","command1", 3);
		CTree.showTree();
	}

	class Node {
		String name;
		String parentName;
		int tier;

		// Node node1;
		// Node node2;
		//Node[] childNodeList; // i want to expand on this later for infinite node count

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
