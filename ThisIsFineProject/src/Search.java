import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Search")
public class Search extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Search() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         T1ServletCoveney.getDBConnectionCoveney();
         connection = T1ServletCoveney.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM TechDB";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM TechTable WHERE NAME LIKE ?";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, keyword +"%");
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String name = rs.getString("name").trim();
            String race = rs.getString("race").trim();
            String characterClass = rs.getString("characterClass").trim();
            String background = rs.getString("background").trim();

            if (keyword.isEmpty() || name.contains(keyword)) {
               out.println("Name: " + name + ", ");
               out.println("Race: " + race + ", ");
               out.println("Class: " + characterClass + ",");
               out.println("Background: " + background + "<br>");
            }
         }
         out.println("<a href=/T1Coveney/search.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
