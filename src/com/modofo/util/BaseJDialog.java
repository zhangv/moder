package com.modofo.util;

import java.awt.Frame;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.swing.JDialog;

import org.springframework.context.MessageSource;

public class BaseJDialog extends JDialog{
	MessageSource messageSource;
	public BaseJDialog() {
	}
	public BaseJDialog(Frame owner) {
		super(owner);
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
