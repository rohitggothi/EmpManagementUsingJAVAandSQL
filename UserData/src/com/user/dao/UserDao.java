
import com.user.bean.UserBean;
import com.user.utility.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    public int registerUser(UserBean ub) {
        int r = 0;
        Connection conn;
        PreparedStatement pstmt;

        try {
            conn = ConnectionPool.connectDB();
            String sql = "INSERT INTO users (uid, name, username, password) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ub.getUid());
            pstmt.setString(2, ub.getName());
            pstmt.setString(3, ub.getUserName());
            pstmt.setString(4, ub.getPassword());
            r = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    public int loginCheck(String un, String ps) {
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        int r = 0;

        try {
            conn = ConnectionPool.connectDB();
            String sql = "SELECT uid FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, un);
            pstmt.setString(2, ps);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                r = rs.getInt("uid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public static void main(String[] args) {
        UserBean u = new UserBean();
        u.setName("Kashish");
        u.setUserName("Kashish123");
        u.setPassword("3333");
        u.setUid(3);
        UserDao ud = new UserDao();
        int r = ud.registerUser(u);
        if (r > 0) {
            System.out.println("Registration Success");
        } else {
            System.out.println("Registration Failed");
        }
        int x = ud.loginCheck("Ashish123", "2222");
        if (x > 0) {
            System.out.println("Login Success");
        } else {
            System.out.println("Login failed");
        }
    }
}
