public class Main {
    public static void main(String[] args) {
        AVLtree tree = new AVLtree();
        /*
        tree.insert(50);
        tree.insert(30);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        tree.insert(90);
        tree.insert(75);
        tree.insert(25);
        tree.insert(20);
        tree.insert(10);
        tree.insert(5);
        tree.insert(5);*/
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        tree.insert(4);
        tree.insert(5);

        //System.out.println("insert order: 50, 30, 40, 70, 60, 80, 90, 75, 25, 20, 10, 5");


        System.out.println("inorder");
        tree.inorder();
        System.out.println();
        
        System.out.println("postorder");
        tree.postorder();
        System.out.println();

        System.out.println("preorder");
        tree.preorder();
        System.out.println();

        System.out.println("representation");
        tree.printTree();
        System.out.println();

        System.out.println("level: " + tree.getDepth());

    }
}
