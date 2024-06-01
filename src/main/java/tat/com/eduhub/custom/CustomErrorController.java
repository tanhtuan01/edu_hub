package tat.com.eduhub.custom;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController{

	
	@GetMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {

	
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            // Xử lý lỗi 404
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404"; 
            }

            if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
            	
                return "error/405"; 
            }
            	
            // Xử lý lỗi 403
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403"; 
            }

            // Xử lý lỗi 500
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500"; 
            }

        }
        
        return "error";
    }
	
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}

}
