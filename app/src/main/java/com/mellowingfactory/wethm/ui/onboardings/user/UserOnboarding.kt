package com.mellowingfactory.wethm.ui.onboardings.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.extentions.AuthLayout
import com.mellowingfactory.wethm.ui.extentions.ButtonGradient
import com.mellowingfactory.wethm.ui.extentions.WhilePickerDialog
import com.mellowingfactory.wethm.ui.onboardings.user.entities.Genders
import com.mellowingfactory.wethm.ui.onboardings.user.extentions.DropDownContainer
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.defaultTextGradient
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.utils.textBrush
import java.util.Calendar

@Composable
fun UserOnboarding(
    onUserOnboarded: () -> Unit
) = AuthLayout(
    hasSkip = true,
    hasBack = false,
    showBranding = false,
    onSkipClick = onUserOnboarded,
    headerContent = { Header() }
){
    val vm = remember { UserOnboardingViewModel() }
    MainContent(vm)
    ButtonStart{ vm.saveData(onUserOnboarded) }
    Dialogs(vm)
}

@Composable
private fun BoxScope.Header() = Column(
    modifier = Modifier.align(Alignment.BottomCenter),
    horizontalAlignment = Alignment.CenterHorizontally
){
    Image(
        modifier = Modifier.width(42.dp),
        painter = painterResource(R.drawable.branding_icon),
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
    Image(
        modifier = Modifier
            .padding(top = 16.dp)
            .width(150.dp),
        painter = painterResource(R.drawable.logos),
        contentScale = ContentScale.FillWidth,
        colorFilter = ColorFilter.tint(white),
        contentDescription = null
    )
    val sloganBrush = Brush.horizontalGradient(defaultTextGradient)
    Text(
        modifier = Modifier
            .textBrush(sloganBrush)
            .padding(
                top = 16.dp,
                bottom = 48.dp
            ),
        text = stringResource(R.string.SLOGAN),
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center,
        )
    )
}

@Composable
private fun Dialogs(
    vm: UserOnboardingViewModel
){
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val maxYear = currentYear - 3
    val minYear = currentYear - 150
    WhilePickerDialog(
        isOpen = vm.showBirthYear,
        list = (minYear..maxYear).toList().map { it.toString() },
        suffix = "",
        onSelect = {
            vm.updateUserState(
                birthYear = it
            )
        }
    )
    WhilePickerDialog(
        isOpen = vm.showGender,
        list = Genders.values().toList().map{ stringResource(it.label) },
        suffix = "",
        onSelect = {
            vm.updateUserState(
                gender = it
            )
        }
    )
    WhilePickerDialog(
        isOpen = vm.showWeight,
        list = (20..200).toList().map { it.toString() },
        suffix = stringResource(R.string.KG),
        onSelect = {
            vm.updateUserState(
                weight = it
            )
        }
    )
    WhilePickerDialog(
        isOpen = vm.showHeight,
        list = (50..300).toList().map { it.toString() },
        suffix = stringResource(R.string.CM),
        onSelect = {
            vm.updateUserState(
                height = it
            )
        }
    )
}

@Composable
private fun MainContent(
    vm: UserOnboardingViewModel
) = Column{
    Text(
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(horizontal = 30.dp),
        text = stringResource(R.string.USER_ONB_HINT),
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = gray300,
            textAlign = TextAlign.Justify,
        )
    )
    DropDownContainer(
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(horizontal = 22.dp)
            .fillMaxWidth(),
        text = vm.userState.value.birthYear,
        label = stringResource(R.string.BIRTH_YEAR),
        onClick = {vm.showBirthYear.value = true}
    )
    DropDownContainer(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 22.dp)
            .fillMaxWidth(),
        text = vm.userState.value.gender,
        label = stringResource(R.string.GENDER),
        onClick = { vm.showGender.value = true }
    )
    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 22.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropDownContainer(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            text = vm.userState.value.weight,
            label = stringResource(R.string.KG),
            suffix = stringResource(R.string.KG),
            onClick = {vm.showWeight.value = true}
        )
        Spacer(modifier = Modifier.width(16.dp))
        DropDownContainer(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            text = vm.userState.value.height,
            label = stringResource(R.string.CM),
            suffix = stringResource(R.string.CM),
            onClick = {vm.showHeight.value = true}
        )
    }
    Text(
        modifier = Modifier
            .padding(top = 22.dp)
            .padding(horizontal = 30.dp),
        text = stringResource(R.string.USER_ONB_DESC),
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = gray300,
            textAlign = TextAlign.Justify
        )
    )
}

@Composable
private fun BoxScope.ButtonStart(
    onClick: () -> Unit
) = ButtonGradient(
    modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 20.dp)
        .navigationBarsPadding()
        .fillMaxWidth()
        .height(54.dp)
        .align(Alignment.BottomCenter),
    text = stringResource(R.string.START),
    onClick = onClick
)

@Preview
@Composable
private fun PreviewUserOnboarding() = WethmTheme{
    UserOnboarding({})
}
