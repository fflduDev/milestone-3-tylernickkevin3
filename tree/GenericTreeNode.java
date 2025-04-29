package tree;
import java.util.ArrayList;

public class GenericTreeNode<E> {
	E data;
	//<some list of children>
	ArrayList<GenericTreeNode<E>> children;

	public GenericTreeNode(E theItem) {
		data = theItem;
		children = new ArrayList<>();
	}

	public void addChild(GenericTreeNode<E> theItem) {
		children.add(theItem);
	}

	public void removeChild(E theItem) {
		for (GenericTreeNode<E> child : children) {
			if (child.data.equals(theItem)) {
				children.remove(child);
				if (!child.children.isEmpty()) {
					children.addAll(child.children);
				}
				break;
			}
		}
	}
}
