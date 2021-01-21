package interviewCode;

import java.util.*;

public class BinaryTree {
    public static void main(String[] args) {
        int[] values = new int[]{4, 2, 1, 3, 5, 7, 6, 8};
        BuildTree helper = new BuildTree();
        TreeNode root = helper.buildTree(values);
        //helper.printTree(root);
        //System.out.println();

        int[] postOrder = new int[]{1, 3, 2, 6, 8, 7, 5, 4};
         TreeNode node = new RebuildBSTByPostOrder().rebuildBSTByPostOrder(postOrder);
        helper.printTree(node);
    }
}

class TreeNode {
    int value;
     TreeNode left;
     TreeNode right;
     TreeNode parent;

    TreeNode(int value) {
        this.value = value;
    }
}

class BuildTree {
    public  TreeNode buildTree(int[] arr) {
         TreeNode root = null;
        for (int i = 0; i < arr.length; i++) {
            root = put(root, arr[i]);
        }
        return root;
    }

    private  TreeNode put( TreeNode node, int value) {
        if (node == null) node = new  TreeNode(value);
        else if (value < node.value) node.left = put(node.left, value);
        else if (value > node.value) node.right = put(node.right, value);
        return node;
    }

    public void printTree( TreeNode node) {
        if (node == null) return;
        printTree(node.left);
        System.out.print(node.value + " ");
        printTree(node.right);
    }
}

class PreOrder {
    public List<Integer> recursive( TreeNode root) {
        List<Integer> order = new ArrayList<Integer>();
        preOrder(root, order);
        return order;
    }

    private void preOrder( TreeNode root, List<Integer> order) {
        if (root == null) return;
        order.add(root.value);
        preOrder(root.left, order);
        preOrder(root.right, order);
    }

    public List<Integer> iterative( TreeNode root) {
        List<Integer> order = new ArrayList<Integer>();
        if (root == null) return order;
        Stack< TreeNode> stack = new Stack< TreeNode>();
        stack.push(root);
         TreeNode cur = root;
        while (stack.size() > 0) {
            cur = stack.pop();
            order.add(cur.value);
            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);
        }
        return order;
    }
}

class InOrder {
    public List<Integer> recursive( TreeNode root) {
        List<Integer> order = new ArrayList<Integer>();
        inOrder(root, order);
        return order;
    }

    private void inOrder( TreeNode node, List<Integer> order) {
        if (node == null) return;
        inOrder(node.left, order);
        order.add(node.value);
        inOrder(node.right, order);
    }

    public List<Integer> iterative( TreeNode root) {
        List<Integer> order = new ArrayList<Integer>();
        if (root == null) return order;
        Stack< TreeNode> stack = new Stack< TreeNode>();
         TreeNode cur = root;
        while (stack.size() > 0 || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            order.add(cur.value);
            cur = cur.right;
        }
        return order;
    }
}

class PostOrder {
    public List<Integer> recursive( TreeNode root) {
        List<Integer> order = new ArrayList<Integer>();
        postOrder(root, order);
        return order;
    }

    private void postOrder( TreeNode node, List<Integer> order) {
        if (node == null) return;
        postOrder(node.left, order);
        postOrder(node.right, order);
        order.add(node.value);
    }

    public List<Integer> iterative( TreeNode root) {
        List<Integer> order = new ArrayList<Integer>();
        if (root == null) return order;
        Stack< TreeNode> stack = new Stack< TreeNode>();
         TreeNode cur = root;
        stack.push(root);
        while (stack.size() > 0) {
            cur = stack.pop();
            if (cur.left != null) stack.push(cur.left);
            if (cur.right != null) stack.push(cur.right);
            order.add(0, cur.value);
        }
        return order;
    }
}

