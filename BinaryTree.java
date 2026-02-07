import java.util.*;

/*
 * A BinaryTree class with an inner Node class. Contains lots of practice 
 * with recursive methods.
 * Many (but not all) of the methods in this class will require additional
 * recursive helper methods. The helper method would typically
 * have a Node as its only parameter or as one of its parameters.
 * 
 * Amelia Gelb, CSE 274 Section C
 */

// Note that the following tags appear in the code:
//@formatter:off
//@formatter:on

// These tags tell the Eclipse formatter not to reformat these blocks. That
// helps make sure that the sample binary trees that appear in the comments
// will maintain their appearance.


/////////////////////////////////////////////////////////////////////////
// Unless noted otherwise, each method should be solved using recursion.
/////////////////////////////////////////////////////////////////////////

public class BinaryTree {

	public Node root; // LEAVE THIS PUBLIC TO AID WITH INSTRUCTOR TESTING

	// constructs an empty binary tree
	public BinaryTree() {
		root = null;
	}

	/*
	 * Feel free to modify this method however you want (or even write more methods
	 * like it) that will help you manually build trees for testing.
	 */
	public static BinaryTree myTree() {
		Node myRoot = new Node(4);
		myRoot.right = new Node(8);
		myRoot.right.left = new Node(12);

		BinaryTree result = new BinaryTree();
		result.root = myRoot;
		return result;
	}

	/*
	 * Returns the sum of the data in the nodes
	 */
	public int sum() {
		return sum(root);
	}

	private int sum(Node start) {
		if (start == null) return 0;
		
		return sum(start.left) + sum(start.right) + start.data;
	}
	
	/*
	 * Returns a count of the number of nodes in the tree
	 */
	public int nodeCount() {
		return nodeCount(root);
	}
	
	private int nodeCount(Node start) {
		int count = 0;
		
		if (start.left != null) count += nodeCount(start.left);
		if (start.right != null) count += nodeCount(start.right);
		
		return count + 1;
	}

	/*
	 * Returns the height of this binary tree. If the tree is empty, return -1
	 */
	public int height() {
		return height(root);
	}
	
	private int height(Node start) {
		if (start == null) return -1;
		
		int leftCount = height(start.left);
		int rightCount = height(start.right);
		
		int max;
		if (leftCount > rightCount) {
			max = leftCount;
		} else {
			max = rightCount;
		}
		
		return max + 1;
	}

	// @formatter:off
	/* We could say that a binary tree has "levels" with the root
	 * being at level 0, the root's children are at level 1, the
	 * root's grandchildren are at level 2, and so on. In this sample
	 * tree with height 2:
	 * there is 1 node at level 0
	 * there are 2 nodes at level 1
	 * there are 2 nodes at level 2
	 * 
	 *         5
	 *        / \
	 *       /   \
	 *      10   20
	 *     /  \    
	 *    30  40   
	 *  
	 *  Return the number of nodes at a level in a tree.
	 *  If the specified level is not a valid level for the tree (either
	 *  too big or too small), return 0.
	 */
	// @formatter:on
	public int nodesAtLevel(int level) {
		return nodesAtLevel(root, level, 0);
	}
	
	private int nodesAtLevel(Node start, int targetLevel, int current) {
		if (start == null) return 0;
		if (current == targetLevel) return 1;
		
		if (current < targetLevel) {
			int countedLeft = nodesAtLevel(start.left, targetLevel, current + 1);
			int countedRight = nodesAtLevel(start.right, targetLevel, current + 1);
			return countedLeft + countedRight;
		}
		
		return 0;
	}

	/*
	 * Returns max value in the tree. If the tree is empty, throws an
	 * IllegalStateException()
	 */
	public int max() { // client method
		if (root == null) throw new IllegalStateException("Tree is empty");
		
		return max(root);
	}
	
	private int max(Node start) {
		// find the max of the left subtree, right subtree and the node itself
		if (start == null) return Integer.MIN_VALUE;
		
		int maxLeft = max(start.left);
		int maxRight = max(start.right);
		
		// return the largest of those 2 and start.data
		return Math.max(start.data, Math.max(maxLeft, maxRight));
	}

	/*
	 * Returns a count of how many times the specified value appears in the binary
	 * tree
	 */
	public int countValue(int n) {
		return countValue(root, n);
	}

	private int countValue(Node start, int target) {
		if (start == null) return 0;
		
		int count = 0;
		
		if (start.data == target) {
			count += 1;
		}
		
		count += countValue(start.left, target);
		count += countValue(start.right, target);
		
		return count;
	}
	
	/*
	 * Returns true if this is a full binary tree, and false otherwise
	 */
	public boolean isFull() {
		int bottomLevel = height(root);
		return isFull(root, 0, bottomLevel);
	}
	
