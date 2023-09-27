package com.mellowingfactory.wethm.ui.auth.sign_up.restore_password

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
fun RestorePassword(
    vm: SignUpViewModel,
    goBack: () -> Unit,
    goEnteringCode: () -> Unit
){
    val keyboardState = keyboardAsState()
    AuthLayout(
        headerContent = { Header() },
        title = stringResource(R.string.RESET_PASSWORD),
        isExpand = !keyboardState.value,
        onBackClick = goBack
    ) {
        MainContent(
            vm = vm,
            goEnteringCode = goEnteringCode
        )
    }
}

@Composable
private fun BoxScope.Header() = Text(
    modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(
            start = 32.dp,
            bottom = 22.dp
        ),
    style = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.pretendard_light)),
        fontWeight = FontWeight(300),
        lineHeight = 23.sp,
        color = white,
    ),
    text = buildAnnotatedString {
        withStyle(SpanStyle()){
            append(stringResource(R.string.FORGOT_P_TITLE2))
        }
        append(" ")
        withStyle(SpanStyle(
            fontWeight = FontWeight(600)
        )){
            append(stringResource(R.string.app_name))
        }
    }
)


@Composable
private fun BoxScope.MainContent(
    vm: SignUpViewModel,
    goEnteringCode: () -> Unit
){
    Column {
        WethmTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp),
            value = vm.forgetState.value.email,
            onValueChange = {
                vm.editEmail(email = it)
            },
            label = stringResource(R.string.EMAIL),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 21.dp),
            text = stringResource(R.string.FORGOT_P_HINT),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(400),
                color = gray300,
                textAlign = TextAlign.Justify,
            )
        )
    }
    ButtonNext(
        onClick = { vm.resetPassword() },
        enable = vm.validEmail.value
    )
}

@Composable
private fun BoxScope.ButtonNext(
    onClick: () -> Unit,
    enable: Boolean
) = WethmButton(
    modifier = Modifier
        .imePadding()
        .align(Alignment.BottomCenter)
        .padding(bottom = 20.dp),
    enable = enable,
    onClick = onClick,
    text = stringResource(id = R.string.NEXT)
)


@Preview
@Composable
private fun PreviewPassword() = WethmTheme{
    RestorePassword(SignUpViewModel(rememberNavController()),{},{})
}