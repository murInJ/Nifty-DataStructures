package BPTree;

import java.util.ArrayList;

/**
 * @title: LeafNode
 * @Author MurInj
 * @Date: 2022/8/3 11:29
 * @Version 1.0
 */

public class LeafNode extends Node{



    public LeafNode(Node parent){
        super();
        this.parent = parent;
        nodeType = TreeNodeType.Leaf;
    }

    public LeafNode(Node parent, int order){
        super(order);
        this.parent = parent;
        nodeType = TreeNodeType.Leaf;
    }

    @Override
    protected void split(){
        int splitIndex = keys.size() / 2;
        LeafNode newNode = new LeafNode(parent);
        for(int i = splitIndex; i < keys.size(); ++i){
            newNode.addKey(keys.get(keys.size() - 1));
            delKey(keys.size() - 1);
        }
        back = newNode;
        newNode.front = this;

        int ival = newNode.keys.get(0);
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
        leftBro.back = back;
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
        back = back.back;
        return true;
    }
}