	private boolean isFull(Node start, int currLevel, int bottomLevel) {
        if (start == null) return true;
        
        if (start.left == null && start.right == null) {
        	if (bottomLevel == 0) {
        		return true;
        	}
        	
        	return currLevel == bottomLevel;
        }
        
        if (start.left == null || start.right == null) {
        	return false;
        }
        
        return isFull(start.left, currLevel + 1, bottomLevel) &&
        		isFull(start.right, currLevel + 1, bottomLevel);
   }

	/*
	 * Returns true if this is a balanced binary tree, and false otherwise
	 */ boolean isBalanced() {
		return isBalanced(root);
	}
	 
	private boolean isBalanced(Node start) {
		if (start == null) return true;
		
		int leftHeight = height(start.left);
		int rightHeight = height(start.right);
		
		if ((leftHeight - rightHeight) > 1) return false;
		
		return isBalanced(start.left) && isBalanced(start.right);
	}

	/*
	 * returns the number of leaves in a tree
	 */
	public int leafCount() {
		return leafCount(root);
	}

	private int leafCount(Node start) {
		if (start == null) return 0;
		
		if (start.left == null && start.right == null) return 1; // start is leaf
		
		return leafCount(start.left) + leafCount(start.right);
	}
	
	/*
	 * Returns the leftmost value of this tree. That is, navigate from the root
	 * following only left children until you can't travel left any farther, and
	 * return the value at that node. Throw an IllegalStateException if the tree is
	 * empty
	 */
	public int leftmostValue() {
	    /////////////////// Recursion optional
	    if (root == null) throw new IllegalStateException();
	    
	    int currHeight = 0;
	    Node currNode = root;
	    
	    while (currNode.left != null && currHeight < this.height()) {
	    	currNode = currNode.left;
	    	currHeight++;
	    }
	    
		return currNode.data;
	}
	

	/*
	 * Returns a set of all leaf values.
	 */
	public Set<Integer> allLeaves() {
		Set<Integer> result = new TreeSet<>();
		
		// send set to recursive call, that way recursive call can put things
		// in the set
		allLeaves(root, result);
		
		return result;
	}
	
	public void allLeaves(Node start, Set<Integer> setSoFar) {
		if (start == null) return;
		else if (start.left == null && start.right == null) { // start is a leaf
			setSoFar.add(start.data);
			return;
		}
		
		// call allLeaves on the left child and the right child
		allLeaves(start.left, setSoFar);
		allLeaves(start.right, setSoFar);
	}

	/*
	 * Returns a set of all sums that can be obtained by starting at the root and
	 * traveling to each leaf.
	 */
	public Set<Integer> allSums() {
		Set<Integer> result = new TreeSet<>();
		
		allSums(root, result, 0);
		
		return result;
	}
	
	private void allSums(Node start, Set<Integer> setSoFar, int runningTotal) {
		if (start == null) return;
		
		runningTotal += start.data;
		
		if (start.left == null && start.right == null) { // reached a leaf
			setSoFar.add(start.data + runningTotal);
		}
		
		allSums(start.left, setSoFar, runningTotal);
		allSums(start.right, setSoFar, runningTotal);
	}

	// @formatter:off
	/*
	 * Returns a set of all possible paths from the root to the leaves. Similar
	 * to allSums, but rather than computing sums along the way, builds a string
	 * along the way, space-separated. 
	 * Trim the strings that get added to the sets. So, for example, in this tree:
	 *         5
	 *        / \
	 *       /   \
	 *      10   20
	 *     /  \    \
	 *    30  40   50
	 *  
	 *  allPaths() would return this set (or some permutation of this set):
	 *   {"5 10 30", "5 10 40", "5 20 50"}
	 *  TIP: allPaths() is a lot like allSums(). The difference
	 *  is that allSums is adding numbers to a total, and this
	 *  is adding numbers to a string.
	 */
	// @formatter:on
	public Set<String> allPaths() {
		Set<String> result = new TreeSet<>();
		
		allPaths(root, result, "");
		
		return result;
	}
	
	private void allPaths(Node start, Set<String> setSoFar, String current) {
		if (start == null) return;
		
		current += start.data;
		
		if (start.left == null && start.right == null) {
			setSoFar.add(current.trim());
			return;
		}
		
		if (start.left != null) {
			allPaths(start.left, setSoFar, current + " ");
		}
		
		if (start.right != null) {
			allPaths(start.right, setSoFar, current + " ");
		}
	}

