package Lab5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {
	
	static JPanel panel = new JPanel();
	static JButton browseBtn = new JButton("Browse");
	
	static JLabel caseLabel = new JLabel("Check for non Camel or Snake case names");
	static JCheckBox caseCheckbox = new JCheckBox("");
	
	static JLabel voidConstLabel = new JLabel("Method with no return called from constructor");
	static JCheckBox voidConstCheckbox = new JCheckBox("");
	
	static JLabel publicFieldsLabel = new JLabel("Instantiated classes with public fields");
	static JCheckBox publicFieldsCheckbox = new JCheckBox("");
	
	static JFileChooser fileChooser = new JFileChooser();
	static String folderOrFileAddress;
	
	public static void main(String[] args) {
		
		JFrame ui = new JFrame();
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setBounds(0, 0, 500, 300);
		
		panel.setLayout(null);
		ui.setContentPane(panel);
		
		browseBtn.setBounds(10, 10, 70, 40);
		panel.add(browseBtn);
		
		caseLabel.setBounds(130, 10, 300, 20);
		panel.add(caseLabel);
		caseCheckbox.setBounds(100, 10, 20, 20);
		panel.add(caseCheckbox);
		
		voidConstLabel.setBounds(130, 40, 300, 20);
		panel.add(voidConstLabel);
		voidConstCheckbox.setBounds(100, 40, 20, 20);
		panel.add(voidConstCheckbox);
		
		publicFieldsLabel.setBounds(130, 70, 300, 20);
		panel.add(publicFieldsLabel);
		publicFieldsCheckbox.setBounds(100, 70, 20, 20);
		panel.add(publicFieldsCheckbox);
		
		
		ui.setVisible(true);
		
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int selected = fileChooser.showOpenDialog(ui.getComponent(0));
				
				if (selected == JFileChooser.APPROVE_OPTION) {
					folderOrFileAddress = fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println("Selected folder: " + folderOrFileAddress);
					
					if(caseCheckbox.isSelected()) {
						CamelSnakeCase.search(folderOrFileAddress);
					}
					if(voidConstCheckbox.isSelected()) {
						VoidFunctionInConstructor.search(folderOrFileAddress);
					}
					if(publicFieldsCheckbox.isSelected()) {
						InstantiaedPublicFields.search(folderOrFileAddress);
					}
				}
			}
		});
		
		
	}

}
