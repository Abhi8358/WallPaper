package com.vedic.deepinsea.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vedic.deepinsea.data.models.Collection
import com.vedic.deepinsea.icons.AppIcons

@Composable
fun CollectionItemView(collection: Collection, onClick: (Collection) -> Unit) {
    collection.title?.let {
        ConstraintLayout(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    onClick(collection)
                }
        ) {
            val (texts, arrow) = createRefs()
            Text(
                modifier = Modifier
                    .constrainAs(texts) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(arrow.start)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.W500,
                text = it
            )
            Icon(
                modifier = Modifier
                    .constrainAs(arrow) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)

                    }
                    .padding(start = 4.dp, end = 12.dp, top = 4.dp, bottom = 4.dp),
                imageVector = AppIcons.arrowRight,
                contentDescription = collection.description
            )
        }
    }
}

@Composable
@Preview
fun prev() {
    CollectionItemView(Collection("vdfvdf", "fghdfjghdfg", 4, 9, true, "fjghdfjghdgjhdf", 5)) {

    }
}