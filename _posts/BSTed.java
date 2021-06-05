package dict02;

import java.util.Scanner;

public class BSTed {
	
	public class Node {
		private String word;
		private String meaning;
		private Node left;
		private Node right;

		/* 생성자 */
		public Node(String word, String meaning) {
			this.setWord(word);
			this.setMeaning(meaning);
			setLeft(null);
			setRight(null);
		}

		/* 각종 getter / setter */

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

	/* 탐색 연산 */
	public boolean find(String word) {
		Node currentNode = root;
		while (currentNode != null) {
			// 현재 노드와 찾는 값이 같으면
			if (currentNode.getWord().equals(word)) {
				System.out.println("["+word+" 검색 완료]");
				System.out.println("단어:"+word);
				System.out.println("의미:"+root.meaning);
				System.out.println("");
				return true;
			} else if (currentNode.getWord().compareTo(word) < 0) { // 찾는 값이 현재 노드보다 작으면
				currentNode = currentNode.getLeft();
			} else {// 찾는 값이 현재 노드보다 크면
				currentNode = currentNode.getRight();
			}
		}
		System.out.println("찾는 키가 없습니다.");
		return false;
	}

	/* 삽입 연산 */
	public void insert(String word, String meaning) {
		Node newNode = new Node(word, meaning); // 왼쪽, 오른쪽 자식 노드가 null 이며 data 의 값이 저장된 새 노드 생성
		if (root == null) {// 루트 노드가 없을때, 즉 트리가 비어있는 상태일 때,
			root = newNode;
			System.out.println(word+" 입력 완료");
			return;
		}
		Node currentNode = root;
		Node parentNode = null;

		while (true) {

			parentNode = currentNode;

			if (currentNode.getWord().compareTo(word) < 0) { // 해당 노드보다 클 떄.
				currentNode = currentNode.getLeft();
				if (currentNode == null) {
					parentNode.setLeft(newNode);
					return;
				}
			} else { // 해당 노드보다 작을 때.
				currentNode = currentNode.getRight();
				if (currentNode == null) {
					parentNode.setRight(newNode);
					return;
				}
			}
		}

	}
	
	/* 출력 연산 */
	public void display(Node root) {
		
		if(root != null) {
			display(root.right);
			System.out.println(root.getWord()+" : "+root.getMeaning());
			display(root.left);
			
		}
	}
	
	
	/* 삭제 연산 */
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

		if (currentNode.getLeft() == null && currentNode.getRight() == null) { // 1. 자식 노드가 없는 경우
			if (currentNode == root) {
				root = null; // 해당 노드가 root node일 경우
			}
			if (isLeftChild) {
				parentNode.setLeft(null); // 삭제할 노드가 부모 노드의 왼쪽 자식일 경우
			} else {
				parentNode.setRight(null); // 삭제할 노드가 부모 노드의 오른쪽 자식일 경우
			}
		} else if (currentNode.getRight() == null) { // 2-1. 자식 노드가 하나인 경우(오른쪽 자식이 없을 때)
			parentNode.setLeft(currentNode.getLeft());
		} else if (currentNode.getLeft() == null) { // 2-2. 자식 노드가 하나인 경우(왼쪽 자식이 없을 떄)
			parentNode.setRight(currentNode.getRight());
		} else { // 3. 자식이 둘인 경우
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
		System.out.println("*-- 이진 탐색 트리 영어 사전 --*");
		System.out.println("1 : 입력");
		System.out.println("2 : 삭제");
		System.out.println("3 : 검색");
		System.out.println("4 : 출력");
		System.out.println("exit : 종료");
		System.out.println("*------------------------*");
		System.out.print("실행할 번호 입력 >>");
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
				System.out.print("단어 : ");
				word = sc.nextLine();
				System.out.print("의미 : ");
				meaning = sc.nextLine();
				// e = new Element(word, meaning);
				tree.insert(word, meaning);
				break;
			case "2":
				System.out.print("삭제할 단어 : ");
				word = sc.nextLine();
				tree.delete(word);
				break;
			case "3":
				System.out.println("사전을 탐색합니다.");
				System.out.print("검색 할 단어를 입력하세요 : ");
				word = sc.nextLine();
				tree.find(word);
				break;
			case "4":
				System.out.println("사전을 출력합니다.");
				tree.display(tree.root);;
				
				break;
			default:
				System.out.println("없는 선택지 입니다. 다시 선택하세요!");
				break;
			}
		}
	}
}

