package BPTree;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @title: BPNode
 * @Author MurInj
 * @Date: 2022/8/2 18:03
 * @Version 1.0
**/
abstract class Node {
        protected int ORDER;
        protected int keyNum;
        protected Node parent;
        protected LeafNode front;
        protected LeafNode back;

        protected TreeNodeType nodeType;
        protected ArrayList<Integer> keys = new ArrayList<Integer>();
        protected ArrayList<Node> child = new ArrayList<Node>();

        public Node(){
            ORDER = 5;
            keyNum = 0;
        }

        public Node(int order){
            ORDER = order;
            keyNum = 0;
        }

        protected int binSearchKey(int val){
            int l = 0, r = keys.size() - 1, m = l;
            while(l < r){
                m = (r - l) / 2 + l;
                int key = keys.get(m);
                if(key == val) break;
                else if(key < val) l = m + 1;
                else r = m - 1;
            }
            return m;
        }

        protected void split() throws Exception {
            throw new Exception("需要重写");
        }

        protected void setNodeType(TreeNodeType nodeType){
            this.nodeType = nodeType;
        }

        protected void addKey(int val){
            int index = binSearchKey(val);
            keys.add(index,val);
        }

        protected void delKey(int val){
            int index = binSearchKey(val);
            keys.remove(index);
        }

        protected int getIndexInParent(){
            return parent.binSearchKey(keys.get(0));
        }

        protected boolean tryLeftRotate(){
            int parentIndex = getIndexInParent();
            if(parentIndex == 0) return false;
            Node leftBro = parent.child.get(parentIndex - 1);
            if(leftBro.keys.size() <= leftBro.ORDER) return false;
            int val = leftBro.keys.get(leftBro.keys.size() - 1);
            leftBro.delKey(leftBro.keys.size() - 1);
            addKey(val);
            parent.keys.set(parentIndex,keys.get(0));
            return true;
        }

        protected boolean tryRightRotate(){
            int parentIndex = getIndexInParent();
            if(parentIndex == parent.keys.size() - 1) return false;
            Node rightBro = parent.child.get(parentIndex + 1);
            if(rightBro.keys.size() <= rightBro.ORDER) return false;
            int val = rightBro.keys.get(rightBro.keys.size() - 1);
            rightBro.delKey(rightBro.keys.size() - 1);
            addKey(val);
            parent.keys.set(parentIndex,keys.get(0));
            return true;
        }

        protected boolean tryLeftMerge() throws Exception {
            throw new Exception("需要重写");
        }

        protected boolean tryRightMerge() throws Exception {
            throw new Exception("需要重写");
        }

        protected  boolean isRoot(){
            return parent == null;
        }

        protected boolean isLeaf(){
            return nodeType == TreeNodeType.Leaf;
        }

        protected boolean isInternal(){
            return nodeType == TreeNodeType.InternalNode;
        }

        protected boolean isOverFlow(){
            return keys.size() > ORDER - 1;
        }

        protected boolean isUnderFlow(){
            return Math.ceil((double)ORDER / 2) + 1 >= keys.size();
        }

        protected boolean isFull(){
            return keys.size() >= ORDER;
        }

        protected boolean isEmpty(){
            return keys.size() == 0;
        }

}
