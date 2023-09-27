package com.mellowingfactory.wethm.ui.terms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.extentions.AuthLayout
import com.mellowingfactory.wethm.ui.extentions.WethmButton
import com.mellowingfactory.wethm.ui.extentions.WethmCheckBox
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.gray600
import com.mellowingfactory.wethm.ui.theme.gray900
import com.mellowingfactory.wethm.ui.theme.green400
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.utils.openBrowser

@Composable
fun Terms(
    onReadyTerms: () -> Unit
) = AuthLayout(
    headerContent = { Header() }
){
    val vm = remember { TermsViewModel(onReadyTerms) }
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .padding(top = 40.dp),
            text = stringResource(R.string.TERMS_DESC),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(400),
                color = gray300,
                textAlign = TextAlign.Justify,
            )
        )
        Checks(vm)
    }
    ButtonNext(
        enable = with(vm.termsState.value){
            termsAndCondition && privacyPolicy
        },
        onNext = { vm.saveTerms() }
    )
}

@Composable
private fun BoxScope.ButtonNext(
    enable: Boolean,
    onNext: () -> Unit
) = WethmButton(
    modifier = Modifier
        .align(Alignment.BottomCenter)
        .navigationBarsPadding()
        .padding(bottom = 20.dp),
    enable = enable,
    onClick = onNext,
    text = stringResource(id = R.string.AGREE)
)

@Composable
private fun Checks(
    vm: TermsViewModel
){
    CheckText(
        modifier = Modifier.padding(
            start = 30.dp,
            top = 22.dp,
            bottom = 30.dp
        ),
        text = stringResource(R.string.TERMS_ALL),
        isChecked = vm.termsState.value.isAllAccept(),
        onCheck = {
            vm.updateTermsState(allAccept = it)
        }
    )
    CheckText(
        modifier = Modifier.padding(
            start = 30.dp,
            bottom = 22.dp
        ),
        text = stringResource(R.string.TERMS_AND_CONDITIONS),
        link = stringResource(R.string.terms_of_service_url),
        isChecked = vm.termsState.value.termsAndCondition,
        onCheck = {
            vm.updateTermsState(termsAndCondition = it)
        }
    )
    CheckText(
        modifier = Modifier.padding(
            start = 30.dp,
            bottom = 22.dp
        ),
        text = stringResource(R.string.TERMS_PRIVACY),
        link = stringResource(R.string.privacy_policy_url),
        isChecked = vm.termsState.value.privacyPolicy,
        onCheck = {
            vm.updateTermsState(privacyPolicy = it)
        }
    )
    CheckText(
        modifier = Modifier.padding(
            start = 30.dp,
            bottom = 22.dp
        ),
        text = stringResource(R.string.TERMS_DATA),
        desc = stringResource(R.string.TERMS_DATA_DESC),
        link = stringResource(R.string.privacy_policy_url),
        isChecked = vm.termsState.value.userDataPolicy,
        onCheck = {
            vm.updateTermsState(userDataPolicy = it)
        }
    )
    CheckText(
        modifier = Modifier.padding(
            start = 30.dp,
            bottom = 22.dp
        ),
        text = stringResource(R.string.TERMS_MARKETING),
        desc = stringResource(R.string.TERMS_MARKETING_DESC),
        link = stringResource(R.string.terms_of_sale_url),
        isChecked = vm.termsState.value.marketingPolicy,
        onCheck = {
            vm.updateTermsState(marketingPolicy = it)
        }
    )
}

private val linkText = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
    fontWeight = FontWeight(400),
    color = green400,
    textDecoration = TextDecoration.Underline,
)

private val defaultText = TextStyle(
    fontSize = 18.sp,
    lineHeight = 42.sp,
    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
    fontWeight = FontWeight(500),
    color = gray900,
)

@Composable
private fun CheckText(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    link: String = "",
    desc: String = "",
    onCheck: (Boolean) -> Unit
) = Row(modifier = modifier) {
    val context = LocalContext.current
    WethmCheckBox(
        checked = isChecked,
        onCheckedChange = onCheck
    )
    Spacer(Modifier.width(12.dp))
    Column(
        modifier = Modifier
            .clickable {
                when(link.isEmpty()){
                    true -> onCheck(!isChecked)
                    false -> openBrowser(link, context)
                }
            },
    ) {
        Text(
            text = text,
            style = when(link.isEmpty()){
                true -> defaultText
                false -> linkText
            }
        )
        if (desc.isNotEmpty()){
            Text(
                text = text,
                style = defaultText.copy(
                    fontSize = 14.sp,
                    color = gray600
                )
            )
        }
    }


}

@Composable
private fun BoxScope.Header() = Text(
    modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(
            start = 30.dp,
            bottom = 22.dp
        ),
    text = stringResource(R.string.TERMS_HINT),
    style = TextStyle(
        fontSize = 18.sp,
        lineHeight = 23.sp,
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight(300),
        color = white,
    )
)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewTerms() = WethmTheme{
    Terms{}
}