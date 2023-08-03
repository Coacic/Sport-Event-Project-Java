package sportsEvents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ProgressBar extends JPanel {

	  static JProgressBar pbar;

	  static final int MY_MINIMUM = 0;

	  static final int MY_MAXIMUM = 100;

	  public ProgressBar() {
	    // initialize Progress Bar
	    pbar = new JProgressBar();
	    pbar.setMinimum(MY_MINIMUM);
	    pbar.setMaximum(MY_MAXIMUM);
	    // add to JPanel
	    add(pbar);
	  }

	  public static void updateBar(int newValue) {
	    try {
			pbar.setValue(newValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public static int getBarValue() {
		  return pbar.getValue();
	  }

	  public static void main(String args[]) {

	    final ProgressBar it = new ProgressBar();

	    JFrame frame = new JFrame("Progress Bar Example");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setContentPane(it);
	    frame.pack();
	    frame.setVisible(true);

	    // run a loop to demonstrate raising
	    for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
	      final int percent = i;
	      try {
	        SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	            ProgressBar.updateBar(percent);
	          }
	        });
	        java.lang.Thread.sleep(100);
	      } catch (InterruptedException e) {
	        ;
	      }
	    }
	  }
	}