	// @formatter:off
	/*
	 * Returns a set of all possible sums that can be obtained
	 * in the tree by starting at the root and traveling any path.
	 * This is similar to allSums() (in fact, it will include all the sums
	 * that are returned by allSums(), but it also includes all sums that can
	 * be obtained by stopping part-way.  So, for example, in this tree:
	 *         5
	 *        / \
	 *       /   \
	 *      10   20
	 *     /  \    \
	 *    30  40   50
	 *  
	 *  allSumsIncludingPartial() would return {5, 15, 25, 45, 55, 75}
	 *  whereas allSums() would only return {45, 55, 75}
	 */
	// @formatter:on
	public Set<Integer> allSumsIncludingPartial() {
		Set<Integer> result = new TreeSet<>();
		
		allSumsIncludingPartial(root, result, 0);
		
		return result;
	}
	
	private void allSumsIncludingPartial(Node start, Set<Integer> setSoFar, int currTotal) {
		if (start == null) return;
		
		currTotal += start.data;
		
		setSoFar.add(currTotal);
		
		if (start.left != null) {
			setSoFar.add(currTotal + start.left.data);
			
		}
		
		if (start.right != null) {
			setSoFar.add(currTotal + start.right.data);
		}
		
		allSumsIncludingPartial(start.left, setSoFar, currTotal);
		allSumsIncludingPartial(start.right, setSoFar, currTotal);
	}

	// @formatter:off
	/*
	 * Returns the sum obtained using a "greedy algorithm" by starting at the root
	 * and going left or right depending on which node has
	 * the larger value (in case of a tie, go left). 
	 * If the tree is empty, throw an IllegalStateException();
	 * 
	 * So, for example, in this tree:
	 *         5
	 *        / \
	 *       /   \
	 *      10   20
	 *     / \   / \
	 *    30 90 70 50
	 *  
	 *  greedySum() would return 95 (5 + 20 + 70).
	 */
	// @formatter:on
	public int greedySum() {
	    /////////////////// Recursion optional
		if (root == null)
			throw new IllegalStateException("Empty tree");

		Node curr = root;
		int result = curr.data;
		
		while (curr.left != null || curr.right != null) {
			if (curr.left != null && curr.right != null) { // apply "greedy" rule if both exist
				if (curr.left.data >= curr.right.data) {
					curr = curr.left;
				} else {
					curr = curr.right;
				}
			} else if (curr.left != null) { // if only left exists
				curr = curr.left;
			} else { // if only right exists
				curr = curr.right;
			}
			
			result += curr.data; // add to result
		}
		
		return result;
	}

	/*
	 * We will say that a node is an "only child node" if it has a parent node and
	 * it is the only child of that parent node. Return a count of how many nodes in
	 * the binary tree are "only child nodes". Note that by this definition, the
	 * root node is always an only child.
	 */
	public int onlyChildCount() {
		return onlyChildCount(root, null);
	}
	
	private int onlyChildCount(Node start, Node before) {
		if (start == null) {
            return 0;
        }
		
		int count = 0;
        
        if (before == null) {
        	count++;
        } else {
        	boolean left = before.left != null;
        	boolean right = before.right != null;
        	
        	if ((!left && right) || (left && !right)) {
        		count++;
        	}
        }
        
        count += onlyChildCount(start.left, start);
        count += onlyChildCount(start.right, start);
        
        return count;
	}
	
	// In-order traversal that PRINTS the nodes as they are visited,
	// space-separated
	public void inOrderTraversal() {
		inOrderTraversal(root);
		System.out.println();
	}
	
	// left, start, right
	private void inOrderTraversal(Node start) {
		if (start == null) return;
		
		inOrderTraversal(start.left);
		System.out.print(start.data + " ");
		inOrderTraversal(start.right);
	}

	// returns the the data in the tree as visited by the in-order
	// traversal. The data should be space-separated and trimmed.
	// if the tree is empty, return an empty string.
	public String toString() {
		return toString(root);
	}
	
	private String toString(Node start) {
		String result = "";
		
		if (start == null) return result;
		
		result += toString(start.left) + " ";
		
		result += start.data + " ";
		
		result += toString(start.right) + " ";
		
		return result.trim();
	}

	/*
	 * Returns a list of all the node data in the tree, in the order that they would
	 * be visited in an in-order traversal. Solve this one recursively, building the
	 * list as you go (rather than printing the data). if the tree is empty, return
	 * an empty list.
	 */
	public ArrayList<Integer> inOrderTraversalPath() {
		ArrayList<Integer> result = new ArrayList<>();
		
		inOrderTraversalPath(root, result);
		
		return result;
	}
	
	private void inOrderTraversalPath(Node start, ArrayList<Integer> arrSoFar){
		if (start == null) return;
		
		inOrderTraversalPath(start.left, arrSoFar);
		arrSoFar.add(start.data);
		inOrderTraversalPath(start.right, arrSoFar);
		
	}

