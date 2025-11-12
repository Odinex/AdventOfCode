package utils;// Java program to implement LRU Least Recently Used)
import java.util.HashMap;
import java.util.Map;

class CircularLinkedList {

    // https://www.geeksforgeeks.org/circular-linked-list/?ref=shm
    class Node {
        int data;
        Node next;

        Node(int data)
        {
            this.data = data;
            this.next = null;
        }
    }

    Node insertAtPosition(Node last, int data,
                          int pos){
        if (last == null) {
            // If the list is empty
            if (pos != 1) {
                System.out.println("Invalid position!");
                return last;
            }
            // Create a new node and make it point to itself
            Node newNode = new Node(data);
            last = newNode;
            last.next = last;
            return last;
        }

        // Create a new node with the given data
        Node newNode = new Node(data);

        // curr will point to head initially
        Node curr = last.next;

        if (pos == 1) {
            // Insert at the beginning
            newNode.next = curr;
            last.next = newNode;
            return last;
        }

        // Traverse the list to find the insertion point
        for (int i = 1; i < pos - 1; ++i) {
            curr = curr.next;

            // If position is out of bounds
            if (curr == last.next) {
                System.out.println("Invalid position!");
                return last;
            }
        }

        // Insert the new node at the desired position
        newNode.next = curr.next;
        curr.next = newNode;

        // Update last if the new node is inserted at the
        // end
        if (curr == last)
            last = newNode;

        return last;
    }
    Node insertEnd(Node tail, int value){
        Node newNode = new Node(value);
        if (tail == null) {
            // If the list is empty, initialize it with the
            // new node
            tail = newNode;
            newNode.next = newNode;
        }
        else {
            // Insert new node after the current tail and
            // update the tail pointer
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
        return tail;
    }

    public Node insertAtBeginning(Node last,
                                  int value){
        Node newNode = new Node(value);

        // If the list is empty, make the new node point to
        // itself and set it as last
        if (last == null) {
            newNode.next = newNode;
            return newNode;
        }

        // Insert the new node at the beginning
        newNode.next = last.next;
        last.next = newNode;

        return last;
    }

    // Function to print the circular linked list
    static void printList(Node last){
        if (last == null) return;

        Node head = last.next;
        while (true) {
            System.out.print(head.data + " ");
            head = head.next;
            if (head == last.next) break;
        }
        System.out.println();
    }
    public static Node deleteFirstNode(Node last) {
        if (last == null) {
            // If the list is empty
            System.out.println("List is empty");
            return null;
        }

        Node head = last.next;

        if (head == last) {
            // If there is only one node in the list
            last = null;
        } else {
            // More than one node in the list
            last.next = head.next;
        }

        return last;
    }

    public static Node deleteSpecificNode(Node last,
                                          int key){
        if (last == null) {
            // If the list is empty
            System.out.println(
                    "List is empty, nothing to delete.");
            return null;
        }
        Node curr = last.next;
        Node prev = last;

        // If the node to be deleted is the only node in the
        // list
        if (curr == last && curr.data == key) {
            last = null;
            return last;
        }

        // If the node to be deleted is the first node
        if (curr.data == key) {
            last.next = curr.next;
            return last;
        }

        // Traverse the list to find the node to be deleted
        while (curr != last && curr.data != key) {
            prev = curr;
            curr = curr.next;
        }

        // If the node to be deleted is found
        if (curr.data == key) {
            prev.next = curr.next;
            if (curr == last) {
                last = prev;
            }
        }
        else {
            // If the node to be deleted is not found
            System.out.println("Node with data " + key
                    + " not found.");
        }
        return last;
    }

    public static Node deleteLastNode(Node last){
        if (last == null) {
            // If the list is empty
            System.out.println(
                    "List is empty, nothing to delete.");
            return null;
        }
        Node head = last.next;

        // If there is only one node in the list
        if (head == last) {
            last = null;
            return last;
        }
        // Traverse the list to find the second last node
        Node curr = head;
        while (curr.next != last) {
            curr = curr.next;
        }
        // Update the second last node's next pointer to
        // point to head
        curr.next = head;
        last = curr;

        return last;
    }
    public Node getInstance() {
        Node first = new Node(2);
        first.next = new Node(3);
        first.next.next = new Node(4);

        Node last = first.next.next;
        last.next = first;

        System.out.print("Original list: ");
        printList(last);

        return first;
    }
}
class LRUCache {
    class Node {
        int key;
        int value;
        Node next;
        Node prev;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    private int capacity;
    private Map<Integer, Node> cacheMap;
    private Node head;
    private Node tail;

    // Constructor to initialize the cache with a given
    // capacity
    LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    // Function to get the value for a given key
    int get(int key) {
        if (!cacheMap.containsKey(key)) {
            return -1;
        }

        Node node = cacheMap.get(key);
        remove(node);
        add(node);
        return node.value;
    }

    // Function to put a key-value pair into the cache
    void put(int key, int value) {
        if (cacheMap.containsKey(key)) {
            Node oldNode = cacheMap.get(key);
            remove(oldNode);
        }

        Node node = new Node(key, value);
        cacheMap.put(key, node);
        add(node);

        if (cacheMap.size() > capacity) {
            Node nodeToDelete = tail.prev;
            remove(nodeToDelete);
            cacheMap.remove(nodeToDelete.key);
        }
    }

    // Add a node right after the head (most recently used
    // position)
    private void add(Node node) {
        Node nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    // Remove a node from the list
    private void remove(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }
}


public class Main {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
}