class PrintTreeBoard {
    public List<Integer> printTreeBoard( TreeNode root) {
        List<Integer> list = new ArrayList<Integer>(); // left board
        if (root == null) return list;
        int height = getHeight(root);
        Queue< TreeNode> queue = new LinkedList< TreeNode>();
        Stack<Integer> stack = new Stack<Integer>(); // right board
        queue.offer(root);
        list.add(root.value);
         TreeNode cur;
        for (int i = 0; i < height && queue.size() > 0; i++) {
            int levelSize = queue.size();
            for (int j = 0; j < levelSize; j++) {
                cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);

                if (i == 0) continue;
                else if (i == height - 1) list.add(cur.value); // bottom board
                else {
                    if (j == 0) list.add(cur.value);
                    if (j == levelSize - 1) stack.add(cur.value);
                }
            }
        }
        while (stack.size() > 0) list.add(stack.pop());
        return list;
    }

    public int getHeight( TreeNode root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
}

class SerializeTree {
    // '#' means null, '!' means end of value number
    public String serialize( TreeNode root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        Queue< TreeNode> queue = new LinkedList< TreeNode>();
        queue.offer(root);
        sb.append(root.value + "!");
         TreeNode cur;
        while (queue.size() > 0) {
            cur = queue.poll();
            if (cur.left != null) {
                queue.offer(cur.left);
                sb.append(cur.left.value + "!");
            } else {
                sb.append("#!");
            }
            if (cur.right != null) {
                queue.offer(cur.right);
                sb.append(cur.right.value + "!");
            } else {
                sb.append("#!");
            }
        }
        return sb.toString();
    }

    public  TreeNode deserialize(String str) {
        if (str.isEmpty()) return null;
        String[] values = str.split("!");
         TreeNode root = new  TreeNode(Integer.valueOf(values[0]));
        int index = 0;
        Queue< TreeNode> queue = new LinkedList< TreeNode>();
        queue.offer(root);
         TreeNode cur;
        while (index < values.length - 1) {
            cur = queue.poll();
            if (!values[index + 1].equals("#")) {
                 TreeNode node = new  TreeNode(Integer.valueOf(values[++index]));
                cur.left = node;
                queue.offer(node);
            } else {
                cur.left = null;
                index++;
            }
            if (!values[index + 1].equals("#")) {
                 TreeNode node = new  TreeNode(Integer.valueOf(values[++index]));
                cur.right = node;
                queue.offer(node);
            } else {
                cur.right = null;
                index++;
            }
        }
        return root;
    }
}

class Morris {
    public List<Integer> morrisInOrder( TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
         TreeNode p = root, q = null;
        while (p != null) {
            q = p.left;
            if (q != null) {
                while (q.right != null && q.right != p) q = q.right;
                if (q.right == null) {
                    q.right = p;
                    p = p.left;
                    continue;
                } else {
                    q.right = null;
                }
            }
            list.add(p.value);
            p = p.right;
        }
        return list;
    }

    public List<Integer> morrisPreOrder( TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
         TreeNode p = root, q = null;
        while (p != null) {
            q = p.left;
            if (q != null) {
                while (q.right != null && q.right != p) q = q.right;
                if (q.right == null) {
                    q.right = p;
                    list.add(p.value);
                    p = p.left;
                    continue;
                } else {
                    q.right = null;
                }
            } else {
                list.add(p.value);
            }
            p = p.right;
        }
        return list;
    }
}

class GetPathSumMaxLength {
    public int getPathSumMaxLength( TreeNode root, int sum) {
        Map<Integer, Integer> sumMap = new HashMap<Integer, Integer>();
        sumMap.put(0, 0);
        return preOrder(root, sum, 0, 1, 0, sumMap);
    }

    private int preOrder( TreeNode node, int sum, int preSum, int level, int maxLen, Map<Integer, Integer> sumMap) {
        if (node == null) return maxLen;
        int curSum = preSum + node.value;
        if (!sumMap.containsKey(curSum)) sumMap.put(curSum, level);
        if (sumMap.containsKey(curSum - sum)) maxLen = Math.max(level - sumMap.get(curSum - sum), maxLen);
        maxLen = preOrder(node.left, sum, curSum, level + 1, maxLen, sumMap);
        maxLen = preOrder(node.right, sum, curSum, level + 1, maxLen, sumMap);
        if (level == sumMap.get(curSum)) sumMap.remove(curSum);
        return maxLen;
    }
}

class GetBiggestSubBST {
    public  TreeNode getBiggestSubBST( TreeNode root) {
        int[] record = new int[3];
        return posOrder(root, record);
    }

