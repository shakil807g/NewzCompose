package com.shakil.newzcompose.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.shakil.newzcompose.ui.purple200
import com.shakil.newzcompose.R
import com.shakil.newzcompose.ui.headline.HeadlineScreen
import com.shakil.newzcompose.ui.main.MainViewModel
import com.shakil.newzcompose.util.SysUiController
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.navigationBarsPadding


sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Top : BottomNavigationScreens("Top",R.string.menu_hot, Icons.Filled.Whatshot)
    object Search : BottomNavigationScreens("Search", R.string.menu_search, Icons.Filled.Search)
    object Source : BottomNavigationScreens("Source", R.string.menu_source, Icons.Filled.Source)
}

@ExperimentalMaterialApi
@Composable
fun HomeScreen(headlinesViewModel: MainViewModel) {
    val navController = rememberNavController()
    with(SysUiController.current) {
        setStatusBarColor(Color.Transparent, darkIcons = !isSystemInDarkTheme())
        setNavigationBarColor(Color.Transparent, darkIcons = !isSystemInDarkTheme())
    }
    val bottomNavigationItems = listOf(
            BottomNavigationScreens.Top,
            BottomNavigationScreens.Search,
            BottomNavigationScreens.Source
    )

    ConstraintLayout {
        val (body) = createRefs()
        Scaffold(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier.constrainAs(body) {
                    top.linkTo(parent.top)
                },
                bottomBar = { NewzAppBottomBar(navController, bottomNavigationItems) }
        ) {
            MainScreenNavigationConfigurations(navController,headlinesViewModel)
        }
    }
}

@Composable
fun NewzAppBottomBar(navController: NavHostController,
                     bottomNavigationItems: List<BottomNavigationScreens>) {
    BottomNavigation(
            backgroundColor = purple200,
            modifier = Modifier.navigationBarsHeight(56.dp)
    ) {
        val currentRoute = currentRoute(navController)
        bottomNavigationItems.forEach { tab ->
            BottomNavigationItem(
                    icon = { Icon(imageVector = tab.icon) },
                    label = { Text(text = stringResource(tab.resourceId), color = Color.White) },
                    selected = tab.route == currentRoute,
                    onClick = {
                        if (currentRoute != tab.route) {
                            navController.navigate(tab.route)
                        }
                    },
                    alwaysShowLabels = false,
                    selectedContentColor = AmbientContentColor.current,
                    unselectedContentColor = AmbientContentColor.current,
                    modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}

@ExperimentalMaterialApi
@Composable
private fun MainScreenNavigationConfigurations(
        navController: NavHostController,
        headlinesViewModel: MainViewModel
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Top.route) {
        composable(BottomNavigationScreens.Top.route) {
            HeadlineScreen(headlinesViewModel)
        }
        composable(BottomNavigationScreens.Search.route) {
            Surface(color = Color.Yellow) {
                Text (text = "Search")
            }
        }
        composable(BottomNavigationScreens.Source.route) {
            Surface(color = Color.Blue) {
                Text (text = "Sources")
            }
        }
    }
}






