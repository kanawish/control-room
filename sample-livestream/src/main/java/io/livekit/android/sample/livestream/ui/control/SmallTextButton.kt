/*
 * Copyright 2023 LiveKit, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.livekit.android.sample.livestream.ui.control

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SmallTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
) {
    Button(
        colors = colors,
        shape = RoundedCornerShape(5.dp),
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier.minimumInteractiveComponentSize()
    ) {
        Text(text = text, modifier = Modifier.padding(0.dp))
    }
}

@Preview
@Composable
fun SmallTextButtonPreview() {
    SmallTextButton(
        text = "Button",
        onClick = {},
        modifier = Modifier
            .defaultMinSize(60.dp, 24.dp)
    )
}
