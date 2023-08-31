package com.apero.sample.ui.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.apero.sample.R
import com.apero.sample.ui.compose.theme.ColorPrimary

/**
 * Created by KO Huyn on 28/06/2023.
 */

@Preview
@Composable
fun ToolbarDefaultClosePreview() {
    ToolbarDefault(
        modifier = Modifier,
        iconNegative = painterResource(id = R.drawable.ic_close),
        onClickNegative = {},
        titleAlignment = ToolbarTitleAlignment.CENTER,
        layoutTitle = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp,
                color = Color.White
            )
        }, layoutPositive = {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp + (8.dp * 2))
                    .padding(8.dp),
            )
        }
    )
}

@Preview
@Composable
fun ToolbarDefaultBackPreview() {
    ToolbarDefault(
        modifier = Modifier,
        iconNegative = painterResource(id = R.drawable.ic_back),
        onClickNegative = {},
        titleAlignment = ToolbarTitleAlignment.CENTER,
        layoutTitle = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp,
                color = Color.White
            )
        }
    )
}

@Preview
@Composable
fun ToolbarDefaultHomePreview() {
    ToolbarDefault(
        modifier = Modifier,
        iconNegative = null,
        onClickNegative = {},
        titleAlignment = ToolbarTitleAlignment.START,
        layoutTitle = {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp,
                color = Color.White
            )
        }, layoutPositive = {
            Icon(
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp + (8.dp * 2))
                    .padding(8.dp),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp + (8.dp * 2))
                    .padding(8.dp),
            )
        }
    )
}

enum class ToolbarTitleAlignment {
    START, CENTER
}

@Composable
fun ToolbarDefault(
    modifier: Modifier = Modifier,
    backgroundColor: Color = ColorPrimary,
    iconNegative: Painter? = painterResource(id = R.drawable.ic_back),
    onClickNegative: () -> Unit,
    iconTint: Color = Color.White,
    titleAlignment: ToolbarTitleAlignment = ToolbarTitleAlignment.CENTER,
    layoutPositive: @Composable RowScope.() -> Unit = {},
    layoutTitle: @Composable RowScope.() -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .then(modifier),
    ) {
        val (layoutLeftRef, layoutRightRef, titleRef) = createRefs()
        if (iconNegative != null) {
            Icon(
                modifier = Modifier
                    .constrainAs(layoutLeftRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(24.dp + (8.dp * 2))
                    .padding(8.dp)
                    .clickable(role = Role.Button) { onClickNegative() },
                painter = iconNegative,
                contentDescription = null,
                tint = iconTint
            )
        }
        Row(
            modifier = Modifier.constrainAs(titleRef) {
                when (titleAlignment) {
                    ToolbarTitleAlignment.CENTER -> {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

                    ToolbarTitleAlignment.START -> {
                        if (iconNegative != null) {
                            start.linkTo(layoutLeftRef.end)
                        } else {
                            start.linkTo(parent.start)
                        }
                    }
                }
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            layoutTitle()
        }
        Row(modifier = Modifier.constrainAs(layoutRightRef) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }) {
            layoutPositive()
        }
    }
}