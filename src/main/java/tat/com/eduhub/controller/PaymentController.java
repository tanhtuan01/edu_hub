package tat.com.eduhub.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.config.VNPayConfig;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Payment;
import tat.com.eduhub.entity.Revenue;
import tat.com.eduhub.entity.RevenueLecturer;
import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.PaymentService;
import tat.com.eduhub.service.RevenueLecturerService;
import tat.com.eduhub.service.RevenueService;
import tat.com.eduhub.service.StudentCoursesService;

@Controller
public class PaymentController {
	
	private Long idCourses = (long) 0;
	
	@Autowired
	private CoursesService coursesService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private RevenueLecturerService revenueLecturerService;
	
	@Autowired
	private StudentCoursesService studentCoursesService;

	@GetMapping(value = "/buy-now/pay")
	public RedirectView paymentCourses(HttpServletRequest req, HttpServletResponse resp, Authentication authentication)
			throws ServletException, IOException {
		RedirectView redirectView = new RedirectView();
		Long price = Long.valueOf(req.getParameter("price"));
		idCourses = Long.valueOf(req.getParameter("courses_id"));
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String order_Type = "other";
		long amount = price * 100;
		String bankCode = "NCB";
		System.err.println("idCourses: " + idCourses);
		String vnp_TxnRef = BASE_METHOD.randomString(10) + BASE_METHOD.createStrDateNow();
		String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
		String vnp_IpAddr = "127.0.0.1";

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_BankCode", bankCode);
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Ma don hang: " + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", order_Type);
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if (fieldName != null && fieldValue.length() > 0) {
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

		redirectView.setUrl(paymentUrl);
		return redirectView;
	}

	@GetMapping(value = "/buy-now/pay/result")
	public String paymentResult(HttpServletRequest request, Authentication authentication) {
		// trang thai giao dich
		String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
		// tong tien
		String vnp_Amount = request.getParameter("vnp_Amount");
		// ma don hang
		String vnp_TxnRef = request.getParameter("vnp_TxnRef");
		// ma ngan hang
		String vnp_BankCode = request.getParameter("vnp_BankCode");
		
		if(vnp_TransactionStatus.equals("00")) {
			// success
			Courses courses = coursesService.get(idCourses);
			User user = userHelper.getUserLogged(authentication);
			Payment payment = new Payment();
			payment.setBankCode(vnp_BankCode);
			payment.setCode(vnp_TxnRef);
			payment.setCourses(courses);
			payment.setTotalPrice(Long.valueOf(vnp_Amount));
			payment.setType("buy_courses");
			payment.setUser(user);
			Long idPaymentSaved = paymentService.saveAndGetId(payment);
			
			Payment getPayment = paymentService.get(idPaymentSaved);
			
			Long priceCalc = getPayment.getTotalPrice();
			priceCalc = priceCalc / 100;
			Long priceToLecturer = (long) (priceCalc - priceCalc * 0.1);
			Long priceToAdmin = priceCalc - priceToLecturer;
			
			Revenue revenue = new Revenue();
			revenue.setPayment(getPayment);
			revenue.setTotalPrice(priceToAdmin);
			revenueService.save(revenue);
			
			User lectuer = courses.getUser();
			
			RevenueLecturer revenueLecturer = new RevenueLecturer();
			revenueLecturer.setTotalPrice(priceToLecturer);
			revenueLecturer.setPayment(getPayment);
			revenueLecturer.setUser(lectuer);
			revenueLecturerService.save(revenueLecturer);
			
			StudentCourses studentCourses = new StudentCourses();
			studentCourses.setUser(user);
			studentCourses.setCourses(courses);
			studentCourses.setProgress("on_learning");
			studentCoursesService.save(studentCourses);
			
			return "redirect:/courses/thong-tin-khoa-hoc?courses_id=" + idCourses + "&paysuccess";
		}else {
			return "redirect:/courses/thong-tin-khoa-hoc?courses_id=" + idCourses + "&errorpay";
		}
		
	}
	
}
