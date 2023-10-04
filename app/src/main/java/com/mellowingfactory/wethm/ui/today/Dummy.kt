package com.mellowingfactory.wethm.ui.today

data class StatisticsResponse(
    var created: String?,
    var sleepStages: SleepStageResult,
    var radarValues: List<List<Int>>,
    var heartRate: SignalResult,
    var breathingRate: SignalResult,
    var sleepQuality: List<Int>,
    var sleepLatency: List<Int>,
    var lightDuration: List<Int>,
    var sleepEfficiency: List<Int>,
    var deepDuration: List<Int>,
    var remDuration: List<Int>,
    var wokenDuration: List<Int>,
    var wakeUpState: List<Int>,
    var updated: String?,
    var percentageChangeRadar: List<Double>,
    var sleepDebt: List<Int>,

    var temperature: SignalResult,
    var humidity: SignalResult,
    var audio: SignalResult,
    var bcgRange: SignalResult?,
    var recommendations: List<Recommendation?>,
)

data class SleepStageResult(
    var sleepStart: List<Int>,
    var sleepEnd: List<Int>,
    var sleepDuration: List<Int>,
    var sleepStages: List<List<Int>>
)

data class SignalResult(
    var values: List<Double>,
    var variability: List<Double>,
    var maxValue: List<Double>,
    var minValue: List<Double>,
    var average: List<Double>
)

data class Recommendation(
    var priority: Int,
    var recommendation: String,
    var token: String
)

val rawdummyJ =
    "000000000000000000000233333332333333333333300002223222344444422222222221122223332223333232112112222023332223333232111222000002222000222020221111102233333333200"
val rawdummy1 =
    "00000000000000000000023333333233333333333330000222322233212222222112222023332223333232111222000002222000222020221111102233333333200"
val rawdummy2 =
    "0000000000023003230000100002300000001110022223330333002110222222333333021110022023222232333212101000000000000"
val rawdummy3 =
    "000000000000000022222320222222022211110022222222332222222202202222222111110000000222222222222211111112222222222222211122222022002221100"
val rawdummy4 =
    "00000000000222333333333333333300221212222220002222222233333330211112222222200223330022222022220222210000000000022111102"
val rawdummy5 =
    "00000000022222233300002222112233333333333333222111102222222232222232301110000002233332321112220000022220002220202211111022330222222232211110000000222222200200000000"
val rawdummy6 = "0"
val rawdummy7 =
    "000000000000000000002222323322232202220222202223333202202222202222223232322322211111111212222222222000000000022221123333333221111220200"

val dummyJ = rawdummyJ.map { it.digitToInt() }
val dummy1 = rawdummy1.map { it.digitToInt() }
val dummy2 = rawdummy2.map { it.digitToInt() }
val dummy3 = rawdummy3.map { it.digitToInt() }
val dummy4 = rawdummy4.map { it.digitToInt() }
val dummy5 = rawdummy5.map { it.digitToInt() }
val dummy6 = rawdummy6.map { it.digitToInt() }
val dummy7 = rawdummy7.map { it.digitToInt() }


val ellieStages = SleepStageResult(
    sleepStart = listOf(820, 882, 882, 882, 882, 787, 932),
    sleepEnd = listOf(40, 1364, 1380, 1320, 1290, 1440, 1386),
    sleepDuration = listOf(678, 306, 299, 250, 430, 636, 454),
    sleepStages = listOf(dummyJ, dummy2, dummy3, dummy4, dummy2, dummy5, dummy7)
)

val ellieRadar =
    listOf(
        listOf(70, 95, 98, 73, 90),
        listOf(89, 87, 95, 80, 99),
        listOf(70, 95, 98, 73, 90),
        listOf(70, 100, 100, 73, 90),
        listOf(70, 100, 100, 73, 90),
        listOf(85, 95, 98, 73, 90),
        listOf(70, 95, 98, 73, 90),
        listOf(85, 85, 85, 85, 85),
    )

