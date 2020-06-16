package com.htp.dao;

import com.htp.domain.User;
import com.htp.util.DataBaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



import static com.htp.util.DataBaseConfiguration.DATABASE_DRIVER_NAME;
import static com.htp.util.DataBaseConfiguration.DATABASE_LOGIN;
import static com.htp.util.DataBaseConfiguration.DATABASE_PASSWORD;
import static com.htp.util.DataBaseConfiguration.DATABASE_URL;

public class UserDaoImpl implements UserDao {

    public static DataBaseConfiguration config = DataBaseConfiguration.getInstance();

    public static final String USER_ID = "id";
    public static final String USER_FIRSTNAME = "first_name";
    public static final String USER_LASTNAME = "last_name";
    public static final String USER_BIRTH_DATE = "birth_date";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_CREATED = "created";
    public static final String USER_CHANGED = "changed";
    public static final String USER_PHONE = "phone";
    public static final String USER_EMAIL = "email";


    
    @Override
    public List<User> findAll() {
        final String findAllQuery = "select * from m_users order by id desc";

        String driverName = config.getProperty(DATABASE_DRIVER_NAME);
        String url = config.getProperty(DATABASE_URL);
        String login = config.getProperty(DATABASE_LOGIN);
        String databasePassword = config.getProperty(DATABASE_PASSWORD);

        /*1. Load driver*/
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Don't worry:)");
        }

        List<User> resultList = new ArrayList<>();
        /*2. DriverManager should get connection*/
        try (Connection connection = DriverManager.getConnection(url, login, databasePassword);
                /*3. Get statement from connection*/
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            /*4. Execute query*/
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                /*6. Add parsed info into collection*/
                resultList.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultList;
    }

    private User parseResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        /*5. Columns mapping*/
        user.setId(resultSet.getLong(USER_ID));
        user.setFirstName(resultSet.getString(USER_FIRSTNAME));
        user.setLastName(resultSet.getString(USER_LASTNAME));
        user.setBirthDate(resultSet.getDate(USER_BIRTH_DATE));
        user.setLogin(resultSet.getString(USER_LOGIN));
        user.setPassword(resultSet.getString(USER_PASSWORD));
        user.setCreated(resultSet.getTimestamp(USER_CREATED));
        user.setChanged(resultSet.getTimestamp(USER_CHANGED));
        user.setPhone(resultSet.getString(USER_PHONE));
        user.setEmail(resultSet.getString(USER_EMAIL));

        return user;
    }

}
