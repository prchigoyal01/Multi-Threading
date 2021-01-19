import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input number of nodes: ");
        int n = in.nextInt();
        long startTime = System.nanoTime();
        Tree tree = new Tree(n);
        long stopTime = System.nanoTime();
        System.out.println("Time taken to build Tree: " + ((double)(stopTime - startTime) / 1000000000) + " seconds.");
        System.out.println("Height of tree is " + tree.getHeight());

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        System.out.print("Input number of nodes to find: ");
        int numNodesToFind = in.nextInt();
        System.out.print("Input set of nodes to find: ");
        for(int i = 0; i < numNodesToFind; i++) {
            map.put(in.nextInt(), -1);
        }
        TreeNode.setMap(map);

        int technique = 0;
        while(technique != 4) {
            System.out.println("Specify the technique: 1 - Explicit Multithreading; 2 - ForkJoinPool; 3 - Normal Recursive; 4 - Exit");
            technique = in.nextInt();
            int numThreads = 5;
            while (technique < 3 && (numThreads > 4 || numThreads < 1)){
                System.out.print("Enter num of threads to be used in parallel processing: ");
                numThreads = in.nextInt();
            }

            if(technique == 1) {
                startTime = System.nanoTime();
                tree.getRoot().traverseMultiThread(numThreads);
                stopTime = System.nanoTime();
                System.out.println("Time taken to traverse Tree (MultiThread recursive): " + ((double) (stopTime - startTime) / 1000000000) + " seconds.");
                System.out.println("Status of nodes queried (-1 if not present, otherwise depth) :");
                for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
                    System.out.println("Node = " + entry.getKey() + ", Depth = " + entry.getValue());
                    entry.setValue(-1);
                }
            }

            if(technique == 2) {
                startTime = System.nanoTime();
                ForkJoinPool pool = new ForkJoinPool(numThreads);
                TreeNode.helpQuiesce();
                pool.invoke(tree.getRoot());
                stopTime = System.nanoTime();
                System.out.println("Time taken to traverse Tree (ForkJoinPool recursive): " + ((double) (stopTime - startTime) / 1000000000) + " seconds.");
                System.out.println("Status of nodes queried (-1 if not present, otherwise depth) :");
                for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
                    System.out.println("Node = " + entry.getKey() + ", Depth = " + entry.getValue());
                    entry.setValue(-1);
                }
            }

            if(technique == 3) {
                startTime = System.nanoTime();
                tree.getRoot().traverse();
                stopTime = System.nanoTime();
                System.out.println("Time taken to traverse Tree (Normal recursive): " + ((double) (stopTime - startTime) / 1000000000) + " seconds.");
                System.out.println("Status of nodes queried (-1 if not present, otherwise depth) :");
                for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
                    System.out.println("Node = " + entry.getKey() + ", Depth = " + entry.getValue());
                    entry.setValue(-1);
                }
            }
        }
    }
}