    private  TreeNode posOrder( TreeNode node, int[] record) {
        if (node == null) {
            record[0] = 0;
            record[1] = Integer.MAX_VALUE;
            record[2] = Integer.MIN_VALUE;
            return null;
        }

        // find from left sub tree
         TreeNode left = node.left;
         TreeNode leftBST = posOrder(left, record);
        int leftSize = record[0];
        int leftMin = record[1];
        int leftMax = record[2];

        // find from right sub tree
         TreeNode right = node.right;
         TreeNode rightBST = posOrder(right, record);
        int rightSize = record[0];
        int rightMin = record[1];
        int rightMax = record[2];

        // this tree
        record[1] = Math.min(leftMin, rightMin);
        record[2] = Math.max(leftMax, rightMax);
        int value = node.value;
        if (left == leftBST && right == rightBST && leftMax < value && value < rightMin) {
            record[0] = leftSize + rightSize + 1;
            return node;
        }

        record[0] = Math.max(leftSize, rightSize);
        return leftSize > rightSize ? leftBST : rightBST;
    }
}

class PrintTree {
    public void printTreeByLevel( TreeNode root) {
        if (root == null) return;
        Queue< TreeNode> queue = new LinkedList< TreeNode>();
         TreeNode cur = root;
        queue.offer(cur);
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
                System.out.print(cur.value + " ");
            }
            System.out.println();
        }
    }

    public void printTreeByZigZag( TreeNode root) {
        if (root == null) return;
        LinkedList< TreeNode> queue = new LinkedList< TreeNode>();
         TreeNode cur = root;
        queue.offer(root);
        int level = 1;
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                if (level % 2 == 1) {
                    cur = queue.pollFirst();
                    if (cur.left != null) queue.addLast(cur.left);
                    if (cur.right != null) queue.addLast(cur.right);
                } else {
                    cur = queue.pollLast();
                    if (cur.right != null) queue.offerFirst(cur.right);
                    if (cur.left != null) queue.offerFirst(cur.left);
                }
                System.out.print(cur.value + " ");
            }
            level++;
            System.out.println();
        }
    }
}

class GetTwoErrorNodes {
    public  TreeNode[] getTwoErrorNodes( TreeNode root) {
         TreeNode[] errors = new  TreeNode[2];
        if (root == null) return errors;
        Stack< TreeNode> stack = new Stack< TreeNode>();
         TreeNode cur = root, pre = null;
        while (stack.size() > 0 || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (pre != null && pre.value > cur.value) {
                errors[0] = errors[0] == null ? pre : errors[0];
                errors[1] = cur;
            }
            pre = cur;
            cur = cur.right;
        }
        return errors;
    }

}

class ContainsTreeTopology {
    public boolean containsTreeTopology( TreeNode root1,  TreeNode root2) {
        return check(root1, root2)
                || containsTreeTopology(root1.left, root2)
                || containsTreeTopology(root1.right, root2);
    }

    private boolean check( TreeNode node1,  TreeNode node2) {
        if (node2 == null) return true;
        if (node1 == null || node1.value != node2.value) return false;
        return check(node1.left, node2.left) && check(node1.right, node2.right);
    }
}

class ContainsSubTree {
    public boolean containsSubTree( TreeNode root1,  TreeNode root2) {
        return check(root1, root2)
                || containsSubTree(root1.left, root2)
                || containsSubTree(root1.right, root2);
    }

    private boolean check( TreeNode node1,  TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null || node1.value != node2.value) return false;
        return check(node1.left, node1.left) && check(node1.right, node2.right);
    }
}

class IsBalanceBinaryTree {

    // O(n^2), not good because it's preOrder
    public boolean isBalanceBinaryTree( TreeNode root) {
        if (root == null) return true;
        if (Math.abs(getHeight(root.left) - getHeight(root.right)) > 1) return false;
        return isBalanceBinaryTree(root.left) && isBalanceBinaryTree(root.right);
    }

