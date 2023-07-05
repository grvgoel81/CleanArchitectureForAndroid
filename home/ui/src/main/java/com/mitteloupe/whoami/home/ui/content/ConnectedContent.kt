package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel

@Composable
fun ConnectedContent(
    connectionDetails: ConnectionDetailsUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(12.dp, 8.dp, 12.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_card_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(0.dp),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = connectionDetails.ipAddress,
                fontSize = 24.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
            Text(
                text = stringResource(R.string.home_ip_description),
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
        }
    }

    Column(
        modifier = modifier.padding(12.dp, 24.dp, 12.dp, 0.dp)
    ) {
        Text(
            text = stringResource(R.string.home_details_title),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        listOf(
            R.string.home_city_label to
                connectionDetails.city,
            R.string.home_region_label to
                connectionDetails.region,
            R.string.home_country_label to
                connectionDetails.countryName,
            R.string.home_geolocation_label to
                connectionDetails.geolocation,
            R.string.home_post_code_label to
                connectionDetails.postCode,
            R.string.home_time_zone_label to
                connectionDetails.timeZone,
            R.string.home_internet_service_provider_label to
                connectionDetails.internetServiceProviderName
        ).forEach { (labelResourceId, fieldValue) ->
            if (fieldValue != null) {
                Row(modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)) {
                    Text(
                        text = stringResource(labelResourceId),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(148.dp)
                    )
                    Text(
                        text = fieldValue
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ConnectedContent(
            ConnectionDetailsUiModel(
                ipAddress = "0.0.0.0",
                city = "Brentwood",
                region = "England",
                countryName = "GB",
                geolocation = "0, 0",
                postCode = "AB12 3CD",
                timeZone = "Europe/London",
                internetServiceProviderName = "TalkTalk"
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}