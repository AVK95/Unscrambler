import java.io.Serializable;

@SuppressWarnings("serial")
public class LinkList <type extends Comparable <type> & Serializable>
implements Serializable {
	private class Node 
	implements Comparable <Node>, Serializable	{
		private type data;
		private Node next;
		
		public Node (type myData) {
			data = myData;
			next = null;
		}
		public Node (type myData, Node nextNode) {
			data = myData;
			next = nextNode;
		}
		
		public boolean equals (Object other) {
			if (other == null)
				return false;
			else if (getClass() != other.getClass())
				return false;
			else {
				@SuppressWarnings("unchecked")
				Node otherNode = (Node)other;
				return this.data.equals(otherNode.data);
			}	
		}
		
		public int compareTo (Node other) {
			if (other == null)
				throw new NullPointerException();
			else if (getClass() != other.getClass())
				throw new ClassCastException();
			else
				return this.data.compareTo(other.data);
		}
	}
	
	private Node top;
	private int size;
	
	public LinkList () {
		top = null;
		size = 0;
	}
	
	//assume index <= size, indices start at 0
	public type get (int index) {
		if (index >= size)
			return null;
		Node cur = top;
		for (int i = 0; i < index; i++)
			cur = cur.next;
		return cur.data;
	}
	
	//adds at the end
	public void add (type myData) {
		this.add(size, myData);
	}
	
	public void add (int index, type myData) {
		if (size == 0)
			top = new Node(myData);
		else if (index == 0) {
			Node cur = new Node (myData);
			cur.next = top;
			top = cur;
		} else if (index <= size) {
			Node cur = top;
			for (int i = 0; i < (index - 1); i++)
				cur = cur.next;
			cur.next = new Node (myData, cur.next);
		} else return;
		size++;
	}
	
	public type remove (int index) {
		type returnVal = this.get(index);
		if (returnVal == null) return null;
		else {
			if (index == 0) 
				top = top.next;
			else {
				Node cur = top;
				for (int i = 0; i < (index - 1); i++)
					cur = cur.next;
				cur.next = cur.next.next;
			}
			size--;
			return returnVal;
		}
	}
	
	public void set (int index, type myData) {
		this.remove(index);
		this.add(index, myData);
	}
	
	public int length() {
		return size;
	}
}