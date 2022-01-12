import java.util.ArrayList;

public class AVL {
    private Node root;

    public AVL(){
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int height(Node node){
		if (node == null) 
            return -1;   
        return node.getHeight();
	}

    private int checkBalance(Node x){
		return height(x.getLeft()) - height(x.getRight());
	}

    private int balance(Node x) {
        if (x == null) return 0;

        return height(x.getLeft()) - height(x.getRight());
	}

    private Node insert(Node node, Receipt receipt){
		//code here and change the return value
        if (node == null){
            node = new Node(receipt);
        }
        if (node.getReceipt().getReceiptId() == receipt.getReceiptId()){
            return node;
        }
        if (node.getReceipt().getReceiptId()<receipt.getReceiptId()){
            node.setRight(insert(node.getRight(),receipt));
        }
        else {
            node.setLeft(insert(node.getLeft(), receipt));
        }
        node.setHeight(max(height(node.getLeft()),height(node.getRight()))+1);
        int balance = balance(node);
        if (balance > 1){
            if(receipt.getReceiptId()>node.getLeft().getReceipt().getReceiptId()){
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }else if(balance < -1){
            if(receipt.getReceiptId()<node.getRight().getReceipt().getReceiptId()){
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }
		return node;
	}

    public void insertReceipt(Receipt receipt){
		this.root = insert(this.root, receipt);
	}

    private void NLR(Node node, ArrayList<String> result){
		if (node != null) 
        { 
			result.add(node.getReceipt().toString()) ; 
            NLR(node.getLeft(), result);             
            NLR(node.getRight(), result); 
        } 
	}
	
	public void NLR(ArrayList<String> result){
		NLR(this.root, result);
	}

    private Node search(Node x, int receiptId) {
		//code here and change the return value
        if (receiptId == x.getReceipt().getReceiptId()){
            return x;
        }
        if (receiptId < x.getReceipt().getReceiptId()){
            x = x.getLeft();
            return search(x,receiptId);
        }else {
            x = x.getRight();
            return search(x,receiptId);
        }
	}
	
	public String search(int receiptId){
		return search(root, receiptId).getReceipt().toString();
	}

    private int max(int key1, int key2) {
        if (key1 <= key2) return key2;
        return key1;
    }

	public Node rotateLeft(Node node){
        if (node == null) return null;
        Node returnNode = node.getRight();
        node.setRight(returnNode.getLeft());
        returnNode.setLeft(node);

        node.setHeight(max(height(node.getLeft()),height(node.getRight())) + 1);
        returnNode.setHeight(max(height(returnNode.getLeft()),height(returnNode.getRight())) + 1);

        return returnNode;
    }
    public Node rotateRight(Node node){
        if (node == null) return null;
        Node returnNode = node.getLeft();
        node.setLeft(returnNode.getRight());
        returnNode.setRight(node);

        node.setHeight(max(height(node.getLeft()),height(node.getRight())) + 1);
        returnNode.setHeight(max(height(returnNode.getLeft()),height(returnNode.getRight())) + 1);

        return returnNode;
    }
}
