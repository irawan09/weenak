package irawan.electroshock.weenak.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import irawan.electroshock.weenak.model.RecipeModel


@Composable
fun DataCard(recipeModel: List<RecipeModel>?) {
LazyColumn {
    items(recipeModel!!.size) { index ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(2.dp)
                .wrapContentSize(Alignment.TopStart)) {
                    Card(elevation = 4.dp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                        .clickable { }) {

                        var foodName = recipeModel[index].foodName
                        var foodImage = recipeModel[index].foodImage
                        foodImage =
                            "https://ih1.redbubble.net/image.620404389.8843/st,small,507x507-pad,600x600,f8f8f8.u1.jpg"
                        var foodDescription = recipeModel[index].foodDescription
                        var foodVideos = recipeModel[index].foodVideos
//                    Log.d("Videos", foodVideos)
                        Column(
                            modifier = Modifier.padding(all = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Column(
                                modifier = Modifier.padding(all = 1.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(4.dp)) {
                                    val painter = rememberImagePainter(data = foodImage, builder = {})
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Column(modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()) {
                                Text(text = foodName, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
                                Text(text = foodDescription, fontSize = 10.sp)
                            }
                        }
                    }
            }
        }
    }
}
