package com.example.littlelemon

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

@Composable
fun Profile(context: Context, NavHostController: NavHostController) {

    val sharedPreferences = context.getSharedPreferences("order_preferences", Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString("firstName", null)

    val lastName = sharedPreferences.getString("lastName", null)

    val email = sharedPreferences.getString("email", null)

    val phonenumber = remember {
        mutableStateOf("")
    }

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
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                readExternalStoragePermission
            ) -> {
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

    val checked1 = remember {
        mutableStateOf(true)
    }

    val checked2 = remember {
        mutableStateOf(true)
    }

    val checked3 = remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(
                        shape = CircleShape,
                        brush = Brush.sweepGradient(
                            colors = listOf(Color(0xFF495E57), Color(0xFF495E57)))
                    )
            ) {
                IconButton(
                    onClick = {NavHostController.navigate(Home.route)},
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
                Image(
                    painter = painterResource(id = R.drawable.littlelemonlogo),
                    contentDescription = "Little lemon logo",
                    modifier = Modifier
                        .fillMaxWidth(.50f)
                        .size(50.dp)
                        .align(Alignment.CenterVertically)
                )
            Spacer(modifier = Modifier.width(55.dp))
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
                    .padding(start = 10.dp, top = 10.dp, bottom = 15.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp),
                ) {
                    selectedImageUri.value?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current).data(data = uri)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        transformations(CircleCropTransformation())
                                    }).build()
                            ),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } ?: run {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                        ) {
                            PlaceholderIcon()
                        }
                    }
                }
                Column {
                    Row(modifier = Modifier.padding(start = 4.dp,top = 10.dp)) {
                        Button(
                            onClick = { selectProfilePicture() },
                            enabled = featureEnabled.value,
                            colors = ButtonDefaults.buttonColors(Color(0xFF495E57)),
                            modifier = Modifier
                                .padding(start = 10.dp, end = 15.dp)
                                .wrapContentWidth(),
                            shape = RoundedCornerShape(10.dp),

                            )
                        {
                            Text(
                                text = "Change",
                                fontFamily = FontFamily(Font(R.font.karlaregular))
                            )
                        }
                        Button(
                            onClick = { removeProfilePicture() },
                            enabled = featureEnabled.value && selectedImageUri.value != null,
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFF495E57),
                                disabledContentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Remove",
                                fontFamily = FontFamily(Font(R.font.karlaregular))
                            )
                        }
                    }
                }
            }
            androidx.compose.material.OutlinedTextField(
                value = firstName.toString(),
                label = { Text("First Name") },
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 25.dp, end = 10.dp),
                enabled = false,
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )
            androidx.compose.material.OutlinedTextField(
                value = lastName.toString(),
                label = { Text("Last Name") },
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 35.dp, end = 10.dp),
                enabled = false,
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )
            androidx.compose.material.OutlinedTextField(
                value = email.toString(),
                label = { Text("Email") },
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 35.dp, end = 10.dp),
                enabled = false,
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )
            androidx.compose.material.OutlinedTextField(
                value = phonenumber.value,
                label = { Text("Phone number") },
                onValueChange = { phonenumber.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 35.dp, end = 10.dp),
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Text(
                text = "Email notification",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 12.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )
            )
            Row {
                Checkbox(
                    checked = checked1.value,
                    onCheckedChange = {
                        checked1.value = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF495E57)
                    )
                )
                Text(
                    text = "Order statuses",
                    modifier = Modifier.padding(top = 15.dp),
                    fontFamily = FontFamily(Font(R.font.karlaregular)),
                    fontSize = 15.sp
                )
            }
            Row {
                Checkbox(
                    checked = checked2.value,
                    onCheckedChange = {
                        checked2.value = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF495E57)
                    )
                )
                Text(
                    text = "Password changes",
                    modifier = Modifier.padding(top = 15.dp),
                    fontFamily = FontFamily(Font(R.font.karlaregular)),
                    fontSize = 15.sp
                )
            }
            Row {
                Checkbox(
                    checked = checked3.value,
                    onCheckedChange = {
                        checked3.value = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF495E57)
                    )
                )
                Text(
                    text = "Special offers",
                    modifier = Modifier.padding(top = 15.dp),
                    fontFamily = FontFamily(Font(R.font.karlaregular)),
                    fontSize = 15.sp
                )
            }
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
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF4CE14)),
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14))
            ) {
                Text(
                    text = "Log out",
                    color = Color(0xFF333333),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )
            }
        }
    }
}

@Composable
fun PlaceholderIcon() {
    Image(
        painter = painterResource(R.drawable.profile),
        contentDescription = "Placeholder",
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
    )
}