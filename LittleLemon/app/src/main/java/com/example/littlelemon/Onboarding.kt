package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun OnBoarding(context: Context ,NavHostController: NavHostController) {
    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }
    val sharedPreferences = context.getSharedPreferences("order_preferences", ComponentActivity.MODE_PRIVATE)


    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.littlelemonlogo),
                contentDescription = "Little lemon logo",
                modifier = Modifier
                    .fillMaxWidth(.50f)
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .background(Color(0xFF495E57))
        ) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily =  FontFamily(Font(R.font.markazitextregular))
                )
            )
        }
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Personal information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 40.dp, bottom = 40.dp),

                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily =  FontFamily(Font(R.font.karlaregular))
                ),

                )

            androidx.compose.material.OutlinedTextField(
                value = firstName.value,
                onValueChange = { firstName.value = it},
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Gray,
                    cursorColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                label = { Text("First name") },
                shape = RoundedCornerShape(10.dp)
            )
            androidx.compose.material.OutlinedTextField(
                value = lastName.value,
                onValueChange = {lastName.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 50.dp, end = 10.dp),
                label = { Text("Last name") },
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Gray,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            )
            androidx.compose.material.OutlinedTextField(
                value = email.value,
                onValueChange = {email.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 50.dp, end = 10.dp),
                label = { Text("Email") },
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    focusedBorderColor = Color.Gray,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Button(
                onClick = {
                    if (validateRegData(
                            firstName.value,
                            lastName.value,
                            email.value
                        )) {
                        NavHostController.navigate(Home.route) {
                            popUpTo(Onboarding.route) { inclusive = true }
                            launchSingleTop = true
                        }
                        sharedPreferences.edit()
                            .putString("firstName", firstName.value)
                            .putString("lastName", lastName.value)
                            .putString("email", email.value)
                            .putBoolean("userRegistered", true)
                            .apply()

                        Toast.makeText(
                            context, "Registration successful.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            context, "Registration unsuccessful. Please enter all data.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 105.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF4CE14)),
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14))
                ) {
                Text(
                    text = "Register",
                    color = Color(0xFF333333),
                    fontSize = 18.sp,
                    fontFamily =  FontFamily(Font(R.font.karlaregular))
                )
            }
        }
    }

}