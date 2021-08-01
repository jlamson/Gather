package com.darkmoose117.gather.ui.search

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.components.HomeNavigationIcon
import com.darkmoose117.gather.ui.components.TopAppBarWithBottomContent
import com.darkmoose117.gather.ui.nav.Nav
import com.darkmoose117.gather.util.ThemedPreview
import com.darkmoose117.gather.util.openUrlDialog
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    fun submitQuery(query: String) = scope.launch {
        if (query.isNotBlank()) {
            navController.navigate(Nav.Dest.CardList.route(query))
        } else {
            Toast.makeText(context, "Enter something!", Toast.LENGTH_LONG).show()
        }
    }

    var searchBarInput by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBarWithBottomContent(
            title = { Text(stringResource(id = R.string.search)) },
            navigationIcon = { HomeNavigationIcon() },
            bottomContent = {
                SearchBar(::submitQuery, searchBarInput) { searchBarInput = it }
            }
        )

        AndroidView(factory = ::WebView) { webView ->
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    val httpUrl = url?.toHttpUrlOrNull() ?: return false
                    if (httpUrl.host == "scryfall.com") {
                        val query = httpUrl.queryParameter("q")
                        if (httpUrl.pathSegments[0] == "search" && !query.isNullOrBlank()) {
                            searchBarInput = query
                        } else {
                            context.openUrlDialog(httpUrl.toString())
                        }
                    }
                    return true
                }
            }
            webView.loadUrl("https://scryfall.com/docs/syntax")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    submitQuery: (String) -> Unit,
    input: String,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = input,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(id = R.string.search))
        },
        leadingIcon = {
            IconButton(onClick = { submitQuery(input) }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { onValueChange("") }) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = stringResource(id = R.string.clear)
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                submitQuery(input)
                keyboardController?.hide()
            }
        )
    )
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun LightSetsScreen() {
    ThemedPreview {
        SearchScreen(rememberNavController())
    }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ThemedPreview(darkTheme = true) {
        SearchScreen(rememberNavController())
    }
}