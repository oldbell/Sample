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

@RestController
public class HelloController {

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
	
	
}
