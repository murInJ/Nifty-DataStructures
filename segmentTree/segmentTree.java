/**
 * @title: segmentTree
 * @Author MurInj
 * @Date: 2022/8/10 16:48
 * @Version 1.0
 */




public class segmentTree {
    private class Node{
        public int left,right,lazy,sum;
        public Node(int l,int r,int lz){
            left = l;
            right = r;
            lazy = lz;
        }
    }

    private final int size;
    private Node[] arr;

    public segmentTree(int size){
        this.size = size;
        arr = new Node[4*size];
        build(0,size-1,0);
    }

    public void clear(){
        build(0,size-1,0);
    }

    public void Add(int l,int r,int k){
        add(l,r,k,0);
    }

    public int Search(int l,int r){
        return search(0,l,r);
    }

    private void build(int l,int r,int i){
        arr[i].left=l;arr[i].right=r;
        if(l==r){
            return ;
        }
        int mid=(l+r)>>1;
        build(l,mid,i*2);
        build(mid+1,r,i*2+1);
        arr[i].sum=arr[i*2].sum+arr[i*2+1].sum;
    }

    private void add(int l,int r,int k,int i)
    {
        if(arr[i].right<=r && arr[i].left>=l)
        {
            arr[i].sum+=k*(arr[i].right-arr[i].left+1);
            arr[i].lazy+=k;
            return ;
        }
        push_down(i);
        if(arr[i*2].right>=l)
            add(i*2,l,r,k);
        if(arr[i*2+1].left<=r)
            add(i*2+1,l,r,k);
        arr[i].sum=arr[i*2].sum+arr[i*2+1].sum;
    }
    private void push_down(int i)
    {
        if(arr[i].lazy!=0)
        {
            arr[i*2].lazy+=arr[i].lazy;
            arr[i*2+1].lazy+=arr[i].lazy;
            int mid=(arr[i].left+arr[i].right)/2;
            arr[i*2].sum+=arr[i].lazy*(mid-arr[i*2].left+1);//左右分别求和加起来
            arr[i*2+1].sum+=arr[i].lazy*(arr[i*2+1].right-mid);
            arr[i].lazy=0;//父亲lz归零
        }
    }

    private int search(int i,int l,int r){
        if(arr[i].left>=l && arr[i].right<=r)
            return arr[i].sum;
        if(arr[i].right<l || arr[i].left>r)  return 0;
        push_down(i);
        int s=0;
        if(arr[i*2].right>=l)  s+=search(i*2,l,r);
        if(arr[i*2+1].left<=r)  s+=search(i*2+1,l,r);
        return s;
    }

}
