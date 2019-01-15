import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DifficultySelection {
	private String project = "Quiz Generator";
	private String authors = "Created by team {Algoridmz}:<br>Seyed Madadi, Robert Schmitz, Nicholas Shattuck";
	private JDialog frame;
	private JRadioButton rdbtnCsintermediate;
	private JRadioButton rdbtnCsnovice;
	private final static ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public int DifficultySelectionWindow() {
		try {
			DifficultySelection window = new DifficultySelection();
			window.frame.pack();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getSelectedButton();
	}

	/**
	 * Create the application.
	 */
	public DifficultySelection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog(null, "Select Difficulty", Dialog.ModalityType.APPLICATION_MODAL);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		//frame.getContentPane().setLayout(new GridLayout(3, 0, 0, 0));
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel label = new JLabel("<html><div style='text-align: center'><h1>"+project+"</h1><h3>"+authors+"</h3></div></html>");
		frame.getContentPane().add(label);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.CENTER;
		gbc_label.gridwidth = 2;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		frame.getContentPane().add(label, gbc_label);
		
		rdbtnCsnovice = new JRadioButton("CS142 (Novice)");
		rdbtnCsnovice.addActionListener(new RdbtnCsnoviceActionListener());
		rdbtnCsnovice.setActionCommand("1");
		buttonGroup.add(rdbtnCsnovice);
		GridBagConstraints gbc_rdbtnCsnovice = new GridBagConstraints();
		gbc_rdbtnCsnovice.gridx = 0;
		gbc_rdbtnCsnovice.gridy = 1;
		frame.getContentPane().add(rdbtnCsnovice, gbc_rdbtnCsnovice);
		//frame.getContentPane().add(rdbtnCsnovice);

		rdbtnCsintermediate = new JRadioButton("CS143 (Intermediate)");
		rdbtnCsintermediate.addActionListener(new RdbtnCsintermediateActionListener());
		rdbtnCsintermediate.setActionCommand("2");
		buttonGroup.add(rdbtnCsintermediate);
		GridBagConstraints gbc_rdbtnCsintermediate = new GridBagConstraints();
		gbc_rdbtnCsintermediate.gridx = 1;
		gbc_rdbtnCsintermediate.gridy = 1;
		frame.getContentPane().add(rdbtnCsintermediate, gbc_rdbtnCsintermediate);
		//frame.getContentPane().add(rdbtnCsintermediate);
	}

	private class RdbtnCsnoviceActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			while (true) {
				int ret = JOptionPane.showConfirmDialog(null,
						"Continue with CS142 (Novice) difficulty?\n(select 'OK' to confirm, or 'Cancel' to select again)",
						"Confirm Difficulty", JOptionPane.OK_CANCEL_OPTION);
				switch (ret) {
				case JOptionPane.OK_OPTION :
					break;
				case JOptionPane.CANCEL_OPTION :
					return;
				case JOptionPane.CLOSED_OPTION :
					System.exit(0);
					break;
				}
				break;
			}
			frame.dispose();
		}
	}
	private class RdbtnCsintermediateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			while (true) {
				int ret = JOptionPane.showConfirmDialog(null,
						"Continue with CS143 (Intermediate) difficulty?\n(select 'OK' to confirm, or 'Cancel' to select again)",
						"Confirm Difficulty", JOptionPane.OK_CANCEL_OPTION);
				switch (ret) {
				case JOptionPane.OK_OPTION :
					break;
				case JOptionPane.CANCEL_OPTION :
					return;
				case JOptionPane.CLOSED_OPTION :
					System.exit(0);
					break;
				}
				break;
			}
			frame.dispose();
		}
	}
	// Get selected button
	private static int getSelectedButton() {
		if (buttonGroup.getSelection() == null) return -1;
		return Integer.parseInt(buttonGroup.getSelection().getActionCommand());
	}
}
