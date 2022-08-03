package BPTree;

import java.util.ArrayList;

/**
 * @title: Inode
 * @Author MurInj
 * @Date: 2022/8/3 12:02
 * @Version 1.0
 */

public class InternalNode extends Node{
    public InternalNode(Node parent){
        super();
        this.parent = parent;
        nodeType = TreeNodeType.InternalNode;
    }

    public InternalNode(Node parent, int order){
        super(order);
        this.parent = parent;
        nodeType = TreeNodeType.InternalNode;
    }

    @Override
    protected void split(){
        int splitIndex = keys.size() / 2;
        InternalNode newNode = new InternalNode(parent);
        for(int i = splitIndex; i < keys.size(); ++i){
            newNode.addKey(keys.get(keys.size() - 1));
            newNode.child.add(0,child.get(child.size() - 1));
            delKey(keys.size() - 1);
            child.remove(child.size() - 1);
        }

        int ival = newNode.keys.get(0);

        if(isRoot()){
            InternalNode root = new InternalNode(null);
            root.addKey(ival);
            root.child.add(this);
            root.child.add(newNode);
        }
        int index = parent.binSearchKey(ival);
        parent.addKey(ival);
    }

    @Override
    protected boolean tryLeftMerge(){
        int parentIndex = getIndexInParent();
        if(parentIndex == 0) return false;
        Node leftBro = parent.child.get(parentIndex - 1);
        parent.keys.set(parentIndex - 1,leftBro.keys.get(0));
        keys.clear();
        leftBro.keys.addAll(keys);
        return true;
    }

    @Override
    protected boolean tryRightMerge(){
        int parentIndex = getIndexInParent();
        if(parentIndex == parent.keys.size() - 1) return false;
        Node rightBro = parent.child.get(parentIndex + 1);
        parent.keys.set(parentIndex,keys.get(0));
        keys.clear();
        keys.addAll(rightBro.keys);
        return true;
    }

}
