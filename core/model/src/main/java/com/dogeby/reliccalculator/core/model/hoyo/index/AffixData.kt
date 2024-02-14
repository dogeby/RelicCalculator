package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class AffixData(
    val id: String,
    val affixes: Map<String, AffixInfo>,
)

@TestOnly
val sampleAffixData = AffixData(
    id = "5",
    affixes = mapOf(
        "1" to AffixInfo("1", "HPDelta", 33.870039001107216, 4.23375500086695, 2),
        "2" to AffixInfo("2", "AttackDelta", 16.935019000666216, 2.116877000778913, 2),
        "3" to AffixInfo("3", "DefenceDelta", 16.935019000666216, 2.116877000778913, 2),
        "4" to AffixInfo("4", "HPAddedRatio", 0.034560000523925, 0.004320000065491, 2),
        "5" to AffixInfo("5", "AttackAddedRatio", 0.034560000523925, 0.004320000065491, 2),
        "6" to AffixInfo("6", "DefenceAddedRatio", 0.043199999956414, 0.00539999990724, 2),
        "7" to AffixInfo("7", "SpeedDelta", 2.0, 0.300000000279397, 2),
        "8" to AffixInfo("8", "CriticalChanceBase", 0.025920000392944, 0.003240000223741, 2),
        "9" to AffixInfo("9", "CriticalDamageBase", 0.051840000785887, 0.006480000447482, 2),
        "10" to AffixInfo("10", "StatusProbabilityBase", 0.034560000523925, 0.004320000065491, 2),
        "11" to AffixInfo("11", "StatusResistanceBase", 0.034560000523925, 0.004320000065491, 2),
        "12" to
            AffixInfo("12", "BreakDamageAddedRatioBase", 0.051840000785887, 0.006480000447482, 2),
    ),
)
