package com.modofo.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.zip.CRC32;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.springframework.context.MessageSource;

public class BaseJFrame extends JFrame {
	
	MessageSource messageSource;
	
	private Icon getIcon(String iconfile) {
		return new ImageIcon(this.getClass().getResource(iconfile));
	}
	
	protected void useAlloyTheme() {
		com.incors.plaf.alloy.AlloyLookAndFeel.setProperty("alloy.licenseCode",
				generateLicenseCode("zhangv"));
		try {

			// com.incors.plaf.alloy.AlloyTheme theme = new
			// com.incors.plaf.alloy.themes.bedouin.BedouinTheme();
			// The lines below can be used for setting the other themes.
			com.incors.plaf.alloy.AlloyTheme theme = new com.incors.plaf.alloy.themes.glass.GlassTheme();
			// com.incors.plaf.alloy.AlloyTheme theme = new
			// com.incors.plaf.alloy.themes.acid.AcidTheme();
			javax.swing.LookAndFeel alloyLnF = new com.incors.plaf.alloy.AlloyLookAndFeel(
					theme);
			javax.swing.UIManager.setLookAndFeel(alloyLnF);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			// You may handle the exception here
		}
	}
	
	protected String generateLicenseCode(String user) {
		String exp = "x";
		String rand = "torresg";
		long l = Long.parseLong(rand, 36);
		CRC32 crc32 = new CRC32();
		crc32.update((l % 127L + exp + "#" + user).getBytes());
		String checksum = Long.toString(crc32.getValue(), 36);
		return exp + "#" + user + "#" + checksum + "#" + rand;
	}
	
	protected void useMacTheme() {
		System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
		try {
			UIManager
					.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void setLocation(JFrame f, JDialog d) {
		d.setLocation(f.getX() + f.getWidth() / 2 - d.getWidth() / 2, f.getY()
				+ f.getHeight() / 2 - d.getHeight() / 2);
	}
	
	protected String getLocaleMessage(String key){
		String s = messageSource.getMessage(key, null, null);
		String s1=null;
		try {
			s1 = new String(s.getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s1;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