	/*
	 * Returns a list of all the node data in the tree, in the order that they would
	 * be visited in a level-order traversal. Solve this one using a queue and a
	 * loop, rather than recursively. if the tree is empty, return an empty list.
	 */
	public ArrayList<Integer> levelOrderTraversalPath() {
		ArrayList<Integer> result = new ArrayList<>();
		
		List<Node> que = new ArrayList<>();
		int head = 0;
		
		que.add(root);
		
		while (head < que.size()) {
			Node curr = que.get(head);
			result.add(curr.data);
			
			if (curr.left != null) {
				que.add(curr.left);
			}
			if (curr.right != null) {
				que.add(curr.right);
			}
			
			head++;
		}
		
		return result;
	}
	
	// Adds a node to this tree
	// Find the first open spot in my tree and that
	// is where I will put num in a new node.
	public void add(int num) {
		Node temp = new Node(num);

		if (root == null) { // empty tree? make root the new node
			root = temp;
			return;
		}

		// move through the tree using level-order traversal until
		// I find an open spot for the new node
		Queue<Node> q = new LinkedList<>();
		q.add(root);

		while (!q.isEmpty()) {
			Node first = q.remove();
			// see if the left or right is null. If so, put
			// the new node in that location.
			if (first.left == null) { // add new node to left
				first.left = temp;
				return;
			} else { // left is occupied, so try the right:
				if (first.right == null) {
					first.right = temp;
					return;
				} else { // first has two children
					q.add(first.left);
					q.add(first.right);
				}
			}
		}
	}

	public static BinaryTree sampleTree() {
		Node root = new Node(10); // root

		// Build left subtree:
		Node leftST = new Node(11);
		leftST.left = new Node(7);

		// Build right subtree:
		Node rightST = new Node(9);
		rightST.left = new Node(15);
		rightST.right = new Node(8);

		root.left = leftST;
		root.right = rightST;

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// Same structure as the sample tree, but with repeats
	// in the data
	public static BinaryTree manyRepeats() {
		Node root = new Node(10); // root

		// Build left subtree:
		Node leftST = new Node(11);
		leftST.left = new Node(10);

		// Build right subtree:
		Node rightST = new Node(9);
		rightST.left = new Node(10);
		rightST.right = new Node(11);

		root.left = leftST;
		root.right = rightST;

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// Builds a tree that only branches to the right
	public static BinaryTree rightsOnly(int n) {
		Node root = new Node(n);

		for (int i = n - 1; i >= 1; i--) {
			Node temp = new Node(i);
			temp.right = root;
			root = temp;
		}

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// Builds a tree that only branches to the right,
	// and all values are 50
	public static BinaryTree rightsOnlyAll50(int n) {
		Node root = new Node(50);

		for (int i = n - 1; i >= 1; i--) {
			Node temp = new Node(50);
			temp.right = root;
			root = temp;
		}

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// Builds a tree that only branches to the left
	public static BinaryTree leftsOnly(int n) {
		Node root = new Node(n);

		for (int i = n - 1; i >= 1; i--) {
			Node temp = new Node(i);
			temp.left = root;
			root = temp;
		}

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// A zig-zagging binary tree, with 1 at the top, 2 to the right of 1
	// 3 to the left of 2, etc.
	public static BinaryTree zigZag(int n) {
		Node root = new Node(1);
		Node curr = root;

		for (int i = 2; i <= n; i++) {
			Node temp = new Node(i);
			if (i % 2 == 0)
				curr.right = temp;
			else
				curr.left = temp;
			curr = temp;
		}

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// A complete binary tree, with level-order traversal
	// producing 1, 2, 3, 4, 5, 6, ... , n.
	// Assumes n >= 1
	public static BinaryTree completeTree(int n) {
		Node root = new Node(1);

		// Nodes get in line to have children attached
		Queue<Node> q = new LinkedList<>();
		q.add(root);

		int i = 2;

		while (i <= n) {
			Node temp = new Node(i);
			Node front = q.peek();
			if (front.left == null)
				front.left = temp;
			else {
				front.right = temp;
				q.remove();
				q.add(front.left);
				q.add(front.right);
			}
			i++;
		}

		BinaryTree result = new BinaryTree();
		result.root = root;
		return result;
	}

	// A full tree is just a complete tree with the bottom level full.
	// fullTree(3) would give a complete tree with 15 nodes numbered
	// 1, 2, 3, 4, 5, ... , 15.
	public static BinaryTree fullTree(int height) {
		int n = (int) Math.pow(2, height + 1) - 1;

		return completeTree(n);
	}

	static class Node {
		int data;
		Node left, right;

		Node(int data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}
	}
}