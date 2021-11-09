/**
 * @file Insert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Insert")
public class Insert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Insert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String race = request.getParameter("race");
      String characterClass = request.getParameter("characterClass");
      String background = request.getParameter("background");

      Connection connection = null;
      String insertSql = " INSERT INTO TechDB (id, NAME, RACE, CLASS, BACKGROUND) values (default, ?, ?, ?, ?)";

      try {
         T1ServletCoveney.getDBConnectionCoveney();
         connection = T1ServletCoveney.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, race);
         preparedStmt.setString(3, characterClass);
         preparedStmt.setString(4, background);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Character's Name</b>: " + name + "\n" + //
            "  <li><b>Character's Race</b>: " + race + "\n" + //
            "  <li><b>Character's Class</b>: " + characterClass + "\n" + //
            "  <li><b>Character's Background</b>: " + background + "\n" + //

            "</ul>\n");

      out.println("<a href=/T1Coveney/search.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
