import javax.swing.JDialog;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class CheckBox {

	private JDialog frame;
	private JLabel jlabel;
	private ArrayList<JCheckBox> chkbxAnswers = new ArrayList<>();
	private ArrayList<GridBagConstraints> gbc_chkbxAnswers = new ArrayList<>();
	private JButton btnCitation;
	private JButton btnConfirm;
	private JButton btnHint;

	/**
	 * Launch the application.
	 */
	public String checkBoxWindow() {
		try {
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getSelectedBoxes().toString();
	}

	/**
	 * Create the application.
	 */
	public CheckBox(ReadYaml quiz) {
		initialize(quiz);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ReadYaml quiz) {
		String title = "Score: " + TestGui.SCORE + "/" + TestGui.ATTEMPTS + " - " + quiz.getTitle();
		frame = new JDialog(null, title, Dialog.ModalityType.APPLICATION_MODAL);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);

		// This is the question
		String question = "<html>"+quiz.getQuestion().replaceAll("(\r\n|\n)", "<br />")+"</html>";
		jlabel = new JLabel(question);
		GridBagConstraints gbc_jlabel = new GridBagConstraints();
		gbc_jlabel.gridx = 0;
		gbc_jlabel.gridy = 0;
		frame.getContentPane().add(jlabel, gbc_jlabel);

		// These are the answers
		int i=0;
		for (String s : quiz.getAnswers()) {
			chkbxAnswers.add(new JCheckBox(s));
			chkbxAnswers.get(i).setActionCommand(String.valueOf(i));
			gbc_chkbxAnswers.add(new GridBagConstraints());
			gbc_chkbxAnswers.get(i).anchor = GridBagConstraints.WEST;
			gbc_chkbxAnswers.get(i).gridx = 0;
			gbc_chkbxAnswers.get(i).gridy = i+1;
			frame.getContentPane().add(chkbxAnswers.get(i), gbc_chkbxAnswers.get(i));
			i++;
		}
		i++;

		// Confirm button
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new Confirm());
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = i;
		frame.getContentPane().add(btnConfirm, gbc_btnConfirm);

		// Hint button, will only be displayed if there is a hint
		if (quiz.getHintText() != null || quiz.getHintImage() != null) {
			btnHint = new JButton("Hint");
			btnHint.addActionListener(new Hint(quiz));
			GridBagConstraints gbc_btnHint = new GridBagConstraints();
			gbc_btnHint.gridx = 1;
			gbc_btnHint.gridy = i;
			frame.getContentPane().add(btnHint, gbc_btnHint);
		}
		
		// Citation button
		if (quiz.getAuthor() != null && quiz.getUrl() != null) {
			btnCitation = new JButton("Citation");
			btnCitation.addActionListener(new Citation(quiz));
			GridBagConstraints gbc_btnCitation = new GridBagConstraints();
			gbc_btnCitation.gridx = 2;
			gbc_btnCitation.gridy = i;
			frame.getContentPane().add(btnCitation, gbc_btnCitation);
		}
	}

	// Get selected button
	private ArrayList<Integer> getSelectedBoxes() {
		ArrayList<Integer> selectedBoxes = new ArrayList<Integer>();
		for (int i = 0; i < chkbxAnswers.size(); i++) {
			if (chkbxAnswers.get(i).isSelected()) {
				selectedBoxes.add(Integer.parseInt(chkbxAnswers.get(i).getActionCommand()));
			}
		}
		return selectedBoxes;
	}
	// Citation button
	private class Citation implements ActionListener {
		ReadYaml quiz;
		public Citation(ReadYaml quiz) {
			this.quiz = quiz;
		}
		public void actionPerformed(ActionEvent arg0) {
			JLabel website = new JLabel();
			website.setText("<html>Author: "+quiz.getAuthor()+"<br>Quiz: "+quiz.getQuizTitle()+"<br>URL: "+quiz.getUrl()+"</html>");
			JOptionPane.showMessageDialog(null, website, "Citation", JOptionPane.PLAIN_MESSAGE);
		}
	}
	// Confirm action listener
	private class Confirm implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (getSelectedBoxes().size() == 0) {
				JOptionPane.showMessageDialog(null, "You must select an answer before continuing, try again!");
			} else {
				frame.dispose();
			}
		}
	}
	// Display a hint
	private class Hint implements ActionListener {
		ReadYaml quiz;
		public Hint(ReadYaml quiz) {
			this.quiz = quiz;
		}

		public void actionPerformed(ActionEvent arg0) {
			String hint = (quiz.getHintText() == null ? null : "<html>" + quiz.getHintText().replaceAll("(\r\n|\n)", "<br />") + "<html/>");
			JLabel label = new JLabel(hint);
			if (quiz.getHintImage() != null) {
				label.setIcon(new ImageIcon(this.getClass().getResource(quiz.getHintImage())));
			}
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			JOptionPane.showMessageDialog(null, label, "Hint", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
