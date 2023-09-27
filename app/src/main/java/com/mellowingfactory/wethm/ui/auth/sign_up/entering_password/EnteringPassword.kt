package com.mellowingfactory.wethm.ui.auth.sign_up.entering_password

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.auth.sign_up.SignUpViewModel
import com.mellowingfactory.wethm.ui.extentions.AuthLayout
import com.mellowingfactory.wethm.ui.extentions.PasswordTextField
import com.mellowingfactory.wethm.ui.extentions.WethmButton
import com.mellowingfactory.wethm.ui.extentions.WethmCheckBox
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.gray100
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.gray600
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.utils.ValidPasswordState
import com.mellowingfactory.wethm.utils.keyboardAsState

@Composable
fun EnteringPassword(
    vm: SignUpViewModel,
    goBack: () -> Unit,
    goEnteringCode: () -> Unit,
    goToLogin: () -> Unit
){
    val keyboardState = keyboardAsState()
    AuthLayout(
        headerContent = { Header() },
        title = stringResource(R.string.CREATE_PASSWORD),
        isExpand = !keyboardState.value,
        onBackClick = goBack
    ) {

        MainContent(
            vm = vm,
            goEnteringCode = goEnteringCode,
            goToLogin = goToLogin
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
    text = stringResource(R.string.SIGNUP_PASSWORD_SET),
    style = TextStyle(
        fontSize = 18.sp,
        lineHeight = 23.sp,
        fontFamily = FontFamily(Font(R.font.pretendard_light)),
        fontWeight = FontWeight(300),
        color = white,
    )
)



@Composable
private fun BoxScope.MainContent(
    vm: SignUpViewModel,
    goEnteringCode: () -> Unit,
    goToLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Fields(vm)
        SecurityText()
        ValidChecks(
            validState = vm.validPasswordState.value
        )
    }
    ButtonNext(
        onClick = {
            vm.enterPassword()
        },
        enable = vm.validPasswordState.value.isAllValid() &&
                vm.confirmPassword.value == when (vm.isRestorePassword.value) {
            true -> vm.forgetState.value.password
            false -> vm.signUpState.value.password
        }
    )
}

@Composable
private fun Fields(
    vm: SignUpViewModel
){
    PasswordTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 40.dp)
            .fillMaxWidth(),
        value = when(vm.isRestorePassword.value){
            true -> vm.forgetState.value.password
            false -> vm.signUpState.value.password
                                          },
        onValueChange = { vm.editPassword(it) },
    )

    PasswordTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 22.dp)
            .fillMaxWidth(),
        label = stringResource(R.string.CONFIRM_PASSWORD),
        value = vm.confirmPassword.value,
        onValueChange = { vm.confirmPassword.value = it },
        error = when(
            when(vm.isRestorePassword.value){
                true -> vm.forgetState.value.password
                false -> vm.signUpState.value.password
            } == vm.confirmPassword.value){
                true -> ""
                false -> stringResource(R.string.PASSWORD_MISMATCH)
            }
    )
}

@Composable
private fun SecurityText() = Text(
    modifier = Modifier.padding(
        horizontal = 30.dp,
        vertical = 8.dp
    ),
    text = stringResource(R.string.SIGNUP_PASSWORD_DESC),
    style = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight(400),
        color = gray300,
        textAlign = TextAlign.Justify,
    )
)

@Composable
private fun ValidChecks(
    validState: ValidPasswordState
) = Column(
    modifier = Modifier.padding(start = 40.dp)
){
    CheckText(
        label = stringResource(R.string.SIGNUP_SYMBOLS_COUNT),
        checked = validState.isLengthCase
    )
    CheckText(
        label = stringResource(R.string.SIGNUP_SYMBOLS_CAPITAL),
        checked = validState.isUpperCase
    )
    CheckText(
        label = stringResource(R.string.SIGNUP_SYMBOLS_LOWERCASE),
        checked = validState.isLowerCase
    )
    CheckText(
        label = stringResource(R.string.SIGNUP_NUMBERS),
        checked = validState.isNumberCase
    )
    CheckText(
        label = stringResource(R.string.SIGNUP_SPECIAL),
        checked = validState.isSpecialCharacter
    )
}

@Composable
private fun CheckText(
    label: String,
    checked: Boolean
) = Row(
    modifier = Modifier.padding(vertical = 4.dp)
) {
    WethmCheckBox(
        checked = checked,
        onCheckedChange = {},
        useBorder = false
    )
    Text(
        modifier = Modifier.padding(start = 10.dp),
        text = label,
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 26.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = when(checked){
                true -> gray100
                false -> gray600
            },
            textAlign = TextAlign.Justify,
            textDecoration = when(checked){
                true -> TextDecoration.LineThrough
                false -> TextDecoration.None
            }
        )
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

@Preview
@Composable
private fun PreviewEnteringPassword() = WethmTheme{
    EnteringPassword(SignUpViewModel(rememberNavController()),{},{},{})
}