package com.telstra.billing_system.controller;

import com.telstra.billing_system.model.Admin;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private static final String VALID_ADMIN_JSON = "{\"user\":{\"name\":\"admin\",\"password\":\"password\",\"role\":\"ADMIN\"}}";
    private static final String VALID_MASTER_ADMIN_JSON = "{\"user\":{\"name\":\"masterAdmin\",\"password\":\"masterPassword\",\"role\":\"MASTER_ADMIN\"}}";
    private static final String INVALID_USER_JSON = "{\"user\":{\"name\":\"\",\"password\":\"\",\"role\":\"\"}}";

    // @Test
    // public void testLogin_Success() throws Exception {
    //     User user = new User("admin", "password", "ADMIN");
    //     Admin admin = new Admin();
    //     admin.setUser(user);

    //     when(adminService.verifyAdmin(admin)).thenReturn("Login Successful");

    //     mockMvc.perform(post("/admin/login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(VALID_ADMIN_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("Login Successful"));
    // }

    @Test
    public void testLogin_InvalidUser() throws Exception {
        mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_USER_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient information provided for customer verification"));
    }

    // @Test
    // public void testMasterLogin_Success() throws Exception {
    //     User user = new User("masterAdmin", "masterPassword", "MASTER_ADMIN");
    //     Admin admin = new Admin();
    //     admin.setUser(user);

    //     when(adminService.verifyMasterAdmin(admin)).thenReturn("Master Login Successful");

    //     mockMvc.perform(post("/admin/master-login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(VALID_MASTER_ADMIN_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("Master Login Successful"));
    // }

    @Test
    public void testMasterLogin_InvalidUser() throws Exception {
        mockMvc.perform(post("/admin/master-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_USER_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient information provided for customer verification"));
    }


    @Test
    public void testHelloWorld() throws Exception {
        mockMvc.perform(post("/admin/hello-world"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"));
    }
}
