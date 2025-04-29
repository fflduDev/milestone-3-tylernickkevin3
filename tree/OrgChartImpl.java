package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrgChartImpl implements OrgChart {

	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();
	private GenericTreeNode<Employee> root = null;

	@Override
	public void addRoot(Employee e) {
		GenericTreeNode<Employee> rootEmployee = new GenericTreeNode<>(e);
		nodes.add(rootEmployee);
		root = rootEmployee;
	}

	@Override
	public void clear() {
		nodes = new ArrayList<>();
		root = null;
	}

	@Override
	public void addDirectReport(Employee manager, Employee newPerson) {
		for (GenericTreeNode<Employee> currentEmployee : nodes) {
			if (currentEmployee.data.equals(manager)) {
				GenericTreeNode<Employee> newE = new GenericTreeNode<>(newPerson);
				currentEmployee.addChild(newE);
				nodes.add(newE);
				break;
			}
		}
	}

	@Override
	public void removeEmployee(Employee firedPerson) {
		GenericTreeNode<Employee> firedNode = null;
		for (GenericTreeNode<Employee> node : nodes) {
			if (node.data.equals(firedPerson)) {
				firedNode = node;
				break;
			}
		}
		if (firedNode != null) {
			GenericTreeNode<Employee> parent = findParent(firedNode);
			if (parent != null) {
				parent.children.remove(firedNode);
				parent.children.addAll(firedNode.children);
			}
			nodes.remove(firedNode);
		}
	}


	private GenericTreeNode<Employee> findParent(GenericTreeNode<Employee> node) {
		for (GenericTreeNode<Employee> parentNode : nodes) {
			if (parentNode.children.contains(node)) {
				return parentNode;
			}
		}
		return null;
	}

	@Override
	public void showOrgChartDepthFirst() {
		if (root != null) {
			depthFirstTraversal(root);
		}
	}

	private void depthFirstTraversal(GenericTreeNode<Employee> node) {
		System.out.println("- " + node.data);
		for (GenericTreeNode<Employee> child : node.children) {
			depthFirstTraversal(child);
		}
	}

	@Override
	public void showOrgChartBreadthFirst() {
		if (root != null) {
			Queue<GenericTreeNode<Employee>> queue = new LinkedList<>();
			queue.add(root);
			while (!queue.isEmpty()) {
				int treeLevel = queue.size();
				for (int i = 0; i < treeLevel; i++) {
					GenericTreeNode<Employee> current = queue.poll();
					System.out.print(current.data);
					if (i < treeLevel - 1) {
						System.out.print(" - ");
					}
					for (GenericTreeNode<Employee> child : current.children) {
						queue.add(child);
					}
				}
				System.out.println();
			}
		}
	}
}