package co.edu.eci.cvds;

import co.edu.eci.cvds.exceptions.UserException;
import co.edu.eci.cvds.model.User;
import co.edu.eci.cvds.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest{

    @Autowired
    private UserService userService;

    @BeforeEach
    public void deleteUsers(){
        List<User> users = userService.getAllUsers();
        for(User u : users){
            userService.deleteUser(u);
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCorrectPassword(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        try {
            User check = userService.getUser(userService.getAllUsers().get(0).getUserId());
            System.out.println(check.getPassword());
            assertTrue(check.checkPassword("pswd"));
        } catch (UserException e) {
            fail();
        }
    }

    @Test
    void shouldFindUserByEmail(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        try {
            User login = userService.findByEmail("juan@gmail.com");
        } catch (UserException e){
            fail();
        }
    }

    @Test
    void shouldNotFindUserByEmail(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        try {
            User login = userService.findByEmail("david@gmail.com");
            fail();
        } catch (UserException e){
            assertEquals(1, userService.getAllUsers().size());
        }
    }


    @Test
    public void ShouldCreationNameUserTest(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        try {
            assertEquals(user.getUserName(), userService.getUser(userService.getAllUsers().get(0).getUserId()).getUserName());
        } catch (UserException e) {
             fail();
        }   
    }

    @Test
    public void ShouldCreationUser(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    public void ShouldDeleteUser(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        userService.deleteUser(user);
        try{
            userService.getUser(1);
            fail();
        } catch (UserException e){
            assertEquals(0, userService.getAllUsers().size());
        }
    }

    @Test
    public void ShouldUpdateUserTest(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        User newUser = new User("David", 123, 123456789, "juan@gmail.com", "pswd");
        newUser.setUserId(userService.getAllUsers().get(0).getUserId());
        try {
            userService.updateUser(newUser);
            assertEquals(userService.getUser(userService.getAllUsers().get(0).getUserId()).getUserName(), newUser.getUserName());
        } catch (UserException e) {
            fail();
        }
    }

    @Test
    public void shouldDeleteUserById(){
        User user = new User("Juan", 123, 123456789, "juan@gmail.com", "pswd");
        userService.addUser(user);
        try{
            userService.deleteUser(userService.getAllUsers().get(0).getUserId());
            assertEquals(userService.getAllUsers().size(), 0);
        } catch (UserException e){
            fail();
        }
    }

    @Test
    public void shouldManyInsertions(){
        User user1 = new User("Juan", 123, 1234567891, "juan@gmail.com", "pswd");
        User user2 = new User("David", 124, 1234567892, "david@gmail.com", "pswd");
        User user3 = new User("Mauricio", 125, 1234567893, "mauricio@gmail.com", "pswd");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        assertEquals(3, userService.getAllUsers().size());
    }

    @Test
    public void shouldFailDelete(){
        User user = new User("Juan", 123, 1234567891, "juan@gmail.com", "pswd");
        userService.addUser(user);
        try{
            userService.deleteUser(-1);
            fail();
        } catch (UserException e){
            assertEquals(1, userService.getAllUsers().size());
        }
    }

    @Test
    public void shouldManyDelete(){
        User user1 = new User("Juan", 123, 1234567891, "juan@gmail.com", "pswd");
        User user2 = new User("David", 124, 1234567892, "david@gmail.com", "pswd");
        User user3 = new User("Mauricio", 125, 1234567893, "mauricio@gmail.com", "pswd");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.deleteUser(user1);
        userService.deleteUser(user2);
        userService.deleteUser(user3);
        assertEquals(0, userService.getAllUsers().size());
    }

    @Test
    public void shouldManyDeleteById(){
        User user1 = new User("Juan", 123, 1234567891, "juan@gmail.com", "pswd");
        User user2 = new User("David", 124, 1234567892, "david@gmail.com", "pswd");
        User user3 = new User("Mauricio", 125, 1234567893, "mauricio@gmail.com", "pswd");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        System.out.println(userService.getAllUsers().size());
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            try {
                userService.deleteUser(user.getUserId());
            } catch (UserException e) {
                fail();
            }
        }
        assertEquals(0, userService.getAllUsers().size());
    }

    @Test
    void shouldUpdateAndDelete(){
        User user = new User("Juan", 123, 1234567891, "juan@gmail.com", "pswd");
        userService.addUser(user);
        User user2 = new User("David", 124, 1234567892, "david@gmail.com", "pswd");
        try {
            user2.setUserId(userService.getAllUsers().get(0).getUserId());
            userService.updateUser(user2);
        } catch (UserException e) {
            fail();
        }
        assertEquals("David", userService.getAllUsers().get(0).getUserName());
    }
}
