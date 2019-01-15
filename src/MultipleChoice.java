import java.awt.Dialog;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MultipleChoice {

	private JDialog frmMultipleChoiceQuestion;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private ArrayList<JRadioButton> rdbtnAnswers = new ArrayList<>();
	private ArrayList<GridBagConstraints> gbc_rdbtnAnswers = new ArrayList<>();
	private JButton btnCitation;

	/**
	 * Launch the application.
	 */
	public String multipleChoiceWindow() {
		try {
			// Resize window based on content
			frmMultipleChoiceQuestion.pack();
			frmMultipleChoiceQuestion.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getSelectedButton().toString();
	}

	/**
	 * Create the application.
	 */
	public MultipleChoice(ReadYaml quiz) {
		initialize(quiz);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ReadYaml quiz) {
		String title = "Score: " + TestGui.SCORE + "/" + TestGui.ATTEMPTS + " - " + quiz.getTitle();
		frmMultipleChoiceQuestion = new JDialog(null, title, Dialog.ModalityType.APPLICATION_MODAL);
		frmMultipleChoiceQuestion.setLocationRelativeTo(null);
		frmMultipleChoiceQuestion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frmMultipleChoiceQuestion.getContentPane().setLayout(gridBagLayout);

		// This is the question
		String question = "<html>"+quiz.getQuestion().replaceAll("(\r\n|\n)", "<br />")+"</html>";
		JLabel label = new JLabel(question);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		frmMultipleChoiceQuestion.getContentPane().add(label, gbc_label);

		// These are the answers
		int i=0;
		for (String s : quiz.getAnswers()) {
			rdbtnAnswers.add(new JRadioButton(s));
			rdbtnAnswers.get(i).setActionCommand(String.valueOf(i));
			buttonGroup.add(rdbtnAnswers.get(i));
			gbc_rdbtnAnswers.add(new GridBagConstraints());
			gbc_rdbtnAnswers.get(i).anchor = GridBagConstraints.WEST;
			gbc_rdbtnAnswers.get(i).gridx = 0;
			gbc_rdbtnAnswers.get(i).gridy = i+1;
			frmMultipleChoiceQuestion.getContentPane().add(rdbtnAnswers.get(i), gbc_rdbtnAnswers.get(i));
			i++;
		}
		i++;
		// Confirm button
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new Confirm());
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = i;
		frmMultipleChoiceQuestion.getContentPane().add(btnConfirm, gbc_btnConfirm);

		// Hint button, will only be displayed if there is a hint
		if (quiz.getHintText() != null || quiz.getHintImage() != null) {
			JButton btnHint = new JButton("Hint");
			btnHint.addActionListener(new Hint(quiz));
			GridBagConstraints gbc_btnHint = new GridBagConstraints();
			gbc_btnHint.gridx = 1;
			gbc_btnHint.gridy = i;
			frmMultipleChoiceQuestion.getContentPane().add(btnHint, gbc_btnHint);
		}
		// Citation button
		if (quiz.getAuthor() != null && quiz.getUrl() != null) {
			btnCitation = new JButton("Citation");
			btnCitation.addActionListener(new Citation(quiz));
			GridBagConstraints gbc_btnCitation = new GridBagConstraints();
			gbc_btnCitation.gridx = 2;
			gbc_btnCitation.gridy = i;
			frmMultipleChoiceQuestion.getContentPane().add(btnCitation, gbc_btnCitation);
		}
	}

	// Get selected button
	private String getSelectedButton() {
		if (buttonGroup.getSelection() == null) return null;
		return buttonGroup.getSelection().getActionCommand();
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
			if (buttonGroup.getSelection() == null) {
				JOptionPane.showMessageDialog(null, "You must select an answer before continuing, try again!");
			} else {
				frmMultipleChoiceQuestion.dispose();
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
