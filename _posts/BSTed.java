package dict02;

import java.util.Scanner;

public class BSTed {
	
	public class Node {
		private String word;
		private String meaning;
		private Node left;
		private Node right;

		/* ������ */
		public Node(String word, String meaning) {
			this.setWord(word);
			this.setMeaning(meaning);
			setLeft(null);
			setRight(null);
		}

		/* ���� getter / setter */

		public Node getLeft() {	return left; }
		public void setLeft(Node left) { this.left = left; }
		public Node getRight() { return right; }
		public void setRight(Node right) {	this.right = right; }
		public String getWord() {	return word; }
		public void setWord(String newWord) { this.word = newWord;	}
		public String getMeaning() {	return meaning;}
		public void setMeaning(String newMeaning) {	this.meaning = newMeaning;	}
	}

	public Node root;

	BSTed() {
		root = null;
	}

	/* Ž�� ���� */
	public boolean find(String word) {
		Node currentNode = root;
		while (currentNode != null) {
			// ���� ���� ã�� ���� ������
			if (currentNode.getWord().equals(word)) {
				System.out.println("["+word+" �˻� �Ϸ�]");
				System.out.println("�ܾ�:"+word);
				System.out.println("�ǹ�:"+root.meaning);
				System.out.println("");
				return true;
			} else if (currentNode.getWord().compareTo(word) < 0) { // ã�� ���� ���� ��庸�� ������
				currentNode = currentNode.getLeft();
			} else {// ã�� ���� ���� ��庸�� ũ��
				currentNode = currentNode.getRight();
			}
		}
		System.out.println("ã�� Ű�� �����ϴ�.");
		return false;
	}

	/* ���� ���� */
	public void insert(String word, String meaning) {
		Node newNode = new Node(word, meaning); // ����, ������ �ڽ� ��尡 null �̸� data �� ���� ����� �� ��� ����
		if (root == null) {// ��Ʈ ��尡 ������, �� Ʈ���� ����ִ� ������ ��,
			root = newNode;
			System.out.println(word+" �Է� �Ϸ�");
			return;
		}
		Node currentNode = root;
		Node parentNode = null;

		while (true) {

			parentNode = currentNode;

			if (currentNode.getWord().compareTo(word) < 0) { // �ش� ��庸�� Ŭ ��.
				currentNode = currentNode.getLeft();
				if (currentNode == null) {
					parentNode.setLeft(newNode);
					return;
				}
			} else { // �ش� ��庸�� ���� ��.
				currentNode = currentNode.getRight();
				if (currentNode == null) {
					parentNode.setRight(newNode);
					return;
				}
			}
		}

	}
	
	/* ��� ���� */
	public void display(Node root) {
		
		if(root != null) {
			display(root.right);
			System.out.println(root.getWord()+" : "+root.getMeaning());
			display(root.left);
			
		}
	}
	
	
	/* ���� ���� */
	public boolean delete(String word) {
		Node parentNode = root;
		Node currentNode = root;
		boolean isLeftChild = false;

		while (currentNode.getWord().compareTo(word) != 0) {
			parentNode = currentNode;
			if (currentNode.getWord().compareTo(word) < 0) {
				isLeftChild = true;
				currentNode = currentNode.getLeft();
			} else {
				isLeftChild = false;
				currentNode = currentNode.getRight();
			}
			if (currentNode == null) {
				return false;
			}
		}

		if (currentNode.getLeft() == null && currentNode.getRight() == null) { // 1. �ڽ� ��尡 ���� ���
			if (currentNode == root) {
				root = null; // �ش� ��尡 root node�� ���
			}
			if (isLeftChild) {
				parentNode.setLeft(null); // ������ ��尡 �θ� ����� ���� �ڽ��� ���
			} else {
				parentNode.setRight(null); // ������ ��尡 �θ� ����� ������ �ڽ��� ���
			}
		} else if (currentNode.getRight() == null) { // 2-1. �ڽ� ��尡 �ϳ��� ���(������ �ڽ��� ���� ��)
			parentNode.setLeft(currentNode.getLeft());
		} else if (currentNode.getLeft() == null) { // 2-2. �ڽ� ��尡 �ϳ��� ���(���� �ڽ��� ���� ��)
			parentNode.setRight(currentNode.getRight());
		} else { // 3. �ڽ��� ���� ���
			Node minimum = getMinumun(currentNode);
			if (currentNode == root) {
				root = minimum;
			} else if (isLeftChild) {
				parentNode.setLeft(minimum);
			} else {
				parentNode.setLeft(currentNode.getLeft());
			}
			minimum.setLeft(currentNode.getLeft());
		}
		return false;
	}

	Node getMinumun(Node deleteNode) {
		Node minimum = null;
		Node minimumParent = null;
		Node currentNode = deleteNode.getRight();
		while (currentNode != null) {
			minimumParent = minimum;
			minimum = minimumParent;
			currentNode = currentNode.getLeft();
		}
		if (minimum != deleteNode.getRight()) {
			minimumParent.setLeft(minimum.getRight());
			minimum.setRight(deleteNode.getRight());
		}
		return minimum;
	}

	public static void menu() {
		System.out.println("*-- ���� Ž�� Ʈ�� ���� ���� --*");
		System.out.println("1 : �Է�");
		System.out.println("2 : ����");
		System.out.println("3 : �˻�");
		System.out.println("4 : ���");
		System.out.println("exit : ����");
		System.out.println("*------------------------*");
		System.out.print("������ ��ȣ �Է� >>");
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BSTed tree = new BSTed();
		String choice;
		String word, meaning;

		while (true) {
			menu();
			choice = sc.nextLine();

			if (choice.equals("exit")) {
				break;
			}
			switch (choice) {
			case "1":
				System.out.print("�ܾ� : ");
				word = sc.nextLine();
				System.out.print("�ǹ� : ");
				meaning = sc.nextLine();
				// e = new Element(word, meaning);
				tree.insert(word, meaning);
				break;
			case "2":
				System.out.print("������ �ܾ� : ");
				word = sc.nextLine();
				tree.delete(word);
				break;
			case "3":
				System.out.println("������ Ž���մϴ�.");
				System.out.print("�˻� �� �ܾ �Է��ϼ��� : ");
				word = sc.nextLine();
				tree.find(word);
				break;
			case "4":
				System.out.println("������ ����մϴ�.");
				tree.display(tree.root);;
				
				break;
			default:
				System.out.println("���� ������ �Դϴ�. �ٽ� �����ϼ���!");
				break;
			}
		}
	}
}

