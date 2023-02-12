package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.mv.service.ThredServiceImpl;
import com.example.demo.mv.service.ThredServiceImpl2;

@RestController
public class HelloController {

	ThredServiceImpl oT = new ThredServiceImpl();
	ThredServiceImpl oT2 = new ThredServiceImpl();

	/**
	 * p29
	 * @return
	 */
	@RequestMapping("/")
	public String hello()
	{
		return "Hello World";
	}
	
	
	/**
	 * p29
	 * @return
	 */
	@RequestMapping("/info")
	public String info(HttpServletRequest req)
	{
		String from= req.getParameter("from");
		if ( from == null ) from = "";
		return "info - from :" + from;
	}
	
	@RequestMapping("/start")
	public String TestRunning()
	{
//		oT.setName("T1");
//		oT2.setName("T2");
		
		oT.start();
		oT2.start();
		return commonData();
	}
	
	@RequestMapping("/stop")
	public String TestStop()
	{
		oT.stop();
		return commonData();
	}
	
	
	@RequestMapping("/end")
	public String TestEnd()
	{
		oT.end();
		oT2.end();
		return commonData();
	}
	
	
	// return ModelAndView
	@GetMapping("/redirect_modelview")
	public ModelAndView exRedirect2() {
		String projectUrl = "http://localhost:8085/info?from=redirect_modelview";
		return new ModelAndView("redirect:" + projectUrl);
	}	
	
	   // httpServletResponse.sendRedirect
	   @GetMapping("/redirect_reseponse")
	   public void exRedirect3(HttpServletResponse httpServletResponse) throws IOException {
	       httpServletResponse.sendRedirect("http://localhost:8085/info?from=redirect_response");
	   }	

	   // RedirectView 
	   @RequestMapping("/ex_redirect4")
	   public RedirectView exRedirect4() {
	       RedirectView redirectView = new RedirectView();
	       redirectView.setUrl("http://localhost:8085/info?from=ex_redirect4");
	       return redirectView;
	   }	   
	
	   // httpHeaders
	   @RequestMapping("/ex_redirect5")
	   public ResponseEntity<Object> exRedirect5() throws URISyntaxException {
	       URI redirectUri = new URI("http://localhost:8085/info?from=ex_redirect4");
	       HttpHeaders httpHeaders = new HttpHeaders();
	       httpHeaders.setLocation(redirectUri);
	       return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	   }	   
	   
	/**
	 * p29
	 * @return
	 */
	@GetMapping("/redirect")
	public String redirect()
	{
		return "redirect:/info";
	}
	

	private String commonData()
	{
		String r = String.format("<h3><a href='/start'>start</a><br>")
				+ String.format("<a href='/stop'>stop</a><br>")
				+ String.format("<a href='/end'>end</a></h3>");
		return r;
	              
	}
	
}
