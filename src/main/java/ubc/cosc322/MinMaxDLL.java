package ubc.cosc322;
// This list is always sorted largest heuristic to smallest
// node.gtNode contains the actual GameTreeNode where the heuristic is from


public class MinMaxDLL {
    /*
    Node head; // head of list
    Node tail;
    final int maxSize = 5;
    int size=0;

    void sortedInsert(Node new_node)
    {
        Node current;


        if (head == null) {
            head = new_node;
            tail = new_node;
        }
        else if (!comesAfter(head, new_node)) {
        head.prev = new_node;
        new_node.next = head;
        head = new_node;
        }
        else {


            current = head;

            while (current.next != null
                    && comesAfter(current.next, new_node))
                current = current.next;

            new_node.next = current.next;
            if(new_node.next != null)
                new_node.next.prev = new_node;
            else
                tail = new_node;
            current.next = new_node;
            new_node.prev = current;

        }
        if(size == maxSize){
            tail = tail.prev;
            tail.next = null;
        }
        else
            ++size;
    }

    //THIS IS THE METHOD THAT CHANGES IF WE TRY TO WIN OR LOSE, change isLargestFirst if trying to lose
    boolean comesAfter(Node compareTo, Node beingAdded){
        boolean isLargestFirst = head.gtNode.getPlayerQueenNum() == 1; //True if keeps the largest values first, false for smallest
        if(isLargestFirst) {
            if (compareTo.data > beingAdded.data)
                return true;
        }else{
            if (compareTo.data < beingAdded.data)
                return true;
        }
        return false;
    }




    Node newNode(GTNode gtnode)
    {
        Node x = new Node(gtnode);
        return x;
    }


    void printList()
    {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
    }



    public static void main(String args[])
    {
        MinMaxDLL llist = new MinMaxDLL();
        Node new_node;
        Board board = new Board();


        for(int i=150; i>-100; --i) {
            GTNode toAdd = new GTNode(board);
            toAdd.setHeuristic(i);
            new_node = llist.newNode(toAdd);
            llist.sortedInsert(new_node);
        }

        System.out.println("Created Linked List");
        llist.printList();
        System.out.println(llist.size);
    }
    */
}

class Node {
    GTNode gtNode;
    double data;
    Node next;
    Node prev;
    public Node(GTNode d)
    {
        gtNode = d;
        data = gtNode.getNodeHeuristic();
        next = null;
        prev = null;
    }
}
/* Original code written by Rajat Mishra posted on https://www.geeksforgeeks.org/given-a-linked-list-which-is-sorted-how-will-you-insert-in-sorted-way/*/
