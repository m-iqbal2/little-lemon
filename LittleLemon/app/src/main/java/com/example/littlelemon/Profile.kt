package com.example.littlelemon

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(context: Context, NavHostController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("order_preferences", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", null)
    val lastName = sharedPreferences.getString("lastName", null)
    val email = sharedPreferences.getString("email", null)
    val selectedImageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val featureEnabled = rememberSaveable { mutableStateOf(true) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri.value = uri
        }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                galleryLauncher.launch("image/*")
            } else {
                featureEnabled.value = false
            }
        }

    fun selectProfilePicture() {
        val readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
        when {
            ContextCompat.checkSelfPermission(
                context,
                readExternalStoragePermission
            ) == PackageManager.PERMISSION_GRANTED -> {
                galleryLauncher.launch("image/*")
            }
            else -> {
                requestPermissionLauncher.launch(readExternalStoragePermission)
            }
        }
    }
    fun removeProfilePicture() {
        selectedImageUri.value = null
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.littlelemonlogo),
                    contentDescription = "Little lemon logo",
                    modifier = Modifier
                        .fillMaxWidth(.50f)
                        .size(50.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {

            Text(
                text = "Personal information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(50.dp),
                ) {
                    selectedImageUri.value?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Profile")
                    }
                }
                Button(
                    onClick = { selectProfilePicture() },
                    enabled = featureEnabled.value)
                {
                    Text(text = "Change")
                }
                Button(
                    onClick = { removeProfilePicture() },
                    enabled = featureEnabled.value && selectedImageUri.value != null
                ) {
                    Text(text = "Remove")
                }
            }
            TextField(
                value = firstName.toString(),
                label = { Text("First Name") },
                onValueChange = { },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 20.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = lastName.toString(),
                label = { Text("Last Name") },
                onValueChange = { },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 30.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = email.toString(),
                label = { Text("Email") },
                onValueChange = { },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 30.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Button(
                onClick = {
                    sharedPreferences.edit()
                        .clear()
                        .apply()

                    NavHostController.navigate(Onboarding.route) {
                        popUpTo(Home.route) { inclusive = true }
                        launchSingleTop = true
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 160.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF4CE14)),
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
            ) {
                Text(
                    text = "Logout",
                    color = Color(0xFF333333),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )
            }
        }
    }
}

