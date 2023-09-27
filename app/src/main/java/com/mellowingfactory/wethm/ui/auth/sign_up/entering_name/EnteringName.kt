package com.mellowingfactory.wethm.ui.auth.sign_up.entering_name

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.auth.sign_up.SignUpViewModel
import com.mellowingfactory.wethm.ui.extentions.AuthLayout
import com.mellowingfactory.wethm.ui.extentions.WethmButton
import com.mellowingfactory.wethm.ui.extentions.WethmTextField
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.utils.keyboardAsState

@Composable
fun EnteringName(
    vm: SignUpViewModel,
    goToLogin: () -> Unit,
    goEnteringPassword: () -> Unit
){
    val keyboardState = keyboardAsState()
    AuthLayout(
        headerContent = { Header() },
        title = stringResource(R.string.SIGNUP_TITLE),
        onBackClick = goToLogin,
        isExpand = !keyboardState.value
    ) {
        MainContent(
            goEnteringPassword = goEnteringPassword,
            vm = vm
        )
    }
}

@Composable
private fun BoxScope.MainContent(
    goEnteringPassword: () -> Unit,
    vm: SignUpViewModel
){
    Fields(vm)
    ButtonNext(
        onClick = goEnteringPassword,
        enable = vm.validNames.value && vm.validEmail.value
    )
}

@Composable
private fun BoxScope.ButtonNext(
    onClick: () -> Unit,
    enable: Boolean
) = WethmButton(
    modifier = Modifier
        .align(Alignment.BottomCenter)
        .navigationBarsPadding()
        .padding(bottom = 20.dp),
    enable = enable,
    onClick = onClick,
    text = stringResource(id = R.string.NEXT)
)


@Composable
private fun Fields(
    vm: SignUpViewModel
) = Column{
    WethmTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 40.dp),
        value = vm.signUpState.value.surname,
        onValueChange = {
            vm.editNames(surname = it)
        },
        label = stringResource(R.string.SURNAME),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
    )
    WethmTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 22.dp),
        value = vm.signUpState.value.name,
        onValueChange = {
            vm.editNames(name = it)
        },
        label = stringResource(R.string.NAME),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
    )
    WethmTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 22.dp),
        value = vm.signUpState.value.email,
        onValueChange = {
            vm.editEmail(email = it)
        },
        label = stringResource(R.string.EMAIL),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    Text(
        modifier = Modifier
            .padding(
                vertical = 12.dp,
                horizontal = 22.dp
            ),
        text = "•  ${stringResource(R.string.SIGNUP_EMAIL_V)}",
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = gray300
        )
    )
}

@Composable
private fun BoxScope.Header() = Column(
    modifier = Modifier.align(Alignment.BottomStart)
){
    Text(
        modifier = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 32.dp
            ),
        text = stringResource(id = R.string.SIGNUP_WELCOME),
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(600),
            color = white.copy(alpha = 0.3f),
        )
    )
    Image(
        modifier = Modifier
            .padding(start = 32.dp)
            .height(32.dp),
        painter = painterResource(R.drawable.logos),
        contentScale = ContentScale.FillHeight,
        colorFilter = ColorFilter.tint(white),
        contentDescription = null
    )
    Text(
        modifier = Modifier
            .padding(
                start = 32.dp,
                top = 16.dp,
                bottom = 22.dp
            ),
        text = stringResource(R.string.SIGNUP_SUBTITLE),
        style = TextStyle(
            fontSize = 18.sp,
            lineHeight = 23.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(300),
            color = white
        )
    )
}

@Preview
@Composable
private fun PreviewEnteringName() = WethmTheme{
    EnteringName(
        vm = SignUpViewModel(rememberNavController()),
        goToLogin = {},
        goEnteringPassword = {}
    )
}