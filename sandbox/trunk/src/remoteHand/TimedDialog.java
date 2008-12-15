package remoteHand;

import javax.swing.JOptionPane;

public class TimedDialog implements Runnable {

	private String message;
	private String title;
	private boolean result;

	static boolean show(String message, String title, int time) {
		Thread thread;
		TimedDialog dialog = new TimedDialog(message, title);
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

	private TimedDialog(String message, String title) {
		this.message = message;
		this.title = title;
		this.result = true;
	}

	private boolean getResult() {
		return this.result;
	}

	private void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public void run() {
		int answer = JOptionPane.showConfirmDialog(null, message, title,
				JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			setResult(true);
		} else {
			setResult(false);
		}
	}

}
