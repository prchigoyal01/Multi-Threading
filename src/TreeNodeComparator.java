import java.util.Comparator;

public class TreeNodeComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode o1, TreeNode o2) {
        return o1.getNumSuccessor() - o2.getNumSuccessor();
    }
}
