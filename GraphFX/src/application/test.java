package application;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("unused")
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
		
		
//		for (int i = 0; i < 20; i++) {
//			System.out.println(i % 4);
//		}
		
		
//		Rectangle2D are = new Rectangle2D.Double();
//		Point2D p1 = new Point2D.Double(10, 10);
//		Point2D p2 = new Point2D.Double(10, 5);
//		are.setFrameFromDiagonal(p1, p2);
//		System.out.println(are);
//		
//		AffineTransform t = new AffineTransform();
//		AffineTransform s = AffineTransform.getTranslateInstance(-2, -3);
//		
//		System.out.println(s);
		
		
//		HashMap<String, String> whatev = new HashMap<String, String>();
//		
//		String test = whatev.get("hello");
//		
//		if (test == null) {
//			System.out.print("String is null");
//		}
//		
//		if (test == "") {
//			System.out.print("String is empty");
//		}
//		
		
//		MouseFrame frame = new MouseFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		
		
		
//		System.out.println(System.getProperty("user.dir"));
//		
//		Node node1 = new Node("n1");
//		Node node2 = new Node("n2");
//		
//		Node node3 = node1;
//		Node node4 = node2;
//		
//		node1 = null;
//		node2 = null;
//		
//		System.out.println(node3.toString());
//		System.out.println(node4.toString());
//		
//		if(node3 == null){
//			System.out.println("Node3 is null");
//		}else{
//			System.out.println("Node3 is not null");
//		}
//
//		String test1 = null;
//		
//		Node test2 = new Node("1");
//		Node test3 = new Node("2");
//		
//		HashSet<Node> nodelist = new HashSet<Node>();
//		
//		try{
//			nodelist.add(test2);
//			nodelist.add(test2);
//		}catch(Exception e){
//			System.out.println("Node already exists");
//		}
//		
//		UndirectedArc test4 = new UndirectedArc(test2, test3, "whatever");
//		
////		Node test5 = test4.getOtherNode(test3);
////		Node test6 = test4.getOtherNode(test2);
////		Node test7 = test4.getOtherNode(null);
//		HashSet<Node> list = test4.getNodes();
//		
//		Node test8 = new Node("3");
//		
//		HashSet<Node> tmpList = test4.getNodes();
//		
//		Iterator<Node> it = list.iterator();
//		while(it.hasNext()) {
//			tmpList.add(it.next());
//		}
//		
//
//		Node test9 = new Node("3");
//		Node test10 = new Node("4");
//
//		list.add(test8);
//		list.add(test9);
//		list.add(test10);
//		
//		list.remove(test8);
//		list.remove(test8);
//		
//		
////		list.removeAll(tmpList);
//		
//		
////		list.remove(test8);
////		list.remove(test5);
////		list.remove(null);
//		
//		it = list.iterator();
//		while(it.hasNext()) {
//			if(it.next().equals(test8) || it.next().equals(test9)){
//				it.remove();
//			}
//		}
//		
////		@SuppressWarnings("unused")
////		Arc tmp3 = test2.getArc(test8);
////		tmp3 = test2.getArc(test3);
//		
//		@SuppressWarnings("unused")
//		Node test11;
	}
	
}


//class MouseFrame extends JFrame {
//	
//	public static final int DEFAULT_WIDTH = 500;
//	public static final int DEFAULT_HEIGHT = 500;
//	
//	public MouseFrame() {
//		setTitle("MouseTest");
//		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//		
//		DrawCanvas component = new DrawCanvas();
//		add(component);
//		
//		addMouseListener(component);
//		addMouseMotionListener(component);
//	}
//
//}
