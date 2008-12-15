package remoteHand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class TimedDialog implements Runnable {

	private String message;
	private String title;
	private boolean result;
	private int time;

	static boolean show(String message, String title, int time) {
		Thread thread;
		TimedDialog dialog = new TimedDialog(message, title, time);
		thread = new Thread(dialog);
		thread.start();
		try {
			thread.join(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			thread.interrupt();
		}
		return dialog.getResult();
	}

	private TimedDialog(String message, String title, int time) {
		this.message = message;
		this.title = title;
		this.result = true; // expected result if user does not respond
		this.time = time;
	}

	private boolean getResult() {
		return this.result;
	}

	private void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public void run() {
		JLabel messageLabel = new JLabel(message);
		Object components[] = {messageLabel};

		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage(components);
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		optionPane.setOptionType(JOptionPane.YES_NO_CANCEL_OPTION);
		final JDialog dialog = optionPane.createDialog(null, title
				+ String.valueOf(time / 1000 - 1));

		// starting timer
		ActionListener actionListener = new ActionListener() {
			int count = 0;
			public void actionPerformed(ActionEvent actionEvent) {
				count++;
				Timer timer = (Timer) actionEvent.getSource();
				dialog.setTitle(title
						+ String.valueOf((time / 1000 - 1)
								- (timer.getDelay() * count / 1000)));
			}
		};
		Timer timer = new Timer(1000, actionListener);
		timer.start();
		dialog.setVisible(true);

		// waiting for user to answer

		timer.stop();
		dialog.dispose();
		Object answer = optionPane.getValue();
		if (answer == null || answer.equals(JOptionPane.NO_OPTION)
				|| answer.equals(JOptionPane.CANCEL_OPTION)) {
			setResult(false);
		} else {
			setResult(true);
		}
	}
}
