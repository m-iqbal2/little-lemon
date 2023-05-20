package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoarding() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
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
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }

        Column(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
        ) {
            Text(
                text = "Personal information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 40.dp, bottom = 40.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            var firstName by remember { mutableStateOf("") }

            TextField(
                value = firstName,
                onValueChange = { newFirstName -> firstName = newFirstName},
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(.5.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                label = { Text("First name")},

            )

            var lastName by remember { mutableStateOf("") }

            TextField(
                value = lastName,
                onValueChange = { newlastName -> firstName = newlastName},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 50.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(.5.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                label = { Text("Last name")},
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                )
            )

            var email by remember { mutableStateOf("") }

            TextField(
                value = email,
                onValueChange = { newEmail -> firstName = newEmail},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 50.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(.5.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFFF)),
                label = { Text("Email")},
                colors = TextFieldDefaults.textFieldColors(
                    Color(0xFFFFFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 175.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(.5.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFF4CE14)),
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),

            ) {
                Text(
                    text = "Register",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewOnBoarding() {
    OnBoarding()
}