package BPTree;

import java.util.Currency;
import java.util.LinkedList;

/**
 * @title: BPTree.BPTree
 * @Author MurInj
 * @Date: 2022/8/2 16:47
 * @Version 1.0
 */



public class BPTree {
    private final int ORDER;
    private Node root;

    public  BPTree(){
        ORDER = 5;
        root = null;
    }

    public BPTree(int order) {
        ORDER = order;
        root = null;
    }

    public boolean Find(int val){
        if(isEmpty()) return false;

        Node LeafNode = searchLeafNode(val);
        int index = LeafNode.binSearchKey(val);
        int key = LeafNode.keys.get(index);
        return key == val;
    }

    public void Add(int val) throws Exception {
        if(isEmpty()){
            root = new InternalNode(null,ORDER);
            root.addKey(val);
            LeafNode leaf = new LeafNode(root,ORDER);
            LeafNode emptyLeaf = new LeafNode(root,ORDER);

            root.child.add(leaf);
            root.child.add(emptyLeaf);

            leaf.addKey(val);
        }
        else{
            Node leaf = searchLeafNode(val);
            leaf.addKey(val);
            Node current = leaf;
            while(current.isOverFlow()){
                current.split();
                current = current.parent;
            }
        }
    }

    public void Remove(int val) throws Exception{
        if(isEmpty()) return;

        Node LeafNode = searchLeafNode(val);
        int index = LeafNode.binSearchKey(val);
        int key = LeafNode.keys.get(index);
        if(key != val) return;
        LeafNode.delKey(val);
        Node current = LeafNode;
        while (current.parent != null && current.isUnderFlow()){
            if(!current.tryLeftRotate() && !current.tryRightRotate() && !current.tryLeftMerge() && !current.tryRightMerge())
                break;
            current = current.parent;
        }

    }

    private Node searchLeafNode(int val){
        Node current = root;
        while(!current.isLeaf()){
            int index = current.binSearchKey(val);
            int key = current.keys.get(index);
            current = current.child.get(key < val ? index + 1 : index);
        }
        return current;
    }

    public boolean isEmpty(){
        return root == null;
    }

}
