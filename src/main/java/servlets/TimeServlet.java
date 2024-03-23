package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String timezone = request.getParameter("timezone");
        ZonedDateTime currentTime;
        if (timezone != null && !timezone.isEmpty()) {
            if (timezone.startsWith("UTC")) {
                int hours = Integer.parseInt(timezone.trim().substring(4));
                currentTime = ZonedDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(hours)));
            } else {
                currentTime = ZonedDateTime.now(ZoneId.of(timezone));
            }
        } else {
            currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
            timezone = "UTC";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = currentTime.format(formatter);
        out.println("<html><body>");
        out.println("<h2>Current Time</h2>");
        out.println("<p>" + formattedTime + "</p>");
        out.println("<p>Timezone: " + timezone + "</p>");
        out.println("</body></html>");
    }
}
