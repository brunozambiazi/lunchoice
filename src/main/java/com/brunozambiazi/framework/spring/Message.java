
package com.brunozambiazi.framework.spring;

import java.util.Locale;
import org.springframework.context.support.ResourceBundleMessageSource;


public class Message extends ResourceBundleMessageSource {

	public String get(String code, Object... args) {
		return getMessage(code, args, Locale.getDefault());
	}
	
}
