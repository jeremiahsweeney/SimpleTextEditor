import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class SimpleTextEditor {
	private JTextArea mainTextArea;
	private JCheckBoxMenuItem wrapMenuItem;
	private JFrame frame;
	
	public static void main (String[] args) {
		SimpleTextEditor text = new SimpleTextEditor();
		text.gui();
	}
	
	public void gui() { // add font selector?  
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		
		frame = new JFrame("Simple Text Editor");
		JPanel panel = new JPanel(new BorderLayout());
		mainTextArea = new JTextArea(5,20);
		
		JScrollPane scrollbar = new JScrollPane(mainTextArea);
		scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollbar);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new NewMenuListener());
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(saveMenuItem);
		
		JMenu formatMenu = new JMenu("Format");
		wrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
		wrapMenuItem.addItemListener(new WrapMenuListener());
		formatMenu.add(wrapMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(formatMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}	
	
	private void save(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String line : mainTextArea.getText().split("\n")){
				writer.write(line);
				writer.newLine();	
			}
			writer.close();
		} catch (IOException ex) {ex.printStackTrace();}
	}
	
	private void open(File file) { 
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			mainTextArea.setText("");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				mainTextArea.append(line+"\n");
			}
			reader.close();
		} catch (IOException ex) {ex.printStackTrace();}
	}
	
	public class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainTextArea.setText("");
		}
	}
	
	public class OpenMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser openFile = new JFileChooser();
			openFile.showOpenDialog(frame);
			open(openFile.getSelectedFile());
		}
	}
	
	public class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser saveFile = new JFileChooser();
			saveFile.showSaveDialog(frame);
			save(saveFile.getSelectedFile());
		}
	}
	
	public class WrapMenuListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (wrapMenuItem.isSelected()) {
				mainTextArea.setLineWrap(true);
			} else {
				mainTextArea.setLineWrap(false);
			} 
		}
	}
}
