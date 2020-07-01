package com.htp.dao;

import com.htp.domain.User;
import com.htp.exceptions.ResourceNotFoundException;
import com.htp.util.DataBaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @Override
    public List<User> search(String searchParam) {
        return null;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(findOne(userId));
    }

    @Override
    public User findOne(Long userId) {
        final String findById = "select * from m_users where id = ?";

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

        User user = null;
        ResultSet resultSet =null;
        /*2. DriverManager should get connection*/
        try (Connection connection = DriverManager.getConnection(url, login, databasePassword);
                /*3. Get statement from connection*/
             PreparedStatement preparedStatement = connection.prepareStatement(findById);)
            {
                preparedStatement.setLong(1,userId);
                /*4. Execute query*/
                resultSet = preparedStatement.executeQuery() ;
            if (resultSet.next()) {
                /*6. Add parsed info into collection*/
                user= parseResultSet(resultSet);
            } else {
                throw new ResourceNotFoundException("User with id "+ userId+ " not found");
            }

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }

        return user;
    }

    @Override
    public User save(User user) {

        final String insertQuery = "INSERT INTO m_users (first_name, last_name," +
                " birth_date, login, password, address, created, changed, phone, email)\n" +
                "VALUES (?, ?, ?, ?, ?, ?," +
                "?,?, ?, ?);";

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
        try (Connection connection = DriverManager.getConnection(url, login, databasePassword);
                /*3. Get statement from connection*/
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             PreparedStatement lastInsertId = connection.prepareStatement("SELECT currval ('m_users_user_id_seq') as last_insert_id;");) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, user.getBirthDate());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setTimestamp(7, user.getCreated());
            preparedStatement.setTimestamp(8, user.getChanged());
            preparedStatement.setString(9, user.getPhone());
            preparedStatement.setString(10, user.getEmail());

            preparedStatement.executeUpdate();

            ResultSet set = lastInsertId.executeQuery();
            set.next();
            long insertedUserId = set.getInt("last_insert_id");
            return findOne(insertedUserId);
        } catch (SQLException e) {
            throw  new RuntimeException("Some issues in insert operation", e);
        }
        }

    @Override
    public User update(User user) {
        final String updateQuery = "update m_users set  first_name = ?, last_name = ?," +
                " birth_date = ?, login = ?, password = ?, address = ?, created = ?, changed = ?, phone = ?, email = ? " +
                "where id = ?";

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
        try (Connection connection = DriverManager.getConnection(url, login, databasePassword);
                /*3. Get statement from connection*/
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
             ) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, user.getBirthDate());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setTimestamp(7, user.getCreated());
            preparedStatement.setTimestamp(8, user.getChanged());
            preparedStatement.setString(9, user.getPhone());
            preparedStatement.setString(10, user.getEmail());

            preparedStatement.setLong(11, user.getId());

            preparedStatement.executeUpdate();


            return findOne(user.getId());
        } catch (SQLException e) {
            throw  new RuntimeException("Some issues in update operation", e);
        }
    }

    @Override
    public int delete (User user) {
        int status=0;
        final String deleteQuery = "DELETE  FROM  m_users WHERE  id = ?";

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
        try (Connection connection = DriverManager.getConnection(url, login, databasePassword);
                /*3. Get statement from connection*/
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        ) {

            preparedStatement.setLong(1, user.getId());

            status= preparedStatement.executeUpdate() ;


            return  status;
        } catch (SQLException e) {
            throw  new RuntimeException("Some issues in delete operation", e);
        }
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
