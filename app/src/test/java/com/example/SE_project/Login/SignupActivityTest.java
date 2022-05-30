package com.example.SE_project.Login;

import static org.junit.Assert.*;

import android.widget.EditText;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import com.example.SE_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOError;
import java.io.IOException;

@SmallTest
public class SignupActivityTest {
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId("TestID");
        user.setIntro("TestIntro");
        user.setUsername("TestName");
        user.setPro_img("TestImg");
    }
    @Test
    public void test(){
        Assert.assertEquals(user.getPro_img(),"TestImg");
        Assert.assertEquals(user.getUsername(),"TestName");
        Assert.assertEquals(user.getIntro(),"TestIntro");
        Assert.assertEquals(user.getId(),"TestID");
    }
}