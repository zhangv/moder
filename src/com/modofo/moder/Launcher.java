package com.modofo.moder;

import java.sql.SQLException;

import com.modofo.moder.ui.MainFrame;
import com.modofo.util.SplashWindow;
import com.modofo.util.SpringUtils;

public class Launcher {
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		SplashWindow.splash(Launcher.class.getResource("ui/imgs/splash.png"));
		SpringUtils.initFromClasspath(new String[] {"com/modofo/moder/appCtx.xml"});
		//DbUtils.createDerbyDatabase("", "moderdb");
		MainFrame app = (MainFrame)SpringUtils.getBean("main");
		app.setVisible(true);
        SplashWindow.disposeSplash();
		
	}
}
