import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.border.Border;

public class InputBox {

	private JDialog frame;
	private JLabel lblQuestionBox;
	private JButton btnCitation;
	private JButton btnConfirm;
	private JButton btnHint;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public String inputBoxWindow() {
		try {
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textArea.getText();
	}

	/**
	 * Create the application.
	 */
	public InputBox(ReadYaml quiz) {
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
		lblQuestionBox = new JLabel(question);
		GridBagConstraints gbc_lblQuestionBox = new GridBagConstraints();
		gbc_lblQuestionBox.gridheight = 4;
		gbc_lblQuestionBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblQuestionBox.gridwidth = 2;
		gbc_lblQuestionBox.gridx = 0;
		gbc_lblQuestionBox.gridy = 0;
		frame.getContentPane().add(lblQuestionBox, gbc_lblQuestionBox);

		// This is the input box
		textArea = new JTextArea();
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		textArea.setBorder(border);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 2;
		gbc_textArea.gridheight = 4;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 4;
		frame.getContentPane().add(textArea, gbc_textArea);

		// Confirm button
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new Confirm());
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = 8;
		frame.getContentPane().add(btnConfirm, gbc_btnConfirm);

		// Hint button, will only be displayed if there is a hint
		if (quiz.getHintText() != null || quiz.getHintImage() != null) {
			btnHint = new JButton("Hint");
			btnHint.addActionListener(new Hint(quiz));
			GridBagConstraints gbc_btnHint = new GridBagConstraints();
			gbc_btnHint.gridx = 1;
			gbc_btnHint.gridy = 8;
			frame.getContentPane().add(btnHint, gbc_btnHint);
		}
		// Citation button
		if (quiz.getAuthor() != null && quiz.getUrl() != null) {
			btnCitation = new JButton("Citation");
			btnCitation.addActionListener(new Citation(quiz));
			GridBagConstraints gbc_btnCitation = new GridBagConstraints();
			gbc_btnCitation.gridx = 2;
			gbc_btnCitation.gridy = 8;
			frame.getContentPane().add(btnCitation, gbc_btnCitation);
		}
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
	// Confirm button
	private class Confirm implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (textArea.getText().equals("")) {
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
