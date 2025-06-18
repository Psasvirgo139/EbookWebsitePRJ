package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.ServletContext;

import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class OnlineUserListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        AtomicInteger online = (AtomicInteger) context.getAttribute("online");
        if (online == null) {
            online = new AtomicInteger(0);
            context.setAttribute("online", online);
        }
        int current = online.incrementAndGet();
        System.out.println("Session created. User online: " + current);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        AtomicInteger online = (AtomicInteger) context.getAttribute("online");
        if (online != null) {
            int current = online.decrementAndGet();
            System.out.println("Session destroyed. User online: " + current);
        }
    }
}
