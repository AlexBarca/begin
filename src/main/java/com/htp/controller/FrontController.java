package com.htp.controller;

import com.htp.dao.UserDao;
import com.htp.dao.UserDaoImpl;
import com.htp.domain.User;
import com.htp.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class FrontController extends HttpServlet {

    public static final String FIND_ONE="findOne";
    public static final String FIND_BY_ID="findById";
    public static final String FIND_ALL="findAll";
    public static final String SAVE="save";
    public static final String UPDATE="update";
    public static final String DELETE="delete";



    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchQuery = StringUtils.isNotBlank(req.getParameter("userId")) ? req.getParameter("userId") :"0";
        String typeOfSearch = StringUtils.isNotBlank(req.getParameter("type")) ? req.getParameter("type") :"0";
        String userName = StringUtils.isNotBlank(req.getParameter("userName")) ? req.getParameter("userName") :"0";


        String result = "";

        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/bye");
        if (dispatcher != null) {
            System.out.println("Forward will be done!");

            switch (typeOfSearch){

                case FIND_ONE:

                    result=userDao.findOne(Long.parseLong(searchQuery)).getLogin();
                    break;

                case FIND_BY_ID:
//                    первый вариант Optional
//                    result=userDao.findById(Long.parseLong(searchQuery)).orElseThrow(ResourceNotFoundException::new).getLogin();
//                    второй вариант обхода Optional
                    Optional<User> optionalUser = userDao.findById(Long.parseLong(searchQuery));
                    if (optionalUser.isPresent()){
                        result=optionalUser.get().getLogin();
                    }
                    break;

                case SAVE:

                    User user = new User();
                    /*5. Columns mapping*/
                    user.setFirstName(userName);
                    user.setLastName(userName);
                    user.setBirthDate(new java.sql.Date(new java.util.Date().getTime()));
                    user.setLogin(UUID.randomUUID().toString());
                    user.setPassword(UUID.randomUUID().toString());
                    user.setAddress(UUID.randomUUID().toString());
                    user.setCreated(new Timestamp(new java.util.Date().getTime()));
                    user.setChanged(new Timestamp(new java.util.Date().getTime()));
                    user.setPhone(UUID.randomUUID().toString());
                    user.setEmail(UUID.randomUUID().toString());
                    result=userDao.save(user).getLogin();
                    break;

                case UPDATE:

                    User userForUpdate = userDao.findOne(Long.parseLong(searchQuery));
                    /*5. Columns mapping*/

                    userForUpdate.setFirstName(userName);
                    userForUpdate.setAddress(UUID.randomUUID().toString());
                    userForUpdate.setChanged(new Timestamp(new java.util.Date().getTime()));


                    result=userDao.update(userForUpdate).getLogin();
                    break;

                case DELETE:

                    User userDelete = userDao.findOne(Long.parseLong(searchQuery));
                    /*5. Columns mapping*/

                    userDelete.setId(Long.parseLong(searchQuery));

//                    userDelete.setId(Long.parseLong(userName));



                    userDao.delete(userDelete);
//                    result=userDao.delete(Long.parseLong(userDelete));
                    break;

                case FIND_ALL :

                default:
                    result=userDao.findAll().stream().map(User::getLogin).collect(Collectors.joining(","));
                    break;
            }

            req.setAttribute("userNames", result);
            dispatcher.forward(req, resp);
        }
    } catch ( Exception e) {
            RequestDispatcher dispatcher =req.getRequestDispatcher("/errors");
        req.setAttribute("errors", e.getMessage());
        dispatcher.forward(req, resp);
        }
    }
}
