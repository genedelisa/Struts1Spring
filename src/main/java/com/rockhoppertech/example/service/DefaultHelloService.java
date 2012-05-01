package com.rockhoppertech.example.service;

import org.springframework.stereotype.Service;

/**
 * A very simple service annotated so Spring will pick it up.
 * 
 * @author <a href="mailto:gene@rockhoppertech.com">Gene De Lisa</a>
 *
 */
@Service("HelloService")
public class DefaultHelloService implements HelloService {
	
	/* 
	 * @see com.rockhoppertech.example.service.HelloService#getMessage()
	 */
	public String getMessage() {
		return "Yo";
	}

}