    private int getHeight( TreeNode node) {
        if (node == null) return 0;
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    // O(n) solution by postOrder
    static boolean isBalanced;

    public boolean isBalanceBinaryTree2( TreeNode root) {
        isBalanced = true;
        getHeight2(root);
        return isBalanced;
    }

    private int getHeight2( TreeNode node) {
        if (node == null) return 0;

        int leftHeight = getHeight(node.left);
        if (isBalanced == false) return 0;

        int rightHeight = getHeight2(node.right);
        if (isBalanced == false) return 0;

        if (Math.abs(leftHeight - rightHeight) > 1) isBalanced = false;
        return Math.max(leftHeight, rightHeight);
    }
}

class IsBinarySearchTree {
    // use inOrder
    public boolean isBinarySearchTree( TreeNode root) {
        if (root == null) return true;
        Stack< TreeNode> stack = new Stack< TreeNode>();
         TreeNode cur = root, pre = null;
        while (stack.size() > 0 || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (pre != null && pre.value > cur.value) return false;
            else {
                pre = cur;
                cur = cur.right;
            }
        }
        return true;
    }
}

class IsCompleteBinaryTree {
    public boolean isCompleteBinaryTree( TreeNode root) {
        if (root == null) return true;
        Queue< TreeNode> queue = new LinkedList< TreeNode>();
        queue.offer(root);
         TreeNode cur;
        boolean isLeaf = false;
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                cur = queue.poll();
                if (cur.right != null && cur.left == null) return false;
                if (isLeaf && (cur.left != null || cur.right != null)) return false;
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
                else isLeaf = true; // if cur.right==null, then it must be a leaf
            }
        }
        return true;
    }
}

class RebuildBSTByPostOrder {
    public  TreeNode rebuildBSTByPostOrder(int[] postOrder) {
        if (postOrder == null || postOrder.length == 0) return null;
        return recursive(postOrder, 0, postOrder.length - 1);
    }

    private  TreeNode recursive(int[] postOrder, int start, int end) {
        if (start > end) return null;
         TreeNode root = new  TreeNode(postOrder[end]);
        int i = end - 1;
        while (i >= start && postOrder[i] > postOrder[end]) i--;
        root.left = recursive(postOrder, start, i);
        root.right = recursive(postOrder, i + 1, end - 1);
        return root;
    }
}

class RebuildBSTBySortedArray {
    public  TreeNode rebuild(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        return build(arr, 0, arr.length - 1);
    }

    private  TreeNode build(int[] arr, int lo, int hi) {
        if (lo > hi) return null;
        int mid = (lo + hi) / 2;
         TreeNode root = new  TreeNode(arr[mid]);
        root.left = build(arr, lo, mid - 1);
        root.right = build(arr, mid + 1, hi);
        return root;
    }
}

class GetNextNode {
    public  TreeNode getNextNode( TreeNode node) {
        if (node == null) return node;
        if (node.right != null) {
            node = node.right;
            while (node.left != null) node = node.left;
            return node;
        } else {
            if (node.parent.left == node) return node.left;
            else {
                while (node.parent != null && node.parent.right == node) node = node.parent;
                return node.parent;
            }
        }
    }
}

class FindLowestCommonAncestor {
    public  TreeNode findLCA( TreeNode root,  TreeNode node1,  TreeNode node2) {
        if (root == null || root == node1 || root == node2) return root;
         TreeNode left = findLCA(root.left, node1, node2);
         TreeNode right = findLCA(root.right, node1, node2);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }
}

class TreeNodeMaxDistance {
    private static int distance = 0;

    public int maxDistance( TreeNode root) {
        return posOrder(root);
    }

    private int posOrder( TreeNode root) {
        if (root == null) {
            distance = 0;
            return 0;
        }

        // left
        int leftMax = posOrder(root.left);
        int maxFromLeft = distance;

        // right
        int rightMax = posOrder(root.right);
        int maxFromRight = distance;

        // cur max
        int curMax = maxFromLeft + 1 + maxFromRight;
        distance = Math.max(maxFromLeft, maxFromRight) + 1;

        return Math.max(curMax, Math.max(leftMax, rightMax));
    }
}

class CountCompleteBinaryTreeNodes {
    public int countNodes( TreeNode root) {
        int h = height(root);
        return h < 0 ? 0 : height(root.right) == h - 1 ? (1 << h) + countNodes(root.right) : (1 << h - 1) + countNodes(root.left);
    }

    private int height( TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
}