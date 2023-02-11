package com.example.demo.mv.service;

import java.lang.Thread.State;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@WebListener
@Slf4j
public class ThredServiceImpl implements ThreadService, ServletContextListener, Runnable {

	private boolean work = false;
	
    /** 작업을 수행할 thread */
    private Thread thread;
    
    @Getter
    @Setter
    private boolean isShutdown = false;	

    /** context */
    private ServletContext sc;    
    
    /** 컨텍스트 초기화 시 데몬 스레드를 작동한다 */
    public void contextInitialized (ServletContextEvent event) {
    	
        log.info("== DaemonListener.contextInitialized has been called. ==");
        sc = event.getServletContext();
        this.start();
    }    
    
    public void start()
    {
    	log.info("start()");
    	this.work = true;
    	this.isShutdown = false;
    	if ( this.thread == null ) this.thread = new Thread(this,"Daemon thread for background task");
    	
    	log.info( String.format( "Thread Status : %s", this.thread.getState().name() ));
    	log.info( String.format( "Thread isAlive : %b", this.thread.isAlive() ));
    	    	
    	if ( this.thread.getState().compareTo( State.RUNNABLE ) == 0 ) 
    	{
    		log.info("실행 중이니 추가 실행할 필요 없음");
    	}
    	
    	if ( ! this.thread.isAlive() )
    	{
       		this.thread.start();
       		log.info("thread.start() 실행함");
    	} else {

    		if ( this.thread.getState().compareTo( State.WAITING) == 0 )
    		{
    			this.thread.notify();
    			log.info("thread.notify() 실행함");	
    		}
    	}
    }
    
    public void stop()
    {
    	log.info( String.format( "Thread Status : %s", this.thread.getState().name() ));
    	if ( this.thread.getState().compareTo( State.TIMED_WAITING ) == 0 )
    	{
    		this.work = false;
    	}
    }
    
    public void end()
    {
    	this.work = false;
    	this.isShutdown = true;
    }
    
	@Override
	public void run() {
		
        Thread currentThread = Thread.currentThread();
        while (currentThread == thread) {
        	
        	if ( this.isShutdown ) break;
        	if (  ! this.work ) 
        	{
            	log.info( String.format( "Pre Join Thread Status : %s", this.thread.getState().name() ));
        		try {this.thread.wait(); } catch (InterruptedException e1) {log.error(e1.toString());} // join을 실행하면 멈춘다(다음 Line으로 넘어가지 않음 )
//            	log.info("thread.yield() 실행");
        		this.thread.yield();
        		
            	log.info( String.format( "After Join Thread Status : %s", this.thread.getState().name() ));
        	}
        	
        	if ( this.thread.getState().compareTo( State.TERMINATED ) == 0) break;
        	
            try { log.info( this.thread.getState().toString() ); Thread.sleep(500);} catch (InterruptedException e) {}
        }
		log.info("End Thread");
	}

    /** 컨텍스트 종료 시 thread를 종료시킨다 */
    public void contextDestroyed (ServletContextEvent event) {
    	
        this.log.info ("== DaemonListener.contextDestroyed has been called. ==");
        this.isShutdown = true;
        try
        {
            this.thread.join();
            this.thread = null;
        } catch (InterruptedException ie) {
            log.error(ie.toString());
        }
    }	
	
}
