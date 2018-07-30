package com.brouwershuis.system;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

@WebListener // register it as you wish
public class ContainerContextClosedHandler implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // nothing to do
    }
    
    
    /*
     * Solves Issue related to MySQL driver unload after restart: The web application [Demo2] registered the JDBC driver [com.mysql.cj.jdbc.Driver] 
     * but failed to unregister it when the web application was stopped. To prevent a memory leak, 
     * the JDBC Driver has been forcibly unregistered.
     * 
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();     

        Driver driver = null;

        System.out.println("START CUSTOM DRIVERS UNLOAD !!!");
        // clear drivers
        while(drivers.hasMoreElements()) {
            try {
                driver = drivers.nextElement();
                System.out.println("Unload deregistered driver:" + driver.toString());
                DriverManager.deregisterDriver(driver);

               
            } catch (SQLException ex) {
                // deregistration failed, might want to do something, log at the very least
            	System.out.println(ex.getMessage());
            }
        }

        // MySQL driver leaves around a thread. This static method cleans it up.
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            // again failure, not much you can do
        	System.out.println(e.getMessage());
        }
    }

}