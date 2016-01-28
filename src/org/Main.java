package org;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.client.Config;
import org.ui.ClientFrame;
import org.util.Utilities;
import org.util.Condition;
import org.util.Time;
import org.web.LoginFrame;

public class Main {
	
	public Main(){
		final Config config = Config.getInstance();
		
		LoginFrame login = new LoginFrame();
		login.setVisible(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				login.setVisible(true);
			}
		}).start();
		
		Time.sleep(new Condition() {
			@Override
			public boolean active() {
				return !login.isVisible();
			}
		}, 2000); // to help people with slower computers.
		
		while (login.isVisible()) {
			Utilities.sleep(200, 300);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run(){
				config.setclientFrame(new ClientFrame());
				config.getclientFrame().setVisible(true);
			}
		});
	}

	public static void main(String[] args)  {
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}

		JPopupMenu.setDefaultLightWeightPopupEnabled(true);
		
		new Main();
	}

}