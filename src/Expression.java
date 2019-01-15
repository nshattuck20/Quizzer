import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

//import bsh.EvalError;
import bsh.Interpreter;

public class Expression {
	static Interpreter evaluater = new Interpreter();

	private double floater(int j) {
		double floating = 0.00;
		int floatChooser = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		switch (floatChooser) {
		case 0:
			floating = 0.0;
			break;
		case 1:
			floating = 0.25;
			break;
		case 2:
			floating = 0.5;
			break;
		case 3:
			floating = 0.75;
			break;
		}
		return j + floating;
	}

	private String expressionrandommer(int i) {
		int operant = 0;
		String sign = null;
		if (i == 1) {
			return "";
		} else
			operant = ThreadLocalRandom.current().nextInt(0, 4 + 1);

		switch (operant) {
		case 0:
			sign = "+";
			break;
		case 1:
			sign = "-";
			break;
		case 2:
			sign = "/";
			break;
		case 3:
			sign = "%";
			break;
		case 4:
			sign = "*";
			break;

		}

		return sign + " ";
	}

	public boolean eval(int difficulty) {
		String result = "";
		String answer = null;
		int currentnumber = 0;
		String currentoperand = null;
		boolean firsttime = true;
		int floatOrNot = 0;
		int concatOrNot = 0;
		boolean concatinateStarted = false;
		boolean concatinateEnded = false;
		boolean bracketStarted = false;
		boolean bracketEnded = false;
		boolean floatused = false;
		int difficultyHolder = difficulty;
		while (difficulty > 0) {
			currentnumber = ThreadLocalRandom.current().nextInt(0, 99 + 1);
			if (firsttime) {
				firsttime = false;
			}
			currentoperand = expressionrandommer(difficulty);

			// decide if we are going to add floating number
			floatOrNot = ThreadLocalRandom.current().nextInt(0, 6 + 1);
			if (floatOrNot == 6 && difficultyHolder > 6) {
				result += floater(currentnumber) + " " + currentoperand;
				--difficulty;
				floatused = true;
				continue;
			}

			// Add concatenation with quotation marks
			if (!concatinateEnded && difficulty > 1 && difficulty != difficultyHolder && !concatinateStarted
					&& difficultyHolder > 6) {
				concatOrNot = ThreadLocalRandom.current().nextInt(0, 4 + 1);
				if (!concatinateStarted && concatOrNot == 4) {
					result = result.substring(0, result.length() - 2);
					result += "+\"" + currentnumber + " " + currentoperand + ""
							+ ThreadLocalRandom.current().nextInt(0, 99 + 1) + "\"+";
					concatinateStarted = true;
					concatinateEnded = true;
					--difficulty;
					--difficulty;
					continue;
				}
			}

			if (!bracketEnded && difficulty > 1 && difficulty != difficultyHolder && !bracketStarted
					&& difficultyHolder > 6) {
				concatOrNot = ThreadLocalRandom.current().nextInt(0, 4 + 1);
				if (!concatinateStarted && concatOrNot == 4) {
					// result = result.substring(0, result.length() - 2);
					result += "(" + currentnumber + " " + currentoperand + " "
							+ ThreadLocalRandom.current().nextInt(0, 99 + 1) + ")" + expressionrandommer(difficulty);
					bracketStarted = true;
					bracketEnded = true;
					--difficulty;
					--difficulty;
					continue;
				}
			} else {
				result += currentnumber + " " + currentoperand;
			}

			--difficulty;
		}

		try {
			if (floatused) {
				result = result.replaceAll("/", "+");
			}
			String myWeirdExpression = result.replaceAll("\"", "\\\"");
			answer = (String) evaluater.eval(myWeirdExpression);
		} catch (Exception e) {

			return eval(difficultyHolder);
		}
		String output = "What is the output of the following expression?\n\n"+result+"\n\n";
		String userInput = JOptionPane.showInputDialog(null, output, "Input your integer");
		return userInput.equals(answer);
	}
}
