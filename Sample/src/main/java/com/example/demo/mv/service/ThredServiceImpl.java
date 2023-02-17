package com.example.demo.mv.service;

import java.lang.Thread.State;

//import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//import org.springframework.stereotype.Service;

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

    @Getter
    private String name ;
    
    /** context */
//    private ServletContext sc;    
    
    
    public ThredServiceImpl()
    {
    	
    }

    public void setName(String name)
    {
    	this.name = name;
    }
    
    /** 컨텍스트 초기화 시 데몬 스레드를 작동한다 */
    public void contextInitialized (ServletContextEvent event) {
    	
        log.info("== DaemonListener.contextInitialized has been called. ==");
//        sc = event.getServletContext();
        this.start();
    }    
    
    public void start()
    {
    	log.info(String.format("%s.start()", this.name ));
    	this.work = true;
    	this.isShutdown = false;
    	if ( this.thread == null ) 
    	{
    		this.thread = new Thread(this);
    		this.thread.setDaemon(true);
    		if ( this.name != null ) this.thread.setName(this.name);
    	}
    	
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
            	synchronized ( this.thread ) {
                	log.info("thread.notify() 실행");
        			this.thread.notify();
					log.info("thread.notify() 실행 완료");
            	}            	
    		}
    	}
    }
    
    public void stop()
    {
    	if ( this.thread == null ) return ;
    	log.info( String.format( "Thread Status : %s", this.thread.getState().name() ));
    	if ( this.thread.getState().compareTo( State.TIMED_WAITING ) == 0 )
    	{
        	log.info( String.format( "----- %s try work stop()", this.thread.getName() ));
    		this.work = false;
    	}
    }
    
    public void end()
    {
    	String threadName = "nothing";
    	if ( this.thread != null ) name = this.thread.getName();
    	log.info( String.format( "----- %s try thread end()", threadName ));
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
            	log.info( String.format( "Pre wait Thread Status : %s %s", this.thread.getName(), this.thread.getState().name() ));
//        		try { this.thread.join(); } catch (InterruptedException e1) {log.error(e1.toString());} // join을 실행하면 멈춘다(다음 Line으로 넘어가지 않음 )
//        		this.thread.yield();
        		
            	synchronized ( this.thread ) {
            		try {
                    	log.info("thread.wait() 실행");
						thread.wait();
						log.info("thread.wait() 실행 완료");
					} catch (InterruptedException e) {
						log.error(e.toString());
					}
            	}
            	log.info( String.format( "After wait Thread Status : %s", this.thread.getState().name() ));
        	}
        	
        	if ( this.thread.getState().compareTo( State.TERMINATED ) == 0) break;
            try { log.info( String.format( "%s status : %S", this.thread.getName(), this.thread.getState().toString() )); Thread.sleep(2000);} catch (InterruptedException e) {}
        }
		log.info( String.format("%s Thread State : %s --> End Thread", this.thread.getName(), this.thread.getState().name() ));
		this.thread = null;
	}

    /** 컨텍스트 종료 시 thread를 종료시킨다 */
    public void contextDestroyed (ServletContextEvent event) {
    	
        log.info ("== DaemonListener.contextDestroyed has been called. ==");
        this.isShutdown = true;
        try
        {
            this.thread.join();
//            this.thread = null;
        } catch (InterruptedException ie) {
            log.error(ie.toString());
        }
    }
    
    
	
}
