package com.darkmoose117.gather.ui.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.rememberImagePainter
import com.darkmoose117.gather.ui.LocalSymbolProvider
import com.darkmoose117.scryfall.data.CardSymbol

private const val SYM_OPEN = '{'
private const val SYM_CLOSE = '}'

@Composable
fun SymbolText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    val symbolMap = LocalSymbolProvider.current
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val inlineContent = mutableMapOf<String, InlineTextContent>()
    val annotatedString = remember(text) {
        var remaining: String = text
        buildAnnotatedString {
            while (remaining.isNotEmpty()) {
                if (remaining.first() == SYM_OPEN) {
                    val closeSymbolIndex = remaining.indexOf(SYM_CLOSE)
                    val symbolText = remaining.substring(0, closeSymbolIndex + 1)
                    val cardSymbol = symbolMap[symbolText]
                    if (cardSymbol == null) {
                        this.append(symbolText)
                    } else {
                        this.appendInlineContent(id = symbolText, alternateText = symbolText)
                        if (!inlineContent.containsKey(symbolText)) {
                            inlineContent[symbolText] = inlineSymbolContent(cardSymbol)
                        }
                    }
                    remaining = remaining.removePrefix(symbolText)
                } else {
                    val normalText = remaining.substringBefore(SYM_OPEN)
                    this.append(normalText)
                    remaining = remaining.removePrefix(normalText)
                }
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier,
        style = style,
        inlineContent = inlineContent,
        onTextLayout = { layoutResult.value = it }
    )
}

fun inlineSymbolContent(cardSymbol: CardSymbol): InlineTextContent {
    return InlineTextContent(
        Placeholder(1.em, 1.em, PlaceholderVerticalAlign.TextCenter)
    ) { altText ->
        Image(
            painter = rememberImagePainter(data = cardSymbol.svgUri),
            contentDescription = altText,
            modifier = Modifier.defaultMinSize(12.dp, 12.dp)
        )
    }
}

