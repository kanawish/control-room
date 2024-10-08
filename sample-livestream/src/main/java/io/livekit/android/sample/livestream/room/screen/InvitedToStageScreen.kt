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

package io.livekit.android.sample.livestream.room.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.livekit.android.sample.livestream.model.AppModel
import io.livekit.android.sample.livestream.model.RoomNav
import io.livekit.android.sample.livestream.ui.control.HorizontalLine
import io.livekit.android.sample.livestream.ui.control.LargeTextButton
import io.livekit.android.sample.livestream.ui.control.Spacer
import io.livekit.android.sample.livestream.ui.theme.Dimens
import org.koin.compose.koinInject

/**
 * Displays for a viewer that has been invited to the stage.
 */
@Composable
fun InvitedToStageScreen(
    appModel: AppModel = koinInject(),
    roomNav: RoomNav = koinInject()
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "You've been invited to speak!",
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(24.dp)
        )

        HorizontalLine()

        Spacer(Dimens.spacer)

        LargeTextButton(
            text = "Accept",
            onClick = {
                appModel.requestToJoin()
                roomNav.roomNavigateUp()
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
