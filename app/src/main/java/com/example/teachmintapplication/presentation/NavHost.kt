package com.example.teachmintapplication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.teachmintapplication.domain.Item
import kotlinx.serialization.Serializable

@Composable
fun MyNavHost() {


    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            val viewModel = hiltViewModel<MyViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val query = remember { mutableStateOf("") }

            Column(
                modifier = Modifier.fillMaxSize().padding(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                OutlinedTextField(
                    value = query.value,
                    onValueChange = {
                        query.value = it
                        viewModel.searchRepo(it)
                    },
                    maxLines = 1,
                    label = {
                        Text("Search for repository")
                    }

                )

                uiState.repoList?.let { repoList ->

                    LazyColumn {

                        val lastIndex = repoList.lastIndex
                        itemsIndexed(
                            items = repoList,
                        ) { lazyItemScope, repo ->
                            RepoCard(repo)
                            if ((lastIndex == lazyItemScope) && (lastIndex != 0)) {
                                LaunchedEffect(Unit) {
                                    viewModel.getMoreRepo()
                                }
                            }
                        }

                    }


                }


            }
        }
    }
}






@Composable
fun RepoCard(repo : Item) {
   Card(
      modifier = Modifier.fillMaxWidth()
          .padding(16.dp)
          .wrapContentHeight(),
       shape = RoundedCornerShape(8.dp)
   ) {

       Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){

           AsyncImage(
               model = repo.owner.avatar_url,
               contentDescription = null,
               modifier = Modifier.size(64.dp).padding(end = 16.dp),
               contentScale = ContentScale.Crop

           )



           Text(style = TextStyle.Default, text = repo.full_name)


       }



   }
}

sealed class Screen{

    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data class RepoDetailsScreen(val key:String) : Screen()
}