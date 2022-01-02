package com.github.darrime;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import com.github.darrime.privateVariable;


public class Main {

    private static final int COLUMNNUMBER = 5;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------------------");
        System.out.println("Friends DB에 오신것을 환영합니다");
        System.out.println("-----------------------------");
        while(true) {
            System.out.println("원하시는 작업을 입력하세요.\n 친구추가   친구검색");

            String input = sc.next();
            switch (input) {
                case "친구추가":
                    String[] friend = {};
                    System.out.println("친구의 이름, 학교, 사는곳, MBTI, 생일을 차례대로 모두 입력하세요\n\n");
                    try {
                        for (int i = 0; i < 5; i++) {
                            friend[i] = sc.next();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("빠진 정보가 있습니다. 다시 입력하세요.\n\n");
                        break;
                    }
                    for (int i = 0; i < 5; i++) {
                        System.out.println(friend[i]);
                    }
                    addFriend(friend[0], friend[1], friend[2], friend[3], friend[4]);
                case "친구검색":
                    System.out.println("친구의 이름을 입력하세요");
                    String friendName = sc.next();
                    try {
                        String[] Friend = searchFriend(friendName);
                        for (int i = 0; i < Friend.length; i++) {
                            System.out.println(Friend[i]);
                        }
                    } catch (SQLException e) {
                        System.out.println("해당 친구는 존재하지 않습니다.");
                        e.printStackTrace();
                    }
                default:
                    System.out.println("다시 입력하세요\n\n\n");



            }

        }


    }

    public static void addFriend(String name, String school, String address, String mbti, String birth) {
        try {
            Class.forName("org.sqlite.JDBC");
            PreparedStatement pstmt = null;
            Connection conn = DriverManager.getConnection(privateVariable.url);

            String sql = "insert into friend values(?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, school);
            pstmt.setString(3, address);
            pstmt.setString(4, mbti);
            pstmt.setString(5, birth);

            int result = pstmt.executeUpdate();
            if (result == 1) System.out.println("\n 친구 저장 완료");
            else System.out.println("실패");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String[] searchFriend(String name) throws SQLException{
        try {
            String[] friendInfo = new String[4];
            Class.forName("org.sqlite.JDBC");
            PreparedStatement pstmt = null;
            Connection conn = DriverManager.getConnection(privateVariable.url);

            String sql = "select * from friend where name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet result = pstmt.executeQuery();

            for (int i = 2; i < COLUMNNUMBER + 1; i++) {
                friendInfo[i - 2] = result.getString(i);
            }
            return friendInfo;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("해당 친구는 존재하지 않습니다.");
            e.printStackTrace();
        }
        return null;
    }
}
