package com.dogeby.reliccalculator.core.model.preset

enum class ComparisonOperator(val symbol: String) {
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL_TO(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL_TO("<="),
    ;

    companion object {
        fun ComparisonOperator.performComparison(
            attrValue: Double,
            comparedValue: Double,
        ): Boolean {
            return when (this) {
                GREATER_THAN -> attrValue > comparedValue
                GREATER_THAN_OR_EQUAL_TO -> attrValue >= comparedValue
                LESS_THAN -> attrValue < comparedValue
                LESS_THAN_OR_EQUAL_TO -> attrValue <= comparedValue
            }
        }
    }
}
