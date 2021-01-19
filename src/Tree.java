import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

class Tree{
    private final Random rand;
    private final TreeNode root;
    private final int height;

    Tree(int n) {
        ArrayList<Integer> values = new ArrayList<Integer>();
        for(int i = 1; i <= 1000000; i++) {
            values.add(i);
        }
        Collections.shuffle(values);
        rand = new Random();
        int val = values.get(0);
        int num = rand.nextInt(4) + 2;
        this.root = new TreeNode(val, num);
        for(int i = 1; i < n; i++) {
            val = values.get(i);
            num = rand.nextInt(4) + 2;
            TreeNode node = new TreeNode(val, num);
            this.addNode(this.root, node);
        }
        this.root.children.sort(new TreeNodeComparator());
        countSuccessor(this.root);
        this.height = updateHeight(this.root);
        updateDepth(this.root, 0);
    }

    public int getHeight() { return height; }
    public TreeNode getRoot() { return root; }

    void addNode(TreeNode parent, TreeNode node) {
        if(parent == null) {
            parent = node;
        }
        else {
            if(parent.children.size() < parent.getNumChildren()) {
                parent.children.add(node);
            } else {
                int idx = rand.nextInt(parent.getNumChildren());
                addNode(parent.children.get(idx), node);
            }
        }
    }

    int updateHeight(TreeNode node) {
        if(node == null) {
            return -1;
        }
        int[] h = new int[node.getNumChildren()];
        for(int i=0; i < node.children.size(); i++) {
            h[i] = updateHeight(node.children.get(i));
        }
        return 1 + maxElement(h);
    }

    void updateDepth(TreeNode node, int depth) {
        if(node != null) {
            node.setDepth(depth++);
            for(TreeNode x: node.children) {
                updateDepth(x, depth);
            }
        }
    }

    static int maxElement(int[] arr) {
        int max = -1;
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    int countSuccessor(TreeNode node) {
        if(node == null) {
            return 0;
        }
        if(node.children.size() == 0) {
            node.setNumSuccessor(0);
            return 1;
        }
        for(TreeNode x: node.children) {
            node.setNumSuccessor(node.getNumSuccessor() + countSuccessor(x));
        }
        return node.getNumSuccessor() + 1;
    }
}