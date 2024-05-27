package com.cglia.sms.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cglia.sms.model.Student;
import com.cglia.sms.service.StudentService;

@Controller(value = "smsController")
public class SMSController {

	@Autowired
	private StudentService service;

	@GetMapping({ "/", "/home" })
	public String showHome() {
		return "Home";
	}

	@GetMapping({ "/add" })
	public String addStudent(HttpServletRequest request) {
		return "AddStd";
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute Student std, HttpServletRequest request, RedirectAttributes attrs) {
		System.out.println(std);

		Integer id = service.saveStd(std);
		attrs.addFlashAttribute("stdid", id);

		if (id > 0) {
			attrs.addFlashAttribute("successmsg", "Student added with id " + id);
		} else {
			attrs.addFlashAttribute("failuremsg", "Failed to generate student id!");
		}

		return "redirect:/getAll";
	}

	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public String getAllStudent(HttpServletRequest request) {

		List<Student> studentList = service.getAllStudent();

		request.setAttribute("studentList", studentList);
		System.out.println(studentList);
		return "ShowAllStd";
	}

	@GetMapping("/getStd")
	public String getStudentByID(@RequestParam("id") Integer id, HttpServletRequest request) {

		Student std = service.getStdById(id);
		request.setAttribute("std", std);
		return "UpdateStd";
	}

	@PostMapping("/update")
	public String updateStudent(@ModelAttribute Student std, RedirectAttributes attrs) {

		int count = service.updateStd(std);
		attrs.addFlashAttribute("updatecount", count);
		if (count > 0) {
			attrs.addFlashAttribute("updated", "Student with ID: " + std.getId() + " is updated successfully");
		} else {
			attrs.addFlashAttribute("notupdated", "Failed to update employee details!");
		}
		return "redirect:/getAll";
	}

	@PostMapping("/delete")
	public String deleteStudent(HttpServletRequest request, RedirectAttributes attrs) {
		Integer id = null;
		int count = 0;
		if (Objects.nonNull(request.getParameter("idtodelete"))) {
			id = Integer.parseInt(request.getParameter("idtodelete"));
		}

		if (Objects.nonNull(id)) {
			count = service.deleteStdById(id);
			attrs.addFlashAttribute("deletecount", count);
		}

		if (count > 0) {
			attrs.addFlashAttribute("deleted", "Student with ID: " + id + " is deleted successfully.");
		} else {
			attrs.addFlashAttribute("notdeleted", "Deletion failed!");
		}
		return "redirect:/getAll";

	}

}







//package com.cglia.sms.controller;
//
//import java.util.List;
//import java.util.Objects;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.cglia.sms.model.Student;
//import com.cglia.sms.service.StudentService;
//
//@Controller
//@RequestMapping("/sms")
//public class SMSController {
//
//    @Autowired
//    private StudentService service;
//
//    @GetMapping({ "/", "/home" })
//    public String showHome() {
//        return "Home";
//    }
//
//    @GetMapping("/add")
//    public String addStudentForm(Model model) {
//        model.addAttribute("student", new Student());
//        return "AddStd";
//    }
//
//    @PostMapping("/save")
//    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes attrs) {
//        Integer id = service.saveStd(student);
//        if (id > 0) {
//            attrs.addFlashAttribute("successmsg", "Student added with id " + id);
//        } else {
//            attrs.addFlashAttribute("failuremsg", "Failed to generate student id!");
//        }
//        return "redirect:/sms/getAll";
//    }
//
//    @GetMapping("/getAll")
//    public String getAllStudents(Model model) {
//        List<Student> stdList = service.getAllStudent();
//        model.addAttribute("studentlist", stdList);
//        return "ShowAllStd";
//    }
//
//    @GetMapping("/getStd")
//    public String getStudentById(@RequestParam("id") Integer id, Model model) {
//        Student std = service.getStdById(id);
//        model.addAttribute("std", std);
//        return "UpdateStd";
//    }
//
//    @PostMapping("/update")
//    public String updateStudent(@ModelAttribute("student") Student student, RedirectAttributes attrs) {
//        int count = service.updateStd(student);
//        if (count > 0) {
//            attrs.addFlashAttribute("updated", "Student with ID: " + student.getId() + " is updated successfully");
//        } else {
//            attrs.addFlashAttribute("notupdated", "Failed to update student details!");
//        }
//        return "redirect:/sms/getAll";
//    }
//
//    @PostMapping("/delete")
//    public String deleteStudent(@RequestParam("id") Integer id, RedirectAttributes attrs) {
//        int count = service.deleteStdById(id);
//        if (count > 0) {
//            attrs.addFlashAttribute("deleted", "Student with ID: " + id + " is deleted successfully.");
//        } else {
//            attrs.addFlashAttribute("notdeleted", "Deletion failed!");
//        }
//        return "redirect:/sms/getAll";
//    }
//}
