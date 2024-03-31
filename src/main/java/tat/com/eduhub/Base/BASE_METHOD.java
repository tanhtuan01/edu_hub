package tat.com.eduhub.base;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.ui.Model;

public class BASE_METHOD {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();
	
	public static void FragmentWeb(String fragment, Model model) {
		String fragmentUrl =  "fragment/web/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static void FragmentAdminWeb(String fragment, Model model) {
		String fragmentUrl =  "fragment/admin/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
	
	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
	}
}
