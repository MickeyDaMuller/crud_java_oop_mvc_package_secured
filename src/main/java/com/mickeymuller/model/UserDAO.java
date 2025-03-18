package com.mickeymuller.model;

import com.mickeymuller.util.DBConnection;
import com.mickeymuller.util.SecurityUtil;
import java.sql.*;

public class UserDAO {
    public static boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, email, salt) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String salt = SecurityUtil.generateSalt();
            String hashedPassword = SecurityUtil.hashPassword(user.getPassword(), salt);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getEmail());
            stmt.setString(4, salt);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}