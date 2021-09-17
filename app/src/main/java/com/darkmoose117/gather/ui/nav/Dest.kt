package com.darkmoose117.gather.ui.nav

object Nav {

    object Args {
        const val QUERY = "query"
        const val ID = "id"
    }

    sealed class Dest(
        open val route: String
    ) {

        object Sets : Dest(
            route = "sets"
        )

        object Search : Dest(
            route = "search"
        )

        object CardList : Dest(
            route = "cards/{${Args.QUERY}}"
        ) {
            fun route(query: String) = "cards/$query"

            fun routeForSet(setCode: String) =
                route("e:$setCode")
        }

        object CardDetail : Dest(
            route = "card/{${Args.ID}}",
        ) {
            fun route(id: String) = "card/$id"
        }
    }

}