package com.mellowingfactory.wethm.ui.auth.sign_up.code

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.auth.sign_up.SignUpViewModel
import com.mellowingfactory.wethm.ui.extentions.AuthLayout
import com.mellowingfactory.wethm.ui.extentions.CodeField
import com.mellowingfactory.wethm.ui.extentions.WethmButton
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.red400
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.utils.keyboardAsState
import kotlinx.coroutines.CoroutineScope

@Composable
fun Code(
    vm: SignUpViewModel,
    goBack: () -> Unit,
    onSignIn: (scope: CoroutineScope) -> Unit
) {
    val scope = rememberCoroutineScope()
    val keyboardState = keyboardAsState()
    AuthLayout(
        title = stringResource(R.string.VERIFICATION_TITLE),
        headerContent = { Header() },
        onBackClick = goBack,
        isExpand = !keyboardState.value
    ) {
        MainContent(
            vm = vm,
            onSignIn = { onSignIn(scope) }
        )
    }
}

@Composable
private fun BoxScope.Header(){
    Text(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(
                start = 32.dp,
                bottom = 22.dp
            ),
        text = stringResource(R.string.VERIFICATION_TITLE2),
        style = TextStyle(
            fontSize = 18.sp,
            lineHeight = 23.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_light)),
            fontWeight = FontWeight(300),
            color = white,
        )
    )
}

@Composable
private fun BoxScope.MainContent(
    vm: SignUpViewModel,
    onSignIn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoBlock(
            email = vm.codeSignUpState.value.email,
            message = vm.messageCode.value
        )

        CodeField(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 30.dp),
            length = 6,
            isError = false,
            value = when (vm.isRestorePassword.value) {
                true -> vm.forgetState.value.code
                false -> vm.codeSignUpState.value.code
            },
            onValueChange = { vm.editCode(it) }
        )
    }
    Buttons(
        onVerify = {
            vm.confirmCode(
                onSignIn = onSignIn
            )
        },
        enable = when (vm.isRestorePassword.value) {
            true -> vm.forgetState.value.code.length
            false -> vm.codeSignUpState.value.code.length
        } == 6
    )
}

@Composable
private fun InfoBlock(
    email: String,
    message: String
){
    Text(
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(horizontal = 30.dp),
        text = email,
        style = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = gray300,
        )
    )

    Text(
        modifier = Modifier
            .padding(top = 6.dp)
            .padding(horizontal = 30.dp),
        text = message,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = red400
        )
    )
}

@Composable
private fun BoxScope.Buttons(
    onVerify: () -> Unit,
    enable: Boolean
) = Column(
    modifier = Modifier
        .imePadding()
        .align(Alignment.BottomCenter)
        .navigationBarsPadding()
){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerify() }
            .padding(bottom = 32.dp),
        text = stringResource(R.string.VERIFICATION_CODE_R),
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = gray300,
            textAlign = TextAlign.Center,
        )
    )
    WethmButton(
        modifier = Modifier
            .padding(bottom = 20.dp),
        enable = enable,
        onClick = onVerify,
        text = stringResource(id = R.string.VERIFY)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewCode() = WethmTheme{
    Code(SignUpViewModel(rememberNavController()),{},{})
}