val ellieStatHeart = SignalResult(
    values = listOf(55, 64, 53, 51, 49, 69, 56).map { it.toDouble() },
    variability = listOf(),
    maxValue = listOf(58, 63, 59, 60, 52, 54, 70).map { it.toDouble() },
    minValue = listOf(50, 55, 50, 49, 46, 50, 52).map { it.toDouble() },
    average = listOf(54, 54, 54, 54, 54, 54, 54).map { it.toDouble() },
)

val ellieStatBreathing = SignalResult(
    values = listOf(14, 14, 14, 14, 14, 14, 20).map { it.toDouble() },
    variability = listOf(0, 2.1, 1.8, 1.6, 1.7, 2, 1.9).map { it.toDouble() },
    maxValue = listOf(13, 18, 17, 17, 23, 16, 17).map { it.toDouble() },
    minValue = listOf(8, 7, 7, 8, 7, 13, 11).map { it.toDouble() },
    average = listOf(10, 12, 11, 12, 17, 14, 14).map { it.toDouble() },
)

val dummyaudio = SignalResult(
    values = listOf(9, 14, 12, 12, 12, 13, 12).map { it.toDouble() },
    variability = listOf(1, 2, 3, 3, 4, 4, 2).map { it.toDouble() },
    maxValue = listOf(17, 10, 9, 4, 8, 13, 12).map { it.toDouble() },
    minValue = listOf(5, 8, 6, 3, 4, 4, 7).map { it.toDouble() },
    average = listOf(12, 12, 12, 12, 12, 12, 12).map { it.toDouble() },
)

val dummyhumidity = SignalResult(
    values = listOf(22, 33, 24, 44, 52, 40, 50).map { it.toDouble() },
    variability = listOf(1, 1, 1, 1, 1, 1, 1).map { it.toDouble() },
    maxValue = listOf(26, 35, 29, 46, 55, 43, 53).map { it.toDouble() },
    minValue = listOf(20, 30, 20, 40, 50, 37, 45).map { it.toDouble() },
    average = listOf(34, 34, 34, 34, 34, 34, 34).map { it.toDouble() },
)

val dummytemperature = SignalResult(
    values = listOf(21, 21, 21, 21, 23, 23, 23).map { it.toDouble() },
    variability = listOf(1, 1, 1, 1, 1, 1, 1).map { it.toDouble() },
    maxValue = listOf(22, 24, 24, 22, 23, 21, 22).map { it.toDouble() },
    minValue = listOf(21, 23, 22, 21, 21, 21, 21).map { it.toDouble() },
    average = listOf(23, 23, 23, 23, 23, 23, 23).map { it.toDouble() },
)

val ellie = StatisticsResponse(
//    id = presentationAccount,
    created = "2023-03-11T06:39:19.661Z",


    lightDuration = listOf(67, 60, 74, 83, 0, 86, 75),


    deepDuration = listOf(67, 60, 74, 83, 86, 0, 75),
    remDuration = listOf(89, 87, 95, 80, 0, 90, 99),
    wokenDuration = listOf(20, 25, 30, 20, 0, 30, 10),
    wakeUpState = listOf(0, 0, 0, 0, 0, 0, 0),
    updated = "2023-03-13T06:39:19.661Z",
    percentageChangeRadar = listOf(10, -10, -10, 10, -3).map { it.toDouble() },
    bcgRange = null,
    recommendations = listOf(),


    /**
     * Graphics
     */
    radarValues = ellieRadar,
    /**
     *
     */
    sleepStages = ellieStages,
    sleepDebt = listOf(138, -105, -96, -95, 0, 96, -72),
    sleepQuality = listOf(100, 95, 90, 85, 85, 75, 89),
    sleepLatency = listOf(7, 18, 5, 6, 0, 17, 19),
    sleepEfficiency = listOf(96, 97, 98, 97, 0, 90, 97),

    /**
     * BOTTOMSHEET
     */
    temperature = dummytemperature,
    humidity = dummyhumidity,
    audio = dummyaudio,
    breathingRate = ellieStatBreathing,
    heartRate = ellieStatHeart,
)



