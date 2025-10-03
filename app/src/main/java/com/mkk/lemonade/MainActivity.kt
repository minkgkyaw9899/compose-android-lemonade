package com.mkk.lemonade

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mkk.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      LemonadeTheme {
        LemonadeApp(modifier = Modifier)
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp(modifier: Modifier) {
  var currentStep by remember { mutableIntStateOf(1) }

  var sequenceCount by remember { mutableIntStateOf(0) }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Lemonade",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
      )
    }
  ) { innerPadding ->
    Surface(modifier = modifier.padding(innerPadding)) {
      when (currentStep) {
        1 -> {
          LemonadeImageAndText(
            resourceTextId = R.string.lemon_select,
            resourceImageId = R.drawable.lemon_tree,
            resourceImageContentId = R.string.lemon_tree_content_description,
            onClick = {
              currentStep = 2
              sequenceCount = (2..4).random()
            },
            modifier = modifier
          )
        }

        2 -> {
          LemonadeImageAndText(
            resourceTextId = R.string.lemon_squeeze,
            resourceImageId = R.drawable.lemon_squeeze,
            resourceImageContentId = R.string.lemon_content_description,
            onClick = {
              sequenceCount--
              if (sequenceCount == 0) {
                currentStep = 3
              }
            }
          )
        }

        3 -> {
          LemonadeImageAndText(
            resourceTextId = R.string.lemon_drink,
            resourceImageId = R.drawable.lemon_drink,
            resourceImageContentId = R.string.lemonade_content_description,
            onClick = {
              currentStep = 4
            }
          )
        }

        4 -> {
          LemonadeImageAndText(
            resourceTextId = R.string.lemon_empty_glass,
            resourceImageId = R.drawable.lemon_restart,
            resourceImageContentId = R.string.empty_glass_content_description,
            onClick = {
              currentStep = 1
            }
          )
        }
      }
    }
  }
}

@Composable
fun LemonadeImageAndText(
  resourceImageId: Int,
  resourceImageContentId: Int,
  resourceTextId: Int,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
  ) {
    Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
      ) {
        Image(
          painter = painterResource(resourceImageId),
          contentDescription = stringResource(resourceImageContentId),
          modifier = modifier
            .width(dimensionResource(R.dimen.button_image_width))
            .height(dimensionResource(R.dimen.button_image_height))
            .padding(dimensionResource(R.dimen.button_interior_padding))
        )
      }
      Spacer(modifier = modifier.height(20.dp))
      Text(
        text = stringResource(resourceTextId),
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LemonadeAppPreview() {
  LemonadeTheme {
    LemonadeApp(modifier = Modifier)
  }
}
