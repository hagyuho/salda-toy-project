package com.salda.saldahomework.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.salda.saldahomework.dto.MoveReservationRequestDTO;
import com.salda.saldahomework.service.MoveReservationService;

@Controller
public class ViewController {
	
	@Autowired
	MoveReservationService moveRsvService;
	
	//입장페이지
	@RequestMapping("/welcome")
	private String welcome() {
		return "welcomePage";
	}
	
	//예약목록페이지
	@RequestMapping("/enterProc")
    private String myPageEntrProc(@RequestParam String hpNumber,Model model) throws Exception{
        
		MoveReservationRequestDTO reqDTO = new MoveReservationRequestDTO().builder().hpNumber(hpNumber).build();
		 model.addAttribute("list",moveRsvService.findByHpNumber(reqDTO));
        return "rsvList";
    }
	
	//예약가입화면
	@RequestMapping("/rsv")
	private String insertRsvInfo() {
		return "insertRsv";
	}    

	

    @RequestMapping("/list") //게시판 리스트 화면 호출  
    private String boardList(Model model) throws Exception{
        
        model.addAttribute("list", moveRsvService.findAll());
        
        return "rsvList"; //생성할 jsp
    }
	
	
}
