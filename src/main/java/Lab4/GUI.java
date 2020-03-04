package Lab4;

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
	
	static JLabel pscLabel = new JLabel("Public Static Caller in AST");
	static JCheckBox pscCheckbox = new JCheckBox("");
	
	static JLabel fourArgsLabel = new JLabel("Classes with method with more than 4 args");
	static JCheckBox fourArgsCheckbox = new JCheckBox("");
	
	static JLabel twoTryLabel = new JLabel("Classes with more than 2 try blocks");
	static JCheckBox twoTryCheckbox = new JCheckBox("");
	
	static JLabel tryNoCatchLabel = new JLabel("Try block no catch or returns null");
	static JCheckBox tryNoCatchCheckbox = new JCheckBox("");
	
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
		
		pscLabel.setBounds(130, 10, 300, 20);
		panel.add(pscLabel);
		pscCheckbox.setBounds(100, 10, 20, 20);
		panel.add(pscCheckbox);
		
		fourArgsLabel.setBounds(130, 40, 300, 20);
		panel.add(fourArgsLabel);
		fourArgsCheckbox.setBounds(100, 40, 20, 20);
		panel.add(fourArgsCheckbox);
		
		twoTryLabel.setBounds(130, 70, 300, 20);
		panel.add(twoTryLabel);
		twoTryCheckbox.setBounds(100, 70, 20, 20);
		panel.add(twoTryCheckbox);
		
		tryNoCatchLabel.setBounds(130, 100, 300, 20);
		panel.add(tryNoCatchLabel);
		tryNoCatchCheckbox.setBounds(100, 100, 20, 20);
		panel.add(tryNoCatchCheckbox);
		
		ui.setVisible(true);
		
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int selected = fileChooser.showOpenDialog(ui.getComponent(0));
				
				if (selected == JFileChooser.APPROVE_OPTION) {
					folderOrFileAddress = fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println(folderOrFileAddress);
					
					if(pscCheckbox.isSelected()) {
						PublicStaticSolver.search(folderOrFileAddress);
					}
					if(fourArgsCheckbox.isSelected()) {
						FourArgumentsSolver.search(folderOrFileAddress);
					}
					if(twoTryCheckbox.isSelected()) {
						TwoTryBlockSolver.search(folderOrFileAddress);
					}
					if(tryNoCatchCheckbox.isSelected()) {
						TryBlockNoCatchOrNull.search(folderOrFileAddress);
					}
				}
			}
		});
		
		
	}

}
