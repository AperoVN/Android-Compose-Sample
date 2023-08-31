package com.apero.sample.ui.compose.navigation.controller

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

abstract class NavigationNoArg : NavNode, NavDestination<Unit> {
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun parse(entry: NavBackStackEntry) {}

    override fun parse(saveStateHandled: SavedStateHandle){}
    override fun destination(arg: Unit): DestinationRoute = route
}

interface NavigationController<Arg> : NavNode, NavDestination<Arg>

interface NavNode {
    val route: String
    val arguments: List<NamedNavArgument>
}

typealias DestinationRoute = String

interface NavDestination<Arg> {
    fun destination(arg: Arg): DestinationRoute
    fun parse(entry: NavBackStackEntry): Arg
    fun parse(saveStateHandled: SavedStateHandle): Arg
}