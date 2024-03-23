package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time/*")
public class TimeFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");
        if(timezone != null && !timezone.isEmpty()&&!isValidZone(timezone)){
            res.setContentType("text/html");
            res.setStatus(400);
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }else
            chain.doFilter(req, res);
    }

    private boolean isValidZone(String timezone) {
        timezone = timezone.trim().substring(4);
        int offsetHours = Integer.parseInt(timezone);
        return offsetHours >= -18 && offsetHours <= 18;
    }
}
