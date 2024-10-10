package com.example.teachmintapplication.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.example.teachmintapplication.domain.models.RepositoryItem
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            val viewModel = hiltViewModel<MainViewModel>()
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
                        viewModel.searchRepositories(it)
                    },
                    maxLines = 1,
                    label = {
                        Text("Search for repository")
                    }

                )

                uiState.repositoryItemList?.let { repositoryItemList ->

                    LazyColumn {
                        val lastIndex = repositoryItemList.lastIndex
                        itemsIndexed(
                            items = repositoryItemList,
                        ) { lazyItemScope, repo ->
                            RepoCard(repo) {
                                navHostController.navigate(Screen.RepoDetailsScreen(it))
                            }
                            if ((lastIndex == lazyItemScope) && (lastIndex != 0)) {
                                LaunchedEffect(Unit) {
                                    viewModel.getMoreRepositories()
                                }
                            }
                        }

                    }
                }
            }
        }

        composable<Screen.RepoDetailsScreen> { backStackEntry ->
            val profile: Screen.RepoDetailsScreen = backStackEntry.toRoute()
            val viewModel = hiltViewModel<MainViewModel>()
            val context = LocalContext.current
            LaunchedEffect(key1 = profile.key) {
                viewModel.getRepositoryDetails(profile.key)

            }

            val repoUiState by viewModel.repositoryDetailsState.collectAsStateWithLifecycle()

            repoUiState.repository?.let { repository ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentHeight()
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                repository.htmlUrl.let { url ->
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                }
                            },
                        shape = RoundedCornerShape(8.dp),
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = repository.owner.avatarUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(end = 16.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Repository Name",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = repository.fullName.orEmpty(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Description",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = repository.description ?: "No description available",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))


                                    Text(
                                        text = "Repository URL",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = repository.htmlUrl ?: "No URL available",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            val topContributors =
                                repository.contributors?.take(2)?.joinToString(", ") { it.login }

                            if (!topContributors.isNullOrEmpty()) {
                                Text(
                                    text = "Top Contributors: $topContributors",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary // Secondary color for contributors
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                        }
                    }


                }
            }
        }
    }
}

@Composable
fun RepoCard(repo : RepositoryItem, onCardClick: (String)->Unit) {
   Card(
      modifier = Modifier.fillMaxWidth()
          .padding(16.dp)
          .wrapContentHeight().clickable {onCardClick(repo.fullName)},
       shape = RoundedCornerShape(8.dp)
   ) {

       Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){

           AsyncImage(
               model = repo.owner.avatarUrl,
               contentDescription = null,
               modifier = Modifier.size(64.dp).padding(end = 16.dp),
               contentScale = ContentScale.Crop

           )

           Text(style = TextStyle.Default, text = repo.name)


       }

   }
}

sealed class Screen{

    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data class RepoDetailsScreen(val key:String) : Screen()
}