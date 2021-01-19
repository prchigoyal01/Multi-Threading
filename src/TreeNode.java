import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.RecursiveTask;

class TreeNode extends RecursiveTask<Integer> implements Runnable {
    private final int numChildren;
    private int numSuccessor;
    private final int value;
    private int depth;
    ArrayList<TreeNode> children;
    private static HashMap<Integer, Integer> map;

    TreeNode(int value, int numChildren) {
        this.value = value;
        this.numChildren = numChildren;
        children = new ArrayList<TreeNode>();
    }

    public int getNumChildren() { return numChildren; }
    public int getNumSuccessor() { return numSuccessor; }
    public int getValue() { return value; }
    public void setNumSuccessor(int numSuccessor) { this.numSuccessor = numSuccessor; }
    public int getDepth() { return depth; }
    public void setDepth(int depth) { this.depth = depth; }
    public static void setMap(HashMap<Integer, Integer> map) { TreeNode.map = map; }

    @Override
    public void run() {
        traverse();
    }

    void traverseMultiThread(int numThreads) throws InterruptedException {
        int n = children.size();
        if(n < numThreads) {
            switch (n) {
                case 2 -> {
                    Thread t1 = new Thread(children.get(0));
                    Thread t2 = new Thread(children.get(1));
                    t1.start();
                    t2.start();
                    t1.join();
                    t2.join();
                }
                case 3 -> {
                    Thread t1 = new Thread(children.get(0));
                    Thread t2 = new Thread(children.get(1));
                    Thread t3 = new Thread(children.get(2));
                    t1.start();
                    t2.start();
                    t3.start();
                    t1.join();
                    t2.join();
                    t3.join();
                }
                case 4 -> {
                    Thread t1 = new Thread(children.get(0));
                    Thread t2 = new Thread(children.get(1));
                    Thread t3 = new Thread(children.get(2));
                    Thread t4 = new Thread(children.get(3));
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                }
                case 5 -> {
                    Thread t1 = new Thread(children.get(0));
                    Thread t2 = new Thread(children.get(1));
                    Thread t3 = new Thread(children.get(2));
                    Thread t4 = new Thread(children.get(3));
                    Thread t5 = new Thread(children.get(4));
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t5.start();
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                    t5.join();
                }
            }
        }
        else {
            switch (numThreads) {
                case 1 -> {
                    int idx1 = -1;
                    while (n > 0) {
                        idx1 = n - 1;
                        Thread t1 = new Thread(children.get(idx1));
                        t1.start();
                        n--;
                        t1.join();
                    }
                }
                case 2 -> {
                    int idx1 = -1, idx2 = -1;
                    while (n > 0) {
                        idx1 = n - 1;
                        Thread t1 = new Thread(children.get(idx1));
                        t1.start();
                        n--;
                        Thread t2 = null;
                        if (n > 0) {
                            idx2 = n - 1;
                            t2 = new Thread(children.get(idx2));
                            t2.start();
                            n--;
                        }
                        t1.join();
                        if (idx2 != -1) {
                            t2.join();
                            idx2 = -1;
                        }
                    }
                }
                case 3 -> {
                    int idx1 = -1, idx2 = -1, idx3 = -1;
                    while (n > 0) {
                        idx1 = n - 1;
                        Thread t1 = new Thread(children.get(idx1));
                        t1.start();
                        n--;
                        Thread t2 = null, t3 = null;
                        if (n > 0) {
                            idx2 = n - 1;
                            t2 = new Thread(children.get(idx2));
                            t2.start();
                            n--;
                        }
                        if (n > 0) {
                            idx3 = n - 1;
                            t3 = new Thread(children.get(idx3));
                            t3.start();
                            n--;
                        }
                        t1.join();
                        if (idx2 != -1) {
                            t2.join();
                            idx2 = -1;
                        }
                        if (idx3 != -1) {
                            t3.join();
                            idx3 = -1;
                        }
                    }
                }
                case 4 -> {
                    int idx1 = -1, idx2 = -1, idx3 = -1, idx4 = -1;
                    while (n > 0) {
                        idx1 = n - 1;
                        Thread t1 = new Thread(children.get(idx1));
                        t1.start();
                        n--;
                        Thread t2 = null, t3 = null, t4 = null;
                        if (n > 0) {
                            idx2 = n - 1;
                            t2 = new Thread(children.get(idx2));
                            t2.start();
                            n--;
                        }
                        if (n > 0) {
                            idx3 = n - 1;
                            t3 = new Thread(children.get(idx3));
                            t3.start();
                            n--;
                        }
                        if (n > 0) {
                            idx4 = n - 1;
                            t4 = new Thread(children.get(idx4));
                            t4.start();
                            n--;
                        }
                        t1.join();
                        if (idx2 != -1) {
                            t2.join();
                            idx2 = -1;
                        }
                        if (idx3 != -1) {
                            t3.join();
                            idx3 = -1;
                        }
                        if (idx4 != -1) {
                            t4.join();
                            idx4 = -1;
                        }
                    }
                }
            }
        }
    }

    void traverse() {
        /*
        System.out.print(value + "->");
        for(TreeNode x: children) {
            System.out.print(" " + x.value);
        }
        System.out.println();

         */
        if(map.containsKey(this.value)) {
            map.replace(this.value, this.depth);
        }

        for(TreeNode x: children) {
            x.traverse();
        }
    }

    @Override
    protected Integer compute() {
        if(this.children.size() == 0) {
//            System.out.println(this.value);
            if(map.containsKey(this.value)) {
                map.replace(this.value, this.depth);
            }
            return null;
        }
        int i;
        for(i = 0; i < children.size(); i++) {
            children.get(i).fork();
        }

//        System.out.println(this.value);
        if(map.containsKey(this.value)) {
            map.replace(this.value, this.depth);
        }

        return null;
    }
}