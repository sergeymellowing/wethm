package com.mellowingfactory.wethm.ui.auth.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amplifyframework.auth.AuthProvider
import com.mellowingfactory.wethm.BuildConfig
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.services.auth.entities.SignInState
import com.mellowingfactory.wethm.ui.extentions.ButtonGradient
import com.mellowingfactory.wethm.ui.extentions.PasswordTextField
import com.mellowingfactory.wethm.ui.extentions.WethmCheckBox
import com.mellowingfactory.wethm.ui.extentions.WethmTextField
import com.mellowingfactory.wethm.ui.theme.blue400
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.gray500
import com.mellowingfactory.wethm.ui.theme.red400
import kotlinx.coroutines.CoroutineScope

@Composable
fun SignIn(
    goSignUp: () -> Unit,
    goRestorePassword: () -> Unit,
    onSignIn: (scope: CoroutineScope) -> Unit
){
    val scope = rememberCoroutineScope()
    val vm = remember {
        SignInViewModel(
            authSuccessful = { onSignIn(scope) }
        )
    }
    val signInState = vm.signInState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Header(
            message = vm.message
        )
        Fields(
            state = signInState,
            onUpdateEmail = {
                vm.updateLoginState(username = it)
            },
            onUpdatePassword = {
                vm.updateLoginState(password = it)
            }
        )
        Checks(
            isRememberMeState = vm.isRememberMeState,
            isAutoSignInState = vm.isAutoSignState,
            onRememberMeUpdate = {
                vm.updateRememberState(isRememberMe = it)
                                 },
            onAutoSignInUpdate = {
                vm.updateAutoSignState(isAutoSign = it)
            }
        )

        ButtonGradient( //Login Button
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .padding(horizontal = 16.dp),
            enable = vm.isValidEmail.value && vm.validPasswordState.value.isLengthCase,
            text = stringResource(R.string.SIGNIN),
            onClick = { vm.login() }
        )

        Text( //Forgot password button
            modifier = Modifier
                .align(Alignment.End)
                .padding(
                    vertical = 22.dp,
                    horizontal = 16.dp
                )
                .clickable { goRestorePassword() },
            text = stringResource(R.string.FORGOT_PASSWORD),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(400),
                color = gray300
            ),
        )

        val ctx = LocalContext.current
        OtherSignInMethods(
            onLoginWithGoogle = {
                vm.loginWithProvider(AuthProvider.google(), ctx)
            },
            onLoginWithApple = {
                vm.loginWithProvider(AuthProvider.apple(), ctx)
            }
        )

        SignUpButton {
             goSignUp()
        }

        Text( //Info Version
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = BuildConfig.VERSION_NAME,
            fontSize = 12.sp,
            color = gray300
        )
    }
}

@Composable
private fun Header(
    message: MutableState<String>
) = Column(
    modifier = Modifier.padding(
        start = 30.dp,
        top = 70.dp,
        bottom = 70.dp
    )
){
    Image(
        painter = painterResource(id = R.drawable.logos),
        contentDescription = null,
        modifier = Modifier
            .width(140.dp)
            .height(32.dp),

        contentScale = ContentScale.Fit
    )
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = message.value,
        color = red400,
    )

}


@Composable
private fun Fields(
    state: MutableState<SignInState>,
    onUpdateEmail: (email: String) -> Unit,
    onUpdatePassword: (password: String) -> Unit
) = Column{

    WethmTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = state.value.email,
        onValueChange = onUpdateEmail,
        label = stringResource(R.string.EMAIL),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    Spacer(modifier = Modifier.height(15.dp))

    PasswordTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value.password,
        onValueChange = onUpdatePassword
    )
}

@Composable
private fun Checks(
    isRememberMeState: MutableState<Boolean>,
    isAutoSignInState: MutableState<Boolean>,
    onRememberMeUpdate: (isCheck: Boolean) -> Unit,
    onAutoSignInUpdate: (isCheck: Boolean) -> Unit
){
    Row(
        modifier = Modifier.padding(
            top = 36.dp,
            bottom = 22.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Check Remember me
        WethmCheckBox(
            modifier = Modifier.padding(start = 30.dp, end = 8.dp),
            checked = isRememberMeState.value,
            onCheckedChange = onRememberMeUpdate
        )
        Text(
            text = stringResource(R.string.REMEMBER_ME),
            color = gray500
        )

        //Check Auto SignIn
        WethmCheckBox(
            Modifier.padding(start = 16.dp, end = 8.dp),
            checked = isAutoSignInState.value,
            onCheckedChange = onAutoSignInUpdate
        )
        Text(
            text = stringResource(R.string.AUTO_SIGN_IN),
            color = gray500
        )
    }
}

@Composable
private fun OtherSignInMethods(
    onLoginWithGoogle: () -> Unit,
    onLoginWithApple: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Divider(
            Modifier.fillMaxWidth()
        )

        Text(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            text = stringResource(R.string.THIRD_PARTY),
            color = gray300,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onLoginWithGoogle) {
            Image(
                painter = painterResource(R.drawable.google_icon),
                contentDescription = null
            )
        }
        IconButton(onClick = onLoginWithApple) {
            Image(
                painter = painterResource(R.drawable.apple_icon),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun SignUpButton(
    onClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.SIGNUP_Q),
            color = gray300
        )

        TextButton(
            onClick = onClick
        ) {
            Text(
                text = stringResource(R.string.SIGNUP_B),
                color = blue400,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewSignIn(){
    SignIn({},{},{})
}