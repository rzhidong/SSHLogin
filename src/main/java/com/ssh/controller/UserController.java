package com.ssh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssh.entity.User;
import com.ssh.service.MailService;
import com.ssh.service.UserService;
import com.ssh.utils.DateUtils;
import com.ssh.utils.MailUtils;
import com.ssh.utils.SystemUtils;
import com.ssh.utils.UUIDUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@RequestMapping("/index")
	public String index(ModelMap map, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("id") != null) {
				User user = userService.getUserById(Integer.parseInt((String) session.getAttribute("id")));
				if (user != null) {
					System.out.println(user);
					map.put("loginUser", user);
					return "user/index";
				}
			}
		}

		// return "redirect:/index.jsp";
		return "redirect:/";

	}

	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> userLogin(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = new User();
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));

		User currentUser = userService.queryUser(user);
		if (currentUser != null) {
			if (currentUser.getIsValidated() == 1) {
				resultMap.put("id", currentUser.getId());
				resultMap.put("res", "yes");

				HttpSession session = request.getSession();
				session.setAttribute("id", currentUser.getId().toString());
			}else {
				resultMap.put("res", "validate");
			}
			
		} else {
			resultMap.put("res", "no");
		}
		return resultMap;
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();

		return "redirect:/";
	}

	@RequestMapping("/checkUserName")
	@ResponseBody
	public String checkUserName(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		User user = userService.findUserByUserName(userName);
		if (user != null) {
			return "isExist";
		}
		return "isOK";
	}

	@RequestMapping("/toRegister")
	public String toRegister() {
		return "user/register";
	}

	@RequestMapping("/register")
	@ResponseBody
	public String register(ModelMap map, HttpServletRequest request) throws Exception {
		
		HttpSession tempSession = request.getSession();
		if (tempSession != null && tempSession.getAttribute("id") != null) {
			tempSession.invalidate();
		}
		
		String userName = request.getParameter("userName").trim();
		String password = request.getParameter("password").trim();
		String email = request.getParameter("email").trim();
		
		if (!MailUtils.isMailValidate(email) || userName == "" || password == "" || email == "") {
			return "redirect:/";
		}
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);

		// 生成序列码、过期日期、用户名字节编码串、未验证:更新currUser
		String validateSerCode = UUIDUtils.getUUID();
		Date overDate = DateUtils.getDateAfter(1);
		byte[] userNameEncodes = userName.getBytes("UTF-8");

		user.setValidateSerCode(validateSerCode);
		user.setValidateOverDate(overDate);
		user.setUserNameEncodes(String.valueOf(userNameEncodes));
		user.setIsValidated(0);

		userService.saveUser(user);

		// 生成URL查询串
		String validateStr = SystemUtils.getProjectURLPath(request) + "/user/validateInfo?email=" + email
				+ "&validateSerCode=" + validateSerCode + "&userNameEncodes=" + String.valueOf(userNameEncodes)
				+ "&type=register";

		Map<String, Object> mailMap = new HashMap<String, Object>();
		mailMap.put("username", userName);
		String ftlContent = "请点击下面的安全链接,用于验证个人信息<br><a href='" + validateStr + "'>验证信息</a>";
		mailMap.put("content", ftlContent);
		boolean isSend = mailService.sendWithFreeMarker(email, "验证信息-用于注册成功时候的验证", mailMap);
		
		if (userService.findUserByUserName(userName) != null && isSend) {
			return "success";
		}else {
			return "fail";
		}

	}

	@RequestMapping("/updateUserPassword/{id}")
	@ResponseBody
	public Map<String, Object> updateUserPassword(ModelMap map, HttpServletRequest request,@PathVariable Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		User user = userService.getUserById(id);
		
		if (user == null) {
			resultMap.put("updateRes", "no");
		} else {
			String password = request.getParameter("password");
			user.setPassword(password);
			userService.updateUser(user);

			resultMap.put("updateRes", "ok");
		}

		return resultMap;
	}

	@RequestMapping("/validateInfo")
	public String validateUserInfo(ModelMap map, HttpServletRequest request) throws Exception {
		
		HttpSession tempSession = request.getSession();
		if (tempSession != null && tempSession.getAttribute("id") != null) {
			tempSession.invalidate();
		}
		
		String email = request.getParameter("email");
		String validateSerCode = request.getParameter("validateSerCode");
		String userNameEncodes = request.getParameter("userNameEncodes");

		// 验证的类型 type=register 表示是注册时候的验证
		String type = request.getParameter("type");

		System.out.println("验证信息: " + " -- " + email + " -- " + validateSerCode + " -- " + userNameEncodes);

		String validateRes = null;
		if (userNameEncodes == null || email == null || validateSerCode == null) {
			validateRes = "链接无效";
		} else {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("email", email);
			queryMap.put("validateSerCode", validateSerCode);
			queryMap.put("userNameEncodes", userNameEncodes);

			List<User> userList = userService.findUsers(queryMap);
			if (userList != null && userList.size() > 0) {
				User valiUser = userList.get(0);
				
				map.put("valiUser", valiUser);
				// 判断是否已经验证过了!
				if (valiUser.getIsValidated() == 1) {
					validateRes = "该链接已经验证过了!";
				} else {

					if (valiUser.getValidateOverDate() != null) {
						// 获取记录,查看链接是否过期
						if (DateUtils.isBeforeSpeciDate(DateUtils.getCurrentDate(), valiUser.getValidateOverDate())) {
							map.put("valiUser", valiUser);
							valiUser.setIsValidated(1);
							userService.updateUser(valiUser);
							validateRes = "验证成功";
						} else {
							validateRes = "链接已失效!";
						}
					} else {
						throw new RuntimeException("系统异常!!!!");
					}
				}
			} else {
				validateRes = "链接无效";
			}
		}

		map.put("validateRes", validateRes);

		if (type != null && "register".equals(type)) {
			return "user/validateRegister";
		}

		return "user/validateInfo";
	}

	@ResponseBody
	@RequestMapping("/findPassword")
	public Map<String, Object> findPassword(ModelMap map, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");

		if (userName == "" || email == "" || !MailUtils.isMailValidate(email)) {
			resultMap.put("res", "no");
		}else {
			User currUser = userService.findUserPassword(userName, email);
			if (currUser != null) {
				// 生成序列码、过期日期、用户名字节编码串、未验证:更新currUser
				String serialCode = UUIDUtils.getUUID();
				Date overDate = DateUtils.getDateAfter(1);
				byte[] userNameEncodes = currUser.getUserName().getBytes("UTF-8");

				currUser.setValidateSerCode(serialCode);
				currUser.setValidateOverDate(overDate);
				currUser.setUserNameEncodes(String.valueOf(userNameEncodes));
				currUser.setIsValidated(0);

				userService.updateUser(currUser);

				// 生成URL查询串
				String validateStr = SystemUtils.getProjectURLPath(request) + "/user/validateInfo?email="
						+ currUser.getEmail() + "&validateSerCode=" + serialCode + "&userNameEncodes="
						+ String.valueOf(userNameEncodes);

				Map<String, Object> mailMap = new HashMap<String, Object>();
				mailMap.put("username", request.getParameter("userName"));
				String ftlContent = "请点击下面的安全链接,用于找回密码<br><a href='" + validateStr + "'>找回密码</a>";
				mailMap.put("content", ftlContent);
				mailService.sendWithFreeMarker(request.getParameter("email"), "验证信息-用于找回密码", mailMap);

				resultMap.put("res", "yes");
			} else {
				resultMap.put("res", "no");
			}
		}
		
		return resultMap;
	}
	
	@RequestMapping("/forgetPassword")
	public String forgetPassword(ModelMap mapp,HttpServletRequest request){
		HttpSession tempSession = request.getSession();
		if (tempSession != null && tempSession.getAttribute("id") != null) {
			tempSession.invalidate();
		}
		return "user/forgetPassword";
	}

}
