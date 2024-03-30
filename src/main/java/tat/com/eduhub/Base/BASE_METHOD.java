package tat.com.eduhub.base;

import org.springframework.ui.Model;

public class BASE_METHOD {

	public static String FragmentWeb(String fragment) {
		String fragmentUrl =  "fragment/web/" + fragment;
		return fragmentUrl;
	}
	
	public static void AddFragment(String fragment, Model model) {
		String fragmentUrl =  "fragment/" + fragment;
		model.addAttribute("fragment", fragmentUrl);
	}
}
