import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TestGui {
	private static String difficulty;
	public static int SCORE;
	public static int ATTEMPTS;

	// Display final score for user
	private static void finalScore() {
		int overallScore = (SCORE == 0 ? 0 : SCORE*100/ATTEMPTS);
		String msg="Your final score is " + overallScore + "%, ";
		if (overallScore >= 80) {
			msg+="great job!";
		} else {
			msg+="keep studying...";
		}
		JLabel label = new JLabel(msg);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		JOptionPane.showMessageDialog(null, label, "Overall Score", JOptionPane.PLAIN_MESSAGE);

	}
	// Randomizer helper
	public static void randomizer() throws FileNotFoundException {
		ArrayList<String> files = selectDifficulty();
		randomizer(files);
	}
	// Recursively selects a random quiz to run
	private static void randomizer(ArrayList<String> files) {
		Random rand = new Random();
		int index = rand.nextInt(files.size());
		String file = files.get(index);
		files.remove(index);
		try {
			ReadYaml quiz = new ReadYaml(file);
			switch (quiz.getType()) {
			case 1 :  // Multiple Choice Question
				MultipleChoice question = new MultipleChoice(quiz);
				if (quiz.checkAnswer(question.multipleChoiceWindow())) {
					SCORE++;
				}
				break;
			case 2 :  // Input Box Question
				InputBox question2 = new InputBox(quiz);
				if (quiz.checkAnswer(question2.inputBoxWindow())) {
					SCORE++;
				}
				break;
			case 3 :  // Check Box Question
				CheckBox question3 = new CheckBox(quiz);
				if (quiz.checkAnswer(question3.checkBoxWindow())) {
					SCORE++;
				}
				break;
			default :  // Random Expression Generator
				Expression question4 = new Expression();
				if ((difficulty.equals("cs143") ? question4.eval(9) : question4.eval(7))) {
					SCORE++;
				}
			}
			ATTEMPTS++;
			if (files.size() == 0)  {
				finalScore();
			} else {
				randomizer(files);
			}
		} catch (Exception e) {
			finalScore();
		}
	}
	// Select a difficulty level
	private static ArrayList<String> selectDifficulty() throws FileNotFoundException {
		DifficultySelection lvl = new DifficultySelection();
		switch (lvl.DifficultySelectionWindow()) {
		case 1:
			difficulty="cs142";
			break;
		case 2:		
			difficulty="cs143";
			break;
		}
		ArrayList<String> tmp= new ArrayList<>();
		// This is a ugly hack, we can't figure out how to list the directory contents
		Scanner s = new Scanner(TestGui.class.getResourceAsStream(difficulty+".files"));
		while (s.hasNext()) {
			tmp.add(s.next());
		}
		s.close();
		return tmp;
	}
	//	public static void main(String[] args) {
	public static void main(String[] args) {
		// Kick off quizzes
		try {
			randomizer();
		} catch (FileNotFoundException e) {
			System.out.println("oops!");
		}
	}

}
