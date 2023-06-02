package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavHostController) {
    val viewModel: MyViewModel = viewModel()
    val databaseMenuItems = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value
    val searchPhrase = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMenuDataIfNeeded()
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){
        Header(navController)
        UpperPanel {
            searchPhrase.value = it
        }
        LowerPanel(databaseMenuItems, searchPhrase)
    }
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Spacer(modifier = Modifier.width(50.dp))
        Image(
            painter = painterResource(id = R.drawable.littlelemonlogo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .size(40.dp)
        )

        Box(modifier = Modifier
            .size(50.dp)
            .clickable { navController.navigate(Profile.route) }){
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun UpperPanel(search : (parameter: String)-> Unit) {
    val searchPhrase = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF4CE14),
            fontFamily = FontFamily(Font(R.font.markazitextregular))
        )
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 30.sp,
            color = Color(0xFFEDEFEE),
            fontFamily = FontFamily(Font(R.font.karlaregular))
        )
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.description),
                color = Color(0xFFEDEFEE),
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .fillMaxWidth(0.6f),
                fontFamily = FontFamily(Font(R.font.karlaregular))
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = 20.dp, end = 5.dp)
                    .size(height = 140.dp, width = 120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
        OutlinedTextField(
            value = searchPhrase.value,
            onValueChange = {
                searchPhrase.value = it
                search(searchPhrase.value)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedBorderColor = Color.Yellow,
                cursorColor = Color.Black
            )
        )

    }


}
@Composable
fun LowerPanel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map {
        it.category.replaceFirstChar {character ->
            character.uppercase()
        }
    }.toSet()

    val selectedCategory = remember {
        mutableStateOf("")
    }

    val items = if(search.value == "") {
        databaseMenuItems
    } else {
        databaseMenuItems.filter {
            it.title.contains(search.value, ignoreCase = true)
        }
    }

    val filteredItems = if(selectedCategory.value == "" || selectedCategory.value == "all"){
        items
    }
    else{
        items.filter {
            it.category.contains(selectedCategory.value, ignoreCase = true)
        }
    }
    Column {
        MenuCategories(categories) {selectedCategory.value = it
        }
        Spacer(modifier = Modifier.size(2.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (item in filteredItems){
                MenuItem(item = item)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {

    val itemDescription = if(item.description.length>100) {
        item.description.substring(0,100) + ". . ."
    }
    else{
        item.description
    }
    Card(
        modifier = Modifier
            .clickable {}
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontFamily = FontFamily(Font(R.font.karlaregular)),
                    fontSize = 18.sp
                )
                Text(
                    text = itemDescription,
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )

                Text(
                    text = "$ ${item.price}",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.karlaregular))
                )

            }

            GlideImage(
                model = item.imageUrl,
                contentDescription = "",
                Modifier
                    .size(100.dp, 100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            thickness = .5.dp
        )
    }
}
@Composable
fun MenuCategories(categories: Set<String>, categoryLambda : (sel: String) -> Unit) {
    val cat = remember {
        mutableStateOf("")
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Column(
            Modifier
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 8.dp)
        )
        {
            Text(
                text = "ORDER FOR DELIVERY!",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.karlaregular))
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryButton(category = "All") {
                    cat.value = it.lowercase()
                    categoryLambda(it.lowercase())
                }

                for (category in categories) {
                    CategoryButton(category = category) {
                        cat.value = it
                        categoryLambda(it)
                    }
                }

            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray,
            thickness = .5.dp
        )
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory:(sel: String) -> Unit) {
    val isClicked = remember{
        mutableStateOf(false)
    }
    Button(
        onClick = {
            isClicked.value = !isClicked.value
            selectedCategory(category)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor =  Color.LightGray),
        shape = RoundedCornerShape(40),
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            text = category
        )
    }
}

