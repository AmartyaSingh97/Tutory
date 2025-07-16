package com.amartyasingh.tutory.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amartyasingh.tutory.R
import com.amartyasingh.tutory.dataStore.OnboardingPreferences
import com.amartyasingh.tutory.model.OnboardingCarouselModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun OnboardingPagerScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val onboardingPages = listOf(
        OnboardingCarouselModel(
            imageRes = R.drawable.onboarding1,
            title = "Welcome to Tutory",
            description = "Easily manage student attendance daily with our intuitive app."
        ),
        OnboardingCarouselModel(
            imageRes = R.drawable.onboarding2,
            title = "Effortless Fee Management",
            description = "Simplify fee collection, track payments, and manage student balances effectively with our intuitive app."
        ),
        OnboardingCarouselModel(
            imageRes = R.drawable.onboarding3,
            title = "Analyze your revenue",
            description = "Get insights into your tuition center's financial performance, including revenue trends and outstanding balances."
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
        ) {
            HorizontalPager(
                count = onboardingPages.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(page = onboardingPages[page])
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.Black,
                inactiveColor = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(140.dp))

            Button(
                onClick = {
                    if (pagerState.currentPage == onboardingPages.lastIndex) {
                        scope.launch {
                            OnboardingPreferences.setOnboardingCompleted(context)
                            onFinished()
                        }
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp).padding(horizontal = 16.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == onboardingPages.lastIndex) "Get Started" else "Next",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (pagerState.currentPage != onboardingPages.lastIndex) {
            TextButton(
                onClick = {
                    scope.launch {
                        OnboardingPreferences.setOnboardingCompleted(context)
                        onFinished()
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Text(text = "Skip >", color = Color.Gray)
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingCarouselModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(page.imageRes)
                .build(),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.description,
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

    }
}

