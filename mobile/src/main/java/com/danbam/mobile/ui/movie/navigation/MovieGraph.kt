package com.danbam.mobile.ui.movie.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.danbam.mobile.ui.movie.all.MovieAllScreen
import com.danbam.mobile.ui.movie.detail.MovieDetailScreen
import com.danbam.mobile.ui.movie.make.AddActorScreen
import com.danbam.mobile.ui.movie.make.MakeMovieViewModel
import com.danbam.mobile.ui.movie.make.SearchActorScreen
import com.danbam.mobile.ui.movie.make.WriteActorScreen
import com.danbam.mobile.ui.movie.make.WriteIntroduceScreen
import com.danbam.mobile.ui.movie.play.MoviePlayScreen
import com.google.accompanist.navigation.animation.composable

sealed class MovieNavigationItem(val route: String) {
    object Detail : MovieNavigationItem("movieDetail")
    object Play : MovieNavigationItem("moviePlay")
    object All : MovieNavigationItem("movieAll")
    object WriteIntroduce : MovieNavigationItem("movieWriteIntroduce")
    object AddActor : MovieNavigationItem("movieAddActor")
    object SearchActor : MovieNavigationItem("movieSearchActor")
    object WriteActor : MovieNavigationItem("movieWriteActor")
}

object MovieDeepLinkKey {
    const val ADD_ACTOR_TYPE = "addActorType"
    const val MOVIE_INDEX = "movieIndex"
    const val MOVIE_URL = "movieUrl"
    const val MOVIE_POSITION = "moviePosition"
    const val IS_VERTICAL = "isVertical"
}

object ActorType {
    const val ACTOR = "actor"
    const val DIRECTOR = "director"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.movieGraph(
    navController: NavHostController,
) {
    composable(
        route = MovieNavigationItem.Detail.route
            + MovieDeepLinkKey.MOVIE_INDEX + "{${MovieDeepLinkKey.MOVIE_INDEX}}",
        arguments = listOf(
            navArgument(MovieDeepLinkKey.MOVIE_INDEX) {
                type = NavType.LongType
            }
        )
    ) {
        val movieIndex = it.arguments?.getLong(MovieDeepLinkKey.MOVIE_INDEX) ?: 0
        MovieDetailScreen(navController = navController, movieIndex = movieIndex)
    }
    composable(
        route = MovieNavigationItem.Play.route
            + MovieDeepLinkKey.MOVIE_INDEX + "{${MovieDeepLinkKey.MOVIE_INDEX}}"
            + MovieDeepLinkKey.MOVIE_URL + "{${MovieDeepLinkKey.MOVIE_URL}}"
            + MovieDeepLinkKey.MOVIE_POSITION + "{${MovieDeepLinkKey.MOVIE_POSITION}}"
            + MovieDeepLinkKey.IS_VERTICAL + "{${MovieDeepLinkKey.IS_VERTICAL}}",
        arguments = listOf(
            navArgument(MovieDeepLinkKey.MOVIE_INDEX) {
                type = NavType.LongType
            },
            navArgument(MovieDeepLinkKey.MOVIE_URL) {
                type = NavType.StringType
            },
            navArgument(MovieDeepLinkKey.MOVIE_POSITION) {
                type = NavType.FloatType
            },
            navArgument(MovieDeepLinkKey.IS_VERTICAL) {
                type = NavType.BoolType
            }
        )
    ) {
        val movieUrl = it.arguments?.getString(MovieDeepLinkKey.MOVIE_URL) ?: ""
        val movieIdx = it.arguments?.getLong(MovieDeepLinkKey.MOVIE_INDEX) ?: 0
        val moviePosition = it.arguments?.getFloat(MovieDeepLinkKey.MOVIE_POSITION) ?: 0F
        val isVertical = it.arguments?.getBoolean(MovieDeepLinkKey.IS_VERTICAL) ?: false
        MoviePlayScreen(
            movieUrl = movieUrl,
            movieIdx = movieIdx,
            position = moviePosition,
            isVertical = isVertical,
            navController = navController
        )
    }
    composable(route = MovieNavigationItem.All.route) {
        MovieAllScreen(navController = navController)
    }
    composable(route = MovieNavigationItem.WriteIntroduce.route) {
        WriteIntroduceScreen(navController = navController)
    }
    composable(route = MovieNavigationItem.AddActor.route) {
        AddActorScreen(navController = navController)
    }
    composable(
        route = MovieNavigationItem.WriteActor.route
            + MovieDeepLinkKey.ADD_ACTOR_TYPE + "{${MovieDeepLinkKey.ADD_ACTOR_TYPE}}",
        arguments = listOf(
            navArgument(MovieDeepLinkKey.ADD_ACTOR_TYPE) {
                type = NavType.StringType
            }
        )
    ) {
        val addActorType =
            it.arguments?.getString(MovieDeepLinkKey.ADD_ACTOR_TYPE) ?: ActorType.ACTOR
        WriteActorScreen(
            navController = navController,
            addActorType = addActorType,
        )
    }
    composable(
        route = MovieNavigationItem.SearchActor.route
            + MovieDeepLinkKey.ADD_ACTOR_TYPE + "{${MovieDeepLinkKey.ADD_ACTOR_TYPE}}",
        arguments = listOf(
            navArgument(MovieDeepLinkKey.ADD_ACTOR_TYPE) {
                type = NavType.StringType
            }
        )
    ) {
        val addActorType =
            it.arguments?.getString(MovieDeepLinkKey.ADD_ACTOR_TYPE) ?: ActorType.ACTOR
        SearchActorScreen(
            navController = navController,
            addActorType = addActorType,
        )
    }
}