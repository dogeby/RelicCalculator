package com.dogeby.reliccalculator.core.domain.preference

import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import javax.inject.Inject

class GetCharacterSortFieldsUseCase @Inject constructor() {

    operator fun invoke() = CharacterSortField.entries.toSet()
}
